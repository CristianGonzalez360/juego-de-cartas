package interfaz;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;

import negocio.calculador.Calculadora;
import negocio.carta.Carta;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class Fila extends JPanel {
	
	public static final int CUERPOACUERPO = 0;
	public static final int ADISTANCIA = 1;
	public static final int ASEDIO = 2;
	
	private int ancho;
	private int gap;

	private int idFila;
	private int anchoVistaCarta;
	private PanelCampo campo;
	private List<VistaCarta> cartas;
	private FlowLayout layout;
	private VistaTablero vista;
	private MouseAdapter seleccionar;
	private boolean modoSeleccionar;
	
	public Fila(int idFila, VistaTablero vista) {
		this.gap = 5;
		this.idFila = idFila;
		this.vista = vista;
		this.anchoVistaCarta = Graficos.getTamañoVistaCartaChica().width;
		
		setOpaque(false);
		layout = new FlowLayout(FlowLayout.CENTER, 0, 0);
		setLayout(layout);

		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setPreferredSize(Graficos.getTamañoFila());
		this.cartas = new ArrayList<>();
		
		this.seleccionar = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					seleccionar();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				resaltar(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				resaltar(false);
			}
		};
	}
	
	public void setCampo(PanelCampo campo) {
		this.campo = campo;
	}

	public void seleccionar() {
		if (modoSeleccionar) {
			setBorder(null);
			modoSeleccion(false);
			this.vista.seleccionarFila(idFila);
		}
		else {
			this.campo.seleccinar();
		}
	}
	
	public void resaltar(boolean estado){
		if(modoSeleccionar && estado) {
			setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		}
		else if (!modoSeleccionar){
			this.campo.resaltar(estado);
		}
		else {
			setBorder(null);
		}
	}
	
	public void agregarCarta(Carta carta) {
		VistaCarta vistaCarta = new VistaCarta(carta, VistaCarta.CHICA, this.vista);
		vistaCarta.setFila(this);
		vistaCarta.modoMostrar(true);
		cartas.add(vistaCarta);
		Collections.sort(cartas);
		int indice = cartas.size() - cartas.indexOf(vistaCarta) - 1;
		add(vistaCarta,indice);
		calcularGap();
		revalidate();
		repaint();
	}
	
	public void agregarCarta(VistaCarta vistaCarta) {
		cartas.add(vistaCarta);
		Collections.sort(cartas);
		int indice = cartas.size() - cartas.indexOf(vistaCarta) - 1;
		add(vistaCarta,indice);
		calcularGap();
		revalidate();
		repaint();
	}

	private void calcularGap() {
		int cantidad = cartas.size();
		if ((cantidad*anchoVistaCarta)+(this.gap*cantidad-1)>this.ancho) {
			double b = this.ancho-anchoVistaCarta;
			b = b/(cantidad);
			b = b-anchoVistaCarta;
			int gap = (int) Math.ceil(b);
			layout.setHgap(gap);
		}
		else {
			layout.setHgap(this.gap);
		}
	}
	
	public void modoSeleccion(boolean estado) {
		if(estado) {
			addMouseListener(seleccionar);
		}
		else {
			removeMouseListener(seleccionar);
		}
		this.modoSeleccionar = estado;
	}

	public void bloquear() {
		removeMouseListener(seleccionar);
		setBorder(null);
	}
	
	public void desbloquear() {
		if(modoSeleccionar) {
			addMouseListener(seleccionar);
		}
	}
	
	public void quitarCarta(Carta carta) {
		for(VistaCarta vc :this.cartas) {
			if(vc.getCarta().equals(carta)) {
				this.cartas.remove(vc);
				remove(vc);
				calcularGap();
				revalidate();
				repaint();
				break;
			}
		}
	}

	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		this.ancho = d.width;
	}
	
	public void modoDevolverCarta(boolean estado) {
		for (VistaCarta vistaCarta : cartas) {
			vistaCarta.modoDevolver(estado);
		}
	}
	
	public void modoMostrarCarta(boolean estado) {
		for (VistaCarta vistaCarta : cartas) {
			vistaCarta.modoMostrar(estado);
		}
	}
	
	public void vaciar() {
		removeAll();
		this.cartas.clear();
		revalidate();
		repaint();
	}
	
	public List<Carta> getCartas(){
		List<Carta> ret = new ArrayList<>();
		for(VistaCarta vc : this.cartas) {
			ret.add(vc.getCarta());
		}
		return ret;
	}

	public boolean estaVacia() {
		return this.cartas.isEmpty();
	}

	public void mostrarPuntos(Calculadora calculadora) {
		for(VistaCarta vc : cartas) {
			Carta c = vc.getCarta();
			vc.mostrarFuerza(calculadora.getPuntos(c.getId(), c.getFila()));
		}
	}
	
}
