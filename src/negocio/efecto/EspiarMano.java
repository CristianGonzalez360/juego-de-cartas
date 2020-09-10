package negocio.efecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Carta;

public class EspiarMano implements Efecto, Serializable {
	
	private Carta carta;
	
	public EspiarMano(Carta carta) {
		this.carta = carta;
	}
	
	@Override
	public void entrada(Juego juego) {
		Tablero tablero = juego.getTablero();
		if(carta.getJugador() == 0) {
			tablero.empezarMovimiento();
		}
		else if(carta.getJugador() == 1) {
			List<Carta> mano = tablero.getMano();
			List<Carta> aMostrar = new ArrayList<>();
			if (mano.size()>3) {
				while (aMostrar.size() < 3) {
					Random r = new Random();
					int indice = r.nextInt(mano.size());
					Carta carta = mano.get(indice);
					if (!aMostrar.contains(carta)) {
						aMostrar.add(carta);
					}
				} 
			}
			else {
				aMostrar.addAll(mano);
			}
			juego.mostrarCartas(aMostrar);
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
		// TODO Auto-generated method stub
		return "Mira 3 cartas al azar de la mano del rival. ";
	}

}
