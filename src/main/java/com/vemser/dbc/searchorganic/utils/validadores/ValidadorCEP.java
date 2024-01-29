package com.vemser.dbc.searchorganic.utils.validadores;

public class ValidadorCEP {
    public static String isCepValido(String cep) {
        if (cep == null) return null;
        if (cep.length() != 9) return null;

        String digitoRegiaoDigitoSubRegiao = cep.substring(0, 2);
        int cepInt = Integer.parseInt(digitoRegiaoDigitoSubRegiao);

        if (cepInt >= 1 && cepInt <= 5) {
            return "SP - Capital";
        }
        if (cepInt >= 6 && cepInt <= 9) {
            return "SP - Área Metropolitana";
        }
        if (cepInt == 11) {
            return "SP - Litoral";
        }
        if (cepInt >= 12 && cepInt <= 19) {
            return "SP - Interior";
        }
        return null;
    }
}
