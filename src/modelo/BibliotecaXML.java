package modelo;
import java.sql.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class BibliotecaXML {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/BIBLIOTECA";
        String user = "root"; // tu usuario
        String password = "abcd*1234"; // tu contraseña

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Crear documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Nodo raíz
            Element root = doc.createElement("biblioteca");
            doc.appendChild(root);

            // --- CATEGORIAS ---
            Element categoriasNode = doc.createElement("categorias");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_CATEGORIA, TIPO_CATEGORIA, DESCRIPCION FROM CATEGORIA");
            while (rs.next()) {
                Element cat = doc.createElement("categoria");
                cat.setAttribute("id", rs.getString("ID_CATEGORIA"));
                cat.setAttribute("tipo", rs.getString("TIPO_CATEGORIA"));
                cat.setAttribute("descripcion", rs.getString("DESCRIPCION"));
                categoriasNode.appendChild(cat);
            }
            root.appendChild(categoriasNode);

            // --- LIBROS ---
            Element librosNode = doc.createElement("libros");
            rs = stmt.executeQuery("SELECT ID_LIBRO, TITULO, AUTOR, FECHA FROM LIBRO");
            while (rs.next()) {
                Element libro = doc.createElement("libro");
                libro.setAttribute("id", rs.getString("ID_LIBRO"));

                Element titulo = doc.createElement("titulo");
                titulo.setTextContent(rs.getString("TITULO"));
                libro.appendChild(titulo);

                Element autor = doc.createElement("autor");
                autor.setTextContent(rs.getString("AUTOR"));
                libro.appendChild(autor);

                Element fecha = doc.createElement("fecha");
                fecha.setTextContent(rs.getString("FECHA"));
                libro.appendChild(fecha);

                // Categorías del libro
                PreparedStatement psCat = conn.prepareStatement(
                    "SELECT C.TIPO_CATEGORIA FROM TIENE T JOIN CATEGORIA C ON T.ID_CATEGORIA=C.ID_CATEGORIA WHERE T.ID_LIBRO=?");
                psCat.setInt(1, rs.getInt("ID_LIBRO"));
                ResultSet rsCat = psCat.executeQuery();
                while (rsCat.next()) {
                    Element categoria = doc.createElement("categoria");
                    categoria.setTextContent(rsCat.getString("TIPO_CATEGORIA"));
                    libro.appendChild(categoria);
                }

                librosNode.appendChild(libro);
            }
            root.appendChild(librosNode);

            // --- USUARIOS ---
            Element usuariosNode = doc.createElement("usuarios");
            rs = stmt.executeQuery("SELECT ID_USUARIO, NOMBRE, EMAIL, TELEFONO FROM USUARIO");
            while (rs.next()) {
                Element usuario = doc.createElement("usuario");
                usuario.setAttribute("id", rs.getString("ID_USUARIO"));

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(rs.getString("NOMBRE"));
                usuario.appendChild(nombre);

                Element email = doc.createElement("email");
                email.setTextContent(rs.getString("EMAIL"));
                usuario.appendChild(email);

                Element telefono = doc.createElement("telefono");
                telefono.setTextContent(rs.getString("TELEFONO"));
                usuario.appendChild(telefono);

                usuariosNode.appendChild(usuario);
            }
            root.appendChild(usuariosNode);

            // --- PRESTAMOS ---
            Element prestamosNode = doc.createElement("prestamos");
            rs = stmt.executeQuery(
                "SELECT P.ID_PRESTAMO, U.NOMBRE AS usuario, P.FECHA_PRESTAMO, P.FECHA_LIMITE " +
                "FROM PRESTAMO P JOIN USUARIO U ON P.ID_USUARIO=U.ID_USUARIO");
            while (rs.next()) {
                Element prestamo = doc.createElement("prestamo");
                prestamo.setAttribute("id", rs.getString("ID_PRESTAMO"));
                prestamo.setAttribute("usuario", rs.getString("usuario"));

                Element fechaPrestamo = doc.createElement("fecha_prestamo");
                fechaPrestamo.setTextContent(rs.getString("FECHA_PRESTAMO"));
                prestamo.appendChild(fechaPrestamo);

                Element fechaLimite = doc.createElement("fecha_limite");
                fechaLimite.setTextContent(rs.getString("FECHA_LIMITE"));
                prestamo.appendChild(fechaLimite);

                // Contenido del préstamo (libros prestados)
                PreparedStatement psLibros = conn.prepareStatement(
                    "SELECT L.TITULO, C.FECHA_DEVOLUCION FROM CONTIENE C " +
                    "JOIN LIBRO L ON C.ID_LIBRO=L.ID_LIBRO WHERE C.ID_PRESTAMO=?");
                psLibros.setInt(1, rs.getInt("ID_PRESTAMO"));
                ResultSet rsLibros = psLibros.executeQuery();

                while (rsLibros.next()) {
                    Element libroPrestamo = doc.createElement("libro");
                    libroPrestamo.setAttribute("titulo", rsLibros.getString("TITULO"));
                    libroPrestamo.setAttribute("fecha_devolucion", rsLibros.getString("FECHA_DEVOLUCION"));
                    prestamo.appendChild(libroPrestamo);
                }

                prestamosNode.appendChild(prestamo);
            }
            root.appendChild(prestamosNode);

            // --- TRANSFORMAR A XML ---
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Biblioteca.xml"));
            transformer.transform(source, result);

            System.out.println("XML completo generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}