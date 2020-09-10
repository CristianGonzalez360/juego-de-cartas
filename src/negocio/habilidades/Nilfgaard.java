package negocio.habilidades;

import java.io.Serializable;

import negocio.Juego;

public class Nilfgaard extends Habilidad implements Serializable{

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
		return false;		
	}

	@Override
	public boolean finRonda(Juego juego, int ronda) {
		boolean ret = false;
		
		if(juego.getGanadorRonda(ronda) == 2) {
			juego.desempatar(getJugador());
			ret = true;
		}
		return ret;	
	}

	@Override
	public String getDescripcion() {
		return Habilidad.NILFGAARD;
	}

}
