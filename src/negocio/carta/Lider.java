package negocio.carta;

import java.io.Serializable;

public class Lider extends Carta implements Serializable{
	
	private String nombreBaraja;

	public int getPosicion() {
		return 0;
	}

	public void setNombreBaraja(String nombreBaraja) {
		this.nombreBaraja = nombreBaraja;
	}

	public String getNombreBaraja() {
		return nombreBaraja;
	}

	@Override
	public String getDescripcion() {
		return this.getEfecto().getDescripcion();
	}

}
