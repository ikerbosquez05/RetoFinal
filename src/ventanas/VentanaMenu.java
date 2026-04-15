package ventanas;

import javax.swing.*;

import clases.Categoria;
import dao.CategoriaDAO;
import dao.LibroDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;
import interfaces.InterfazUsuario;
import interfaces.InterfazCategoria;
import interfaces.InterfazLibro;
import interfaces.InterfazPrestamo;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class VentanaMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnXML;

	private JButton btnLibros, btnPrestamos, btnUsuarios, btnCategorias, btnSalir;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new VentanaMenu().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public VentanaMenu() {

		setSize(1920, 1080);
		setTitle("Biblioteca");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel fondo = new JPanel(null);
		fondo.setBackground(new Color(237, 230, 208));
		setContentPane(fondo);

		// 📏 CARD CENTRADO REAL
		int cardWidth = 1000;
		int cardHeight = 600;

		JPanel card = new JPanel(null);
		card.setBounds(277, 159, cardWidth, cardHeight);
		card.setBackground(new Color(244, 239, 230));
		card.setBorder(BorderFactory.createLineBorder(new Color(139, 111, 71), 2));
		fondo.add(card);

		// 🔹 ICONO
		JLabel icono = new JLabel("📚");
		icono.setBounds(0, 20, cardWidth, 60);
		icono.setHorizontalAlignment(SwingConstants.CENTER);
		icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
		card.add(icono);

		// 🔹 TITULO
		JLabel titulo = new JLabel("Sistema Biblioteca");
		titulo.setBounds(0, 80, cardWidth, 40);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
		titulo.setForeground(new Color(139, 111, 71));
		card.add(titulo);

		int centroX = cardWidth / 2;

		// BOTONES

		btnLibros = new JButton("Libros");
		btnLibros.setBounds(centroX - 270, 180, 250, 60);
		btnLibros.setBackground(new Color(120, 94, 60));
		btnLibros.setForeground(Color.WHITE);
		btnLibros.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnLibros.setFocusPainted(false);
		btnLibros.setBorderPainted(false);
		btnLibros.addActionListener(this);
		card.add(btnLibros);

		btnPrestamos = new JButton("Préstamos");
		btnPrestamos.setBounds(centroX + 20, 180, 250, 60);
		btnPrestamos.setBackground(new Color(120, 94, 60));
		btnPrestamos.setForeground(Color.WHITE);
		btnPrestamos.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnPrestamos.setFocusPainted(false);
		btnPrestamos.setBorderPainted(false);
		btnPrestamos.addActionListener(this);
		card.add(btnPrestamos);

		btnUsuarios = new JButton("Usuarios");
		btnUsuarios.setBounds(centroX - 270, 270, 250, 60);
		btnUsuarios.setBackground(new Color(120, 94, 60));
		btnUsuarios.setForeground(Color.WHITE);
		btnUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnUsuarios.setFocusPainted(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.addActionListener(this);
		card.add(btnUsuarios);

		btnCategorias = new JButton("Categorías");
		btnCategorias.setBounds(centroX + 20, 270, 250, 60);
		btnCategorias.setBackground(new Color(120, 94, 60));
		btnCategorias.setForeground(Color.WHITE);
		btnCategorias.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCategorias.setFocusPainted(false);
		btnCategorias.setBorderPainted(false);
		btnCategorias.addActionListener(this);
		card.add(btnCategorias);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds((cardWidth - 200) / 2, 420, 200, 50);
		btnSalir.setBackground(new Color(120, 94, 60));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnSalir.setFocusPainted(false);
		btnSalir.setBorderPainted(false);
		btnSalir.addActionListener(this);
		card.add(btnSalir);

		btnXML = new JButton("Exportar XML");
		btnXML.setBounds(25, 525, 200, 50);
		card.add(btnXML);

		btnXML.setBackground(new Color(120, 94, 60));
		btnXML.setForeground(Color.WHITE);
		btnXML.setFont(new Font("Segoe UI", Font.BOLD, 13));

		btnXML.setFocusPainted(false);
		btnXML.setBorder(null);
		btnXML.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnXML.addActionListener(this);
	}

	// 🔹 ACCIONES
	private void abrirLibros() {
		new VentanaLibros().setVisible(true);
	}

	private void abrirPrestamos() {
		new VentanaPrestamos().setVisible(true);
	}

	private void abrirUsuarios() {
		new VentanaUsuarios().setVisible(true);
	}

	private void abrirCategorias() {
		new VentanaCategorias().setVisible(true);
	}

	private void cerrar() {
		this.dispose();
	}

	private void generarXML() {
		try {

			InterfazUsuario daoUsuarios = new UsuarioDAO();
			InterfazLibro daoLibros = new LibroDAO();
			InterfazPrestamo daoPrestamos = new PrestamoDAO();
			InterfazCategoria daoCategorias = new CategoriaDAO();

			Map<Integer, String[]> usuarios = daoUsuarios.verUsuarios();
			Map<Integer, String[]> libros = daoLibros.verLibros();
			Map<Integer, String[]> prestamos = daoPrestamos.verPrestamos();

			javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

			org.w3c.dom.Document doc = builder.newDocument();

			org.w3c.dom.Element root = doc.createElement("biblioteca");
			doc.appendChild(root);

			// USUARIOS
			org.w3c.dom.Element usuariosXML = doc.createElement("usuarios");
			root.appendChild(usuariosXML);

			for (Integer id : usuarios.keySet()) {

				String[] d = usuarios.get(id);

				org.w3c.dom.Element usuario = doc.createElement("usuario");

				org.w3c.dom.Element eId = doc.createElement("id");
				eId.appendChild(doc.createTextNode(id.toString()));

				org.w3c.dom.Element nombre = doc.createElement("nombre");
				nombre.appendChild(doc.createTextNode(d[0]));

				org.w3c.dom.Element email = doc.createElement("email");
				email.appendChild(doc.createTextNode(d[1]));

				org.w3c.dom.Element telefono = doc.createElement("telefono");
				telefono.appendChild(doc.createTextNode(d[2]));

				usuario.appendChild(eId);
				usuario.appendChild(nombre);
				usuario.appendChild(email);
				usuario.appendChild(telefono);

				usuariosXML.appendChild(usuario);
			}

			// LIBROS
			org.w3c.dom.Element librosXML = doc.createElement("libros");
			root.appendChild(librosXML);

			for (Integer id : libros.keySet()) {

				String[] d = libros.get(id);

				org.w3c.dom.Element libro = doc.createElement("libro");

				org.w3c.dom.Element eId = doc.createElement("id");
				eId.appendChild(doc.createTextNode(id.toString()));

				org.w3c.dom.Element titulo = doc.createElement("titulo");
				titulo.appendChild(doc.createTextNode(d[0]));

				org.w3c.dom.Element autor = doc.createElement("autor");
				autor.appendChild(doc.createTextNode(d[1]));

				org.w3c.dom.Element fecha = doc.createElement("fecha");
				fecha.appendChild(doc.createTextNode(d[2]));

				libro.appendChild(eId);
				libro.appendChild(titulo);
				libro.appendChild(autor);
				libro.appendChild(fecha);

				librosXML.appendChild(libro);
			}

			// PRESTAMOS
			org.w3c.dom.Element prestamosXML = doc.createElement("prestamos");
			root.appendChild(prestamosXML);

			for (Integer id : prestamos.keySet()) {

				String[] d = prestamos.get(id);

				org.w3c.dom.Element prestamo = doc.createElement("prestamo");

				org.w3c.dom.Element eId = doc.createElement("id");
				eId.appendChild(doc.createTextNode(id.toString()));

				org.w3c.dom.Element usuario = doc.createElement("usuario");
				usuario.appendChild(doc.createTextNode(d[0]));

				org.w3c.dom.Element libro = doc.createElement("libro");
				libro.appendChild(doc.createTextNode(d[1]));

				org.w3c.dom.Element fechaP = doc.createElement("fecha_prestamo");
				fechaP.appendChild(doc.createTextNode(d[2]));

				org.w3c.dom.Element fechaL = doc.createElement("fecha_limite");
				fechaL.appendChild(doc.createTextNode(d[3]));

				org.w3c.dom.Element fechaD = doc.createElement("fecha_devolucion");
				fechaD.appendChild(doc.createTextNode(d[4]));

				prestamo.appendChild(eId);
				prestamo.appendChild(usuario);
				prestamo.appendChild(libro);
				prestamo.appendChild(fechaP);
				prestamo.appendChild(fechaL);
				prestamo.appendChild(fechaD);

				prestamosXML.appendChild(prestamo);
			}

			// CATEGORIAS
			org.w3c.dom.Element categoriasXML = doc.createElement("categorias");
			root.appendChild(categoriasXML);

			for (Categoria c : daoCategorias.listarCategorias()) {

				org.w3c.dom.Element cat = doc.createElement("categoria");

				org.w3c.dom.Element eId = doc.createElement("id");
				eId.appendChild(doc.createTextNode(String.valueOf(c.getIdCategoria())));

				org.w3c.dom.Element tipo = doc.createElement("tipo");
				tipo.appendChild(doc.createTextNode(c.getTipoCategoria().toString()));

				org.w3c.dom.Element desc = doc.createElement("descripcion");
				desc.appendChild(doc.createTextNode(c.getDescripcion()));

				cat.appendChild(eId);
				cat.appendChild(tipo);
				cat.appendChild(desc);

				categoriasXML.appendChild(cat);
			}

			// GUARDAR
			javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance()
					.newTransformer();

			transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

			javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);

			javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(
					new java.io.File("biblioteca.xml"));

			transformer.transform(source, result);

			JOptionPane.showMessageDialog(this, "XML COMPLETO generado correctamente");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al generar XML");
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnLibros)
			abrirLibros();
		if (e.getSource() == btnPrestamos)
			abrirPrestamos();
		if (e.getSource() == btnUsuarios)
			abrirUsuarios();
		if (e.getSource() == btnCategorias)
			abrirCategorias();
		if (e.getSource() == btnSalir)
			cerrar();
		if (e.getSource() == btnXML) {
			generarXML();
		}
	}
}