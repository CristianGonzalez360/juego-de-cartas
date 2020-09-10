package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;

public class CuernoEspias implements Efecto, Serializable {

	
	private Carta carta;

	public CuernoEspias(Carta carta) {
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
		juego.getTablero().doblarEspias();
		if(carta.getJugador() == 0) {
			juego.getTablero().anularLider();
		}
		return true;
	}

	@Override
	public String getDescripcion() {
		return "Pasiva - Duplica la fuerza de las cartas Espia. ";
	}
}
