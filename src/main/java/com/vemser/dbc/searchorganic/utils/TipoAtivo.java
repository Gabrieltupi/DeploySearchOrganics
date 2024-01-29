package com.vemser.dbc.searchorganic.utils;

public enum TipoAtivo {
    S("S"),
    N("N");

    private String status;

    TipoAtivo(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static TipoAtivo fromString(String status) {
        for (TipoAtivo tipo : TipoAtivo.values()) {
            if (tipo.status.equals(status)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoAtivo inválido: " + status);
    }

}
