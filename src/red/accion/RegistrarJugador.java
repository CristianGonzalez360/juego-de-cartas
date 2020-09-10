package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.Jugador;

public class RegistrarJugador implements Accion, Serializable {

	private Jugador jugador;
	
	public RegistrarJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	@Override
	public void ejecutar(Juego juego) {
		jugador.getMazo().setJugador(1);
		juego.registrar(jugador, 1);
	}

}
