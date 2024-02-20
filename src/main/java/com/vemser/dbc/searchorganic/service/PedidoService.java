package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.repository.PedidoXProdutoRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoXProdutoRepository pedidoXProdutoRepository;
    private final ObjectMapper objectMapper;
    private final EnderecoService enderecoService;
    private final CupomService cupomService;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ProdutoService produtoService;
    private final List<IValidarPedido> validarPedidoList;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaService empresaService;
    private final BigDecimal TAXA_SERVICO = new BigDecimal(5);

    public Pedido findById(Integer id) throws Exception {
        return pedidoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado: " + id));
    }


    public PedidoDTO getById(Integer idPedido) throws Exception {
        Pedido pedido = findById(idPedido);
        Integer idUsuarioPedido = pedido.getUsuario().getIdUsuario();

        if(!(verificarSeAdminOuUsuario(idUsuarioPedido))){
            throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
        }
        return retornarDto(pedido);
    }

    public void cancelarPedido(Integer idPedido) throws Exception {
        PedidoDTO pedidoDto = getById(idPedido);
        Pedido pedido = objectMapper.convertValue(pedidoDto, Pedido.class);
        Integer idUsuarioPedido = pedido.getUsuario().getIdUsuario();
        Integer idEmpresaPedido = pedido.getEmpresa().getIdUsuario();

        if(!verificarSeEmpresaAdminUsuario(idUsuarioPedido, idEmpresaPedido)){
            throw new RegraDeNegocioException("Você não tem permissão pra cancelar este pedido");
        }

        List<PedidoXProduto> produtos = pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido());
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();
            produto.setQuantidade(produto.getQuantidade().add(BigDecimal.valueOf(pedidoXProduto.getQuantidade())));
            produtoRepository.save(produto);
        }

        pedidoRepository.cancelarPedido(idPedido);

    }

    public List<PedidoDTO> findAll() throws Exception {
        Usuario admin = obterAdmin();
        Integer idUsuarioLogado = usuarioService.getIdLoggedUser();
        if(!(admin.getIdUsuario().equals(idUsuarioLogado))){
            throw new RegraDeNegocioException("Apenas o administrador pode acessar este recurso");
        }
        return pedidoRepository.findAll().stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
    }

    public PedidoDTO save(Integer idUsuario, PedidoCreateDTO pedidoCreateDTO) throws Exception {
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        List<Produto> produtosBanco = new ArrayList<>();

        List<PedidoXProduto> produtos = obterProdutos(pedidoCreateDTO.getProdutosCarrinho(), produtosBanco);
        Usuario usuario = usuarioService.obterUsuarioPorId(idUsuario);
        if(verificarSeAdminOuUsuario(idUsuario)){
            throw new RegraDeNegocioException("Você não tem permissão pra acessar este recurso");
        }

        Endereco endereco = enderecoService.getById(pedidoCreateDTO.getIdEndereco());
        Empresa empresa = empresaService.getById(pedidoCreateDTO.getIdEmpresa());

        pedido.setEndereco(endereco);
        pedido.setUsuario(usuario);
        pedido.setEmpresa(empresa);

        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomService.getById(pedidoCreateDTO.getIdCupom());
            pedido.setCupom(cupom);
        }
        if (this.validarPedidoList != null) {
            for (IValidarPedido validador : validarPedidoList) {
                validador.validar(pedido, usuario.getIdUsuario(), produtos);
            }
        }

        pedido = pedidoRepository.save(pedido);

        for (PedidoXProduto pedidoXProduto : produtos) {
            pedidoXProduto.setPedido(pedido);
            Produto produto = pedidoXProduto.getProduto();
            pedidoXProduto.setProdutoXPedidoPK(new ProdutoXPedidoPK(produto.getIdProduto(), pedido.getIdPedido()));
            pedidoXProdutoRepository.save(pedidoXProduto);
            BigDecimal quantidadeAtualizada = produto.getQuantidade().subtract(BigDecimal.valueOf(pedidoXProduto.getQuantidade()));
            pedidoXProdutoRepository.updateQuantidadeProduto(produto.getIdProduto(), quantidadeAtualizada);
        }

        PedidoDTO pedidoDTO = this.retornarDto(pedido);

        this.emailPedidoCriado(pedidoDTO, usuario);

        return pedidoDTO;
    }

    public PedidoDTO update(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = findById(id);

        if(!verificarSeAdminOuUsuario(pedidoEntity.getUsuario().getIdUsuario())){
            throw new RegraDeNegocioException("Você não tem permissão pra editar este pedido");
        }


        Endereco endereco = enderecoService.getById(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setEndereco(endereco);

        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());

        pedidoRepository.save(pedidoEntity);

        return retornarDto(pedidoEntity);
    }






    public List<PedidoDTO> findAllByIdUsuario(Integer idUsuario) throws Exception {
         if(!verificarSeAdminOuUsuario(idUsuario)){
             throw new RegraDeNegocioException("Você não tem permissão pra acessar este recurso");
         }

        List<Pedido> pedidos = pedidoRepository.findAllByUsuario_IdUsuario(idUsuario);

        ArrayList<PedidoDTO> pedidoDtos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = retornarDto(pedido);
            pedidoDtos.add(pedidoDTO);
        }

        return pedidoDtos;

    }

    public List<PedidoXProduto> obterProdutos(ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate, List<Produto> produtosBanco) throws Exception {
        List<PedidoXProduto> produtos = new ArrayList<>();
        for (ProdutoCarrinhoCreate produtoCarrinhoCreate : produtosCarrinhoCreate) {
            ProdutoDTO produtoDto = produtoService.findById(produtoCarrinhoCreate.getIdProduto());
            Produto produto =  objectMapper.convertValue(produtoDto,Produto.class);
            produtosBanco.add(produto);
            PedidoXProduto pedidoXProduto = new PedidoXProduto();
            pedidoXProduto.setProduto(produto);
            pedidoXProduto.setQuantidade(produtoCarrinhoCreate.getQuantidade());
            produtos.add(pedidoXProduto);

        }
        return produtos;

    }


    public PedidoDTO pagamento(Integer idPedido, PagamentoDTO pagamentoDTO) throws Exception {

        Pedido pedido = findById(idPedido);

        if(!verificarSeAdminOuUsuario(pedido.getUsuario().getIdUsuario())){
            throw new RegraDeNegocioException("Você não tem permissão pra acessar este recurso");
        }

        if (pedido.getStatusPedido() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RegraDeNegocioException("Este pedido não está aguardando pagamento");
        }

        Usuario admin = obterAdmin();

        Usuario usuario = pedido.getUsuario();

        BigDecimal total = pedido.getPrecoCarrinho().add(pedido.getPrecoFrete());


        Carteira carteiraAdmin = admin.getCarteira();
        Carteira carteiraUsuario = usuario.getCarteira();

        if (pagamentoDTO.getFormaPagamento() == FormaPagamento.CREDITO || pagamentoDTO.getFormaPagamento() == FormaPagamento.DEBITO) {
            //Simulando pagamento com cartão, adicionando saldo na conta
            if(pagamentoDTO.getNumeroCartao() == null || pagamentoDTO.getCvv() == null || pagamentoDTO.getDataValidade() == null){
                throw new RegraDeNegocioException("Cartão inválido");
            }
            usuario.getCarteira().setSaldo(carteiraUsuario.getSaldo().add(total));
        }

        efetuarTransferencia(carteiraUsuario, carteiraAdmin, total);

        pedido.setStatusPedido(StatusPedido.PAGO);
        pedido.setFormaPagamento(pagamentoDTO.getFormaPagamento());

        usuarioRepository.save(usuario);
        usuarioRepository.save(admin);
        pedidoRepository.save(pedido);

        return retornarDto(pedido);

    }

    public PedidoEmpresaDTO updatePedidoStatus(Integer idPedido, StatusPedido novoStatus, Integer idEmpresa) throws Exception {
        Pedido pedido = findById(idPedido);
        if(!verificarSeEmpresa(pedido.getEmpresa().getIdUsuario())){
            throw  new RegraDeNegocioException("Sua empresa não é responsável por este pedido");
        }
        return updateStatus(pedido, novoStatus, idEmpresa);

    }

    public PedidoEmpresaDTO updateStatus(Pedido pedido, StatusPedido novoStatus, Integer idEmpresa) throws Exception {
        pedido.setStatusPedido(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        PedidoEmpresaDTO pedidoDTO = convertToPedidoDTO(pedidoAtualizado, idEmpresa);

        return pedidoDTO;
    }

    private PedidoEmpresaDTO convertToPedidoDTO(Pedido pedido, Integer idEmpresa) {
        PedidoEmpresaDTO pedidoDTO = new PedidoEmpresaDTO();
        pedidoDTO.setIdPedido(pedido.getIdPedido());
        pedidoDTO.setStatusPedido(pedido.getStatusPedido());
        pedidoDTO.setIdEmpresa(idEmpresa);

        return pedidoDTO;
    }


    public PedidoDTO entregue(Integer idPedido) throws Exception {
        Pedido pedido = findById(idPedido);

        if(!verificarSeAdminOuUsuario(pedido.getUsuario().getIdUsuario())){
            throw new RegraDeNegocioException("Você não tem permissão pra acessar este recurso");
        }

        if (pedido.getStatusPedido() != StatusPedido.A_CAMINHO) {
            throw new RegraDeNegocioException("Pedido não enviado ou cancelado");
        }

        Integer idUsuarioEmpresa = pedido.getEmpresa().getIdUsuario();

        Usuario empresa = usuarioService.obterUsuarioPorId(idUsuarioEmpresa);
        Usuario admin = obterAdmin();

        BigDecimal total = pedido.getPrecoCarrinho().add(pedido.getPrecoFrete());

        BigDecimal porcentagemTaxaServico = total.multiply(TAXA_SERVICO).divide(new BigDecimal("100"));

        BigDecimal valorFinal = total.subtract(porcentagemTaxaServico);


        efetuarTransferencia(admin.getCarteira(), empresa.getCarteira(), valorFinal);

        pedido.setStatusPedido(StatusPedido.ENTREGUE);
        return retornarDto(pedido);
    }

    public PedidoRastreioDTO updateCodigoDeRastreio(Integer idPedido, String codigoRastreio) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado"));

        if(!verificarSeEmpresa(pedido.getEmpresa().getIdUsuario())){
            throw  new RegraDeNegocioException("Sua empresa não é responsável por este pedido");
        }

        pedido.setCodigoDeRastreio(codigoRastreio);

        if (pedido.getCodigoDeRastreio() != null) {
            pedido.setStatusPedido(StatusPedido.A_CAMINHO);
        }else{
            pedido.setStatusPedido(StatusPedido.EM_SEPARACAO);
        }
        PedidoRastreioDTO pedidoRastreioDTO = objectMapper.convertValue(pedido, PedidoRastreioDTO.class);
        return pedidoRastreioDTO;
    }


    public void efetuarTransferencia(Carteira carteiraOrigem, Carteira carteiraDestino, BigDecimal total) throws RegraDeNegocioException {
        BigDecimal valorPendente = carteiraOrigem.getSaldo().subtract(total);
        if (valorPendente.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraDeNegocioException("SALDO INSUFICIENTE");
        }
        carteiraOrigem.setSaldo(carteiraOrigem.getSaldo().subtract(total));
        carteiraDestino.setSaldo(carteiraDestino.getSaldo().add(total));
    }
    public Boolean verificarSeAdminOuUsuario(Integer idUsuarioPedido) throws Exception {
        Integer idAdmin = obterAdmin().getIdUsuario();
        Integer idUsuarioLogado = usuarioService.getIdLoggedUser();
        if(!(idUsuarioLogado.equals(idUsuarioPedido)) && !(idUsuarioLogado.equals(idAdmin)) ){
            return false;
        }
        return true;
    }
    public Boolean verificarSeEmpresaAdminUsuario(Integer idUsuarioPedido, Integer idUsuarioEmpresa) throws Exception {
        if(verificarSeAdminOuUsuario(idUsuarioPedido)){
            return true;
        }

        return verificarSeEmpresa(idUsuarioEmpresa);
    }


    public Boolean verificarSeEmpresa(Integer idUsuarioEmpresa) throws Exception {
        Integer idUsuarioLogado = usuarioService.getIdLoggedUser();

        return idUsuarioLogado.equals(idUsuarioEmpresa);
    }


    public Usuario obterAdmin() throws Exception {
        Usuario admin = usuarioService.findByLogin("admin")
                .orElseThrow(() -> new RegraDeNegocioException("Ocorreu um erro com o administrador"));
        boolean isAdmin = admin.getCargos().stream()
                .anyMatch(cargo -> "ROLE_ADMIN".equalsIgnoreCase(cargo.getNome()));
        if (!isAdmin) {
            throw new RegraDeNegocioException("Ocorreu um erro interno");
        }

        return admin;
    }


    public PedidoDTO retornarDto(Pedido pedido) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(pedido.getUsuario());
        EnderecoDTO enderecoDTO = new EnderecoDTO(pedido.getEndereco());
        CupomDTO cupomDto = new CupomDTO(pedido.getCupom());
        EmpresaDTO empresa = new EmpresaDTO(pedido.getEmpresa());

        List<PedidoXProduto> pedidoProdutos = pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido());
        List<ProdutoPedidoDTO> produtos = new ArrayList<>();

        for (PedidoXProduto pedidoXProduto : pedidoProdutos) {
            ProdutoResponsePedidoDTO produtoDTO = objectMapper.convertValue(pedidoXProduto.getProduto(), ProdutoResponsePedidoDTO.class);

            ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(produtoDTO, pedidoXProduto.getQuantidade());

            produtos.add(produtoPedidoDTO);
        }

        PedidoDTO pedidoDTO = new PedidoDTO(pedido, usuarioDTO, enderecoDTO, cupomDto, produtos, empresa);
        return pedidoDTO;
    }

    public void emailPedidoCriado(PedidoDTO pedidoDTO, Usuario usuario) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEntrega = formato.format(pedidoDTO.getDataEntrega());
        String dataPedido = formato.format(pedidoDTO.getDataDePedido());
        String endereco = enderecoService.getMensagemEnderecoEmail(objectMapper.convertValue(pedidoDTO.getEndereco(), Endereco.class));
        String produtos = produtoService.getMensagemProdutoEmail(pedidoDTO.getProdutos());
        String mensagem = String.format("""
                        <span style="display: block; font-size: 16px; font-weight: bold;">Pedido Realizado</span>
                        <section style="color: #ffffff;">
                            <p style="color: #ffffff;">Olá %s, seu pedido foi realizado com sucesso,</p>
                            <p style="color: #ffffff;">O Status atual do seu pedido é %s,</p>
                            <p style="color: #ffffff;">Abaixo seguem detalhes do seu pedido:</p>
                            <p style="color: #ffffff;">Pedido N°: %d,</p>
                            <p style="color: #ffffff;">Total: %s,</p>
                            <p style="color: #ffffff;">Forma de pagamento: %s,</p>
                            <p style="color: #ffffff;">Data do pedido: %s,</p>
                            <p style="color: #ffffff;">Data da entrega: %s,</p>
                            <p style="color: #ffffff;">Produtos: <br> %s </p>
                            <p style="color: #ffffff;">Endereço: <br> %s </p>
                        </section>
                        """,
                usuario.getNome(),
                pedidoDTO.getStatusPedido().toString(),
                pedidoDTO.getIdPedido(),
                pedidoDTO.getTotal(),
                pedidoDTO.getFormaPagamento().toString(),
                dataPedido,
                dataEntrega,
                produtos,
                endereco
        );
        String assunto = "Pedido realizado";
        String destinatario = usuario.getEmail();
        this.emailService.sendEmail(mensagem, assunto, destinatario);
    }

}
