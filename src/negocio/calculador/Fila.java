package negocio.calculador;

import java.util.ArrayList;
import java.util.List;

import negocio.carta.Unidad;

public class Fila {

	public static final int CUERPOACUERPO = 0;
	public static final int ADISTANCIA = 1;
	public static final int ASEDIO = 2;

	private List<Puntaje> puntajes;
	private List<Integer> companeros;
	private List<Integer> morales;
	private int moral;
	private boolean doble;
	private boolean dobleEspecial;
	private int idCarta;
	private boolean dobleEspias;
	private boolean clima;
	private boolean climaAlterado;
	

	public Fila() {
		this.puntajes = new ArrayList<>();
		this.companeros = new ArrayList<>();
		this.morales = new ArrayList<>();
		this.moral = 0;
	}

	public void agregar(Puntaje puntaje) {
		this.puntajes.add(puntaje);
	}

	public void quitar(int id) {
		for (int i = 0; i < puntajes.size(); i++) {
			Puntaje p = puntajes.get(i);
			if (p.getId() == id) {
				puntajes.remove(p);
				break;
			}
		}
	}

	public int getFuerza() {
		int ret = 0;
		for (Puntaje p : puntajes) {
			int puntos = calcularPuntaje(p);
			ret += puntos;
		}
		return ret;
	}

	public void clima() {
		this.clima = true;
	}

	public void despejar() {
		this.clima = false;
	}

	public void agregarCompañeros(int[] companeros) {
		for (int i = 0; i < companeros.length; i++) {
			this.companeros.add(new Integer(companeros[i]));
		}
	}

	public void quitarCompaneros(int[] companeros) {
		List<Integer> aBorrar = new ArrayList<>();
		for (int i = 0; i < companeros.length; i++) {
			boolean agregado = false;
			for(Integer integer : this.companeros) {
				if(integer.intValue() == companeros[i] && !agregado) {
					aBorrar.add(integer);
					agregado = true;
				}
			}
		}
		for(Integer i : aBorrar) {
			this.companeros.remove(i);
		}
	}

	public void subirMoral(int idCarta) {
		this.morales.add(idCarta);
		moral++;
	}

	public void bajarMoral(int idCarta) {
		this.morales.remove(new Integer(idCarta));
		moral--;
	}

	public void doblar(boolean estado) {
		this.dobleEspecial = estado;
	}
	
	public void doblar(boolean estado, int idCarta) {
		this.idCarta = idCarta;
		this.doble = estado;
	}

	public int getPuntaje(Unidad unidad) {
		Puntaje p = new Puntaje(unidad.getId(), unidad.getFuerza(), !unidad.esHeroe(), unidad.esEspia()); 
		return calcularPuntaje(p);
	}

	public int getFuerzaMaxima() {
		int fuerzaMaxima = 0;
		for (Puntaje p : puntajes) {
			int puntos = calcularPuntaje(p);
			if(p.esModificable() && puntos>fuerzaMaxima) {
				fuerzaMaxima = puntos;
			}
		}
		return fuerzaMaxima;
	}

	public List<Integer> getCartasMasFuertes() {
		List<Integer> ret = new ArrayList<>();
		int fuerzaMaxima = getFuerzaMaxima();
		for (Puntaje p : puntajes) {
			int puntos = calcularPuntaje(p);
			if(p.esModificable() && puntos==fuerzaMaxima) {
				ret.add(p.getId());
			}
		}
		return ret; 
	}
	
	public int getFuerza(int id) {
		Puntaje puntaje = null;
		for (Puntaje p : puntajes) {
			if(p.getId() == id) {
				puntaje = p;
			}
		}
		int ret = 0;
		if(puntaje != null) {
			ret = calcularPuntaje(puntaje);
		}
		return ret;
	}

	public void quemar() {
		List<Integer> cartas = getCartasMasFuertes();
		for(Integer i : cartas) {
			quitar(i.intValue());
		}
	}

	public void siguienteRonda() {
		this.clima = false;
		this.companeros.clear();
		this.doble = false;
		this.moral = 0;
		this.puntajes.clear();
	}
	
	private int calcularPuntaje(Puntaje puntaje) {
		int puntos = puntaje.getPuntos();
		if (puntaje.esModificable()) {
			if (clima) {
				if(puntos > 1) {
					if(climaAlterado) {
						puntos /= 2;
					}
					else {
						puntos = 1;
					}
				}
			}
			int p = puntos;
			for (Integer i : companeros) {
				if (i.equals(puntaje.getId())) {
					puntos += p;
				}
			}
			if(this.morales.contains(puntaje.getId())) {
				puntos += (moral - 1);
			}
			else {
				puntos += moral;
			}
			if (dobleEspecial) {
				puntos *= 2;
			}
			else if(doble) {
				if(puntaje.getId() != idCarta) {
					puntos *= 2;
				}
			}
			if(puntaje.esEspia() && dobleEspias) {
				puntos *= 2;
			}
		}
		return puntos;
	}

	public void doblarEspias() {
		this.dobleEspias =true;
	}
	
	public void alterarClima() {
		this.climaAlterado = true;
	}

	public void reiniciar() {
		siguienteRonda();
		this.dobleEspias = false;
		this.climaAlterado = false;		
	}
}
