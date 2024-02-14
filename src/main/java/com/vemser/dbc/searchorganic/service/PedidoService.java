package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
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
        if(isEmpresa()) {

            List<Integer> pedidoIdDoEmpresaLogado = pedidoRepository.findEmpresaIdByUserId(getIdLoggedUser());
            if(pedidoIdDoEmpresaLogado.isEmpty()){
                throw new RegraDeNegocioException("não há pedidos para sua empresa.");
            }


            if (pedidoIdDoEmpresaLogado.contains(id) || isAdmin()) {
                return pedidoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado: " + id));
            } else {
                throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
            }

        }else {
            Integer pedidoIdDoUsuarioLogado = pedidoRepository.findUsuarioIdByUserId(getIdLoggedUser()).orElseThrow(() -> new RegraDeNegocioException("Endereço do usuário logado não encontrado"));

            if (pedidoIdDoUsuarioLogado.equals(id) || isAdmin()) {
                return pedidoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado: " + id));
            } else {
                throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
            }
        }
    }


    public boolean isAdmin() {
        Integer userId = getIdLoggedUser();
        Integer count = pedidoRepository.existsAdminCargoByUserId(userId);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpresa(){
        Integer userId= getIdLoggedUser();
        Integer count= pedidoRepository.existsEmpresaCargoByUserId(userId);

        if(count>0){
            return true;
        } else{
            return false;
        }
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }



    public PedidoDTO getById(Integer idPedido) throws Exception {
        return retornarDto(findById(idPedido));
    }

    public List<PedidoDTO> findAll() throws Exception {
        return pedidoRepository.findAll().stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
    }

    public PedidoDTO save(Integer id, PedidoCreateDTO pedidoCreateDTO) throws Exception {

        Integer loggedUserId = getIdLoggedUser();

        if (loggedUserId.equals(id) || isAdmin()) {
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        List<Produto> produtosBanco = new ArrayList<>();
        List<PedidoXProduto> produtos = obterProdutos(pedidoCreateDTO.getProdutosCarrinho(), produtosBanco);

        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        Endereco endereco = enderecoService.getById(pedidoCreateDTO.getIdEndereco());
        Empresa empresa = empresaService.getById(pedidoCreateDTO.getIdEmpresa());

        pedido.setEndereco(endereco);
        pedido.setUsuario(usuario);
        pedido.setEmpresa(empresa);

        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomService.getById(pedidoCreateDTO.getIdCupom());
            pedido.setCupom(cupom);
        }

        for (IValidarPedido validador : validarPedidoList) {
            validador.validar(pedido, id, produtos);
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

    } else {
        throw new RegraDeNegocioException("Apenas o usuário dono da conta ou um administrador pode remover o usuário.");
    }
    }

    public PedidoDTO update(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = findById(id);
        Endereco endereco = enderecoService.getById(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setEndereco(endereco);

        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());

        pedidoRepository.save(pedidoEntity);

        return retornarDto(pedidoEntity);
    }

    public void cancelarPedido(Integer idPedido) throws Exception {
        Integer pedidoIdDoUsuarioLogado = pedidoRepository.findUsuarioIdByUserId(getIdLoggedUser()).orElseThrow(() -> new RegraDeNegocioException("Endereço do usuário logado não encontrado"));

        if (pedidoIdDoUsuarioLogado.equals(idPedido) || isAdmin()) {
        Pedido pedido = findById(idPedido);
        List<PedidoXProduto> produtos = pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido());
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();

            produto.setQuantidade(produto.getQuantidade().add(BigDecimal.valueOf(pedidoXProduto.getQuantidade())));

            produtoRepository.save(produto);
        }

        pedidoRepository.cancelarPedido(idPedido);
        } else {
            throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
        }

    }

    public List<PedidoDTO> findAllByIdUsuario(Integer idUsuario) throws Exception {


        Integer loggedUserId = getIdLoggedUser();

        if (loggedUserId.equals(idUsuario) || isAdmin()) {

        List<Pedido> pedidos = pedidoRepository.findAllByUsuario_IdUsuario(idUsuario);

        ArrayList<PedidoDTO> pedidoDtos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = retornarDto(pedido);
            pedidoDtos.add(pedidoDTO);
        }

        return pedidoDtos;


        } else {
            throw new RegraDeNegocioException("Apenas o usuário dono da conta ou um administrador pode remover o usuário.");
        }
    }

    private List<PedidoXProduto> obterProdutos(ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate, List<Produto> produtosBanco) throws Exception {
        List<PedidoXProduto> produtos = new ArrayList<>();
        for (ProdutoCarrinhoCreate produtoCarrinhoCreate : produtosCarrinhoCreate) {
            Produto produto = produtoService.getById(produtoCarrinhoCreate.getIdProduto());
            produtosBanco.add(produto);
            PedidoXProduto pedidoXProduto = new PedidoXProduto();
            pedidoXProduto.setProduto(produto);
            pedidoXProduto.setQuantidade(produtoCarrinhoCreate.getQuantidade());
            produtos.add(pedidoXProduto);

        }
        return produtos;

    }


    public PedidoDTO pagamento(Integer idPedido, PagamentoDTO pagamentoDTO) throws Exception {
        Integer pedidoIdDoUsuarioLogado = pedidoRepository.findUsuarioIdByUserId(getIdLoggedUser()).orElseThrow(() -> new RegraDeNegocioException("Endereço do usuário logado não encontrado"));

        if (pedidoIdDoUsuarioLogado.equals(idPedido) || isAdmin()) {

        Pedido pedido = findById(idPedido);

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

        } else {
            throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
        }
    }

    public PedidoEmpresaDTO updatePedidoStatus(Integer idPedido, StatusPedido novoStatus, Integer idEmpresa) throws Exception {
        findById(idPedido);
        return updateStatus(idPedido, novoStatus, idEmpresa);

    }

    public PedidoEmpresaDTO updateStatus(Integer idPedido, StatusPedido novoStatus, Integer idEmpresa) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado"));

        StatusPedido statusAntigo = pedido.getStatusPedido();
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

    public PedidoRastreioDTO updateCodigoDeRastreio(Integer idPedido, String codigoRastreio, Integer idEmpresa) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado"));

        if (!pedido.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraDeNegocioException("Você não tem permissão para atualizar o código de rastreamento deste pedido.");
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


    private void efetuarTransferencia(Carteira carteiraOrigem, Carteira carteiraDestino, BigDecimal total) throws RegraDeNegocioException {
        BigDecimal valorPendente = carteiraOrigem.getSaldo().subtract(total);
        if (valorPendente.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraDeNegocioException("SALDO INSUFICIENTE");
        }
        carteiraOrigem.setSaldo(carteiraOrigem.getSaldo().subtract(total));
        carteiraDestino.setSaldo(carteiraDestino.getSaldo().add(total));
    }

    public Usuario obterAdmin() throws Exception {
        Usuario admin = usuarioService.findByLogin("admin")
                .orElseThrow(() -> new RegraDeNegocioException("Ocorreu um erro ao processar o pagamento"));
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

    private void emailPedidoCriado(PedidoDTO pedidoDTO, Usuario usuario) throws Exception {
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
