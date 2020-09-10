package negocio.carta;

import java.io.Serializable;

public class Especial extends Carta implements Serializable{

	@Override
	public String getDescripcion() {
		return this.getEfecto().getDescripcion();
	}

}
