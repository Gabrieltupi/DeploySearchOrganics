package utils;

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

    @Override
    public String toString(){
        return mostrar;
    }
}
