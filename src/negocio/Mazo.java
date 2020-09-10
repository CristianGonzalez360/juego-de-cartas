package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import negocio.carta.Carta;
import negocio.carta.Especial;
import negocio.carta.Invocadora;
import negocio.carta.Lider;
import negocio.carta.Unidad;

public class Mazo extends Baraja implements Serializable{

	public static final int FUERZA_MAXIMA = 100;
	public static final int MINIMO_UNIDADES = 22;
	public static final int MAXIMO_ESPECIALES = 10;
	public static final int MAXIMO_HEROES = 4;

	private Lider lider;
	private List<Carta> noDisponibles;
	private HashMap<Integer, Carta> invocadoras;
	private int jugador;
	private int cantidadEspeciales;
	private int cantidadUnidades;
	private int cantidadHeroes;
	
	public Mazo(int id, String nombre) {
		super(id, nombre);
		this.noDisponibles = new ArrayList<>();
		this.invocadoras = new HashMap<>();
	}
	
	public void setLider(Lider lider) {
		this.lider = lider;
	}
	
	public Lider getLider() {
		return this.lider;
	}
	
	public boolean tieneSiguiente() {
		boolean ret = false;
		if(!this.getCartas().isEmpty()) {
			ret = true;
		}
		return ret;
	}
	
	public Carta getSiguiente() {
		Carta ret = null;
		if(tieneSiguiente()) {
			ret = getCartas().get(0);
			noDisponibles.add(ret);
			quitarCarta(ret);
		}
		return ret;
	}
	
	public void mezclar() {
		Collections.shuffle(getCartas());
	}
	
	public Carta getCarta(int idCarta) {
		Carta ret = null;
		for(Carta carta: getCartas()) {
			if(carta.getId() == idCarta) {
				ret = carta;
				noDisponibles.add(ret);
				quitarCarta(ret);
				break;
			}
		}
		return ret;
	}
	
	public void setCartas(List<Carta> cartas) {
		for(Carta c : cartas) {
			agregarCarta(c);
		}
	}
	public String getRutaImagen() {
		return "/"+getNombre()+"/"+getNombre()+".png";
	}
	
	public String getRutaIcono() {
		return "/Iconos/" + getNombre() + ".png";
	}
	
	public void reiniciar() {
		for (Carta carta : noDisponibles) {
			if(carta instanceof Unidad && ((Unidad) carta).esAgil()) {
				carta.setFila(-1);
			}
			super.agregarCarta(carta);
		}
		noDisponibles.clear();
		//quito las invocadas y las que no son mias
		for (Carta carta : getCartas()) {
			Carta invocadora = invocadoras.get(carta.getId()); 
			if(invocadora != null) {
				getCartas().remove(carta);
			}
			if(carta.getJugador() != 0) {
				getCartas().remove(carta);
			}
		}
	}
	
	public void devolver(Carta carta) {
		if(carta instanceof Unidad && ((Unidad) carta).esAgil()) {
			carta.setFila(-1);
		}
		this.agregarCarta(carta);
		this.noDisponibles.remove(carta);
	}
	
	@Override
	public void agregarCarta(Carta carta) {
		super.agregarCarta(carta);
		if(carta instanceof Unidad) {
			this.cantidadUnidades++;
			if(((Unidad) carta).esHeroe()) {
				this.cantidadHeroes++;
			}
			if(carta instanceof Invocadora) {
				Invocadora invocadora = (Invocadora) carta;
				invocadoras.put(invocadora.getInvocada().getId(),invocadora);
			}
		}
		else if(carta instanceof Especial) {
			this.cantidadEspeciales++;
		}
	}
	
	@Override
	public void quitarCarta(Carta carta) {
		super.quitarCarta(carta);
		if(carta instanceof Unidad) {
			this.cantidadUnidades--;
			if(((Unidad) carta).esHeroe()) {
				this.cantidadHeroes--;
			}
			if(carta instanceof Invocadora) {
				Invocadora invocadora = (Invocadora) carta;
				invocadoras.remove(invocadora.getInvocada().getId());
			}
		}
		else if(carta instanceof Especial) {
			this.cantidadEspeciales--;
		}
	}

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
		getHabilidad().setJugador(jugador);
		this.lider.setJugador(jugador);
	}
	
	public int getCantidadUnidades() {
		return cantidadUnidades;
	}

	public int getCantidadEspeciales() {
		return this.cantidadEspeciales;
	}

	public int getFuerzaMaxima() {
		int ret = 0;
		for(Carta carta : getCartas()) {
			if(carta instanceof Unidad) {
				ret += ((Unidad)carta).getFuerza();
			}
		}
		return ret;
	}

	public int getCantidad() {
		return getCartas().size();
	}

	public int getCantidadHeroes() {
		return this.cantidadHeroes;
	}
	
	public boolean validar() {
		boolean ret = true;
		if(getCantidadUnidades()<MINIMO_UNIDADES) {
			ret = false;
		}
		if(getCantidadEspeciales()>MAXIMO_ESPECIALES) {
			ret = false;
		}
		if(getFuerzaMaxima()>FUERZA_MAXIMA) {
			ret = false;
		}
		if(getCantidadHeroes()>MAXIMO_HEROES) {
			ret = false;
		}
		return ret;
	}
}
