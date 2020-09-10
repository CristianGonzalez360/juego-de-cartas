package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class QuitarDeDescartes implements Accion, Serializable {

	private Carta carta;
	
	public QuitarDeDescartes(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		int jugador = (carta.getJugador()+1)%2;
		juego.getTablero().quitarCartaDeDescartes(carta, jugador, false);
	}

}
