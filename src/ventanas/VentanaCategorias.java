package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import interfaces.InterfazCategoria;
import modelo.AccesoBD;
import dao.CategoriaDAO;
import clases.Categoria;

import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

// Ventana principal para gestionar categorías
public class VentanaCategorias extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Campos de texto para introducir datos
	private JTextField txtId, txtTipo, txtDescripcion;

	// Botones de la interfaz
	private JButton btnAgregar, btnEliminar, btnVolver;

	// Tabla y modelo de datos
	private JTable tabla;
	private DefaultTableModel modelo;

	// DAO para acceder a la base de datos
	private InterfazCategoria dao = new CategoriaDAO();

	public VentanaCategorias() {

		// Configuración básica de la ventana
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Category Management");

		// Panel de fondo
		JPanel fondo = new JPanel();
		fondo.setLayout(null);
		fondo.setBackground(new Color(237, 230, 208));
		setContentPane(fondo);

		// Tarjeta principal (contenedor)
		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBounds(265, 90, 1000, 600);
		card.setBackground(new Color(244, 239, 230));
		card.setBorder(BorderFactory.createLineBorder(new Color(139, 111, 71), 2));
		fondo.add(card);

		// Título
		JLabel titulo = new JLabel("Category Management");
		titulo.setBounds(300, 20, 400, 30);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(titulo);

		// Labels
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(new Color(139, 111, 71));
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setBounds(250, 100, 100, 25);
		card.add(lblId);

		JLabel lblTipo = new JLabel("Type");
		lblTipo.setForeground(new Color(139, 111, 71));
		lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTipo.setBounds(548, 100, 100, 25);
		card.add(lblTipo);

		JLabel lblDesc = new JLabel("Description");
		lblDesc.setForeground(new Color(139, 111, 71));
		lblDesc.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDesc.setBounds(250, 184, 100, 25);
		card.add(lblDesc);

		// Campos de texto
		txtId = new JTextField();
		txtId.setBounds(350, 100, 150, 35);
		txtId.setBackground(new Color(250, 247, 242));
		txtId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtId);

		txtTipo = new JTextField();
		txtTipo.setBounds(650, 100, 150, 35);
		txtTipo.setBackground(new Color(250, 247, 242));
		txtTipo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtTipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtTipo);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(350, 180, 150, 35);
		txtDescripcion.setBackground(new Color(250, 247, 242));
		txtDescripcion
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtDescripcion);

		// Botón agregar
		btnAgregar = new JButton("Add");
		btnAgregar.setBounds(150, 280, 150, 40);
		btnAgregar.setBackground(new Color(120, 94, 60));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAgregar.setFocusPainted(false);
		btnAgregar.setBorder(null);
		btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAgregar.addActionListener(this);
		card.add(btnAgregar);

		// Botón eliminar
		btnEliminar = new JButton("Delete");
		btnEliminar.setBounds(332, 280, 150, 40);
		btnEliminar.setBackground(new Color(120, 94, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBorder(null);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(this);
		card.add(btnEliminar);

		// Botón volver
		btnVolver = new JButton("Return");
		btnVolver.setBounds(518, 280, 150, 40);
		btnVolver.setBackground(new Color(120, 94, 60));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(null);
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(this);
		card.add(btnVolver);

		// Botón para llamar a la función de BD
		JButton btnVerLibros = new JButton("See books");
		btnVerLibros.setBounds(700, 280, 150, 40);
		btnVerLibros.setBackground(new Color(120, 94, 60));
		btnVerLibros.setForeground(Color.WHITE);
		btnVerLibros.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVerLibros.setFocusPainted(false);
		btnVerLibros.setBorder(null);
		btnVerLibros.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVerLibros.addActionListener(this);
		card.add(btnVerLibros);

		// Modelo de tabla
		modelo = new DefaultTableModel(new String[] { "ID", "Type", "Description" }, 0);

		tabla = new JTable(modelo);

		// Estilo de tabla
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tabla.setRowHeight(28);
		tabla.setShowGrid(false);
		tabla.setIntercellSpacing(new Dimension(0, 0));

		// Filas alternas
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int row,
					int col) {
				super.getTableCellRendererComponent(t, v, sel, foc, row, col);
				if (!sel) {
					setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 247, 242));
				}
				return this;
			}
		});

		// Selección
		tabla.setSelectionBackground(new Color(200, 183, 156));
		tabla.setSelectionForeground(Color.BLACK);

		// Cabecera
		JTableHeader header = tabla.getTableHeader();
		header.setBackground(new Color(120, 94, 60));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Segoe UI", Font.BOLD, 14));

		// Scroll
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(150, 350, 700, 200);
		card.add(scroll);

		// Cargar datos iniciales
		cargarCategorias();
	}

	// VALIDACIÓN DE CAMPOS
	private boolean validar() {
		if (txtId.getText().isEmpty())
			return mensaje("ID is required");
		if (txtTipo.getText().isEmpty())
			return mensaje("Type is required");
		if (txtDescripcion.getText().isEmpty())
			return mensaje("Description is required");
		return true;
	}

	// Mostrar mensaje
	private boolean mensaje(String m) {
		JOptionPane.showMessageDialog(this, m);
		return false;
	}

	// Manejo de errores
	private void mostrarError(Exception e) {
		String msg = "Error";
		if (e instanceof NumberFormatException)
			msg = "Invalid number";
		else if (e instanceof SQLException)
			msg = "Database error";
		else if (e.getMessage() != null)
			msg = e.getMessage();

		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Cargar datos en tabla
	private void cargarCategorias() {
		try {
			modelo.setRowCount(0);
			for (Categoria c : dao.listarCategorias()) {
				modelo.addRow(new Object[] { c.getIdCategoria(), c.getTipoCategoria(), c.getDescripcion() });
			}
		} catch (Exception e) {
			mostrarError(e);
		}
	}

	// Insertar categoría
	private void agregar() {
		try {
			if (!validar())
				return;

			Categoria c = new Categoria(Integer.parseInt(txtId.getText()), txtTipo.getText(), txtDescripcion.getText());

			dao.insertarCategoria(c);

			JOptionPane.showMessageDialog(this, "Category added successfully");

			cargarCategorias();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	// Llamar procedimiento almacenado
	private void librosPorCategoriaBD() {
		try {
			Connection con = new AccesoBD().conectar();

			CallableStatement cs = con.prepareCall("{CALL LibrosPorCategoria(?)}");
			cs.setInt(1, Integer.parseInt(txtId.getText()));

			ResultSet rs = cs.executeQuery();

			modelo.setRowCount(0);

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("ID_LIBRO"), rs.getString("TITULO"), rs.getString("AUTOR") });
			}

			con.close();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	// Eliminar categoría
	private void eliminar() {
		try {
			int fila = tabla.getSelectedRow();

			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Select a category");
				return;
			}

			int id = (int) tabla.getValueAt(fila, 0);

			dao.borrarCategoria(id);

			cargarCategorias();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	// Acciones de botones
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAgregar)
			agregar();

		if (e.getSource() == btnEliminar)
			eliminar();

		if (e.getSource() == btnVolver)
			dispose();

		// IMPORTANTE: debe coincidir con el texto del botón
		if (e.getActionCommand().equals("See books"))
			librosPorCategoriaBD();
	}
}