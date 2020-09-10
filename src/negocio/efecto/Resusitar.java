package negocio.efecto;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;

public class Resusitar implements Efecto, Serializable {

	private Carta carta;
	public Resusitar(Carta carta) {
		this.carta = carta;
	}
	@Override
	public void entrada(Juego juego) {
		if(carta.getJugador() == 0) {
			Tablero tablero = juego.getTablero();
			List<Carta> revivibles = tablero.getCartasRevivibles();
			if (!revivibles.isEmpty()) {
				Random r = new Random();
				int indice = r.nextInt(revivibles.size());
				Carta carta = revivibles.get(indice);
				tablero.quitarCartaDeDescartes(carta, 0, true);
				tablero.agregarCartaALaMano(carta, 0);
			}
		}
	}

	@Override
	public void salida(Juego juego) {

	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		return "Devuelve a tu mano una Unidad al azar del montón de descartes. ";
	}

}
