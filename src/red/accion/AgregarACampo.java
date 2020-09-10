package red.accion;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;
import negocio.carta.Unidad;

public class AgregarACampo implements Accion, Serializable {

	private Unidad carta;

	public AgregarACampo(Unidad unidad) {
		this.carta = unidad;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.getTablero().agregarCartaAlCampo(carta, 1, false);
	}

}
