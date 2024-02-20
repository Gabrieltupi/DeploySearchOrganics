package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import com.vemser.dbc.searchorganic.model.Carteira;
import com.vemser.dbc.searchorganic.model.Usuario;

import java.math.BigDecimal;

public class MockCarteira {

    public static CarteiraDTO retornaCarteiraDto(){
        CarteiraDTO carteiraDTO= new CarteiraDTO();
        carteiraDTO.setIdCarteira(1);
        carteiraDTO.setSaldo(BigDecimal.valueOf(100));
        return carteiraDTO;
    }

    public static Carteira retornarCarteira(){
        Usuario usuario= MockUsuario.retornarUsuario();
        Carteira carteira= new Carteira();
        carteira.setIdCarteira(1);
        carteira.setSaldo(BigDecimal.valueOf(100));
        carteira.setUsuario(usuario);
        return carteira;
    }
    public static Carteira retornarCarteira2(){
        Usuario usuario= MockUsuario.retornarUsuario();
        Carteira carteira= new Carteira();
        carteira.setIdCarteira(2);
        carteira.setSaldo(BigDecimal.valueOf(100));
        carteira.setUsuario(usuario);
        return carteira;
    }
}
