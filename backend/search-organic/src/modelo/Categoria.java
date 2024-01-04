package modelo;

import interfaces.Impressao;

public class Categoria implements Impressao {

    private static int categoriaId = 1;
    private int id;
    private String variedade;

    public Categoria(String variedade) {
        this.id = categoriaId;
        this.variedade = variedade;
        categoriaId++;
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

    @Override
    public void imprimir() {
        System.out.println("ID da categoria: " + getId());
        System.out.println("Variedade da categoria: " + getVariedade());
    }
}
