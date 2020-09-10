package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class PasarTurno implements Accion, Serializable {

	@Override
	public void ejecutar(Juego juego) {
		juego.terminarTurno(1);
	}

}
