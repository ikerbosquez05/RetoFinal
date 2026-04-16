package ventanas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaPrestamos extends JFrame implements ActionListener {

    private JTextField txtLibro;
    private JTextField txtFecha;

    private JButton btnRegistrar;
    private JButton btnVolver;

    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaPrestamos(){

        setTitle("Préstamos");
        setSize(600,420);
        setLocationRelativeTo(null);

        JPanel fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(new Color(244,239,230));

        setContentPane(fondo);

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(50,40,500,320);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200,183,156)));

        fondo.add(card);

        JLabel titulo = new JLabel("Gestión de Préstamos");
        titulo.setFont(new Font("Segoe UI",Font.BOLD,20));
        titulo.setBounds(150,10,220,30);

        card.add(titulo);

        JLabel lblLibro = new JLabel("Libro");
        lblLibro.setBounds(40,70,80,20);
        card.add(lblLibro);

        txtLibro = new JTextField();
        txtLibro.setBounds(100,70,150,25);
        card.add(txtLibro);

        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setBounds(260,70,80,20);
        card.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(310,70,150,25);
        card.add(txtFecha);

        btnRegistrar = crearBoton("Registrar préstamo",120);
        btnVolver = crearBoton("Volver",160);

        card.add(btnRegistrar);
        card.add(btnVolver);

        String[] columnas = {"Libro","Fecha"};

        modelo = new DefaultTableModel(columnas,0);

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(40,200,420,80);

        card.add(scroll);
    }

    private JButton crearBoton(String texto,int y){

        JButton b = new JButton(texto);

        b.setBounds(170,y,180,30);

        b.setBackground(new Color(139,111,71));
        b.setForeground(Color.WHITE);

        b.setFocusPainted(false);
        b.setBorderPainted(false);

        b.addActionListener(this);

        return b;
    }

    private void registrarPrestamo(){

        String libro = txtLibro.getText();
        String fecha = txtFecha.getText();

        modelo.addRow(new Object[]{libro,fecha});

        txtLibro.setText("");
        txtFecha.setText("");
    }

    private void volver(){
        this.dispose();
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==btnRegistrar)
            registrarPrestamo();

        if(e.getSource()==btnVolver)
            volver();
    }
}