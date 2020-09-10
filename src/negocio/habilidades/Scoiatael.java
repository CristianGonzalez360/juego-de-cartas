package negocio.habilidades;

import java.io.Serializable;

import negocio.Juego;

public class Scoiatael extends Habilidad implements Serializable{

	@Override
	public boolean inicioPartida(Juego juego) {
		juego.decidirInicio(getJugador());
		return true;
	}

	@Override
	public void FinPartida(Juego juego) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inicioRonda(Juego juego, int ronda) {
		return false;
	}

	@Override
	public boolean finRonda(Juego juego, int ronda) {
		return false;

	}

	@Override
	public String getDescripcion() {
		return Habilidad.SCOIATAEL;
	}

}
