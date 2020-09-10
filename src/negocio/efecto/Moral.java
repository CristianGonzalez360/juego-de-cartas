package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class Moral implements Efecto, Serializable {
		
	private Carta carta;
	
	public Moral(Carta carta) {
		this.carta = carta;
	}

	@Override
	public void entrada(Juego juego) {
		juego.getTablero().subirMoral(carta.getJugador(), carta.getFila(), carta.getId());
	}

	@Override
	public void salida(Juego juego) {
		juego.getTablero().bajarMoral(carta.getJugador(), carta.getFila(), carta.getId());		
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Aumenta en 1 la fuerza de las cartas de la fila. ";
	}
}
