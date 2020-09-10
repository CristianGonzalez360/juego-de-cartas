package red.accion;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.carta.Carta;

public class MostrarCartas implements Accion, Serializable {
	
	private List<Carta> cartas;

	public MostrarCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	@Override
	public void ejecutar(Juego juego) {
		juego.getTablero().espiarMano(cartas);
	}

}
