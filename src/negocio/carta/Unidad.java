package negocio.carta;

import java.io.Serializable;
import negocio.efecto.Efecto;

public class Unidad extends Carta implements Serializable {

	private int fuerza;
	private boolean espia;
	private boolean agil;
	private boolean heroe;
	private boolean revivible;
			
	public Unidad() {
		setRevivible(true);
	}
	
	public int getFuerza() {
		return fuerza;
	}

	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}
	
	public boolean tieneEfectoSalida() {
		boolean ret = false;
		if(getEfecto() != null) {
			ret = true;
		}
		return ret;
	}

	public boolean esEspia() {
		return espia;
	}

	public void setEsEspia(boolean esEspia) {
		this.espia = esEspia;
	}

	public boolean esAgil() {
		return agil;
	}

	public void setAgil(boolean esAgil) {
		this.agil = esAgil;
	}

	public boolean esHeroe() {
		return heroe;
	}

	public void setHeroe(boolean esHeroe) {
		this.heroe = esHeroe;
		this.revivible = !esHeroe;
	}

	@Override
	public String getDescripcion() {
		String ret = "";
		
		if(tieneEfecto()) {
			ret = getEfecto().getDescripcion();
		}		
		if(heroe) {
			ret += "No lo afectan las cartas Especiales, Climaticas o Habilidades. ";
		}
		if(agil) {
			ret += "Se puede jugar en la fila Cuerpo a Cuerpo o a Distancia. ";
		}
		return ret;
	}

	public boolean esRevivible() {
		return revivible;
	}

	public void setRevivible(boolean esRevivible) {
		this.revivible = esRevivible;
	}

}
