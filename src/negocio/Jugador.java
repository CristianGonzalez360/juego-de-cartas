package negocio;

import java.io.Serializable;

public class Jugador implements Serializable{

	private String nombre;
	private Mazo mazo;
	
	public static int JUGADOR1 = 0;
	public static int JUGADOR2 = 1;
	
	public Jugador(String nombre, Mazo mazo) {
		
		this.nombre = nombre;
		this.mazo = mazo;
	}
	
	public String getNombre() {
		return nombre;
	}
		
	public Mazo getMazo() {
		return this.mazo;
	}
	
	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}
}
