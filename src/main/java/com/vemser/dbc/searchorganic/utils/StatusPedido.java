package com.vemser.dbc.searchorganic.utils;

public enum StatusPedido {
    AGUARDANDO_PAGAMENTO,
    CANCELADO,
    PAGO,
    EM_SEPARACAO,
    COLETADO,
    A_CAMINHO,
    ENTREGUE;

    public static StatusPedido fromInt(int valor) {
        valor -= 1;
        for (StatusPedido status : StatusPedido.values()) {
            if (status.ordinal() == valor) {
                return status;
            }
        }
        throw new IllegalArgumentException("StatusCategoria inválido: " + valor);
    }
}
