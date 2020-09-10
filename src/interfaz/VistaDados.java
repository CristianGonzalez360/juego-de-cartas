package interfaz;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import negocio.Juego;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;

public class VistaDados extends JPanel {
	
	public static String mensajeLanzar = "Preciona el dado para LANZARLO";
	public static String mensajeDetener = "Preciona el dado para DETENERLO";
	public static String mensajeGana = "GANASTE, tú comienzas la partida";
	public static String mensajePierde = "PERDISTE, el rival comienza la partida";
	public static String mensajeEmpate = "EMPATE, lanza el dado otra vez";
	public static String mensajeEspera = "Esperando al rival";
	
	private JPanel panelDados;
	private Dado dadoRival;
	private Dado dado;
	private Juego juego;
	private JLabel lblMensaje;
	
	public VistaDados() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setBackground(Color.BLACK);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(Graficos.getTamañoTablero());
		panelPrincipal.setLayout(null);
		add(panelPrincipal);
		
		panelDados = new JPanel();
		panelDados.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		panelDados.setOpaque(false);
		panelDados.setBounds(Graficos.getPosicionDados());
		panelPrincipal.add(panelDados);
		
		dado = new Dado();
		panelDados.add(dado);
		
		lblMensaje = new JLabel(mensajeLanzar);
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
	
	public void agregarDado(int valor) {
		this.dadoRival = new Dado(valor);
		this.panelDados.add(dadoRival);
		this.panelDados.revalidate();
		this.panelDados.repaint();
	}
	
	public void reiniciar() {
		if(dadoRival != null) {
			this.panelDados.remove(dadoRival);
		}
		this.panelDados.revalidate();
		this.panelDados.repaint();
		mostrarMensaje(mensajeLanzar);
		dado.reiniciar();
	}
	
	public void mostrarMensaje(String mensaje) {
		this.lblMensaje.setText(mensaje);
	}
	
	private class Dado extends JLabel {
		
		private Random random;
		private Timer t = new Timer();
		private int valor;
		private boolean continuar;
		private boolean rodando;
		
		public Dado() {
			setPreferredSize(Graficos.getTamañoDado());	
			this.random = new Random();
			this.continuar = true;
			setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 165, 0), new Color(255, 69, 0)));
			tirarDado();
			mostrarDado();
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(rodando) {
						t.cancel();
						detener();
						juego.tirarDado(valor, 0);
					}
					else if(continuar) {
						iniciar();
						mostrarMensaje(mensajeDetener);
					}
				}
			});
		}
		
		public Dado(int valor) {
			setPreferredSize(Graficos.getTamañoDado());	
			this.valor = valor;
			mostrarDado();
		}
		
		public void mostrarDado() {
			setIcon(Graficos.getImagenDado(valor));
		}
		
		public void tirarDado() {
			this.valor = this.random.nextInt(6)+1;
		}
		
		public void iniciar(){
			if (continuar) {
				rodando = true;
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						tirarDado();
						mostrarDado();
						iniciar();
					}
				}, 25);
			}
		}
		
		public void detener(){
			this.continuar = false;
			this.rodando = false;
		}
		
		public void reiniciar() {
			this.t = new Timer();
			this.continuar = true;
			this.rodando = false;
		}
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}
}
