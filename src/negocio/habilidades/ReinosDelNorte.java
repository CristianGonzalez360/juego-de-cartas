package negocio.habilidades;

import java.io.Serializable;

import negocio.Juego;

public class ReinosDelNorte extends Habilidad implements Serializable{
	
	@Override
	public boolean inicioPartida(Juego juego) {
		return false;		
	}

	@Override
	public void FinPartida(Juego juego) {
	}

	@Override
	public boolean inicioRonda(Juego juego, int ronda) {
		boolean ret = false;
		if(ronda > 0 && ronda < 3) {
			if(juego.getGanadorRonda(ronda-1) == getJugador()) {
				if (getJugador() == 0) {
					juego.getTablero().levantarCarta();
					ret = true;
				}
				else if(getJugador() == 1) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public boolean finRonda(Juego juego, int ronda) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return Habilidad.REINOS_DEL_NORTE;
	}
	
}
