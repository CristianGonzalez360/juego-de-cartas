package negocio.efecto;

import java.io.Serializable;
import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;
import negocio.carta.Relacionada;

public class Reunion implements Efecto, Serializable {
	
	private int[] cartas;
	private Carta carta;

	public Reunion(Relacionada relacionada) {
		this.cartas = relacionada.getCartasRelacionadas();
		this.carta = relacionada;
	}

	@Override
	public void entrada(Juego juego) {
		if(carta.getJugador() == 0) {
			Tablero tablero = juego.getTablero();
			for(int i = 0; i<cartas.length; i++) {
				Carta carta = tablero.getCarta(cartas[i]);
				if(carta != null) {
					tablero.jugarCarta(carta, 0);
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
		return "Juega todas las cartas con el mismo nombre que hay en el mazo. ";
	}

}
