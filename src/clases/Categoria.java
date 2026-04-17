package clases;

public class Categoria {

    private int idCategoria;
    private TipoCategoria tipoCategoria;
    private String descripcion;

    public Categoria() {}

    public Categoria(int idCategoria, String tipoCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.tipoCategoria = TipoCategoria.valueOf(tipoCategoria.toUpperCase().replace(" ", "_"));  // Convierte el texto recibido a mayúsculas, reemplaza comas si es necesario
        // y lo transforma al enum TipoCategoria
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public TipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
