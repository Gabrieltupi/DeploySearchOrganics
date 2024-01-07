import exceptions.UsuarioJaCadastradoException;
import modelo.Carrinho;
import modelo.Endereco;
import modelo.Produto;
import modelo.Usuario;
import servicos.*;
import utils.GeradorSeeds;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GeradorSeeds geradorSeeds = new GeradorSeeds();
        ConsumidorCRUD consumidorCRUD = new ConsumidorCRUD();
        CupomCRUD cupomCRUD = new CupomCRUD();
        DadosPessoaisCRUD dadosPessoaisCRUD = new DadosPessoaisCRUD();
        EmpresaCRUD empresaCRUD = new EmpresaCRUD();
        EnderecoCRUD enderecoCRUD = new EnderecoCRUD();
        PedidoCRUD pedidoCRUD = new PedidoCRUD();
        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
        ProdutoCRUD produtoCRUD = new ProdutoCRUD();

        boolean sair = false;

        GeradorSeeds.gerarSeeds(enderecoCRUD, usuarioCRUD, produtoCRUD, empresaCRUD);

        while (!sair) {
            System.out.println("""
                    1 - Login
                    2 - Cadastro
                    0 - Sair
                    """);
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        Usuario usuario = login(scanner, usuarioCRUD);
                        if (usuario != null) {
                            System.out.println("Bem vindo " + usuario.getNome());
                            while (true) {
                                System.out.println("""
                                        1 - Minha conta
                                        2 - Lojas
                                        3 - Carrinho
                                        0 - Voltar
                                        """);
                                try {
                                    int escolhaMenuConsumidor = scanner.nextInt();

                                    scanner.nextLine();
                                    Carrinho carrinho = new Carrinho(usuario);

                                    if (escolhaMenuConsumidor == 1) {
                                        menuMinhaConta(scanner, usuario, enderecoCRUD);
                                    }
                                    if (escolhaMenuConsumidor == 2) {
                                        menuLojas(scanner, produtoCRUD, carrinho);

                                    }
                                    if (escolhaMenuConsumidor == 3) {
                                        menuCarrinho(scanner, carrinho);
                                    }
                                    if (escolhaMenuConsumidor == 0) {
                                        break;
                                    }
                                } catch (InputMismatchException ipt) {
                                    System.out.println("Opção inválida!");
                                    scanner.nextLine();
                                }
                            }
                        } else {
                            System.out.println("Usuário não encontrado");
                        }
                        break;
                    case 2:
                        cadastro(scanner, usuarioCRUD, enderecoCRUD);
                        break;
                    case 0:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida");
                }

            } catch (InputMismatchException ipt) {
                System.out.println("Opção inválida!");
                scanner.nextLine();
            } catch (DateTimeParseException dt) {
                System.out.println("Data de nascimento inválida!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println();
            }
        }

        scanner.close();
    }

    private static void menuCarrinho(Scanner scanner, Carrinho carrinho) {
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
                // TODO: Ir para pagamento
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
    private static void menuLojas(Scanner scanner, ProdutoCRUD produtoCRUD, Carrinho carrinho) {
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
                                """);
                        int indexCategoria = scanner.nextInt();
                        scanner.nextLine();

                        produtoCRUD.listarProdutosPorCategoria(TipoCategoria.values()[indexCategoria - 1]);
                    }
                    if (escolhaMenuListarProdutos == 2) {
                        produtoCRUD.listarProdutos();
                    }
                    if (escolhaMenuListarProdutos == 0) {
                        break;
                    }
                }
            }

            if (escolhaMenuProdutos == 2) {
                System.out.println("Digite o ID do produto: ");
                int idProduto = scanner.nextInt();

                System.out.println("Digite a quantidade: ");
                BigDecimal quantidadeProduto = scanner.nextBigDecimal();

                Produto produto = produtoCRUD.buscarProdutoPorId(idProduto);
                carrinho.setIdEmpresa(produto.getEmpresaId());
                carrinho.adicionarProdutoAoCarrinho(produtoCRUD.buscarProdutoPorId(idProduto), quantidadeProduto);
            }

            if (escolhaMenuProdutos == 0) {
                return;
            }

            if (escolhaMenuProdutos != 1 && escolhaMenuProdutos != 2) {
                System.out.println("Opção inválida");
            }
        }
    }
    private static void menuMinhaConta(Scanner scanner, Usuario usuario, EnderecoCRUD enderecoCRUD) {
        System.out.println("""
                                        1 - Editar dados pessoais
                                        2 - Editar endereço
                                        3 - Editar login
                                        0 - Voltar
                                        """);

        int escolhaMenuDadosPessoais = scanner.nextInt();
        scanner.nextLine();

        switch (escolhaMenuDadosPessoais) {
            case 1:
                System.out.println("Digite seu nome: ");
                String nome = scanner.nextLine();

                System.out.println("Digite seu sobrenome: ");
                String sobrenome = scanner.nextLine();

                System.out.println("Digite sua data de nascimento: ");
                String stringNascimento = scanner.nextLine();

                LocalDate dataNascimento = LocalDate.parse(stringNascimento);

                usuario.setNome(nome);
                usuario.setSobrenome(sobrenome);
                usuario.setDataNascimento(dataNascimento);
                break;
            case 2:
                Endereco enderecoAtualizado = obterEndereco(scanner);
                enderecoCRUD.atualizarEndereco(usuario.getEndereco().getId(), enderecoAtualizado.getLogradouro(),
                        enderecoAtualizado.getNumero(), enderecoAtualizado.getComplemento(), enderecoAtualizado.getCep(),
                        enderecoAtualizado.getCidade(), enderecoAtualizado.getEstado(), enderecoAtualizado.getPais());
                break;
            case 3:
                System.out.println("Digite seu login: ");
                String loginEditado = scanner.nextLine();

                System.out.println("Digite sua senha: ");
                String senhaEditada = scanner.nextLine();

                usuario.setLogin(loginEditado);
                usuario.setPassword(senhaEditada);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private static Usuario login(Scanner scanner, UsuarioCRUD usuarioCRUD){
        System.out.println("Digite seu login: ");
        String login = scanner.nextLine();

        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioCRUD.buscarUsuarioPorLoginESenha(login, senha);

        return usuario;
    }

    private static void cadastro(Scanner scanner, UsuarioCRUD usuarioCRUD, EnderecoCRUD enderecoCRUD){
        System.out.println("Digite seu login: ");
        String loginCadastro = scanner.nextLine();

        System.out.println("Digite sua senha: ");
        String senhaCadastro = scanner.nextLine();

        System.out.println("Digite seu nome: ");
        String nomeCadastro = scanner.nextLine();

        System.out.println("Digite seu sobrenome: ");
        String sobrenomeCadastro = scanner.nextLine();

        System.out.println("Digite sua data de nascimento: ");
        String stringNascimentoCadastro = scanner.nextLine();



        LocalDate dataNascimentoCadastro = LocalDate.parse(stringNascimentoCadastro);

        Endereco enderecoCadastro = obterEndereco(scanner);
        if (!enderecoCRUD.adicionarEndereco(enderecoCadastro)) {
            return ;
        }

        try {
            usuarioCRUD.criarUsuario(loginCadastro, senhaCadastro, nomeCadastro, sobrenomeCadastro, enderecoCadastro,
                    dataNascimentoCadastro);
        }
        catch (UsuarioJaCadastradoException uce) {
            System.out.println("Ocorreu um erro de cadastro: " +  uce.getMessage());
        }
    }

    private static Endereco obterEndereco(Scanner scanner){
        System.out.println("""
                                                Infos de endereço:
                                                Logradouro,
                                                Número,
                                                Complemento,
                                                CEP,
                                                Cidade,
                                                Estado,
                                                País,
                                                Região
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
        Endereco endereco = new Endereco(logradouro, numero, complemento, cep, cidade, estado, pais);
        return endereco;
    }
}