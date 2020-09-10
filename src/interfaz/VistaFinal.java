package interfaz;

import javax.swing.JPanel;

import negocio.Juego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class VistaFinal extends BackgroundPanel {
	
	private Juego juego;
	private JLabel lblResultado;
	private JLabel resultadoRondas[];
	private JPanel panelRecompensa;
	private JPanel panelResultado;
	private JLabel[] iconos;
	private JButton botonEditarMazo;
	private JButton botonRevancha;
	private JButton botonAbandonar;
	
	public VistaFinal() {
		super(Graficos.getImagenFinal());
		this.iconos = new JLabel[2];
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(Graficos.ANCHO_PANTALLA, Graficos.ALTO_PANTALLA));
		
		JPanel panelSur = new JPanel();
		panelSur.setOpaque(false);
		add(panelSur, BorderLayout.SOUTH);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		panelBotones.setPreferredSize(new Dimension(200, 95));
		panelSur.add(panelBotones);
		
		botonRevancha = new JButton("Revancha");
		botonRevancha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonRevancha.setText("Esperando al rival");
				bloquear();
				juego.revancha();
			}
		});
		botonRevancha.setPreferredSize(new Dimension(190, 25));
		panelBotones.add(botonRevancha);
		
		botonEditarMazo = new JButton("Editar Mazo");
		botonEditarMazo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonEditarMazo.setText("Esperando al rival");
				bloquear();
				juego.editar();
			}
		});
		botonEditarMazo.setPreferredSize(new Dimension(190, 25));
		panelBotones.add(botonEditarMazo);
		
		botonAbandonar = new JButton("Abandonar");
		botonAbandonar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juego.abandonar(0);
			}
		});
		botonAbandonar.setPreferredSize(new Dimension(190, 25));
		panelBotones.add(botonAbandonar);
		
		panelRecompensa = new JPanel();
		Dimension d = Graficos.getTamañoPanelResultado();
		panelRecompensa.setOpaque(false);
		panelResultado = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelResultado.setBorder(new LineBorder(Color.ORANGE));
		panelResultado.setBackground(Color.BLACK);
		panelResultado.setPreferredSize(d);
		panelRecompensa.add(panelResultado);
		
		JPanel panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(panelRecompensa);
		add(panelCentral, BorderLayout.CENTER);
				
		lblResultado = new JLabel();
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setFont(new Font("Gwent", Font.BOLD, Graficos.getTamañoFuente(80)));
		lblResultado.setForeground(Color.ORANGE);
		add(lblResultado, BorderLayout.NORTH);
		
		this.resultadoRondas = new JLabel[3];
		for(int i = 0; i<3; i++) {
			this.resultadoRondas[i] = new JLabel();
			this.resultadoRondas[i].setPreferredSize(new Dimension(d.width, d.height / 3));
			this.resultadoRondas[i].setHorizontalAlignment(SwingConstants.CENTER);
			this.resultadoRondas[i].setFont(new Font("Gwent", Font.BOLD, Graficos.getTamañoFuente(50)));
			this.resultadoRondas[i].setForeground(Color.ORANGE);
			panelResultado.add(this.resultadoRondas[i]);
		}
	}
	
	public void setJuego(Juego juego) {
		this.juego = juego;
	}
	
	public void mostrarResultado(String descripcion, int[][] resultado) {
		this.lblResultado.setText(descripcion);
		for(int i = 0; i<3; i++) {
			int ronda = i + 1;
			this.resultadoRondas[i].setText("Ronda " + ronda + ": " + resultado[i][0] + " - " + resultado[i][1]);;
		}
	}

	public void setIcono(String rutaIcono, int jugador) {
		if(this.iconos[jugador] != null) {
			this.panelRecompensa.remove(this.iconos[jugador]);
		}
		this.iconos[jugador] = new JLabel(Graficos.getIconoMazo(rutaIcono));
		
		this.panelRecompensa.add(iconos[jugador], jugador * -1);	
	}
	
	public void bloquear() {
		this.botonRevancha.setEnabled(false);
		this.botonEditarMazo.setEnabled(false);
		this.botonAbandonar.setEnabled(false);
	}
	
	public void desbloquear() {
		this.botonRevancha.setEnabled(true);
		this.botonEditarMazo.setEnabled(true);
		this.botonAbandonar.setEnabled(true);
	}
	
	public void reiniciar() {
		desbloquear();
		this.botonRevancha.setText("REVANCHA");
		this.botonEditarMazo.setText("EDITAR MAZO");
		this.botonAbandonar.setText("ABANDONAR");
	}
}
