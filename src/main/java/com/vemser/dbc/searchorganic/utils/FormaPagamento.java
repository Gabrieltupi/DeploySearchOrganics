package com.vemser.dbc.searchorganic.utils;

public enum FormaPagamento {
    PIX,
    CREDITO,
    DEBITO;

    public static FormaPagamento fromString(String valor) {
        switch (valor.toUpperCase()) {
            case "PIX":
                return PIX;
            case "CARTAO":
                return CREDITO;
            case "BOLETO":
                return DEBITO;
            default:
                throw new IllegalArgumentException("Forma de pagamento desconhecida: " + valor);
        }
    }
}
