package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class AlterarClima implements Efecto, Serializable {

	private Carta carta;
	
	public AlterarClima(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
		juego.getTablero().alterarClima(carta.getJugador());
	}

	@Override
	public void salida(Juego juego) {

	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Las cartas climáticas reducen la fuerza de las unidades a la mitad. ";
	}

}
