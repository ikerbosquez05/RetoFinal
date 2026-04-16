package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import interfaces.*;
import modelo.AccesoBD;
import dao.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class VentanaUsuarios extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField txtId, txtNombre, txtEmail, txtTelefono;
	private JButton btnAgregar, btnEliminar, btnModificar, btnVolver;

	private JTable tabla;
	private DefaultTableModel modelo;

	public VentanaUsuarios() {

		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("User Management");

		JPanel fondo = new JPanel();
		fondo.setLayout(null);
		fondo.setBackground(new Color(237, 230, 208));
		setContentPane(fondo);

		// CARD
		JPanel card = new JPanel();
		card.setLayout(null);
		card.setBounds(265, 90, 1000, 600);
		card.setBackground(new Color(244, 239, 230));
		card.setBorder(BorderFactory.createLineBorder(new Color(139, 111, 71), 2));
		fondo.add(card);

		JLabel titulo = new JLabel("User Management");
		titulo.setBounds(300, 20, 400, 30);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(titulo);

		// LABELS
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(250, 100, 100, 25);
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setForeground(new Color(139, 111, 71));
		card.add(lblId);

		JLabel lblNombre = new JLabel("Name");
		lblNombre.setBounds(550, 100, 100, 25);
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNombre.setForeground(new Color(139, 111, 71));
		card.add(lblNombre);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(250, 180, 100, 25);
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblEmail.setForeground(new Color(139, 111, 71));
		card.add(lblEmail);

		JLabel lblTelefono = new JLabel("Phone");
		lblTelefono.setBounds(550, 180, 100, 25);
		lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTelefono.setForeground(new Color(139, 111, 71));
		card.add(lblTelefono);

		// CAMPOS
		txtId = new JTextField();
		txtId.setBounds(350, 100, 150, 35);
		txtId.setBackground(new Color(250, 247, 242));
		txtId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtId);

		txtNombre = new JTextField();
		txtNombre.setBounds(650, 100, 150, 35);
		txtNombre.setBackground(new Color(250, 247, 242));
		txtNombre.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtNombre);

		txtEmail = new JTextField();
		txtEmail.setBounds(350, 180, 150, 35);
		txtEmail.setBackground(new Color(250, 247, 242));
		txtEmail.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtEmail);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(650, 180, 150, 35);
		txtTelefono.setBackground(new Color(250, 247, 242));
		txtTelefono
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtTelefono);

		// BOTONES
		btnAgregar = new JButton("Add");
		btnAgregar.setBounds(64, 280, 150, 40);
		btnAgregar.setBackground(new Color(120, 94, 60));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAgregar.setFocusPainted(false);
		btnAgregar.setBorder(null);
		btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAgregar.addActionListener(this);
		card.add(btnAgregar);

		btnModificar = new JButton("Modify");
		btnModificar.setBounds(243, 280, 150, 40);
		btnModificar.setBackground(new Color(120, 94, 60));
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnModificar.setFocusPainted(false);
		btnModificar.setBorder(null);
		btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnModificar.addActionListener(this);
		card.add(btnModificar);

		btnEliminar = new JButton("Delete");
		btnEliminar.setBounds(417, 280, 150, 40);
		btnEliminar.setBackground(new Color(120, 94, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBorder(null);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(this);
		card.add(btnEliminar);

		btnVolver = new JButton("Return");
		btnVolver.setBounds(591, 280, 150, 40);
		btnVolver.setBackground(new Color(120, 94, 60));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(null);
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(this);
		card.add(btnVolver);

		JButton btnExiste = new JButton("Exists?");
		btnExiste.setBounds(761, 280, 150, 40);
		btnExiste.setBackground(new Color(120, 94, 60));
		btnExiste.setForeground(Color.WHITE);
		btnExiste.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnExiste.setFocusPainted(false);
		btnExiste.setBorder(null);
		btnExiste.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExiste.addActionListener(this);
		card.add(btnExiste);

		// TABLA
		modelo = new DefaultTableModel(new String[] { "ID", "Name", "Email", "Phone" }, 0);
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
					txtNombre.setText(tabla.getValueAt(fila, 1).toString());
					txtEmail.setText(tabla.getValueAt(fila, 2).toString());
					txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
				} catch (Exception ex) {
					mostrarError(ex);
				}
			}
		});

		cargarUsuarios();
	}

	// VALIDACIÓN
	private boolean validarCamposUsuario() {

		if (txtId.getText().trim().isEmpty())
			return mensaje("ID is required");

		try {
			Integer.parseInt(txtId.getText());
		} catch (NumberFormatException e) {
			return mensaje("The ID must be numeric.");
		}

		if (txtNombre.getText().trim().isEmpty())
			return mensaje("The name is required");

		if (txtEmail.getText().trim().isEmpty())
			return mensaje("Email is mandatory");

		String email = txtEmail.getText().trim();
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
			return mensaje("Invalid email");

		if (txtTelefono.getText().trim().isEmpty())
			return mensaje("The telephone is mandatory");

		try {
			Integer.parseInt(txtTelefono.getText());
		} catch (NumberFormatException e) {
			return mensaje("The phone number must be numeric.");
		}

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
			msg = "Invalid number";
		else if (e instanceof SQLException)
			msg = "Database error";
		else if (e.getMessage() != null)
			msg = e.getMessage();

		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// ID DUPLICADO
	private boolean idExiste(int id) {
		try {
			InterfazUsuario dao = new UsuarioDAO();
			Map<Integer, String[]> datos = dao.verUsuarios();
			return datos.containsKey(id);
		} catch (Exception e) {
			mostrarError(e);
			return true;
		}
	}

	// LIMPIAR
	private void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
	}

	// CRUD
	private void cargarUsuarios() {
		try {
			modelo.setRowCount(0);
			InterfazUsuario dao = new UsuarioDAO();
			Map<Integer, String[]> datos = dao.verUsuarios();

			for (Integer id : datos.keySet()) {
				String[] d = datos.get(id);
				modelo.addRow(new Object[] { id, d[0], d[1], d[2] });
			}
		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void agregarUsuario() {
		try {
			if (!validarCamposUsuario())
				return;

			int id = Integer.parseInt(txtId.getText());

			if (idExiste(id)) {
				JOptionPane.showMessageDialog(this, "The ID already exists");
				return;
			}

			InterfazUsuario dao = new UsuarioDAO();
			dao.insertarUsuario(id, txtNombre.getText(), txtEmail.getText(), Integer.parseInt(txtTelefono.getText()));

			cargarUsuarios();
			limpiarCampos();

			JOptionPane.showMessageDialog(this, "User successfully added");

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void comprobarUsuarioBD() {
		try {
			AccesoBD bd = new AccesoBD();
			Connection con = bd.conectar();

			PreparedStatement ps = con.prepareStatement("SELECT ExisteUsuario(?)");
			ps.setInt(1, Integer.parseInt(txtId.getText()));

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int existe = rs.getInt(1);

				if (existe == 1) {
					JOptionPane.showMessageDialog(this, "The user EXISTS");
				} else {
					JOptionPane.showMessageDialog(this, "The user does NOT exist");
				}
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void modificarUsuario() {
		try {
			if (!validarCamposUsuario())
				return;

			InterfazUsuario dao = new UsuarioDAO();
			dao.modificarUsuario(Integer.parseInt(txtId.getText()), txtNombre.getText(), txtEmail.getText(),
					Integer.parseInt(txtTelefono.getText()));

			cargarUsuarios();
			limpiarCampos();

			JOptionPane.showMessageDialog(this, "Successfully modified user");

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	private void eliminarUsuario() {
		try {
			int fila = tabla.getSelectedRow();

			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Select a user");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this user?\nThis action cannot be undone.",
					"Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm != JOptionPane.YES_OPTION)
				return;

			int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

			InterfazUsuario dao = new UsuarioDAO();
			dao.borrarUsuario(id);

			cargarUsuarios();
			limpiarCampos();

			JOptionPane.showMessageDialog(this, "Successfully deleted user");

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnAgregar)
				agregarUsuario();
			if (e.getSource() == btnModificar)
				modificarUsuario();
			if (e.getSource() == btnEliminar)
				eliminarUsuario();
			if (e.getSource() == btnVolver)
				dispose();
			if (e.getActionCommand().equals("Exists?"))
				comprobarUsuarioBD();
		} catch (Exception ex) {
			mostrarError(ex);
		}
	}
}