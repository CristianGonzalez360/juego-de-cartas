package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Unidad;

public class QuitarDeCampo implements Serializable, Accion {

	private Unidad unidad;

	public QuitarDeCampo(Unidad unidad) {
		this.unidad = unidad;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.getTablero().quitarCartaDelCampo(unidad, 1, false);
	}
}
