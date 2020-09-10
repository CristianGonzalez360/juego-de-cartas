package red.accion;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.carta.Carta;

public class EnviarCarta implements Accion, Serializable {

	private List<Carta> cartas;
	
	public EnviarCarta(List<Carta> revivibles) {
		this.cartas = revivibles;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.getTablero().robarCarta(cartas);
	}

}
