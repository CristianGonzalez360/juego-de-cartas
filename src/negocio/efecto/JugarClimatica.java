package negocio.efecto;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;

public class JugarClimatica implements Efecto, Serializable {
	
	private int fila;
	private Carta carta;
	
	public JugarClimatica(Carta carta) {
		this.fila = carta.getFila();
		this.carta = carta;
	}

	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		List<Carta> climas = tablero.getClimaticas();

		if (carta.getJugador() == 0) {
			if (fila < 4) {
				for (Carta climatica : climas) {
					if (climatica.getFila() == this.fila) {
						tablero.jugarCarta(climatica, 0);
						break;
					}
				}
			} else {
				tablero.jugar(climas);
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
		String ret = "";
		if(fila == 0) {
			ret = "Toma una carta Frío del mazo y la juega. ";
		}
		if(fila == 1) {
			ret = "Toma una carta Nuebla del mazo y la juega. ";
		}
		if(fila == 2) {
			ret = "Toma una carta Lluvia del mazo y la juega. ";
		}
		if(fila == 3) {
			ret = "Toma una carta Tormenta de Skellige del mazo y la juega. ";
		}
		if(fila == 4) {
			ret = "Juega una carta Climática del mazo a elección. ";
		}
		return ret;
	}

}
