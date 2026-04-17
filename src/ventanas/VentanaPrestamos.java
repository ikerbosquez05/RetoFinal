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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Ventana de gestión de préstamos de la biblioteca.
 * <p>
 * Proporciona una interfaz gráfica completa para realizar operaciones CRUD
 * (Create, Read, Update, Delete) sobre los préstamos almacenados en la base de datos.
 * Incluye un formulario con los datos del préstamo, una tabla de visualización,
 * botones de acción y la posibilidad de filtrar préstamos por usuario mediante
 * un procedimiento almacenado.
 * </p>
 *
 * @author [Autor]
 * @version 1.0
 * @see InterfazPrestamo
 * @see PrestamoDAO
 * @see AccesoBD
 */
public class VentanaPrestamos extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Campos de texto del formulario.
	 * <ul>
	 *   <li>{@code txtId} – Identificador único del préstamo.</li>
	 *   <li>{@code txtUsuario} – ID del usuario que realiza el préstamo.</li>
	 *   <li>{@code txtLibro} – ID del libro prestado.</li>
	 *   <li>{@code txtFechaPrestamo} – Fecha de inicio del préstamo (formato {@code yyyy-MM-dd}).</li>
	 *   <li>{@code txtFechaLimite} – Fecha límite de devolución (formato {@code yyyy-MM-dd}).</li>
	 *   <li>{@code txtFechaDevolucion} – Fecha real de devolución (puede estar vacía si no se ha devuelto).</li>
	 * </ul>
	 */
	private JTextField txtId, txtUsuario, txtLibro, txtFechaPrestamo, txtFechaLimite, txtFechaDevolucion;

	/** Botón para registrar un nuevo préstamo en la base de datos. */
	private JButton btnAgregar;

	/** Botón para eliminar el préstamo seleccionado en la tabla. */
	private JButton btnEliminar;

	/** Botón para modificar los datos del préstamo seleccionado. */
	private JButton btnModificar;

	/** Botón para cerrar esta ventana y volver a la pantalla anterior. */
	private JButton btnVolver;

	/** Tabla que muestra el listado de préstamos cargados desde la base de datos. */
	private JTable tabla;

	/** Modelo de datos que gestiona las filas y columnas de la tabla de préstamos. */
	private DefaultTableModel modelo;

	/**
	 * Constructor de la ventana de gestión de préstamos.
	 * <p>
	 * Inicializa y configura todos los componentes de la interfaz gráfica:
	 * panel de fondo, tarjeta principal, etiquetas, campos de texto,
	 * botones de acción, tabla de préstamos con scroll y listeners de eventos.
	 * Al finalizar la construcción, carga automáticamente los préstamos existentes
	 * mediante {@link #cargarPrestamos()}.
	 * </p>
	 */
	public VentanaPrestamos() {

		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Loan Management");

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

		JLabel titulo = new JLabel("Loan Management");
		titulo.setBounds(300, 20, 400, 30);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		card.add(titulo);

		// LABELS
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(new Color(139, 111, 71));
		lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblId.setBounds(200, 79, 100, 25);
		card.add(lblId);

		JLabel lblUsuario = new JLabel("User");
		lblUsuario.setForeground(new Color(139, 111, 71));
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblUsuario.setBounds(480, 79, 100, 25);
		card.add(lblUsuario);

		JLabel lblLibro = new JLabel("Book");
		lblLibro.setForeground(new Color(139, 111, 71));
		lblLibro.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblLibro.setBounds(200, 154, 100, 25);
		card.add(lblLibro);

		JLabel lblFechaPrestamo = new JLabel("Loan Date");
		lblFechaPrestamo.setForeground(new Color(139, 111, 71));
		lblFechaPrestamo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFechaPrestamo.setBounds(480, 154, 120, 25);
		card.add(lblFechaPrestamo);

		JLabel lblFechaLimite = new JLabel("Deadline");
		lblFechaLimite.setForeground(new Color(139, 111, 71));
		lblFechaLimite.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFechaLimite.setBounds(200, 231, 120, 25);
		card.add(lblFechaLimite);

		JLabel lblFechaDevolucion = new JLabel("Return Date");
		lblFechaDevolucion.setForeground(new Color(139, 111, 71));
		lblFechaDevolucion.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFechaDevolucion.setBounds(480, 231, 150, 25);
		card.add(lblFechaDevolucion);

		// CAMPOS
		txtId = new JTextField();
		txtId.setBounds(300, 75, 150, 35);
		txtId.setBackground(new Color(250, 247, 242));
		txtId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtId);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(610, 75, 150, 35);
		txtUsuario.setBackground(new Color(250, 247, 242));
		txtUsuario
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtUsuario);

		txtLibro = new JTextField();
		txtLibro.setBounds(300, 150, 150, 35);
		txtLibro.setBackground(new Color(250, 247, 242));
		txtLibro.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtLibro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtLibro);

		txtFechaPrestamo = new JTextField();
		txtFechaPrestamo.setBounds(610, 150, 150, 35);
		txtFechaPrestamo.setBackground(new Color(250, 247, 242));
		txtFechaPrestamo
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtFechaPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtFechaPrestamo);

		txtFechaLimite = new JTextField();
		txtFechaLimite.setBounds(300, 225, 150, 35);
		txtFechaLimite.setBackground(new Color(250, 247, 242));
		txtFechaLimite
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtFechaLimite.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtFechaLimite);

		txtFechaDevolucion = new JTextField();
		txtFechaDevolucion.setBounds(610, 225, 150, 35);
		txtFechaDevolucion.setBackground(new Color(250, 247, 242));
		txtFechaDevolucion
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 183, 156)),
						BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		txtFechaDevolucion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		card.add(txtFechaDevolucion);

		// BOTONES
		btnAgregar = new JButton("Add");
		btnAgregar.setBounds(74, 285, 150, 40);
		btnAgregar.setBackground(new Color(120, 94, 60));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnAgregar.setFocusPainted(false);
		btnAgregar.setBorder(null);
		btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAgregar.addActionListener(this);
		card.add(btnAgregar);

		btnModificar = new JButton("Modify");
		btnModificar.setBounds(247, 285, 150, 40);
		btnModificar.setBackground(new Color(120, 94, 60));
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnModificar.setFocusPainted(false);
		btnModificar.setBorder(null);
		btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnModificar.addActionListener(this);
		card.add(btnModificar);

		btnEliminar = new JButton("Delete");
		btnEliminar.setBounds(421, 285, 150, 40);
		btnEliminar.setBackground(new Color(120, 94, 60));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBorder(null);
		btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEliminar.addActionListener(this);
		card.add(btnEliminar);

		btnVolver = new JButton("Return");
		btnVolver.setBounds(592, 285, 150, 40);
		btnVolver.setBackground(new Color(120, 94, 60));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(null);
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(this);
		card.add(btnVolver);

		JButton btnVerPrestamos = new JButton("See Users");
		btnVerPrestamos.setBounds(765, 285, 150, 40);
		btnVerPrestamos.setBackground(new Color(120, 94, 60));
		btnVerPrestamos.setForeground(Color.WHITE);
		btnVerPrestamos.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnVerPrestamos.setFocusPainted(false);
		btnVerPrestamos.setBorder(null);
		btnVerPrestamos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVerPrestamos.addActionListener(this);
		card.add(btnVerPrestamos);

		// TABLA
		modelo = new DefaultTableModel(
				new String[] { "ID", "User", "Book", "Loan Date", "Deadline", "Return Date" }, 0);

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
					txtUsuario.setText(tabla.getValueAt(fila, 1).toString());
					txtLibro.setText(tabla.getValueAt(fila, 2).toString());
					txtFechaPrestamo.setText(tabla.getValueAt(fila, 3).toString());
					txtFechaLimite.setText(tabla.getValueAt(fila, 4).toString());
					txtFechaDevolucion.setText(tabla.getValueAt(fila, 5).toString());

				} catch (Exception ex) {
					mostrarError(ex);
				}
			}
		});

		cargarPrestamos();
	}

	/**
	 * Valida que los campos obligatorios del formulario de préstamo contengan datos válidos.
	 * <p>
	 * Realiza las siguientes comprobaciones en orden:
	 * <ul>
	 *   <li>El campo ID no está vacío y contiene un valor numérico entero.</li>
	 *   <li>Los campos Usuario, Libro, Fecha de Préstamo y Fecha Límite no están vacíos.</li>
	 *   <li>La Fecha de Préstamo cumple el formato {@code yyyy-MM-dd}.</li>
	 * </ul>
	 * El campo Fecha de Devolución es opcional y no se valida aquí.
	 * Si alguna comprobación falla, se muestra un mensaje al usuario.
	 * </p>
	 *
	 * @return {@code true} si todos los campos obligatorios son válidos; {@code false} en caso contrario.
	 */
	private boolean validarCampos() {

		if (txtId.getText().isEmpty())
			return mensaje("Mandatory ID");

		try {
			Integer.parseInt(txtId.getText());
		} catch (Exception e) {
			return mensaje("Numeric ID");
		}

		if (txtUsuario.getText().isEmpty())
			return mensaje("Mandatory user");
		if (txtLibro.getText().isEmpty())
			return mensaje("Mandatory book");
		if (txtFechaPrestamo.getText().isEmpty())
			return mensaje("Mandatory loan date");
		if (txtFechaLimite.getText().isEmpty())
			return mensaje("Mandatory deadline");

		if (!txtFechaPrestamo.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
			return mensaje("Invalid date format (yyyy-MM-dd)");

		return true;
	}

	/**
	 * Muestra un cuadro de diálogo informativo con el mensaje indicado.
	 * <p>
	 * Método auxiliar utilizado para mostrar errores de validación del formulario.
	 * Siempre retorna {@code false} para su uso directo como valor de retorno
	 * en métodos booleanos de validación.
	 * </p>
	 *
	 * @param msg el texto del mensaje a mostrar en el diálogo.
	 * @return {@code false} siempre, para uso directo como valor de retorno en validaciones.
	 */
	private boolean mensaje(String msg) {
		JOptionPane.showMessageDialog(this, msg);
		return false;
	}

	/**
	 * Muestra un cuadro de diálogo de error con información sobre la excepción recibida.
	 * <p>
	 * El mensaje mostrado varía según el tipo de excepción:
	 * <ul>
	 *   <li>{@link NumberFormatException}: indica número inválido.</li>
	 *   <li>{@link SQLException}: indica error de base de datos.</li>
	 *   <li>Cualquier otra excepción: muestra su mensaje si está disponible, o un genérico "Error".</li>
	 * </ul>
	 * </p>
	 *
	 * @param e la excepción capturada que originó el error.
	 */
	private void mostrarError(Exception e) {
		String msg = "Error";
		if (e instanceof NumberFormatException)
			msg = "Invalid number";
		else if (e instanceof SQLException)
			msg = "Error BD";
		else if (e.getMessage() != null)
			msg = e.getMessage();

		JOptionPane.showMessageDialog(this, msg);
	}

	/**
	 * Limpia el contenido de todos los campos de texto del formulario de préstamo.
	 * <p>
	 * Debe invocarse tras completar exitosamente una operación de alta,
	 * modificación o eliminación, dejando el formulario listo para una nueva entrada.
	 * </p>
	 */
	private void limpiar() {
		txtId.setText("");
		txtUsuario.setText("");
		txtLibro.setText("");
		txtFechaPrestamo.setText("");
		txtFechaLimite.setText("");
		txtFechaDevolucion.setText("");
	}

	/**
	 * Carga y refresca la tabla de préstamos con los datos actuales de la base de datos.
	 * <p>
	 * Vacía primero todas las filas existentes en el modelo de la tabla y luego
	 * consulta el DAO para obtener el mapa actualizado de préstamos, añadiendo
	 * una fila por cada entrada encontrada con sus seis columnas:
	 * ID, usuario, libro, fecha de préstamo, fecha límite y fecha de devolución.
	 * </p>
	 */
	private void cargarPrestamos() {
		try {
			modelo.setRowCount(0);

			InterfazPrestamo dao = new PrestamoDAO();
			Map<Integer, String[]> datos = dao.verPrestamos();

			for (Integer id : datos.keySet()) {
				String[] d = datos.get(id);
				modelo.addRow(new Object[] { id, d[0], d[1], d[2], d[3], d[4] });
			}

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	/**
	 * Registra un nuevo préstamo en la base de datos con los datos del formulario.
	 * <p>
	 * Flujo de ejecución:
	 * <ol>
	 *   <li>Valida los campos obligatorios mediante {@link #validarCampos()}.</li>
	 *   <li>Invoca el método de inserción del DAO con los valores del formulario.</li>
	 *   <li>Recarga la tabla y limpia el formulario.</li>
	 * </ol>
	 * La fecha de devolución se envía tal como está en el campo, pudiendo estar vacía
	 * si el libro aún no ha sido devuelto.
	 * </p>
	 */
	private void agregar() {
		try {
			if (!validarCampos())
				return;

			InterfazPrestamo dao = new PrestamoDAO();

			dao.insertarPrestamo(Integer.parseInt(txtId.getText()), Integer.parseInt(txtUsuario.getText()),
					Integer.parseInt(txtLibro.getText()), txtFechaPrestamo.getText(), txtFechaLimite.getText(),
					txtFechaDevolucion.getText());

			cargarPrestamos();
			limpiar();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	/**
	 * Filtra y muestra en la tabla los préstamos activos de un usuario concreto.
	 * <p>
	 * Ejecuta el procedimiento almacenado {@code VerPrestamosUsuario(id_usuario)}
	 * usando el ID introducido en {@code txtUsuario}. El resultado reemplaza
	 * el contenido actual de la tabla con las columnas:
	 * ID_PRESTAMO, NOMBRE, TITULO, FECHA_PRESTAMO y FECHA_LIMITE.
	 * </p>
	 *
	 * @throws NumberFormatException si {@code txtUsuario} no contiene un entero válido.
	 * @throws SQLException          si ocurre un error al ejecutar el procedimiento almacenado.
	 */
	private void verPrestamosUsuarioBD() {
		try {
			AccesoBD bd = new AccesoBD();
			Connection con = bd.conectar();

			CallableStatement cs = con.prepareCall("{CALL VerPrestamosUsuario(?)}");
			cs.setInt(1, Integer.parseInt(txtUsuario.getText()));

			ResultSet rs = cs.executeQuery();

			modelo.setRowCount(0);

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("ID_PRESTAMO"), rs.getString("NOMBRE"), rs.getString("TITULO"),
						rs.getDate("FECHA_PRESTAMO"), rs.getDate("FECHA_LIMITE") });
			}

			rs.close();
			cs.close();
			con.close();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	/**
	 * Modifica los datos de un préstamo existente con la información actual del formulario.
	 * <p>
	 * Primero valida los campos obligatorios mediante {@link #validarCampos()}.
	 * Si son válidos, delega la actualización en el DAO usando el ID del préstamo
	 * como clave de búsqueda. Tras la operación, recarga la tabla y limpia el formulario.
	 * </p>
	 */
	private void modificar() {
		try {
			if (!validarCampos())
				return;

			InterfazPrestamo dao = new PrestamoDAO();

			dao.modificarPrestamo(Integer.parseInt(txtId.getText()), Integer.parseInt(txtUsuario.getText()),
					Integer.parseInt(txtLibro.getText()), txtFechaPrestamo.getText(), txtFechaLimite.getText(),
					txtFechaDevolucion.getText());

			cargarPrestamos();
			limpiar();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	/**
	 * Elimina el préstamo actualmente seleccionado en la tabla de la base de datos.
	 * <p>
	 * Requiere que haya una fila seleccionada en la tabla. Solicita confirmación
	 * explícita al usuario antes de proceder con el borrado. Tras la eliminación,
	 * recarga la tabla y limpia el formulario.
	 * </p>
	 */
	private void eliminar() {
		try {
			int fila = tabla.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(this, "Select a loan");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, "Cancel loan?");
			if (confirm != 0)
				return;

			int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

			InterfazPrestamo dao = new PrestamoDAO();
			dao.borrarPrestamo(id);

			cargarPrestamos();
			limpiar();

		} catch (Exception e) {
			mostrarError(e);
		}
	}

	/**
	 * Maneja los eventos de acción generados por los botones de la interfaz.
	 * <p>
	 * Despacha cada evento al método correspondiente según el botón pulsado:
	 * <ul>
	 *   <li>{@code btnAgregar}   → {@link #agregar()}</li>
	 *   <li>{@code btnModificar} → {@link #modificar()}</li>
	 *   <li>{@code btnEliminar}  → {@link #eliminar()}</li>
	 *   <li>{@code btnVolver}    → cierra la ventana con {@code dispose()}</li>
	 *   <li>{@code "See Users"}  → {@link #verPrestamosUsuarioBD()}</li>
	 * </ul>
	 * </p>
	 *
	 * @param e el evento de acción generado por el componente pulsado.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnAgregar)
				agregar();
			if (e.getSource() == btnModificar)
				modificar();
			if (e.getSource() == btnEliminar)
				eliminar();
			if (e.getSource() == btnVolver)
				dispose();
			if (e.getActionCommand().equals("See Users"))
				verPrestamosUsuarioBD();
		} catch (Exception ex) {
			mostrarError(ex);
		}
	}
}