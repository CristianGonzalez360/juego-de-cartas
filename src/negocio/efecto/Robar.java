package negocio.efecto;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;

public class Robar implements Efecto, Serializable {

	private Carta carta;
	
	public Robar(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		if(carta.getJugador() == 0) {
			tablero.empezarMovimiento();
		}
		else if(carta.getJugador() == 1) {
			List<Carta> revivibles = tablero.getCartasRevivibles();
			if(!revivibles.isEmpty()) {
				juego.enviarCartas(revivibles);
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
		return "Elige una carta del montón de descartes del rival y agregala a tu mano. ";
	}

}
