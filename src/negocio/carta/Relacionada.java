package negocio.carta;

import java.io.Serializable;

public class Relacionada extends Unidad implements Serializable{

	private int[] cartasRelacionadas;
	private int cantidad;
	
	public Relacionada() {

	}
	
	public void agregarRelacion(int idCarta) {
		this.cartasRelacionadas[cantidad] = idCarta;
		this.cantidad--;
	}

	public void setCantidad(int cantidad) {
		cartasRelacionadas = new int[cantidad];
		this.cantidad = cantidad-1;
	}

	public int[] getCartasRelacionadas() {
		return cartasRelacionadas;
	}
	
}
