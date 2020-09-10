package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class AgregarADescartes implements Accion, Serializable {

	private Carta carta;
	
	public AgregarADescartes(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		int jugador = (carta.getJugador()+1)%2;
		juego.getTablero().agregarCartaADescartes(carta, jugador, false);

	}

}
