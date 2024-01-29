package com.vemser.dbc.searchorganic.utils;

public enum TipoCategoria {
    LEGUMES("Legumes", 1),
    VERDURAS_E_TEMPEROS("Verduras e temperos", 2),
    FRUTAS("Frutas", 3),
    OVOS("Ovos", 4),
    LEITES("Leites", 5),
    ARROZ_E_FEIJAO("Arroz e Feijão", 6);

    private final String mostrar;
    private final Integer valor;

    TipoCategoria(String mostrar, Integer valor) {
        this.mostrar = mostrar;
        this.valor = valor;
    }

    public Integer getValor() {
        return valor + 1;
    }

    public static TipoCategoria fromInt(Integer valor) {
        for (TipoCategoria tipo : TipoCategoria.values()) {
            if (tipo.valor == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoCategoria inválido: " + valor);
    }

    @Override
    public String toString() {
        return mostrar;
    }

    public String getNome() {
        return mostrar;
    }
}