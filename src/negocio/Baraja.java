package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import negocio.carta.Carta;
import negocio.carta.Lider;
import negocio.habilidades.Habilidad;

public class Baraja implements Serializable {

	public static int REINOS_DEL_NORTE = 0;
	public static int NILFGAARD = 1;
	public static int MONSTRUOS = 2;
	public static int SCOIATAEL = 3;
	public static int SKELLIGE = 4;
	public static int NEUTRAL = 5;
	public static int ESPECIAL = 6;
	public static int INVOCADAS = 7;
	
	private String nombre;
	private int id;
	
	private Habilidad habilidad;
	
	private List<Carta> cartas;
	private List<Lider> lideres;

	public List<Carta> getCartas() {
		return cartas;
	}

	public Baraja(int id, String nombre) {
		this.setNombre(nombre);
		this.setId(id);
		this.cartas = new ArrayList<>();
		this.lideres = new ArrayList<>();
	}

	public Baraja() {
		this.cartas = new ArrayList<>();
		this.lideres = new ArrayList<>();
	}
	public void agregarCarta(Carta carta) {
		if(carta instanceof Lider) {
			this.lideres.add((Lider)carta);
			Collections.sort(this.lideres);
		}
		else {
			this.cartas.add(carta);
			Collections.sort(this.cartas);
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Lider> getLideres() {
		return this.lideres;
	}

	public void quitarCarta(Carta carta) {
		this.cartas.remove(carta);
	}
	
	public void ordenar() {
		Collections.sort(this.cartas);
		Collections.sort(this.lideres);
	}

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}
}
