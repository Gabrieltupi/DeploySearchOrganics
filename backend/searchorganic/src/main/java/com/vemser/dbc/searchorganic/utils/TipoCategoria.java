package com.vemser.dbc.searchorganic.utils;

public enum TipoCategoria {
    LEGUMES("Legumes"),
    VERDURAS_E_TEMPEROS("Verduras e temperos"),
    FRUTAS("Frutas"),
    OVOS("Ovos"),
    LEITES("Leites"),
    ARROZ_E_FEIJAO("Arroz e Feijão");

    private final String mostrar;

    TipoCategoria(String mostrar) {
        this.mostrar = mostrar;
    }


    public static TipoCategoria fromInt(int valor) {
        valor -= 1;
        for (TipoCategoria tipo : TipoCategoria.values()) {
            if (tipo.ordinal() == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoCategoria inválido: " + valor);
    }

    @Override
    public String toString(){
        return mostrar;
    }
}