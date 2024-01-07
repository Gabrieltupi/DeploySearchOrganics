package utils;
import modelo.*;
import servicos.EmpresaCRUD;
import servicos.EnderecoCRUD;
import servicos.ProdutoCRUD;
import servicos.UsuarioCRUD;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class GeradorSeeds {

    public void gerarSeeds(){

        LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
        LocalDate dataNascimento2 = LocalDate.of(1990, 1, 1);

        Endereco endereco1 = new Endereco("Rua Honesto Barbosa", "1315", "Bloco A", "01153-000", "Sao Paulo", "SP", "Brasil");
        Endereco endereco2 = new Endereco("Avenida Roberto Vila", "3123", "", "10053-000", "Sao Paulo", "SP", "Brasil");

        EnderecoCRUD enderecoCRUD = new EnderecoCRUD();
        enderecoCRUD.adicionarEndereco(endereco1);
        enderecoCRUD.adicionarEndereco(endereco2);

        UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
        Usuario usuario1 = new Usuario("admin", "admin", "admin", "admin", endereco1, dataNascimento);
        usuarioCRUD.criarUsuario("admin", "admin", "admin", "admin", endereco1, dataNascimento);

        EmpresaCRUD empresaCRUD = new EmpresaCRUD();
        ArrayList<Produto> produtosEmpresa1 = new ArrayList<>();
        BigDecimal preco = new BigDecimal("1.5");
        BigDecimal quantidade = new BigDecimal("18");

        Produto produto = new Produto(0, "Maçã", "Gala orgânica", preco, quantidade, TipoCategoria.FRUTAS, 3.2, UnidadeMedida.KG);
        produtosEmpresa1.add(produto);

        preco = new BigDecimal("8.50");
        quantidade = new BigDecimal("100");
        Produto produto1 = new Produto(0, "Banana", "Caixo de banana prata", preco, quantidade, TipoCategoria.FRUTAS, 3.2, UnidadeMedida.PC);
        produtosEmpresa1.add(produto1);

        preco = new BigDecimal("2.0");
        quantidade = new BigDecimal("18");
        Produto produto2 = new Produto(0, "Batata", "Batata organica", preco, quantidade, TipoCategoria.LEGUMES, 3.2, UnidadeMedida.PC);
        produtosEmpresa1.add(produto2);

        ProdutoCRUD produtoCRUD = new ProdutoCRUD();
        produtoCRUD.adicionarProduto(produto);
        produtoCRUD.adicionarProduto(produto1);
        produtoCRUD.adicionarProduto(produto2);

        empresaCRUD.criarEmpresa("Fazenda do tio Zé", "07269844000181", "FazendaZezinho", "0223233556", "Alimenticio", produtosEmpresa1, usuario1);



    }
}
