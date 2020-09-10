package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class DecidirComienzo implements Accion, Serializable {

	private boolean comienzo;
	public DecidirComienzo(boolean comienzo) {
		this.comienzo = comienzo;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.decidirComienzo(comienzo, 1);
	}

}
