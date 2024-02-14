package com.vemser.dbc.searchorganic.utils;

public enum FormaPagamento {
    SALDO,
    CREDITO,
    DEBITO;

    public static FormaPagamento fromString(String valor) {
        switch (valor.toUpperCase()) {
            case "SALDO":
                return SALDO;
            case "CARTAO":
                return CREDITO;
            case "DEBITO":
                return DEBITO;
            default:
                throw new IllegalArgumentException("Forma de pagamento desconhecida: " + valor);
        }
    }
}
