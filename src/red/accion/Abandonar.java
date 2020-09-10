package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class Abandonar implements Accion, Serializable {

	@Override
	public void ejecutar(Juego juego) {
		juego.abandonar(1);
	}

}
