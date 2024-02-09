package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;

public interface ICarteiraService {
    CarteiraDTO obterSaldo() throws Exception;
}
