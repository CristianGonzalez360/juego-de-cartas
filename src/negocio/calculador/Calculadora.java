package negocio.calculador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import negocio.carta.*;

public class Calculadora {

	private Fila[] filas;
	
	public Calculadora() {
		this.filas = new Fila[3];
		this.filas[0] = new Fila();
		this.filas[1] = new Fila();
		this.filas[2] = new Fila();
	}
	
	public void agregar(Unidad unidad) {
		Puntaje puntaje = new Puntaje(unidad.getId(), unidad.getFuerza(), !unidad.esHeroe(), unidad.esEspia());
		filas[unidad.getFila()].agregar(puntaje);
	}
	
	public void quitar(Unidad unidad) {
		filas[unidad.getFila()].quitar(unidad.getId());
	}
	
	public void clima(int fila) {
		filas[fila].clima();
	}
	
	public void alterarClima() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].alterarClima();
		}
	}
	
	public void despejar() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].despejar();
		}
	}
	
	public void agregarCompaneros(int fila, int[] companeros) {
		filas[fila].agregarCompañeros(companeros);
	}
	
	public void quitarCompaneros(int fila, int[] companeros) {
		filas[fila].quitarCompaneros(companeros);
	}
	
	public void subirMoral(int fila, int idCarta) {
		filas[fila].subirMoral(idCarta);
	}
	
	public void bajarMoral(int fila, int idCarta) {
		filas[fila].bajarMoral(idCarta);
	}
	
	public void doblar(int fila, boolean estado) {
		filas[fila].doblar(estado);
	}
	
	public void doblar(int fila, boolean estado, int idCarta) {
		filas[fila].doblar(estado, idCarta);
	}
	
	public void doblarEspias() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].doblarEspias();
		}
	}
	
	public int getFilaOptima(Unidad unidad) {
		int ret = 0;
		int puntos0 = filas[0].getPuntaje(unidad);
		int puntos1 = filas[1].getPuntaje(unidad);
		if(puntos0>puntos1) {
			ret = 0;
		}
		else if(puntos0<puntos1) {
			ret = 1;
		}
		else if(unidad.getFila() == -1){
			Random r = new Random();
			ret = r.nextInt(2);
		}
		else {
			ret = unidad.getFila();
		}
		return ret;
	}
	
	public int getFuerzaFila(int fila) {
		return filas[fila].getFuerza();
	}
	
	public int getTotalPuntos() {
		int ret = 0;
		for (int i = 0; i < filas.length; i++) {
			ret += filas[i].getFuerza();
		}
		return ret;
	}
	
	public List<Integer> getCartasMasFuertes() {
		
		List<Integer> ret = new ArrayList<Integer>();
		List<Integer> filasConFuerzaMaxima = new ArrayList<>();
	
		int fuerzaMaxima = getFuerzaMaxima();
		
		//Busco las filas que tienen la fuerza maxima.
		for (int i = 0; i < filas.length; i++) {
			if(filas[i].getFuerzaMaxima() == fuerzaMaxima) {
				filasConFuerzaMaxima.add(i);
			}
		}
		
		//Obtengo los ids de las cartas mas fuertes de cada fila con la fuerza maxima.
		for(Integer i : filasConFuerzaMaxima) {
			ret.addAll(filas[i].getCartasMasFuertes());
		}
		return ret;
	}
	
	public List<Integer> getCartasMasFuertes(int fila) {
		List<Integer> ret = new ArrayList<>();
		int fuerzaFila = getFuerzaFila(fila);
		if(fuerzaFila>=10){
			ret.addAll(filas[fila].getCartasMasFuertes());
		}
		return ret;
	}
	
	public int getFuerzaMaxima() {
		//Calculo la fuerza maxima de cada fila.
 		int[] fuerzasMaximas = new int[3];
		for (int i = 0; i < filas.length; i++) {
			fuerzasMaximas[i] = filas[i].getFuerzaMaxima();
		}
		//busco cual es la fuerza maximas entre las 3 filas.
		int fuerzaMaxima = 0;
		for (int i = 0; i < fuerzasMaximas.length; i++) {
			if(fuerzasMaximas[i]>fuerzaMaxima) {
				fuerzaMaxima = fuerzasMaximas[i];
			}
		}
		return fuerzaMaxima;
	}

	public int getFuerzaTotal() {
		int ret = 0;
		for (int i = 0; i < filas.length; i++) {
			ret += filas[i].getFuerza();
		}
		return ret;
	}

	public void siguienteRonda() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].siguienteRonda();
		}		
	}	
	
	public void reiniciar() {
		for (int i = 0; i < filas.length; i++) {
			filas[i].reiniciar();
		}	
	}

	public void despejar(int fila) {
		this.filas[fila].despejar();
	}
	
	public int getPuntos(int idCarta, int fila) {
		return this.filas[fila].getFuerza(idCarta);
	}

}