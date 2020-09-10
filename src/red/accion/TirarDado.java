package red.accion;

import java.io.Serializable;

import negocio.Juego;

public class TirarDado implements Accion, Serializable {

	private int valor;
	
	public TirarDado(int valor) {
		this.valor = valor;
	}
	
	@Override
	public void ejecutar(Juego juego) {
		juego.tirarDado(valor,1);
		
	}

}
