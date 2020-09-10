package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import negocio.carta.Carta;
import negocio.carta.Unidad;

public class Campo {

	public static int CUERPOACUERPO = 0;
	public static int ADISTANCIA = 1;
	public static int ASEDIO = 2;

	private Fila[] filas;
	private ArrayList<Unidad> todas;

	public Campo() {
		filas = new Fila[3];
		filas[0] = new Fila();
		filas[1] = new Fila();
		filas[2] = new Fila();
		todas = new ArrayList<>();
	}
	
	public void setJuego(Juego juego) {
		for (int i = 0; i < filas.length; i++) {
			filas[i].setJuego(juego);
		}
	}

	public void jugarCarta(Unidad c) {
		int fila = c.getFila();
		boolean agregar = filas[fila].agregar(c);
		if(agregar) {
			todas.add(c);
		}
	}

	public void quitarCarta(Carta c) {
		int fila = c.getFila();
		filas[fila].quitar(c);
		todas.remove(c);
	}

	public Carta getCarta(int id) {
		Carta ret = null;
		for (int i = 0; i < filas.length; i++) {
			ret = filas[i].getCarta(id);
			if (ret!=null) {
				break;
			}
		}
		return ret;
	}

	public void reiniciar() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].vaciar();
		}
		this.todas.clear();
	}

	public List<Unidad> getCartas() {
		ArrayList<Unidad> ret = new ArrayList<>();
		for(Unidad c : todas) {
			ret.add(c);
		}
		return ret;
	}

	public Unidad getCarta() {
		Unidad ret = null;
		if(!todas.isEmpty()) {
			Random r = new Random();
			int indice = r.nextInt(todas.size());
			ret = todas.get(indice);
			todas.remove(ret);
		}
		return ret;
	}

	public List<Unidad> getCartasAgiles() {
		List<Unidad> ret = new ArrayList<>();
		for (Unidad carta : todas) {
			if(carta.esAgil()) {
				ret.add(carta);
			}
		}
		return ret;
	}
	
	public void mardroeme(boolean estado, int fila) {
		this.filas[fila].mardroeme(estado);
	}
}
