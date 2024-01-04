package Modelo;

import java.util.UUID;

public class Categoria {
    private static int id;
    private String variedade;

    public Categoria(String variedade) {
        this.variedade = variedade;
        id++;
    }

    public int getId() {
        return id;
    }

    public String getVariedade() {
        return variedade;
    }

    public void setVariedade(String variedade) {
        this.variedade = variedade;
    }
}
