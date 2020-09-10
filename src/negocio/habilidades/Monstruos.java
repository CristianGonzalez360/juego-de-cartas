package negocio.habilidades;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Unidad;

public class Monstruos extends Habilidad implements Serializable{

	private Unidad carta;
	
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
		if(this.carta != null) {
			juego.getTablero().agregarCartaAlCampo(carta, getJugador(), true);
			this.carta = null;
		}
		if(ronda > 0) {
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean finRonda(Juego juego, int ronda) {
		if(getJugador() == 0) {
			this.carta = juego.getTablero().getCartaAlAzarDelCampo();
		}
		return false;
	}

	@Override
	public String getDescripcion() {
		return Habilidad.MONSTRUOS;
	}
}
