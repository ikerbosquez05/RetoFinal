package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.biblioteca.servicio.CategoriaServicio;
import es.biblioteca.servicio.LibroServicio;
import es.biblioteca.servicio.UsuarioServicio;

public class UsuarioCategoria {

    private UsuarioServicio usuarioServicio;
    private LibroServicio libroServicio;
    private CategoriaServicio categoriaServicio;

    @BeforeEach
    void setUp() {
        usuarioServicio = new UsuarioServicio();
        libroServicio = new LibroServicio();
        categoriaServicio = new CategoriaServicio();
    }

    // ── Tests UsuarioServicio ──

    @Test
    void testValidarUsuario_camposVacios_retornaFalse() {
        assertFalse(usuarioServicio.validarUsuario("", "", "", ""));
    }

    @Test
    void testValidarUsuario_idNoNumerico_retornaFalse() {
        assertFalse(usuarioServicio.validarUsuario("abc", "Ana", "600111222", "ana@gmail.com"));
    }

    @Test
    void testValidarUsuario_telefonoNoNumerico_retornaFalse() {
        assertFalse(usuarioServicio.validarUsuario("1", "Ana", "abc", "ana@gmail.com"));
    }

    @Test
    void testValidarUsuario_emailSinArroba_retornaFalse() {
        assertFalse(usuarioServicio.validarUsuario("1", "Ana", "600111222", "anagmail.com"));
    }

    @Test
    void testValidarUsuario_datosCorrectos_retornaTrue() {
        assertTrue(usuarioServicio.validarUsuario("1", "Ana", "600111222", "ana@gmail.com"));
    }

    // ── Tests LibroServicio ──

    @Test
    void testValidarLibro_camposVacios_retornaFalse() {
        assertFalse(libroServicio.validarLibro("", "", "", ""));
    }

    @Test
    void testValidarLibro_idNoNumerico_retornaFalse() {
        assertFalse(libroServicio.validarLibro("abc", "It", "Stephen King", "1986"));
    }

    @Test
    void testValidarLibro_anioFormatoIncorrecto_retornaFalse() {
        assertFalse(libroServicio.validarLibro("1", "It", "Stephen King", "86"));
    }

    @Test
    void testValidarLibro_datosCorrectos_retornaTrue() {
        assertTrue(libroServicio.validarLibro("1", "It", "Stephen King", "1986"));
    }

    // ── Tests CategoriaServicio ──

    @Test
    void testObtenerCategorias_noRetornaNull() throws Exception {
        assertNotNull(categoriaServicio.obtenerCategorias());
    }

    @Test
    void testObtenerCategorias_retornaLista() throws Exception {
        assertFalse(categoriaServicio.obtenerCategorias().isEmpty());
    }
}