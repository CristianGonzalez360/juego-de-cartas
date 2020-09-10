package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class Pasar implements Accion, Serializable {

	@Override
	public void ejecutar(Juego juego) {
		juego.pasar(1);
	}

}
