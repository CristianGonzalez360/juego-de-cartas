package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class Espia implements Efecto, Serializable {
	
	private Carta carta;
	
	public Espia(Carta carta) {
		this.carta = carta;
	}

	@Override
	public void entrada(Juego juego) {
		if(carta.getJugador() == 1) {
			juego.getTablero().levantarCartas(2, 0);
		}
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
		return "Juega la carta en el campo del rival y levanta 2 cartas. ";
	}

}
