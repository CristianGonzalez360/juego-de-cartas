package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;
import negocio.carta.Especial;

public class Mardroeme implements Efecto, Serializable {

	private Carta carta;
	
	public Mardroeme(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
		juego.getTablero().mardroeme(true, carta.getFila(), carta.getJugador());
	}

	@Override
	public void salida(Juego juego) {
		juego.getTablero().mardroeme(false, carta.getFila(), carta.getJugador());
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Transforma los berserkers de la fila. ";
	}

}
