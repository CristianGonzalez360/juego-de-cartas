package interfaz;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import red.Red;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;

public class VistaRed extends BackgroundPanel {
	
	private JTextField textFieldIP;
	private JTextField textFieldPuerto;
	private Red red;
	private JTextArea taMensaje;
	private JButton btnCrear;
	private JButton btnUnirse;
	private JButton btnCancelar;
	private JScrollPane scrollPane;
	private JButton btnSalir;
	public VistaRed() {
		super(Graficos.getImagenFondo());
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(new Dimension(280, 300));
		panelPrincipal.setOpaque(false);
		panelPrincipal.setLayout(null);
		
		JLabel lblDireccinIp = new JLabel("Direccion IP:");
		lblDireccinIp.setForeground(Color.ORANGE);
		lblDireccinIp.setFont(new Font("thewitcher", Font.PLAIN, 13));
		lblDireccinIp.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblDireccinIp.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccinIp.setBounds(10, 9, 80, 14);
		panelPrincipal.add(lblDireccinIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldIP.setBounds(100, 6, 80, 20);
		textFieldIP.setText("127.0.0.1");
		panelPrincipal.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setForeground(Color.ORANGE);
		lblPuerto.setFont(new Font("thewitcher", Font.PLAIN, 13));
		lblPuerto.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setBounds(10, 40, 80, 14);
		panelPrincipal.add(lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPuerto.setBounds(100, 37, 80, 20);
		textFieldPuerto.setText("2222");
		panelPrincipal.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		btnCrear = new JButton("Crear");
		btnCrear.setFont(new Font("thewitcher", Font.PLAIN, 13));
		btnCrear.setBounds(190, 5, 80, 23);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonCrear(false);
				botonCancelar(true);
				botonUnirse(false);			
				botonSalir(false);
				red.crear(Integer.parseInt(textFieldPuerto.getText()));
			}
		});
		panelPrincipal.add(btnCrear);
		
		btnUnirse = new JButton("Unirse");
		btnUnirse.setFont(new Font("thewitcher", Font.PLAIN, 13));
		btnUnirse.setBounds(190, 36, 80, 23);
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonCrear(false);
				botonCancelar(false);
				botonUnirse(false);		
				botonSalir(false);
				red.conectar(textFieldIP.getText(), Integer.parseInt(textFieldPuerto.getText()));
			}
		});
		panelPrincipal.add(btnUnirse);
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 101, 260, 188);
		panelPrincipal.add(scrollPane);
		taMensaje = new JTextArea();
		taMensaje.setBorder(null);
		taMensaje.setColumns(10);
		taMensaje.setEditable(false);
		taMensaje.setFocusable(false);
		taMensaje.setForeground(Color.WHITE);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setViewportView(taMensaje);
		
		add(panelPrincipal);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("thewitcher", Font.PLAIN, 13));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				red.cancelarServidor();
				reiniciar();
			}
		});
		btnCancelar.setBounds(190, 5, 80, 23);
		botonCancelar(false);
		panelPrincipal.add(btnCancelar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("thewitcher", Font.PLAIN, 13));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(190, 67, 80, 23);
		panelPrincipal.add(btnSalir);
		
	}
	public void setRed(Red red) {
		this.red = red;		
	}
	public void mostrarMensaje(String mensaje) {
		this.taMensaje.append(mensaje + "\n");
	}
	
	public void botonUnirse(boolean estado){
		this.btnUnirse.setVisible(estado);
	}
	
	public void botonCrear(boolean estado){
		this.btnCrear.setVisible(estado);
	}
	
	public void botonCancelar(boolean estado){
		this.btnCancelar.setVisible(estado);
	}
	
	public void botonSalir(boolean estado) {
		btnSalir.setVisible(estado);
	}
	
	public void reiniciar() {
		botonCrear(true);
		botonCancelar(false);
		botonUnirse(true);		
		botonSalir(true);
	}
}
