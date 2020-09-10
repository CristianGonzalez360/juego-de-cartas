package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;
import negocio.Tablero;
import negocio.carta.Invocadora;
import negocio.carta.Unidad;

public class Berserker implements Efecto, Serializable{

	private Unidad transformacion;
	private Invocadora original;
	
	public Berserker(Invocadora invocadora) {
		this.transformacion = invocadora.getInvocada();
		this.original = invocadora;
		this.original.setEsBerserker(true);
		this.transformacion.setRevivible(true); 
	}
	@Override
	public void entrada(Juego juego) {
		
	}
	
	@Override
	public void salida(Juego juego) {
	
	}
	
	@Override
	public boolean activar(Juego juego) {
		Tablero tablero = juego.getTablero();
		tablero.quitarCartaDelCampo(original, original.getJugador(), false);
		if(original.getJugador() == 0) {
			tablero.jugarCarta(transformacion, 0);
		}
		return true;
	}
	@Override
	public String getDescripcion() {
		return "Se transforma en un Oso cuando se juega una carta Mardroeme en su fila. ";
	}
}
