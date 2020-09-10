package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.carta.Carta;
import negocio.carta.Invocadora;
import negocio.carta.Unidad;

public class Invocar implements Efecto, Serializable {
	
	private Unidad invocado;
	private Carta carta;

	public Invocar(Invocadora invocadora) {
		this.carta = invocadora;
		this.invocado = invocadora.getInvocada();
	}
	@Override
	public void entrada(Juego juego) {
		// TODO Auto-generated method stub
	}

	@Override
	public void salida(Juego juego) {
		juego.getTablero().agregarCartaAlCampo(invocado, carta.getJugador(), false);
		if(invocado.tieneEfecto()) {
			invocado.getEfecto().entrada(juego);
		}
	}
	
	@Override
	public boolean activar(Juego juego) {
		return false;
	}
	
	@Override
	public String getDescripcion() {
		return "Juega una carta poderosa cuando es quitada del campo. ";
	}

}
