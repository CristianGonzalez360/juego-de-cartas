package interfaz;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import negocio.Jugador;
import negocio.Mazo;
import negocio.Tablero;
import negocio.calculador.Calculadora;
import negocio.carta.*;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class VistaTablero extends JPanel {

	private Campo[] campos;
	private JLabel[] barajas;
	private PanelDescartes[] descartes;
	private PanelLider[] lideres;
	private PanelPuntajes[] puntajes;
	private MostradorCartas mostradorCartas;
	private JLabel zoom;
	private Climaticas climaticas;

	private Mano mano;

	private Tablero tablero;
	private JButton botonPasar;
	private JLabel[] totales;
	private Cartel cartel;
	private Gemas[] gemas;
	private Paso[] paso;
	private JLabel[] cartasMano;
	private JLabel[] cartasMazo;
	private boolean bloqueado;
	private int bloqueos;
	private JButton botonAbandonar;

	public VistaTablero() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setBackground(Color.BLACK);

		JPanel panel = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		LayoutManager overlay = new OverlayLayout(panel);
		panel.setLayout(overlay);
		add(panel);

		cartel = new Cartel(this);
		cartel.setAlignmentX(0.5f);
		cartel.setAlignmentY(0.5f);
		panel.add(cartel);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(Graficos.getTamañoTablero());
		panelPrincipal.setLayout(null);

		panel.add(panelPrincipal);

		mostradorCartas = new MostradorCartas(this);
		mostradorCartas.setVisible(false);
		panelPrincipal.add(mostradorCartas);

		paso = new Paso[2];
		paso[0] = new Paso(0);
		paso[1] = new Paso(1);
		panelPrincipal.add(paso[0]);
		panelPrincipal.add(paso[1]);

		this.campos = new Campo[2];
		this.campos[0] = new Campo(0, this);
		this.campos[1] = new Campo(1, this);
		panelPrincipal.add(this.campos[0]);
		panelPrincipal.add(this.campos[1]);

		this.lideres = new PanelLider[2];
		this.lideres[0] = new PanelLider(0, this);
		panelPrincipal.add(this.lideres[0]);

		this.lideres[1] = new PanelLider(1, this);
		panelPrincipal.add(this.lideres[1]);

		this.puntajes = new PanelPuntajes[2];
		this.puntajes[0] = new PanelPuntajes(0);
		this.puntajes[1] = new PanelPuntajes(1);
		panelPrincipal.add(this.puntajes[0]);
		panelPrincipal.add(this.puntajes[1]);

		this.totales = new JLabel[2];

		JLabel total0 = new JLabel("0");
		total0.setForeground(Color.BLACK);
		total0.setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(50)));
		total0.setHorizontalAlignment(SwingConstants.CENTER);
		total0.setBounds(Graficos.getPosicionTotal(0));
		this.totales[0] = total0;

		JLabel total1 = new JLabel("0");
		total1.setForeground(Color.BLACK);
		total1.setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(50)));
		total1.setHorizontalAlignment(SwingConstants.CENTER);
		total1.setBounds(Graficos.getPosicionTotal(1));
		this.totales[1] = total1;
		panelPrincipal.add(total0);
		panelPrincipal.add(total1);

		this.cartasMazo = new JLabel[2];
		this.cartasMazo[0] = new JLabel();
		this.cartasMazo[0].setForeground(Color.ORANGE);
		this.cartasMazo[0].setHorizontalAlignment(SwingConstants.CENTER);
		this.cartasMazo[0].setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		this.cartasMazo[0].setBounds(Graficos.getPosicionCartasMazo(0));
		panelPrincipal.add(cartasMazo[0]);
		this.cartasMazo[1] = new JLabel();
		this.cartasMazo[1].setForeground(Color.ORANGE);
		this.cartasMazo[1].setHorizontalAlignment(SwingConstants.CENTER);
		this.cartasMazo[1].setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		this.cartasMazo[1].setBounds(Graficos.getPosicionCartasMazo(1));
		panelPrincipal.add(cartasMazo[1]);

		this.barajas = new JLabel[2];
		this.barajas[0] = new JLabel();
		this.barajas[0].setBounds(Graficos.getPosicionBaraja(0));
		this.barajas[0].setHorizontalAlignment(SwingConstants.CENTER);
		panelPrincipal.add(this.barajas[0]);
		this.barajas[1] = new JLabel();
		this.barajas[1].setBounds(Graficos.getPosicionBaraja(1));
		this.barajas[1].setHorizontalAlignment(SwingConstants.CENTER);
		panelPrincipal.add(this.barajas[1]);

		this.descartes = new PanelDescartes[2];

		this.descartes[0] = new PanelDescartes(0, this);
		panelPrincipal.add(this.descartes[0]);
		this.descartes[1] = new PanelDescartes(1, this);
		panelPrincipal.add(this.descartes[1]);

		this.zoom = new JLabel();
		this.zoom.setBounds(Graficos.getPosicionZoom());
		this.zoom.setHorizontalAlignment(SwingConstants.CENTER);
		panelPrincipal.add(zoom);

		this.climaticas = new Climaticas(this);
		panelPrincipal.add(climaticas);

		mano = new Mano(this);
		panelPrincipal.add(mano);

		botonPasar = new JButton("Pasar");
		botonPasar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablero.pasar();
			}
		});
		botonPasar.setBounds(Graficos.getPosicionBotonPasar());
		botonPasar.setVisible(false);
		botonPasar.setFocusable(false);
		panelPrincipal.add(botonPasar);

		gemas = new Gemas[2];
		gemas[0] = new Gemas(0);
		gemas[1] = new Gemas(1);
		panelPrincipal.add(gemas[0]);
		panelPrincipal.add(gemas[1]);

		botonAbandonar = new JButton("ABANDONAR");
		botonAbandonar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tablero.abandonar();
			}
		});
		botonAbandonar.setBounds(Graficos.getPosicionBotonSalir());
		botonAbandonar.setFocusable(false);
		panelPrincipal.add(botonAbandonar);

		this.cartasMano = new JLabel[2];
		this.cartasMano[0] = new JLabel();
		this.cartasMano[0].setForeground(Color.ORANGE);
		this.cartasMano[0].setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		this.cartasMano[0].setBounds(Graficos.getPosicionCartasMano(0));
		panelPrincipal.add(cartasMano[0]);
		this.cartasMano[1] = new JLabel();
		this.cartasMano[1].setForeground(Color.ORANGE);
		this.cartasMano[1].setFont(new Font("Gwent", Font.PLAIN, Graficos.getTamañoFuente(40)));
		this.cartasMano[1].setBounds(Graficos.getPosicionCartasMano(1));
		panelPrincipal.add(cartasMano[1]);

		JLabel fondo = new JLabel();
		fondo.setBounds(new Rectangle(new Point(0, 0), Graficos.getTamañoTablero()));
		fondo.setHorizontalAlignment(SwingConstants.CENTER);
		fondo.setIcon(Graficos.getImagenTablero());
		fondo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				tablero.deseleccionar();
			}
		});
		panelPrincipal.add(fondo);

	}

	public void seleccionarFila(int idFila) {
		this.tablero.seleccionarFila(idFila);
		apagarModosSeleccion();
	}

	public void jugarCarta(Lider lider, int jugador) {
		this.lideres[jugador].modoSeleccion(false);
		if (jugador == 0) {
			this.lideres[0].deseleccionar();
		}
	}

	public void jugarCarta(Especial especial, int jugador) {
		this.campos[jugador].jugarCarta(especial);
	}

	public void agregarAlCampo(Unidad unidad, int jugador) {
		campos[jugador].jugarCarta(unidad);
	}

	public void deseleccionar() {
		this.mano.deseleccionar();
		this.lideres[0].deseleccionar();
		quitarZoom();
		apagarModosSeleccion();
	}

	public void jugarCarta(Climatica climatica, int jugador) {
		this.climaticas.agregarCarta(climatica);
	}

	public void despejar() {
		this.climaticas.vaciar();
		this.campos[0].limpiar();
		this.campos[1].limpiar();
	}

	public void zoom(Carta carta) {
		this.zoom.setIcon(Graficos.getImagenCartaGrande(carta.getRutaImagen()));
	}

	public void quitarZoom() {
		this.zoom.setIcon(null);
	}

	public void mostrarMazo(Mazo mazo, int jugador) {
		lideres[jugador].setLider(mazo.getLider());
		barajas[jugador].setIcon(Graficos.getImagenCartaChica(mazo.getRutaImagen()));
	}

	public void quitarCarta(Carta carta) {
		this.mano.quitarCarta(carta);
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public void mostrarJugador(Jugador jugador, int i) {
		mostrarMazo(jugador.getMazo(), i);
	}

	public void apagarModosSeleccion() {
		this.campos[0].modoSeleccionCarta(false);
		this.campos[0].modoSeleccionCuerno(false);
		this.climaticas.modoSeleccion(false);
		this.campos[0].modoSeleccionCampo(false);
		this.campos[1].modoSeleccionFila(0, false);
		this.campos[0].modoSeleccionFila(0, false);
	}

	public void modoSeleccionFila(int campo, int fila, boolean estado) {
		this.campos[campo].modoSeleccionFila(fila, estado);
	}

	public void modoSeleccionClimaticas(boolean estado) {
		this.climaticas.modoSeleccion(estado);
	}

	public void modoSeleccionCampo(boolean estado) {
		this.campos[0].modoSeleccionCampo(estado);
	}

	public void modoSeleccionCarta(boolean estado) {
		this.campos[0].modoSeleccionCarta(estado);

	}

	public void modoSeleccionCuerno(boolean estado) {
		this.campos[0].modoSeleccionCuerno(estado);
	}

	public void seleccionarCarta(Carta carta) {
		if (carta instanceof Lider) {
			this.mano.deseleccionar();
		} else {
			this.lideres[0].deseleccionar();
		}
		this.tablero.seleccionarCarta(carta);
	}

	public void jugarCartaSeleccionada() {
		this.tablero.jugarCartaSeleccionada();
	}

	public void quitarDelCampo(Carta carta, int jugador) {
		this.campos[jugador].quitarCarta(carta);
	}

	public void sustituir(Carta carta) {
		this.tablero.sustituir(carta);
	}

	public void jugarCuerno(int fila, int jugador) {
		this.campos[jugador].jugarCuerno(fila);
	}

	public void quitarCuerno(int fila, int jugador) {
		this.campos[jugador].quitarCuerno(fila);
	}

	public void modoJugar(List<Carta> cartas) {
		this.mostradorCartas.modoJugar(cartas);
	}

	public void jugarCarta(Carta carta) {
		this.mostradorCartas.cerrar();
		this.tablero.jugarCarta(carta);
		;
	}

	public void modoJugar(boolean estado, boolean liderDisponible) {
		this.mano.modoSeleccionCarta(estado);
		this.lideres[0].modoSeleccion(liderDisponible);
		botonPasar.setVisible(estado);
	}

	public void mostrarPuntos(Calculadora calculadora, int jugador) {
		this.puntajes[jugador].mostrarPuntos(calculadora);
		this.totales[jugador].setText(calculadora.getFuerzaTotal() + "");
		this.campos[jugador].mostrarPuntos(calculadora);
	}

	public void siguienteRonda() {
		this.paso[0].reiniciar();
		this.paso[1].reiniciar();
		this.campos[0].vaciar();
		this.campos[1].vaciar();
		this.climaticas.vaciar();
	}

	public void mostrarMensaje(String mensaje, int tiempo) {
		this.cartel.mostrarMensaje(mensaje, tiempo);
	}

	public void reiniciar() {
		siguienteRonda();
		this.gemas[0].reiniciar();
		this.gemas[1].reiniciar();
		this.vaciarDescartes();
		this.mano.vaciar();
		this.modoJugar(false, false);
	}

	public void ganar() {
		this.gemas[1].perder();
	}

	public void perder() {
		this.gemas[0].perder();
	}

	public void empatar() {
		this.gemas[0].perder();
		this.gemas[1].perder();
	}

	public void paso(int jugador) {
		this.paso[jugador].setText("PASO");
	}

	public void terminar() {
		this.tablero.terminarMovimiento();
	}

	public void modoSacrificar(List<Carta> cartas) {
		this.mostradorCartas.modoSacrificar(cartas);
	}

	public void sacrificar(Carta carta) {
		this.tablero.sacrificar(carta);
	}

	public void modoRobar(List<Carta> cartas) {
		this.mostradorCartas.modoRobar(cartas);
	}

	public void robarCarta(Carta carta) {
		this.tablero.robarCarta(carta);
	}

	public void vaciarDescartes() {
		descartes[0].vaciar();
		descartes[1].vaciar();
	}

	public void quitarCartaDescartes(Carta carta, int jugador) {
		this.descartes[jugador].quitarCarta(carta);
	}

	public void agregarCartaADescartes(Carta carta, int jugador) {
		this.descartes[jugador].agregarCarta(carta);
	}

	public void agregarALaMano(Carta carta) {
		this.mano.agregarCarta(carta);
	}

	public void quitarCartaDeLAMano(Carta carta) {
		this.mano.quitarCarta(carta);
	}

	public void bloquear() {
		this.bloqueos++;
		if (!this.bloqueado) {
			this.bloqueado = true;
			this.mano.bloquear();
			this.lideres[0].bloquear();
			this.lideres[1].bloquear();
			this.descartes[0].bloquear();
			this.descartes[1].bloquear();
			this.campos[0].bloquear();
			this.campos[1].bloquear();
			this.botonPasar.setEnabled(false);
			this.botonAbandonar.setEnabled(false);
		}
	}

	public void desbloquear() {
		this.bloqueos--;
		if (this.bloqueos == 0) {
			this.bloqueado = false;
			this.mano.desbloquear();
			this.descartes[0].desbloquear();
			this.descartes[1].desbloquear();
			this.lideres[0].desbloquear();
			this.lideres[1].desbloquear();
			this.campos[0].desbloquear();
			this.campos[1].desbloquear();
			this.botonPasar.setEnabled(true);
			this.botonAbandonar.setEnabled(true);
		}
	}

	public void mostrarCarta(Carta carta) {
		this.mostradorCartas.mostrarCarta(carta);
	}

	public void mostrarCartas(List<Carta> cartas, int seleccionado) {
		this.mostradorCartas.mostrarCartas(cartas, seleccionado);
	}

	public Carta cambiar(Carta carta) {
		return this.tablero.cambiar(carta);
	}

	public void mostrarMano(ArrayList<Carta> cartas) {
		this.mostradorCartas.modoCambiar(cartas);
		this.mostradorCartas.setVisible(true);
	}

	public void mostrarInfoCartas(int jugador, int cantMano, int cantMazo) {
		this.cartasMano[jugador].setText(cantMano + "");
		this.cartasMazo[jugador].setText(cantMazo + "");
	}

	public void mostrarMensajes() {
		bloquear();
		this.cartel.siguienteMensaje();
	}

	public void encolarMensaje(String mensaje, int tiempo) {
		this.cartel.encolarMensaje(mensaje, tiempo);
	}

	public void clima(int fila) {
		this.campos[0].clima(fila);
		this.campos[1].clima(fila);
	}
}
