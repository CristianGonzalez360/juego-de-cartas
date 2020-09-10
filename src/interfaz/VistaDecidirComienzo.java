package interfaz;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import negocio.Juego;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class VistaDecidirComienzo extends JPanel {

	
	private JPanel panelBotones;
	private Juego juego;
	private JLabel lblMensaje;
	private JButton rival;
	
	public VistaDecidirComienzo() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setBackground(Color.black);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(Graficos.getTamañoTablero());
		panelPrincipal.setLayout(null);
		add(panelPrincipal);
		
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		panelBotones.setOpaque(false);
		panelBotones.setBounds(Graficos.getPosicionDados());
		panelPrincipal.add(panelBotones);
		
		JButton yo = crearBoton(0, "/Iconos/Scoiatael.png");
		yo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juego.decidirComienzo(true, 0);
			}
		});
		panelBotones.add(yo);
		
		lblMensaje = new JLabel("¿Quién comienza la partida?");
		lblMensaje.setForeground(Color.WHITE);
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setFont(new Font("gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		lblMensaje.setBounds(Graficos.getPosicionMensajeDados());
		panelPrincipal.add(lblMensaje);
		
		JLabel fondo = new JLabel();
		fondo.setHorizontalAlignment(SwingConstants.CENTER);
		fondo.setBounds(new Rectangle(new Point(0, 0), Graficos.getTamañoTablero()));
		fondo.setIcon(Graficos.getImagenFondoEditor());
		panelPrincipal.add(fondo);
	}
	
	private JButton crearBoton(int i, String rutaIcono) {
		JButton ret = new JButton();
		ret.setIcon(Graficos.getIconoMazo(rutaIcono));
		ret.setPreferredSize(Graficos.getTamañoDado());
		ret.setContentAreaFilled(false);
		ret.setBorder(null);
		ret.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ret.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.ORANGE));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ret.setBorder(null);
			}
		});
		return ret;
	}
	
	public void mostrarMensaje(String mensaje) {
		this.lblMensaje.setText(mensaje);
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public void setIconoRival(String rutaIcono) {
		if(rival != null) {
			this.panelBotones.remove(rival);
		}
		rival = crearBoton(1, rutaIcono);
		rival.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juego.decidirComienzo(false, 0);
			}
		});
		this.panelBotones.add(rival);
	}
}
