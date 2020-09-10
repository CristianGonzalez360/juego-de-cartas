package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;

public class DevolverAlMazo implements Efecto, Serializable {

	@Override
	public void entrada(Juego juego) {
		juego.getTablero().devolverAlMazo();

	}

	@Override
	public void salida(Juego juego) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Devuelve todas las cartas del montón de descartes al mazo. ";
	}

}
