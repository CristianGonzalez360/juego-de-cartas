package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class CartaExtra implements Efecto, Serializable {

	private Carta carta;
	
	public CartaExtra(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
	
	}

	@Override
	public void salida(Juego juego) {
		
	}

	@Override
	public boolean activar(Juego juego) {
		if(this.carta.getJugador() == 0) {
			juego.getTablero().levantarCartas(1, 0);
			juego.getTablero().anularLider();
		}
		return true;
	}

	@Override
	public String getDescripcion() {
		return "Pasiva - Levanta una carta extra al comienzo de la partida. ";
	}
}
