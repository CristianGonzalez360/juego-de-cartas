package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import negocio.carta.Carta;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MostradorCartas extends JPanel {

	private JPanel panelCartas;
	private List<VistaCarta> vistasCarta;
	private VistaTablero vistaTablero;
	private VistaCarta resaltada;
	private int sacrificios = 0;
	private int devueltas = 0;
	private MouseAdapter cerrar;
	private JLabel lblDescripcion;
	private JScrollPane scroll;
	private JLabel mensaje;

	public MostradorCartas(VistaTablero vista) {
		setBackground(new Color(255, 255, 255, 100));
		this.vistaTablero = vista;
		this.vistasCarta = new ArrayList<>();
		setLayout(null);
		setBounds(new Rectangle(new Point(0, 0), Graficos.getTamañoTablero()));

		JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
		panelPrincipal.setBorder(new LineBorder(Color.ORANGE));
		panelPrincipal.setBackground(Color.BLACK);
		panelPrincipal.setBounds(Graficos.getPosicionMostradorCartas());
		add(panelPrincipal);
		
		mensaje = new JLabel();
		mensaje.setBounds(Graficos.getPosicionMensajeMostradorCartas());
		mensaje.setForeground(Color.BLACK);
		mensaje.setFont(new Font("gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		add(mensaje);

		panelCartas = new JPanel();
		panelCartas.setBackground(Color.BLACK);
		panelCartas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

		scroll = new JScrollPane(panelCartas, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(null);
		scroll.setViewportBorder(null);
		scroll.getViewport().setOpaque(false);
		scroll.getHorizontalScrollBar().setUnitIncrement(Graficos.getTamañoVistaCartaGrande().width + 5);
		
		panelPrincipal.add(scroll, BorderLayout.CENTER);

		lblDescripcion = new JLabel();
		lblDescripcion.setPreferredSize(new Dimension(Graficos.getPosicionMostradorCartas().width, 20));
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(18)));
		lblDescripcion.setForeground(Color.ORANGE);
		lblDescripcion.setBackground(Color.BLACK);
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setVerticalAlignment(SwingConstants.CENTER);
		panelPrincipal.add(lblDescripcion, BorderLayout.NORTH);
		
		cerrar = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cerrar();
			}
		};
	}

	public void mostrarCartas(List<Carta> cartas, int seleccionado) {
		vistaTablero.bloquear();
		for (Carta carta : cartas) {
			VistaCarta vc = new VistaCarta(carta, VistaCarta.GRANDE, vistaTablero);
			vc.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					resaltada.setBorder(null);
					vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
					lblDescripcion.setText(vc.getCarta().getDescripcion());
					resaltada = vc;
				}
			});
			panelCartas.add(vc);
			vistasCarta.add(vc);
		}
		this.panelCartas.revalidate();
		this.panelCartas.repaint();
		
		this.vistasCarta.get(seleccionado).setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		this.lblDescripcion.setText(this.vistasCarta.get(seleccionado).getCarta().getDescripcion());
		this.resaltada = this.vistasCarta.get(seleccionado);
		Dimension d = Graficos.getTamañoVistaCartaGrande();
		Rectangle posicionScroll = new Rectangle(new Point(((d.width+5)*seleccionado)+10, 0), d);
		this.scroll.getViewport().scrollRectToVisible(posicionScroll);
		
		addMouseListener(cerrar);
		setVisible(true);
	}

	public void modoJugar(List<Carta> cartas) {
		mostrarCartas(cartas, 0);
		for (VistaCarta vistaCarta : vistasCarta) {
			vistaCarta.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (isAlreadyOneClick) {
						vistaTablero.jugarCarta(vistaCarta.getCarta());
						isAlreadyOneClick = false;
					} else {
						isAlreadyOneClick = true;
						Timer t = new Timer("doubleclickTimer", false);
						t.schedule(new TimerTask() {
							@Override
							public void run() {
								isAlreadyOneClick = false;
							}
						}, 500);
					}
				}
			});
		}
		this.mensaje.setText("Elige una carta para jugar");
		removeMouseListener(cerrar);
		setVisible(true);
	}

	public void modoSacrificar(List<Carta> cartas) {
		mostrarCartas(cartas, 0);
		for (VistaCarta vistaCarta : vistasCarta) {
			vistaCarta.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (isAlreadyOneClick) {
						sacrificios++;
						if(sacrificios == 2) {
							cerrar();
							sacrificios = 0;
						}
						quitarCarta(vistaCarta);
						vistaTablero.sacrificar(vistaCarta.getCarta());
						isAlreadyOneClick = false;
					} else {
						isAlreadyOneClick = true;
						Timer t = new Timer("doubleclickTimer", false);
						t.schedule(new TimerTask() {
							@Override
							public void run() {
								isAlreadyOneClick = false;
							}
						}, 500);
					}
				}
			});
		}
		this.mensaje.setText("Elige dos cartas para sacrificar");
		removeMouseListener(cerrar);
		setVisible(true);
	}

	private void quitarCarta(VistaCarta vistaCarta) {
		this.vistasCarta.remove(vistaCarta);
		this.panelCartas.remove(vistaCarta);
		this.panelCartas.revalidate();
		this.panelCartas.repaint();
	}
	
	public void modoRobar(List<Carta> cartas) {
		mostrarCartas(cartas, 0);
		for (VistaCarta vistaCarta : vistasCarta) {
			vistaCarta.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;

				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (isAlreadyOneClick) {
						vistaTablero.robarCarta(vistaCarta.getCarta());
						cerrar();
						isAlreadyOneClick = false;
					} else {
						isAlreadyOneClick = true;
						Timer t = new Timer("doubleclickTimer", false);
						t.schedule(new TimerTask() {
							@Override
							public void run() {
								isAlreadyOneClick = false;
							}
						}, 500);
					}
				}
			});
		}
		this.mensaje.setText("Elige una carta para agregar a tu mano");
		removeMouseListener(cerrar);
		setVisible(true);
	}

	public void mostrarCarta(Carta carta) {
		vistaTablero.bloquear();
		VistaCarta vc = new VistaCarta(carta, VistaCarta.GRANDE, vistaTablero);
		vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		lblDescripcion.setText(vc.getCarta().getDescripcion());
		panelCartas.add(vc);
		vistasCarta.add(vc);
		panelCartas.revalidate();
		panelCartas.repaint();
		addMouseListener(cerrar);
		setVisible(true);
	}

	public void modoCambiar(List<Carta> mano) {
		mostrarCartas(mano, 0);
		for (VistaCarta vistaCarta : vistasCarta) {
			vistaCarta.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (isAlreadyOneClick) {
						Carta carta = vistaTablero.cambiar(vistaCarta.getCarta());
						if(carta != null) {
							vistaCarta.setCarta(carta);
						}
						devueltas++;
						if(devueltas == 2) {
							cerrar();
							devueltas = 0;
						}
						isAlreadyOneClick = false;
					} 
					else {
						isAlreadyOneClick = true;
						Timer t = new Timer("doubleclickTimer", false);
						t.schedule(new TimerTask() {
							@Override
							public void run() {
								isAlreadyOneClick = false;
							}
						}, 500);
					}
				}
			});
			this.panelCartas.add(vistaCarta);
		}
		this.mensaje.setText("Elige dos cartas para cambiar");
	}
	
	public void cerrar() {
		removeMouseListener(cerrar);
		vistasCarta.clear();
		panelCartas.removeAll();
		panelCartas.revalidate();
		panelCartas.repaint();
		this.mensaje.setText("");
		setVisible(false);
		vistaTablero.desbloquear();	
	}
}
