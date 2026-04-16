package interfaces;

import java.util.Map;

public interface InterfazUsuario {
	
    Map<Integer,String[]> verUsuarios() throws Exception;

    void insertarUsuario(int id, String nombre, String email, int telefono) throws Exception;

    void borrarUsuario(int id) throws Exception;

    void modificarUsuario(int id, String nombre, String email, int telefono) throws Exception;
}