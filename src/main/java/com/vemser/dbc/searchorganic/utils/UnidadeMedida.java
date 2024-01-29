package com.vemser.dbc.searchorganic.utils;

public enum UnidadeMedida {
    KG("KG"),
    PC("PC"),
    GR("GR"),
    UN("UN"),
    L("L");

    private String unidade;

    UnidadeMedida(String unidade) {
        this.unidade = unidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public static UnidadeMedida fromString(String unidade) {
        for (UnidadeMedida tipo : UnidadeMedida.values()) {
            if (tipo.unidade.equals(unidade.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Unidade inválida: " + unidade);
    }
}
