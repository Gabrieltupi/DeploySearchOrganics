package view;

import exceptions.BancoDeDadosException;
import model.*;
import service.*;
import model.Usuario;
import service.UsuarioService;
import service.PedidoService;
import service.EnderecoService;
import service.ProdutoService;
import service.EmpresaService;
import utils.FormaPagamento;
import utils.TipoCategoria;
import utils.validadores.TipoEntrega;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {
    static UsuarioService usuarioService = new UsuarioService();
    static PedidoService pedidoService = new PedidoService();
    static EnderecoService enderecoService = new EnderecoService();
    static EmpresaService empresaService = new EmpresaService();
    static CupomService cupomService = new CupomService();
    static ProdutoService produtoService = new ProdutoService();
    static Endereco endereco = new Endereco();
    static Usuario usuario = new Usuario();
    static Empresa empresa = new Empresa();
    static Pedido pedido = new Pedido();
    static Produto produto = new Produto();
    static Cupom cupom = new Cupom();
    static ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();

    static Scanner scanner = new Scanner(System.in);

    public static void run() {

        boolean sair = false;
        while (!sair) {
            System.out.println("""
                    1 - Login
                    2 - Cadastro
                    0 - Sair
                    """);
            int escolha = scanner.nextInt();
            scanner.nextLine();
            switch (escolha) {
                case 1:
                    usuario = login();
                    if (usuario != null) {
                     if (enderecoService.verificaSeUsuarioPossuiEndereco(usuario.getIdUsuario()))  {
                         Endereco endereco = enderecoService.getEndereco(usuario.getIdUsuario());
                         usuario.setEndereco(endereco);
                     }
                        System.out.println("Bem vindo " + usuario.getNome());
                        Carrinho carrinho = new Carrinho(usuario);
                        while (true) {
                            System.out.println("""
                                    1 - Minha conta
                                    2 - Lojas
                                    3 - Carrinho
                                    0 - Voltar
                                    """);

                            int escolhaMenuUsuario = scanner.nextInt();
                            scanner.nextLine();

                            if (escolhaMenuUsuario == 1) {
                                menuMinhaConta(usuario);
                            }
                            if (escolhaMenuUsuario == 2) {
                                menuLojas(carrinho);
                            }
                            if (escolhaMenuUsuario == 3) {
                                menuCarrinho(carrinho);
                            }
                            if (escolhaMenuUsuario == 0) {
                                break;
                            }
                            scanner.nextLine();
                        }

                    }
                    break;
                case 2:
                    cadastro();
                    break;
                case 0:
                    sair = true;
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção inválida");

            }
        }
    }

    private static Usuario login() {
        System.out.println("Digite seu login: ");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();
        return usuarioService.autenticar(email, senha);
    }

    private static void menuCarrinho(Carrinho carrinho) {
        while (true) {
            System.out.println("""
                    1 - Ir para pagamento
                    2 - Listar produtos do carrinho
                    3 - Editar quantidade de produto do carrinho
                    4 - Remover produto do carrinho
                    5 - Limpar carrinho
                    0 - Voltar
                    """);

            int escolhaMenuCarrinho = scanner.nextInt();
            scanner.nextLine();
            if (escolhaMenuCarrinho == 1) {
               if (usuario.getEndereco() == null){
                   System.out.println("Usuário sem endereço cadastrado!");
                   Endereco endereco = adicionarEndereco(usuario.getIdUsuario());
                   usuario.setEndereco(endereco);
               }
                System.out.println("""
                        Escolha a forma de pagamento:
                        1 - Pix
                        2 - Cartão de crédito
                        3 - Cartão de débito
                        """);
                int escolhaPagamento = scanner.nextInt();
                scanner.nextLine();
                
                boolean finalizou = carrinho.finalizarPedido(FormaPagamento.values()[escolhaPagamento - 1], LocalDate.now(),
                        usuario.getEndereco(),
                       new Cupom());
                if(finalizou){
                    System.out.println("Pedido finalizado com sucesso!");
                    return;
                }
                System.out.println("Pedido não finalizado");
            }

            if (escolhaMenuCarrinho == 2) {
                System.out.println("Produtos do carrinho: ");
                carrinho.listarProdutosDoCarrinho();
            }
            if (escolhaMenuCarrinho == 3) {
                System.out.println("Digite o ID do produto: ");
                int idProdutoEditar = scanner.nextInt();

                System.out.println("Digite a quantidade: ");
                BigDecimal quantidade = scanner.nextBigDecimal();

                carrinho.editarQuantidadeProdutoDaSacola(idProdutoEditar, quantidade);

            }
            if (escolhaMenuCarrinho == 4) {
                carrinho.listarProdutosDoCarrinho();
                System.out.println("Digite o ID do produto: ");
                int idProdutoRemover = scanner.nextInt();

                carrinho.removerProdutoDoCarrinho(idProdutoRemover);

            }
            if (escolhaMenuCarrinho == 5) {
                carrinho.limparSacola();

            }

            if (escolhaMenuCarrinho == 0) {
                break;
            }

            if (escolhaMenuCarrinho != 1 && escolhaMenuCarrinho != 2 && escolhaMenuCarrinho != 3 && escolhaMenuCarrinho
                    != 4 && escolhaMenuCarrinho != 5) {
                System.out.println("Opção inválida");
            }
        }
    }

    private static void menuLojas(Carrinho carrinho) {
        while (true) {
            System.out.println("""
                    1 - Listar produtos
                    2 - Adicionar produto ao carrinho
                    0 - Voltar
                    """);

            int escolhaMenuProdutos = scanner.nextInt();
            scanner.nextLine();

            if (escolhaMenuProdutos == 1) {
                while (true) {
                    System.out.println("""
                            1 - Listar produtos por categoria
                            2 - Listar todos os produtos
                            0 - Voltar
                            """);

                    try {
                        int escolhaMenuListarProdutos = scanner.nextInt();
                        scanner.nextLine();

                        if (escolhaMenuListarProdutos == 1) {
                            System.out.println("""
                                    Escolha uma catergoria:
                                    1 - LEGUMES,
                                    2 - VERDURAS E TEMPEROS,
                                    3 - FRUTAS,
                                    4 - OVOS,
                                    5 - LEITES,
                                    6 - ARROZ E FEIJAO
                                    -------------------------------
                                    \n
                                    """);
                            int indexCategoria = scanner.nextInt();
                            scanner.nextLine();

                            produtoService.listarProdutosPorCategoria(TipoCategoria.fromInt(indexCategoria));
                        }
                        if (escolhaMenuListarProdutos == 2) {
                            produtoService.listarProdutos();
                        }
                        if (escolhaMenuListarProdutos == 0) {
                            break;
                        }

                    } catch (InputMismatchException ipt) {
                        System.out.println("Opção inválida!");
                        scanner.nextLine();
                    }
                }
            }

            if (escolhaMenuProdutos == 2) {
                System.out.println("----------------------------------------");
                System.out.println("Digite o ID do produto que quer adicionar ao carrinho");
                int idProduto = scanner.nextInt();

                if (produtoService.buscarProdutoPorId(idProduto) == null) continue;
                System.out.println("Digite a quantidade: ");
                BigDecimal quantidadeProduto = scanner.nextBigDecimal();
                if(carrinho.adicionarProdutoAoCarrinho(produtoService.buscarProdutoPorId(idProduto), quantidadeProduto)){
                    System.out.println("Produto adicionado com sucesso");
                };
            }

            if (escolhaMenuProdutos == 0) {
                return;
            }

            if (escolhaMenuProdutos != 1 && escolhaMenuProdutos != 2) {
                System.out.println("Opção inválida");
            }
        }
    }

    private static void menuMinhaConta(Usuario usuario) {
        while (true) {
            System.out.println("""
                    1 - Editar dados pessoais
                    2 - Editar endereço
                    3 - Editar senha
                    4 - Meu Perfil
                    0 - Voltar
                    """);

            int escolhaMenuDadosPessoais = scanner.nextInt();
            scanner.nextLine();

            if (escolhaMenuDadosPessoais == 1) {
                System.out.println("Digite seu nome: ");
                String novoNome = scanner.nextLine();

                System.out.println("Digite seu sobrenome: ");
                String novoSobrenome = scanner.nextLine();

                System.out.println("Digite seu CPF: ");
                String novoCpf = scanner.nextLine();

                System.out.println("Digite sua data de nascimento: ");
                String stringNascimento = scanner.nextLine();

                System.out.println("Digite seu email: ");
                String novoEmail = scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate novaDataNascimento = LocalDate.parse(stringNascimento, formatter);

                usuarioService.editarUsuario(usuario.getIdUsuario(), new Usuario(novoNome, novoSobrenome, novoCpf, novaDataNascimento, novoEmail, usuario.getLogin(), usuario.getSenha()));
            }
            if (escolhaMenuDadosPessoais == 2) {
                if (usuario.getEndereco() == null){
                    System.out.println("Usuário sem endereço cadastrado!");
                    Endereco endereco = adicionarEndereco(usuario.getIdUsuario());
                    usuario.setEndereco(endereco);
                    break;
                }
                System.out.println("Digite seu logradouro: ");
                String novoLogradouro = scanner.nextLine();
                System.out.println("Digite seu número: ");
                String novoNumero = scanner.nextLine();

                System.out.println("Digite seu complemento: ");
                String novoComplemento = scanner.nextLine();

                System.out.println("Digite seu CEP: ");
                String novoCep = scanner.nextLine();

                System.out.println("Digite sua cidade: ");
                String novaCidade = scanner.nextLine();
                System.out.println("Digite seu estado: ");
                String novoEstado = scanner.nextLine();
                System.out.println("Digite seu país: ");
                String novoPais = scanner.nextLine();

                Endereco novoEndereco = new Endereco();
                novoEndereco.setId(usuario.getEndereco().getId());
                novoEndereco.setLogradouro(novoLogradouro);
                novoEndereco.setNumero(novoNumero);
                novoEndereco.setComplemento(novoComplemento);
                novoEndereco.setCep(novoCep);
                novoEndereco.setCidade(novaCidade);
                novoEndereco.setEstado(novoEstado);
                novoEndereco.setPais(novoPais);

                enderecoService.atualizarEndereco(novoEndereco);
            }


            if (escolhaMenuDadosPessoais == 3) {

                System.out.println("Digite a nova senha: ");
                String senhaEditada = scanner.nextLine();

                usuario.setSenha(senhaEditada);

                usuarioService.editarUsuario(usuario.getIdUsuario(), usuario);

            }
            if (escolhaMenuDadosPessoais == 4) {
                usuarioService.exibirUsuario(usuario.getIdUsuario());
            }

            if (escolhaMenuDadosPessoais == 0) {
                break;
            }

            if (escolhaMenuDadosPessoais != 1 && escolhaMenuDadosPessoais != 2 && escolhaMenuDadosPessoais != 3 && escolhaMenuDadosPessoais != 4) {
                System.out.println("Opção inválida");
            }
        }
    }

    private static boolean login(Scanner scanner) {
        System.out.println("Digite seu login: ");
        String loginUsuario = scanner.nextLine();

        System.out.println("Digite sua senha: ");
        String senhaUsuario = scanner.nextLine();
        if (usuarioService.autenticar(loginUsuario, senhaUsuario) != null) {
            return true;
        }
        System.out.println("Login ou senha incorreta!!");
        return false;
    }

    private static void cadastro() {
        System.out.println("Digite seu login: ");
        String loginCadastro = scanner.nextLine();

        System.out.println("Digite sua senha: ");
        String senhaCadastro = scanner.nextLine();

        System.out.println("Digite seu nome: ");
        String nomeCadastro = scanner.nextLine();

        System.out.println("Digite seu sobrenome: ");
        String sobrenomeCadastro = scanner.nextLine();

        System.out.println("Digite seu CPF: ");
        String cpfCadastro = scanner.nextLine();

        System.out.println("Digite sua data de nascimento: ");
        String stringNascimentoCadastro = scanner.nextLine();

        System.out.println("Digite seu email: ");
        String emailCadastro = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataNascimentoCadastro = LocalDate.parse(stringNascimentoCadastro, formatter);

        try {
            usuarioService.criarUsuario(new Usuario(nomeCadastro, sobrenomeCadastro,
                    cpfCadastro, dataNascimentoCadastro, emailCadastro, loginCadastro,
                    senhaCadastro));
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);

        }
    }

    private static Endereco adicionarEndereco(Integer idUsuario) {

        System.out.println("""
                Infos de endereço:
                Logradouro,
                Número,
                Complemento,
                CEP,
                Cidade,
                Estado,
                País,
                Regiãoz\n
                """);

        System.out.println("Digite seu logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.println("Digite seu número: ");
        String numero = scanner.nextLine();

        System.out.println("Digite seu complemento: ");
        String complemento = scanner.nextLine();

        System.out.println("Digite seu CEP: ");
        String cep = scanner.nextLine();

        System.out.println("Digite sua cidade: ");
        String cidade = scanner.nextLine();
        System.out.println("Digite seu estado: ");
        String estado = scanner.nextLine();
        System.out.println("Digite seu país: ");
        String pais = scanner.nextLine();
        Endereco endereco = enderecoService.adicionarEndereco(new Endereco(logradouro, numero, complemento, cep, cidade, estado, pais, idUsuario));
        return endereco;

    }
}



