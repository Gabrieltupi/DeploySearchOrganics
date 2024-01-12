package utils;

public enum TipoAtivo {
    S(true),
    N(false);

    private Boolean status;

    TipoAtivo(boolean status) {
        this.status = status;
    }
    public Boolean getStatus() {
        return status;
    }

    public static TipoAtivo fromBoolean(boolean status) {
        for (TipoAtivo tipo : TipoAtivo.values()) {
            if (tipo.status.equals(status)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoAtivo inválido: " + status);
    }

}
