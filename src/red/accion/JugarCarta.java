package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class JugarCarta implements Accion, Serializable {

	private Carta carta;
	
	public JugarCarta(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.jugarCarta(carta, 1);
	}

}
