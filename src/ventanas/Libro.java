package ventanas;

public class Libro {

    private String titulo;
    private String autor;
    private String anio;
    

    public Libro(String titulo, String autor, String anio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getAnio() { return anio; }
}