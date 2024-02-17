package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Mocks {
    public static Empresa retornarEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(new Random().nextInt());
        empresa.setCnpj("56318380000161");
        empresa.setInscricaoEstadual("231321");
        empresa.setNomeFantasia("Empresa Fantasia");
        empresa.setRazaoSocial("Fantasia S.A");
        empresa.setSetor("Agrícola");

        List<Produto> produtos = criarProdutos(empresa.getIdEmpresa());

        empresa.setProdutos(produtos);

        return empresa;
    }

    public static EmpresaDTO retornarEmpresaDTO(Empresa empresaEntity){
        EmpresaDTO empresaDTO = new EmpresaDTO(empresaEntity);
        return empresaDTO;
    }
    public static CreateEmpresaDTO retornarEmpresaCreateDTO(){
        return new CreateEmpresaDTO
                ("Empresa Fantasia", "56318380000161", "Fantasia S.A", "231321", "Agrícola");
    }

    public static List<Produto> criarProdutos(Integer idEmpresa){
        List<Produto> produtos = new ArrayList<>();
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setQuantidade(BigDecimal.valueOf(5));
        produto.setUrlImagem("1.jpg");
        produto.setIdEmpresa(idEmpresa);
        produto.setCategoria(TipoCategoria.FRUTAS);
        produto.setDescricao("Produto 1");
        produto.setPreco(BigDecimal.valueOf(10));
        produto.setTipoAtivo(TipoAtivo.S);
        produto.setUnidadeMedida(UnidadeMedida.UN);
        produto.setTaxa(1d);
        produtos.add(produto);

        return produtos;
    }
    public static Usuario retornarUsuario(){
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(new Random().nextInt());
            usuario.setSenha("1234");
            usuario.setCarteira(carteira(usuario));
            usuario.setTipoAtivo(TipoAtivo.S);
            usuario.setNome("Usuario 1");
            usuario.setCpf("12345678900");
            usuario.setEmail("email@example.com");
            usuario.setDataNascimento(LocalDate.parse("1990-05-05"));
            usuario.setSobrenome("Sobrenome do usu 1 ");

            Set<Cargo> cargos = new HashSet<>();
            cargos.add(obterCargo("ROLE_USUARIO"));

            usuario.setCargos(cargos);

            return usuario;
    }

    public static Cargo obterCargo(String role){
        Cargo cargo = new Cargo();
        cargo.setIdCargo(new Random().nextInt());
        cargo.setNome(role);
        return cargo;
    }

    public static Set<Cargo> obterTodosCargos(){
        Set<Cargo> cargos = new HashSet<>();
        cargos.add(obterCargo("ROLE_ADMIN"));
        cargos.add(obterCargo("ROLE_EMPRESA"));
        cargos.add(obterCargo("ROLE_USUARIO"));
        return obterTodosCargos();

    }
    public static Carteira carteira(Usuario usuario){
        Carteira carteira = new Carteira();
        carteira.setSaldo(BigDecimal.valueOf(0));
        carteira.setIdCarteira(1);
        carteira.setUsuario(usuario);

        return carteira;
    }
}
