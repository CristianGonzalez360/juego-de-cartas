package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;
import negocio.carta.Relacionada;

public class Compañero implements Efecto, Serializable {

	private int[] companeros;
	private int fila;
	private Carta carta;
	
	public Compañero(Relacionada relacionada) {
		this.carta = relacionada;
		this.fila = relacionada.getFila();
		this.companeros = relacionada.getCartasRelacionadas();
	}

	@Override
	public void entrada(Juego juego) {
		juego.getTablero().agregarCompaneros(carta.getJugador(), fila, companeros);
	}

	@Override
	public void salida(Juego juego) {
		juego.getTablero().quitarCompaneros(carta.getJugador(), fila, companeros);
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Se duplica su fuerza cuando se juega al lado de una carta con el mismo nombre. ";
	}

}
