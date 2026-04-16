package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import interfaces.InterfazLibro;
import modelo.AccesoBD;
import dao.LibroDAO;

import java.awt.*;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class VentanaLibros extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField txtId, txtTitulo, txtAutor, txtAño;
	private JButton btnAgregar, btnEliminar, btnOrdenar, btnVolver;

	private JTable tabla;
	private DefaultTableModel modelo;

	public VentanaLibros() {

		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Book Management");

		JPanel fondo = new JPanel();
		fondo.setLayout(null);
		fondo.setBackground(new Color(237, 230, 208));
		setContentPane(fondo);

		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBounds(265, 90, 1000, 600);
		card.setBackground(new Color(244, 239, 230));
		card.setBorder(BorderFactory.createLineBorder(new Color(139, 111, 71), 2));
		fondo.add(card);

		JLabel titulo = new JLabel("Book Management");
		titulo.setBounds(300, 20, 400, 30);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(titulo);

		// LABELS
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(new Color(139, 111, 71));
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setBounds(250, 104, 100, 25);
		card.add(lblId);

		JLabel lblTitulo = new JLabel("Qualification");
		lblTitulo.setForeground(new Color(139, 111, 71));
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTitulo.setBounds(550, 104, 100, 25);
		card.add(lblTitulo);

		JLabel lblAutor = new JLabel("Author");
		lblAutor.setForeground(new Color(139, 111, 71));
		lblAutor.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAutor.setBounds(250, 180, 100, 25);
		card.add(lblAutor);

		JLabel lblFecha = new JLabel("Date");
		lblFecha.setForeground(new Color(139, 111, 71));
		lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFecha.setBounds(550, 180, 100, 25);
		card.add(lblFecha);

		// CAMPOS
		txtId = new JTextField();
		txtId.setBounds(350, 100, 150, 35);
		txtId.setBackground(new Color(250, 247, 242));
		txtId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtId);

		txtTitulo = new JTextField();
		txtTitulo.setBounds(650, 100, 150, 35);
		txtTitulo.setBackground(new Color(250, 247, 242));
		txtTitulo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtTitulo);

		txtAutor = new JTextField();
		txtAutor.setBounds(350, 180, 150, 35);
		txtAutor.setBackground(new Color(250, 247, 242));
		txtAutor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtAutor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtAutor);

		txtAño = new JTextField();
		txtAño.setBounds(650, 180, 150, 35);
		txtAño.setBackground(new Color(250, 247, 242));
		txtAño.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtAño.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtAño);

		// BOTONES
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

		btnEliminar = new JButton("Delete");
		btnEliminar.setBounds(333, 280, 150, 40);
		btnEliminar.setBackground(new Color(120, 94, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBorder(null);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(this);
		card.add(btnEliminar);

		btnOrdenar = new JButton("Order");
		btnOrdenar.setBounds(518, 280, 150, 40);
		btnOrdenar.setBackground(new Color(120, 94, 60));
		btnOrdenar.setForeground(Color.WHITE);
		btnOrdenar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnOrdenar.setFocusPainted(false);
		btnOrdenar.setBorder(null);
		btnOrdenar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOrdenar.addActionListener(this);
		card.add(btnOrdenar);

		btnVolver = new JButton("Return");
		btnVolver.setBounds(700, 280, 150, 40);
		btnVolver.setBackground(new Color(120, 94, 60));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(null);
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(this);
		card.add(btnVolver);

		// TABLA
		modelo = new DefaultTableModel(new String[] { "ID", "Qualification", "Author", "Date" }, 0);

		tabla = new JTable(modelo);

		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tabla.setRowHeight(28);
		tabla.setShowGrid(false);
		tabla.setIntercellSpacing(new Dimension(0, 0));

		// colores filas alternas
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

		// selección
		tabla.setSelectionBackground(new Color(200, 183, 156));
		tabla.setSelectionForeground(Color.BLACK);

		// HEADER
		JTableHeader header = tabla.getTableHeader();
		header.setBackground(new Color(120, 94, 60));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header.setBorder(BorderFactory.createEmptyBorder());

		// SCROLL
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(150, 350, 700, 200);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)));

		card.add(scroll);

		// CLICK TABLA
		tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					int fila = tabla.getSelectedRow();
					if (fila < 0)
						return;

					txtId.setText(tabla.getValueAt(fila, 0).toString());
					txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
					txtAutor.setText(tabla.getValueAt(fila, 2).toString());
					txtAño.setText(tabla.getValueAt(fila, 3).toString());
				} catch (Exception ex) {
					mostrarError(ex);
				}
			}
		});
		cargarLibros();
	}

	// VALIDACIÓN
	private boolean validar() {
		if (txtId.getText().isEmpty())
			return mensaje("Mandatory ID");
		if (txtTitulo.getText().isEmpty())
			return mensaje("Mandatory title");
		if (txtAutor.getText().isEmpty())
			return mensaje("Author required");
		if (txtAño.getText().isEmpty())
			return mensaje("Mandatory date");
		return true;
	}

	private boolean mensaje(String msg) {
		JOptionPane.showMessageDialog(this, msg);
		return false;
	}

	// ERRORES
	private void mostrarError(Exception e) {
		String msg = "Error";
		if (e instanceof NumberFormatException)
			msg = "Invalid ID";
		else if (e instanceof SQLException)
			msg = "Error BD";
		else if (e.getMessage() != null)
			msg = e.getMessage();

		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void limpiar() {
		txtId.setText("");
		txtTitulo.setText("");
		txtAutor.setText("");
		txtAño.setText("");
	}

	// CRUD
	private void cargarLibros() {
		try {
			modelo.setRowCount(0);

			InterfazLibro dao = new LibroDAO();
			Map<Integer, String[]> datos = dao.verLibros();

			for (Integer id : datos.keySet()) {
				String[] d = datos.get(id);
				modelo.addRow(new Object[] { id, d[0], d[1], d[2] });
			}

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void agregar() {
		try {
			if (!validar())
				return;

			int id = Integer.parseInt(txtId.getText());

			InterfazLibro dao = new LibroDAO();

			if (dao.existeLibro(id)) {
				JOptionPane.showMessageDialog(this, "ID already exists");
				return;
			}

			dao.insertarLibro(id, txtTitulo.getText(), txtAutor.getText(), txtAño.getText());

			JOptionPane.showMessageDialog(this, "Book added successfully");

			cargarLibros();
			limpiar();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void ejecutarProcedimientoLibros() {
		try {
			AccesoBD bd = new AccesoBD();
			Connection con = bd.conectar();

			CallableStatement cs = con.prepareCall("{CALL TodosLosLibrosOrdenados()}");

			ResultSet rs = cs.executeQuery();

			modelo.setRowCount(0);

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("ID_LIBRO"), rs.getString("TITULO"), rs.getString("AUTOR"),
						rs.getDate("AÑO") });
			}

			rs.close();
			cs.close();
			con.close();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void eliminar() {
		try {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Select a book");
				return;
			}

			int id = (int) tabla.getValueAt(fila, 0);

			int confirm = JOptionPane.showConfirmDialog(this, "Delete book?");
			if (confirm != 0)
				return;

			new LibroDAO().borrarLibro(id);

			cargarLibros();
			limpiar();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAgregar)
			agregar();
		if (e.getSource() == btnEliminar)
			eliminar();
		if (e.getSource() == btnOrdenar)
		    ejecutarProcedimientoLibros();
		if (e.getSource() == btnVolver)
			dispose();
	}
}