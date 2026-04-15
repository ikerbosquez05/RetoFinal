package clases;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String fecha;

    public Libro(int id, String titulo, String autor, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getFecha() { return fecha; }
}