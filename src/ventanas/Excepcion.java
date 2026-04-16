package ventanas;

public class Excepcion extends Exception {
    private static final long serialVersionUID = 1L;

    public Excepcion(String mensaje) { 
        super(mensaje); 
    }
}