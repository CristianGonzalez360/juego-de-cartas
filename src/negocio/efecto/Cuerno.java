package negocio.efecto;

import java.io.Serializable;
import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;
import negocio.carta.Especial;
import negocio.carta.Lider;

public class Cuerno implements Efecto, Serializable {

	private int fila;
	private boolean esEspecial;
	private Carta carta;

	public Cuerno(Carta carta) {
		this.carta = carta;
		this.fila = carta.getFila();
		this.esEspecial = carta instanceof Especial;
	}

	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		if(esEspecial) {
			tablero.doblar(carta.getJugador(), carta.getFila(), true);
		}
		else if(carta instanceof Lider) {
			tablero.jugarCuerno(carta.getJugador(), carta.getFila(), true);
		}
		else {
			tablero.doblar(carta.getJugador(), carta.getFila(), true, carta.getId());
		}
		
	}

	@Override
	public void salida(Juego juego) {
		Tablero tablero = juego.getTablero();
		if(esEspecial || carta instanceof Lider) {
			tablero.doblar(carta.getJugador(), carta.getFila(), false);
		}
		else {
			tablero.doblar(carta.getJugador(), carta.getFila(), false, carta.getId());
		}
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}
	
	@Override
	public String getDescripcion() {
		String ret = "Duplica la fuerza de las Unidades de la fila en la que se juega. ";
		if (this.fila == 0) {
			ret = "Duplica la fuerza de las Unidades Cuerpo a Cuerpo. ";
		} else if (this.fila == 1) {
			ret = "Duplica la fuerza de las Unidades combate a Distancia. ";
		} else if (this.fila == 2) {
			ret = "Duplica la fuerza de las Unidades de Asedio. ";
		}
		return ret;
	}

}
