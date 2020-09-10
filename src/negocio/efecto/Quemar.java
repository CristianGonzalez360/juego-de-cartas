package negocio.efecto;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;

public class Quemar implements Efecto, Serializable {
	
	public static int CUERPOACUERPO = 0;
	public static int ADISTANCIA = 1;
	public static int ASEDIO = 2;
	public static int TODO = 3;
	
	private int fila;
	private Carta carta;
	
	public Quemar(Carta carta) {
		this.carta = carta;
		if(carta.getNombre().equals("Clan Dimun Pirate")) {
			this.fila = 3;
		}
		else {
			this.fila = carta.getFila();
		}
	}
	

	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		tablero.empezarMovimiento();
		List<Integer> masFuertes0 = null;
		List<Integer> masFuertes1 = null;
		if (fila == 3) {
			int fuerzaMaxima0 = tablero.getFuerzaMaxima(0);
			int fuerzaMaxima1 = tablero.getFuerzaMaxima(1);
			if (fuerzaMaxima0 > fuerzaMaxima1) {
				masFuertes0 = tablero.getCartasMasFuertes(0);
			} else if (fuerzaMaxima0 < fuerzaMaxima1) {
				masFuertes1 = tablero.getCartasMasFuertes(1);
			} else {
				masFuertes0 = tablero.getCartasMasFuertes(0);
				masFuertes1 = tablero.getCartasMasFuertes(1);
			}
		} else {
			if (carta.getJugador() == 1) {
				masFuertes0 = tablero.getCartasMasFuertes(fila, 0);
			} else {
				masFuertes1 = tablero.getCartasMasFuertes(fila, 1);
			}
		}
		if (masFuertes0 != null) {
			for (Integer i : masFuertes0) {
				tablero.descartar(i.intValue(), 0);
			}
		}
		if (masFuertes1 != null) {
			for (Integer i : masFuertes1) {
				tablero.descartar(i.intValue(), 1);
			}
		}
		tablero.terminarMovimiento();
	}


	@Override
	public void salida(Juego juego) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		String ret = "";
		if(fila == 0) {
			ret = "Descarta la/s carta/s mas fuerte/s de combate Cuerpo a Cuerpo del rival, si la fuerza de la fila es mayor a 10. ";
		}
		if(fila == 1) {
			ret = "Descarta la/s carta/s mas fuerte/s de combate a Distancia del rival, si la fuerza de la fila es mayor a 10. ";
		}
		if(fila == 2) {
			ret = "Descarta la/s carta/s mas fuerte/s de Asedio del rival, si la fuerza de la fila es mayor a 10. ";
		}
		if(fila == 3) {
			ret = "Descarta la/s carta/s mas fuerte/s del campo, afecta a los dos jugadores. ";
		}
		return ret;
	}

}
