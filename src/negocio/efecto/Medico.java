package negocio.efecto;

import java.io.Serializable;
import java.util.List;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;
import negocio.carta.Unidad;

public class Medico implements Efecto, Serializable {

	private boolean esHeroe;
	private Carta carta;
	
	public Medico(Carta carta) {
		this.carta = carta;
		this.esHeroe = false;
		if(carta instanceof Unidad) {
			this.esHeroe = ((Unidad) carta).esHeroe();
		}
	}
	
	@Override
	public void entrada(Juego juego) {
		if (carta.getJugador() == 0) {
			Tablero tablero = juego.getTablero();
			if(esHeroe || tablero.isMedicoRandom() == false) {
				List<Carta> revivibles = tablero.getCartasRevivibles();
				tablero.jugar(revivibles);
			} 
			else {
				tablero.revivir();
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
		return "Elige una carta del montón de descartes y juégala. ";
	}

}
