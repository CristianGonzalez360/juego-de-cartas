package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class Continuar implements Accion, Serializable {

	private int como;
	
	public Continuar(int como) {
		this.como = como;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.continuar(como, 1);
	}

}
