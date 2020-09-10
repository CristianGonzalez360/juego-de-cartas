package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class InfoRival implements Accion, Serializable {

	private int cantMano;
	private int cantMazo;
	
	public InfoRival(int cantidadMano, int cantidadMazo) {
		this.cantMano = cantidadMano;
		this.cantMazo = cantidadMazo;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.mostrarInfoCartasRival(cantMano, cantMazo);
	}

}
