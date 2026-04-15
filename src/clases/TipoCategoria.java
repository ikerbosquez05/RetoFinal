package clases;

public enum TipoCategoria {
    DRAMA("Drama"),
    TERROR("Terror"),
    FANTASIA("Fantasía"),
    ROMANCE("Romance"),
    AUTO_AYUDA("Auto Ayuda");

    private final String descripcion;

    TipoCategoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}