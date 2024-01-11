package utils;

import exceptions.UsuarioJaCadastradoException;
import modelo.*;
import servicos.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class GeradorSeeds {

        public static void gerarSeeds(EnderecoCRUD enderecoCRUD, UsuarioCRUD usuarioCRUD, ProdutoCRUD produtoCRUD, EmpresaCRUD empresaCRUD, CupomCRUD cupomCRUD) {
                LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
                LocalDate dataNascimento2 = LocalDate.of(1990, 1, 1);

                Endereco endereco1 = new Endereco("Rua Honesto Barbosa", "1315", "Bloco A", "01153-000", "Sao Paulo", "SP", "Brasil");
                Endereco endereco2 = new Endereco("Avenida Roberto Vila", "3123", "Casa", "01053-000", "Sao Paulo", "SP", "Brasil");
                Endereco endereco3 = new Endereco("Rua Barão do Rio Branco", "18", "Vila eldizia", "09181-610", "Sao Paulo", "SP", "Brasil");

                enderecoCRUD.adicionarEndereco(endereco1);
                enderecoCRUD.adicionarEndereco(endereco2);
                enderecoCRUD.adicionarEndereco(endereco3);
//                Usuario usuario1 = new Usuario("admin", "admin", "admin", "admin", endereco1, dataNascimento);
//                Usuario usuario2 = new Usuario("admin2", "admin2", "admin2", "admin2", endereco2, dataNascimento2);
//                Usuario usuario3 = new Usuario("deyvidlucas", "lucas2024", "Deyvid Lucas", "Cunha", endereco3, dataNascimento);
//                Consumidor consumidor = new Consumidor("admin", "admin", "admin", "admin", endereco1, dataNascimento, "56608950046");
//                Consumidor consumidor2 = new Consumidor("admin2", "admin2", "admin2", "admin2", endereco1, dataNascimento, "19191550046");
//                Consumidor consumidor3 = new Consumidor("deyvidlucas", "lucas2024", "Deyvid Lucas", "Cunha", endereco3, dataNascimento, "11684910046");
//
//                try {
//                        consumidorCRUD.criarUsuario(consumidor);
//                        consumidorCRUD.criarUsuario(consumidor2);
//                        consumidorCRUD.criarUsuario(consumidor3);
//                }
//                catch (UsuarioJaCadastradoException uce) {
//                        System.out.println("Ocorreu um erro de cadastro");
//                }
//                int idEmpresaSeed = empresaCRUD.criarEmpresa("Fazenda do tio Zé", "72.351.383/0001-53", "FazendaZezinho", "0223233556", "Alimenticio", usuario1);
//
//                ArrayList<Produto> produtosEmpresa1 = new ArrayList<>();
//
//
//                BigDecimal preco = new BigDecimal("1.5");
//                BigDecimal quantidade = new BigDecimal("18");
//
//                Produto produto = new Produto(idEmpresaSeed, "Maçã", "Gala orgânica", preco, quantidade, TipoCategoria.FRUTAS, 3.2, UnidadeMedida.KG);
//
//                produtosEmpresa1.add(produto);
//
//                preco = new BigDecimal("8.50");
//                quantidade = new BigDecimal("100");
//
//                Produto produto1 = new Produto(idEmpresaSeed, "Banana", "Caixo de banana prata", preco, quantidade, TipoCategoria.FRUTAS, 3.2, UnidadeMedida.PC);
//                produtosEmpresa1.add(produto1);
//
//                preco = new BigDecimal("2.0");
//                quantidade = new BigDecimal("18");
//
//                Produto produto2 = new Produto(idEmpresaSeed, "Batata", "Batata organica", preco, quantidade, TipoCategoria.LEGUMES, 3.2, UnidadeMedida.PC);
//                produtosEmpresa1.add(produto2);
//
//                preco = new BigDecimal("8.0");
//                quantidade = new BigDecimal("18");
//                Produto produto3 = new Produto(1, "Brocolis", "Brocolis de cabeça", preco, quantidade, TipoCategoria.LEGUMES, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto3);
//
//                preco = new BigDecimal("5.46");
//                quantidade = new BigDecimal("18");
//                Produto produto4 = new Produto(1, "Alface", "Alface americana", preco, quantidade, TipoCategoria.VERDURAS_E_TEMPEROS, 3.2, UnidadeMedida.PC);
//                produtosEmpresa1.add(produto4);
//
//                preco = new BigDecimal("40.00");
//                quantidade = new BigDecimal("10");
//                Produto produto5 = new Produto(1, "Coentro", "Coentro", preco, quantidade, TipoCategoria.VERDURAS_E_TEMPEROS, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto5);
//
//                preco = new BigDecimal("3.10");
//                quantidade = new BigDecimal("18");
//                Produto produto6 = new Produto(1, "Beterraba", "Beterraba", preco, quantidade, TipoCategoria.VERDURAS_E_TEMPEROS, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto6);
//
//                preco = new BigDecimal("9.0");
//                quantidade = new BigDecimal("18");
//                Produto produto7 = new Produto(1, "Ovo", "Caixa de Ovo caipira", preco, quantidade, TipoCategoria.OVOS, 3.2, UnidadeMedida.PC);
//                produtosEmpresa1.add(produto7);
//
//                produtoCRUD.adicionarProduto(produto);
//                produtoCRUD.adicionarProduto(produto1);
//                produtoCRUD.adicionarProduto(produto2);
//                produtoCRUD.adicionarProduto(produto3);
//                produtoCRUD.adicionarProduto(produto4);
//                produtoCRUD.adicionarProduto(produto5);
//                produtoCRUD.adicionarProduto(produto6);
//                produtoCRUD.adicionarProduto(produto7);
//
//                preco = new BigDecimal("8.50");
//                quantidade = new BigDecimal("18");
//                Produto produto8 = new Produto(2, "Ovo", "Caixa de Ovo branco", preco, quantidade, TipoCategoria.OVOS, 3.2, UnidadeMedida.PC);
//                produtosEmpresa1.add(produto8);
//
//                preco = new BigDecimal("20");
//                quantidade = new BigDecimal("18");
//                Produto produto9 = new Produto(2, "Arroz", "Saco de 2kg de Arroz integral", preco, quantidade, TipoCategoria.ARROZ_E_FEIJAO, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto9);
//
//                preco = new BigDecimal("15.50");
//                quantidade = new BigDecimal("18");
//                Produto produto10 = new Produto(2, "Feijao Carioca", "Saco de 2kg de Feijão", preco, quantidade, TipoCategoria.ARROZ_E_FEIJAO, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto10);
//
//                preco = new BigDecimal("4.05");
//                quantidade = new BigDecimal("18");
//                Produto produto11 = new Produto(2, "Leite", "Leite", preco, quantidade, TipoCategoria.LEITES, 3.2, UnidadeMedida.L);
//                produtosEmpresa1.add(produto11);
//
//                preco = new BigDecimal("12");
//                quantidade = new BigDecimal("10");
//                Produto produto12 = new Produto(2, "Feijão", "Saco de 1kg de Feijao preto", preco, quantidade, TipoCategoria.ARROZ_E_FEIJAO, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto12);
//
//                preco = new BigDecimal("2.20");
//                quantidade = new BigDecimal("18");
//                Produto produto13 = new Produto(2, "Bergamota", "Bergamota", preco, quantidade, TipoCategoria.FRUTAS, 3.2, UnidadeMedida.KG);
//                produtosEmpresa1.add(produto13);
//
//                produtoCRUD.adicionarProduto(produto8);
//                produtoCRUD.adicionarProduto(produto9);
//                produtoCRUD.adicionarProduto(produto10);
//                produtoCRUD.adicionarProduto(produto11);
//                produtoCRUD.adicionarProduto(produto12);
//                produtoCRUD.adicionarProduto(produto13);
//
//                empresaCRUD.atualizarEmpresa(idEmpresaSeed, "Fazenda do tio Zé", "72.351.383/0001-53", "FazendaZezinho", "0223233556", "Alimenticio", produtosEmpresa1, usuario1);
//
//                Cupom cupom1 = new Cupom(1, "SQUAD1EH10", false, "Cupom de desconto de 10%", new BigDecimal("10"));
//                cupomCRUD.adicionarCupom(cupom1);
//                Cupom cupom2 = new Cupom(2, "SQUAD1EH20", true, "Cupom de desconto de 20%", new BigDecimal("20"));
//                cupomCRUD.adicionarCupom(cupom2);
//                cupomCRUD.adicionarCupom(new Cupom(3, "SQUAD1EH30", true, "Cupom de desconto de 30%", new BigDecimal("30")));
//
//                cupom1.ativarCupom();
//                cupom2.desativarCupom();
        }
}
