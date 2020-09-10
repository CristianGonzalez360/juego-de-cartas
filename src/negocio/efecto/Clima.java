package negocio.efecto;

import java.io.Serializable;

import negocio.Juego;

public class Clima implements Efecto, Serializable {
	
	public static int FRIO = 0;
	public static int NIEBLA = 1;
	public static int LLUVIA = 2;
	public static int TORMENTA = 3;
	public static int LIMPIO = 4;

	private int fila;
	
	public Clima(int fila) {
		this.fila = fila;
	}

	@Override
	public void entrada(Juego juego) {
		if(fila<3) {
			juego.getTablero().clima(this.fila);
		}
		else if (fila == TORMENTA){
			juego.getTablero().clima(LLUVIA);
			juego.getTablero().clima(NIEBLA);
		}
		else if(fila == 4) {
			juego.getTablero().despejar();
		}
	}

	@Override
	public void salida(Juego juego) {
		if(fila<3) {
			juego.getTablero().despejar(this.fila);
		}
		else {
			juego.getTablero().despejar(LLUVIA);
			juego.getTablero().despejar(NIEBLA);
		}
	}

	@Override
	public boolean activar(Juego juego) {
		return false;
	}

	@Override
	public String getDescripcion() {
		String ret = "";
		if(fila == 0) {
			ret = "Reduce la fuerza de las Unidades Cuerpo a Cuerpo a 1. ";
		}
		else if(fila == 1) {
			ret = "Reduce la fuerza de las Unidades a Distancia a 1. ";
		}
		else if(fila == 2) {
			ret = "Reduce la fuerza de las Unidades de Asedio a 1. ";
		}
		else if(fila == 3) {
			ret = "Reduce la fuerza de las Unidades a Distancia y de Asedio a 1. ";
		}
		else if(fila == 4) {
			ret = "Quita todos los efectos de Clima. ";
		}
		return ret;
	}
}
