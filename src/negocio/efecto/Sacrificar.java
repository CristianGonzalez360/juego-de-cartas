package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class Sacrificar implements Efecto, Serializable {

	private Carta carta;
	
	public Sacrificar(Carta carta) {
		this.carta = carta;
	}
	@Override
	public void entrada(Juego juego) {
		if(carta.getJugador() == 0){
			juego.getTablero().sacrificar();
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
		return "Descarta dos cartas a elección de tu mano y levanta una carta del mazo. ";
	}

}
