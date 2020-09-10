package negocio.carta;

import java.io.Serializable;

import negocio.efecto.Efecto;

public abstract class Carta implements Comparable<Carta>, Serializable{
	
	private String nombre;
	private int id;
	private int fila;
	private Efecto efecto; 
	private String nombreBaraja;	
	private transient int jugador;

	public static int UNIDAD = 0;
	public static int LIDER = 1;
	public static int RELACIONADA = 2;
	public static int ESPECIAL = 3;
	public static int CLIMATICA = 4;
	public static int INVOCADORA = 5;
	
	public String getNombre() {
		return this.nombre;
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

	public String getRutaImagen() {
		String ruta = "/"+getNombreBaraja()+"/"+getNombre()+".png"; 
		return ruta;
	}

	public String getNombreBaraja() {
		return nombreBaraja;
	}

	public void setNombreBaraja(String nombreBaraja) {
		this.nombreBaraja = nombreBaraja;
	}
	
	public boolean tieneEfecto() {
		boolean ret = false;
		if(getEfecto() != null) {
			ret = true;
		}
		return ret;
	}

	public Efecto getEfecto() {
		return this.efecto;
	}
	
	public void setEfecto(Efecto efecto) {
		this.efecto = efecto;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if(o instanceof Carta) {
			Carta c = (Carta)o ;
			if(this.getId() == c.getId() ) {
				ret = true;
			}
		}
		return ret ;
		
	}
	
	@Override
	public int compareTo(Carta carta) {		
		int ret = 0;
		
		if(this instanceof Unidad && carta instanceof Unidad) {
			if(((Unidad)this).getFuerza() < ((Unidad) carta).getFuerza()) {
				ret = 1;
			}
			else if(((Unidad)this).getFuerza() > ((Unidad) carta).getFuerza()) {
				ret = -1;
			}
			else {
				ret = this.getNombre().compareTo(carta.getNombre());
			}
		}
		else if(this instanceof Especial && carta instanceof Unidad) {
			ret = 1;
		}
		else if(this instanceof Unidad && carta instanceof Especial) {
			ret = -1;
		}
		else if(this instanceof Especial && carta instanceof Especial) {
			ret = this.getNombre().compareTo(carta.getNombre());
		}
		else {
			ret = this.getNombre().compareTo(carta.getNombre());
		}
		
		return ret;
	}

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}
	
	public abstract String getDescripcion();
}
