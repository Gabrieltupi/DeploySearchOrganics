import modelo.*;
import servicos.*;
import utils.UnidadeMedida;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CategoriaCRUD categoriaCRUD = new CategoriaCRUD();
        ConsumidorCRUD consumidorCRUD = new ConsumidorCRUD();
        CupomCRUD cupomCRUD = new CupomCRUD();
        DadosPessoaisCRUD dadosPessoaisCRUD = new DadosPessoaisCRUD();
        EmpresaCRUD empresaCRUD = new EmpresaCRUD();
        EnderecoCRUD enderecoCRUD = new EnderecoCRUD();
        PedidoCRUD pedidoCRUD = new PedidoCRUD();
        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
//        Carrinho carrinho = new Carrinho();

        // crie um usuario para teste
        usuarioCRUD.criarUsuario("admin", "admin", "admin", "admin", null, null);


        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("""
                    1 - Login
                    2 - Cadastro
                    """);

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.println("Digite seu login: ");
                    String login = scanner.nextLine();

                    System.out.println("Digite sua senha: ");
                    String senha = scanner.nextLine();

                    Usuario usuario = usuarioCRUD.buscarUsuarioPorLoginESenha(login, senha);

                    if (usuario != null) {
                        System.out.println("Bem vindo " + usuario.getNome());

                        do {
                            System.out.println("""
                                    1 - Minha conta
                                    2 - Produtos
                                    3 - Carrinho
                                    """);

                            int escolhaMenuConsumidor = scanner.nextInt();
                            scanner.nextLine();

                            if (escolhaMenuConsumidor == 1) {
                                System.out.println("""
                                        1 - Editar dados pessoais
                                        2 - Editar endereço
                                        3 - Editar login
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
                                        String editarLogradouro = scanner.nextLine();

                                        System.out.println("Digite seu número: ");
                                        String editarNumero = scanner.nextLine();

                                        System.out.println("Digite seu complemento: ");
                                        String editarComplemento = scanner.nextLine();

                                        System.out.println("Digite seu CEP: ");
                                        String editarCep = scanner.nextLine();
                                        if (editarCep == null) editarCep = usuario.getEndereco().getCep();

                                        System.out.println("Digite sua cidade: ");
                                        String editarCidade = scanner.nextLine();

                                        System.out.println("Digite seu estado: ");
                                        String editarEstado = scanner.nextLine();

                                        System.out.println("Digite seu país: ");
                                        String editarPais = scanner.nextLine();

                                        enderecoCRUD.atualizarEndereco(usuario.getEndereco().getId(), editarLogradouro, editarNumero, editarComplemento, editarCep, editarCidade, editarEstado, editarPais);
                                        break;
                                    case 3:
                                        System.out.println("Digite seu login: ");
                                        String loginEditado = scanner.nextLine();

                                        System.out.println("Digite sua senha: ");
                                        String senhaEditada = scanner.nextLine();

                                        usuario.setLogin(loginEditado);
                                        usuario.setPassword(senhaEditada);
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                }
                            }
                            if (escolhaMenuConsumidor == 2) {
                                System.out.println("""
                                        1 - Listar produtos
                                        2 - Adicionar produto ao carrinho
                                        """);

                                int escolhaMenuProdutos = scanner.nextInt();
                                scanner.nextLine();

                                switch (escolhaMenuProdutos) {
                                    case 1:
                                        System.out.println("""
                                                1 - Listar produtos por categoria
                                                2 - Listar todos os produtos
                                                """);

                                        int escolhaMenuListarProdutos = scanner.nextInt();
                                        scanner.nextLine();

                                        switch (escolhaMenuListarProdutos) {
                                            case 1:
                                                System.out.println("Digite o nome da categoria: ");
                                                String nomeCategoria = scanner.nextLine();

                                                produtoCRUD.listarProdutosPorCategoria(nomeCategoria);
                                                break;
                                            case 2:
                                                produtoCRUD.listarTodosProdutos();
                                                break;
                                            default:
                                                System.out.println("Opção inválida");
                                        }
                                        break;

                                    case 2:
                                        System.out.println("Digite o ID do produto: ");
                                        int idProduto = scanner.nextInt();

                                        System.out.println("Digite a quantidade: ");
                                        BigDecimal quantidade = scanner.nextBigDecimal();

                                        Carrinho carrinho = new Carrinho(usuario, 1);
                                        BigDecimal preco = new BigDecimal("1.5");
                                        BigDecimal dsad = new BigDecimal("18");
                                        BigDecimal dasda = new BigDecimal("8");
                                        carrinho.adicionarProdutoAoCarrinho(new Produto(0, "Maçã", "Gala orgânica", preco, dsad, new Categoria("FRUTAS"), 3.2, UnidadeMedida.KG), dasda);
                                }

                            }
                        } while (true);
                    } else {
                        System.out.println("Usuário não encontrado");
                    }
                    break;
                case 2:
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
                    String logradouroCadastro = scanner.nextLine();

                    System.out.println("Digite seu número: ");
                    String numeroCadastro = scanner.nextLine();

                    System.out.println("Digite seu complemento: ");
                    String complementoCadastro = scanner.nextLine();

                    System.out.println("Digite seu CEP: ");
                    String cepCadastro = scanner.nextLine();

                    System.out.println("Digite sua cidade: ");
                    String cidadeCadastro = scanner.nextLine();

                    System.out.println("Digite seu estado: ");
                    String estadoCadastro = scanner.nextLine();

                    System.out.println("Digite seu país: ");
                    String paisCadastro = scanner.nextLine();

                    LocalDate dataNascimentoCadastro = LocalDate.parse(stringNascimentoCadastro);
                    Endereco enderecoCadastro = new Endereco(logradouroCadastro, numeroCadastro, complementoCadastro, cepCadastro, cidadeCadastro, estadoCadastro, paisCadastro);
                    enderecoCRUD.adicionarEndereco(enderecoCadastro);
                    usuarioCRUD.criarUsuario(loginCadastro, senhaCadastro, nomeCadastro, sobrenomeCadastro, enderecoCadastro, dataNascimentoCadastro);

                    break;
                default:
                    System.out.println("Opção inválida");
            }

            usuarioCRUD.exibirTodos();
        } while (true);
    }
}