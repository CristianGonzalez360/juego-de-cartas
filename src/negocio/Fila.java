package negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.carta.Carta;
import negocio.carta.Invocadora;
import negocio.carta.Unidad;

public class Fila {

	private ArrayList<Carta> cartas;
	private boolean mardroeme = false;
	private List<Carta> berserkers;
	private Juego juego;
	
	public Fila() {
		this.cartas = new ArrayList<>();
		this.berserkers = new ArrayList<>();
	}
	
	public boolean agregar(Carta carta) {
		boolean ret = true;
		this.cartas.add(carta);
		if(carta instanceof Invocadora && ((Invocadora) carta).esBerserker()) {
			if(this.mardroeme) {
				carta.getEfecto().activar(juego);
				ret = false;
			}
			else {
				this.berserkers.add(carta);
			}
		}
		
		return ret;
	}
	
	public void quitar(Carta carta) {
		this.cartas.remove(carta);
	}
	
	public Carta getCarta(int id) {
		Carta ret = null;
		for (Carta carta : cartas) {
			if(carta.getId() == id) {
				ret = carta;
			}
		}
		return ret;
	}

	public void vaciar() {
		this.cartas.clear();
		this.berserkers.clear();
		this.mardroeme = false;
	}
	
	public void mardroeme(boolean estado) {
		this.mardroeme = estado;
		if(estado == true) {
			for(Carta carta : berserkers) {
				carta.getEfecto().activar(juego);
			}
			berserkers.clear();
		}
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}
}
