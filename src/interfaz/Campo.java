package interfaz;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import negocio.calculador.Calculadora;
import negocio.carta.Carta;
import negocio.carta.Especial;
import negocio.carta.Unidad;

public class Campo extends JPanel{

	private PanelCampo campo;
	private PanelCuernos cuernos;
	private PanelClimas climas;
	
	public Campo(int jugador, VistaTablero vistaTablero) {
		setOpaque(false);
		OverlayLayout layout = new OverlayLayout(this);
		setLayout(layout);
		setBounds(Graficos.getPosicionCampo(jugador));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setOpaque(false);
		this.campo = new PanelCampo(jugador, vistaTablero);
		this.cuernos = new PanelCuernos(vistaTablero, jugador);
		this.climas = new PanelClimas(jugador);
		
		this.campo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.campo.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		this.cuernos.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.cuernos.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		this.climas.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.climas.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		add(climas);
		panel.add(cuernos);
		panel.add(campo);
		add(panel);
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}
	
	public void jugarCarta(Unidad carta) {
		campo.jugarCarta(carta);
	}
	
	public void jugarCarta(Especial especial) {
		if(especial.getNombre().equals("Commander's Horn") || especial.getNombre().equals("Mardroeme")) {
			cuernos.jugarCuerno(especial);
		}
		else if(especial.getNombre().equals("Decoy")){
			campo.jugarCarta(especial);
		}
	}
	
	public void jugarCuerno(int fila) {
		cuernos.jugarCuerno(fila);
	}

	public void quitarCuerno(int fila) {
		cuernos.quitarCuerno(fila);	
	}
		
	public void modoSeleccionCarta(boolean estado) {
		campo.modoSeleccionCarta(estado);
	}
	
	public void modoSeleccionCampo(boolean estado) {
		campo.modoSeleccion(estado);
	}
	
	public void modoSeleccionCuerno(boolean estado) {
		cuernos.modoSeleccion(estado);
	}
	
	public void modoSeleccionFila(int fila, boolean estado) {
		campo.modoSeleccionFila(fila, estado);
	}
	
	public void quitarCarta(Carta carta) {
		campo.quitarCarta(carta);
	}
	
	public void mostrarPuntos(Calculadora calculadora) {
		campo.mostrarPuntos(calculadora);
	}
	
	public void vaciar() {
		campo.vaciar();
		cuernos.vaciar();
		climas.limpiar();
	}
	
	public void bloquear() {
		campo.bloquear();
	}
	
	public void desbloquear() {
		campo.desbloquear();
	}
	
	public void clima(int fila) {
		climas.clima(fila);
	}
	
	public void limpiar() {
		climas.limpiar();
	}
}
