package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnLibros;
    private JButton btnPrestamos;
    private JButton btnUsuarios;
    private JButton btnCategorias;
    private JButton btnSalir;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenu frame = new VentanaMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    public VentanaMenu() {

        setTitle("Library");
        setSize(520,420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(244,239,230));
        fondo.setLayout(null);

        setContentPane(fondo);

        JPanel card = new JPanel();
        card.setBounds(50,50,400,300);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200,183,156)));

        fondo.add(card);
        card.setLayout(null);

        JLabel icono = new JLabel("📚");
        icono.setBounds(169, 10, 50, 50);
        icono.setFont(new Font("Segoe UI Emoji",Font.PLAIN,40));
        card.add(icono);

        JLabel titulo = new JLabel("Library");
        titulo.setBounds(148, 58, 150, 30);
        titulo.setFont(new Font("Segoe UI",Font.BOLD,22));
        card.add(titulo);

        btnLibros = new JButton("Books");
        btnLibros.setBounds(30, 110, 160, 30);
        btnLibros.setBackground(new Color(139,111,71));
        btnLibros.setForeground(Color.WHITE);
        btnLibros.setFocusPainted(false);
        btnLibros.setBorderPainted(false);
        btnLibros.addActionListener(this);
        card.add(btnLibros);

        btnPrestamos = new JButton("Loans");
        btnPrestamos.setBounds(210, 110, 160, 30);
        btnPrestamos.setBackground(new Color(139,111,71));
        btnPrestamos.setForeground(Color.WHITE);
        btnPrestamos.setFocusPainted(false);
        btnPrestamos.setBorderPainted(false);
        btnPrestamos.addActionListener(this);
        card.add(btnPrestamos);

        btnUsuarios = new JButton("Users");
        btnUsuarios.setBounds(30, 170, 160, 30);
        btnUsuarios.setBackground(new Color(139,111,71));
        btnUsuarios.setForeground(Color.WHITE);
        btnUsuarios.setFocusPainted(false);
        btnUsuarios.setBorderPainted(false);
        btnUsuarios.addActionListener(this);
        card.add(btnUsuarios);

        btnCategorias = new JButton("Categories");
        btnCategorias.setBounds(210, 170, 160, 30);
        btnCategorias.setBackground(new Color(139,111,71));
        btnCategorias.setForeground(Color.WHITE);
        btnCategorias.setFocusPainted(false);
        btnCategorias.setBorderPainted(false);
        btnCategorias.addActionListener(this);
        card.add(btnCategorias);

        btnSalir = new JButton("Exit");
        btnSalir.setBounds(118, 240, 160, 30);
        btnSalir.setBackground(new Color(139,111,71));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.addActionListener(this);
        card.add(btnSalir);
    }

    private void abrirLibros(){
        VentanaLibros v = new VentanaLibros();
        v.setVisible(true);
    }

    private void abrirPrestamos(){
        VentanaPrestamos v = new VentanaPrestamos();
        v.setVisible(true);
    }

    private void abrirUsuarios(){
        VentanaUsuarios v = new VentanaUsuarios();
        v.setVisible(true);
    }

    private void abrirCategorias(){
        VentanaCategorias v = new VentanaCategorias();
        v.setVisible(true);
    }

    private void cerrar(){
        this.dispose();
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==btnLibros)
            abrirLibros();

        if(e.getSource()==btnPrestamos)
            abrirPrestamos();

        if(e.getSource()==btnUsuarios)
            abrirUsuarios();

        if(e.getSource()==btnCategorias)
            abrirCategorias();

        if(e.getSource()==btnSalir)
            cerrar();
    }
}