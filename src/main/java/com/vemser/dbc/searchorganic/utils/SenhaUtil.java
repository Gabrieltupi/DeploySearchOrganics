package com.vemser.dbc.searchorganic.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class SenhaUtil {
    public String gerarSenha(Integer tamanho) {
        return RandomStringUtils.randomAlphanumeric(tamanho);
    }
}
