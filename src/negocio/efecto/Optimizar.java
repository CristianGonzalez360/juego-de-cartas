package negocio.efecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Unidad;

public class Optimizar implements Efecto, Serializable {

	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		for(int jugador = 0; jugador<2; jugador++) {
			List<Unidad> aBorrar = tablero.getCartasAgiles(jugador);
			for(Unidad carta : aBorrar) {
				int fila = tablero.getFilaOptima(carta, jugador);
				if(carta.getFila() != fila) {
					tablero.descartar(carta, jugador);
					carta.setFila(fila);
					if(jugador == 0) {
						tablero.jugarCarta(carta, jugador);
					}
				}
			}
		}
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
		return "Mueve las cartas ágiles a la fila donde suman mas fuerza. ";
	}

}
