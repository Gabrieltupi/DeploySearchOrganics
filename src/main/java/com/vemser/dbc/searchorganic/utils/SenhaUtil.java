package com.vemser.dbc.searchorganic.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


public class SenhaUtil {
    public String gerarSenha(Integer tamanho) {
        return RandomStringUtils.randomAlphanumeric(tamanho);
    }
}
