package negocio.habilidades;

import java.io.Serializable;

import negocio.Juego;

public class Skellige extends Habilidad implements Serializable{

	@Override
	public boolean inicioPartida(Juego juego) {
		return false;
	}

	@Override
	public void FinPartida(Juego juego) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inicioRonda(Juego juego, int ronda) {
		boolean ret = false;
		if(ronda == 2 && getJugador() == 0) {
			juego.getTablero().revivir();
			juego.getTablero().revivir();
			ret = true;
		}
		else if(ronda == 2 && getJugador() == 1) {
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean finRonda(Juego juego, int ronda) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return Habilidad.SKELLIGE;
	}

}
