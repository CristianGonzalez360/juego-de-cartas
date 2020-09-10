package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;
import negocio.carta.Unidad;

public class Senuelo implements Efecto, Serializable {

	private Carta carta;
	private Unidad reemplazada;
	
	public Senuelo(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		reemplazada.setJugador(carta.getJugador());
		if(carta.getJugador() == 0) {
			tablero.agregarCartaALaMano(reemplazada, 0);
		}		
		tablero.quitarCartaDelCampo(reemplazada, carta.getJugador(), false);
		if(reemplazada.tieneEfecto()) {
			reemplazada.getEfecto().salida(juego);
		}
	}

	public Unidad getCarta() {
		return reemplazada;
	}

	public void setCarta(Unidad carta) {
		this.reemplazada = carta;
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
		return "Elige una carta de tu campo y devuelvela a tu mano. ";
	}
	
	

	
}
