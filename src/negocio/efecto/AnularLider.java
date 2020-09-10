package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;

public class AnularLider implements Efecto, Serializable {

	@Override
	public void entrada(Juego juego) {
		juego.getTablero().anularLider();
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
		return "Anula la habilidad del Lider. ";
	}

}
