package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;

public class MedicoRandom implements Efecto, Serializable {

	@Override
	public void entrada(Juego juego) {
		juego.getTablero().medicoRandom();
	}

	@Override
	public void salida(Juego juego) {

	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Los médicos reviven una carta al azar. ";
	}

}
