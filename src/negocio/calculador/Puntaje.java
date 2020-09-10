package negocio.calculador;

public class Puntaje {
	
	private int id;
	private int puntos;
	private boolean modificable;
	private boolean esEspia;
	
	public Puntaje(int id, int puntos, boolean modificable, boolean esEspia) {
		this.id = id;
		this.modificable = modificable;
		this.puntos = puntos;
		this.esEspia = esEspia;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	public boolean esModificable() {
		return modificable;
	}

	public int getId() {
		return id;
	}
	
	public boolean esEspia() {
		return esEspia;
	}

}
