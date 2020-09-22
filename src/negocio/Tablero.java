package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interfaz.VistaTablero;
import negocio.calculador.Calculadora;
import negocio.carta.Carta;
import negocio.carta.Climatica;
import negocio.carta.Especial;
import negocio.carta.Lider;
import negocio.carta.Unidad;
import negocio.efecto.Senuelo;

public class Tablero {

	private Mazo mazo;
	private boolean liderDisponible;
	private Lider liderRival;

	private ArrayList<Carta> mano;
	private Campo[] campos;
	private ArrayList<Carta> descarte;
	private ArrayList<Climatica> climaticas;
	private ArrayList<Especial> especiales;

	private Carta seleccionada;

	private Calculadora[] calculadoras;

	private VistaTablero vista;
	private boolean turno;
	private Juego juego;

	private int movimientos;
	private int sacrificios = 0;
	private boolean medicoRandom;

	public Tablero() {
		this.campos = new Campo[2];
		this.campos[0] = new Campo();
		this.campos[1] = new Campo();
		this.climaticas = new ArrayList<>();
		this.especiales = new ArrayList<>();
		this.liderDisponible = true;
		this.mano = new ArrayList<>();
		this.calculadoras = new Calculadora[2];
		this.calculadoras[0] = new Calculadora();
		this.calculadoras[1] = new Calculadora();
		this.turno = false;
		this.descarte = new ArrayList<>();
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
		this.campos[0].setJuego(juego);
		this.campos[1].setJuego(juego);
	}

	public void setVista(VistaTablero vista) {
		this.vista = vista;
		this.vista.setTablero(this);
	}

	public void jugarCarta(Carta carta, int jugador) {
		empezarMovimiento();

		carta.setJugador(jugador);

		if (jugador == 0) {
			quitarCartaDeLaMano(carta, jugador);
			this.vista.apagarModosSeleccion();
			this.juego.jugarCarta(carta, jugador);
			deseleccionar();
		}

		if (carta instanceof Unidad) {
			Unidad unidad = (Unidad) carta;
			jugarCarta(unidad);
		} else if (carta instanceof Climatica) {
			jugarCarta((Climatica) carta, jugador);
		} else if (carta instanceof Especial) {
			Especial especial = (Especial) carta;
			jugarCarta(especial);
		} else if (carta instanceof Lider) {
			Lider lider = (Lider) carta;
			jugarCarta(lider);
		}

		if (carta.tieneEfecto()) {
			carta.getEfecto().entrada(juego);
		}

		this.vista.mostrarPuntos(calculadoras[0], 0);
		this.vista.mostrarPuntos(calculadoras[1], 1);

		terminarMovimiento();
	}

	private void jugarCarta(Lider lider) {
		this.vista.jugarCarta(lider, lider.getJugador());
		if (lider.getJugador() == 0) {
			this.liderDisponible = false;
		} else {
			this.liderRival = lider;
		}
	}

	private void jugarCarta(Unidad unidad) {
		if (unidad.esEspia()) {
			unidad.setJugador((unidad.getJugador() + 1) % 2);
		}
		agregarCartaAlCampo(unidad, unidad.getJugador(), false);
	}

	private void jugarCarta(Climatica climatica, int jugador) {
		boolean agregar = true;
		for (Climatica c : this.climaticas) {
			if (c.getNombre().equals(climatica.getNombre())) {
				agregar = false;
			}
		}
		if (agregar == true && !climatica.getNombre().equals("Clear Weather")) {
			this.climaticas.add(climatica);
			this.vista.jugarCarta(climatica, jugador);
		} else {
			agregarCartaADescartes(climatica, jugador, false);
		}
	}

	private void jugarCarta(Especial especial) {
		if (especial.getNombre().equals("Scorch")) {
			agregarCartaADescartes(especial, especial.getJugador(), false);
		} else {
			this.especiales.add(especial);
			this.vista.jugarCarta(especial, especial.getJugador());
		}
	}

	public void agregarCartaAlCampo(Unidad carta, int jugador, boolean enviar) {
		this.calculadoras[jugador].agregar(carta);
		this.vista.agregarAlCampo(carta, jugador);
		this.campos[jugador].jugarCarta(carta);
		if (enviar) {
			juego.agregarACampo(carta);
		}
		this.vista.mostrarPuntos(calculadoras[jugador], jugador);
	}

	public void quitarCartaDelCampo(Unidad carta, int jugador, boolean enviar) {
		if (carta != null) {
			this.campos[jugador].quitarCarta(carta);
			this.calculadoras[jugador].quitar(carta);
			this.vista.quitarDelCampo(carta, jugador);
		}
		if (enviar) {
			juego.quitarDeCampo(carta);
		}
		this.vista.mostrarPuntos(calculadoras[jugador], jugador);
	}

	public void agregarCartaADescartes(Carta carta, int jugador, boolean enviar) {
		if (jugador == 0) {
			this.descarte.add(carta);
		}
		if (enviar == true) {
			juego.agregarADescartes(carta);
		}
		this.vista.agregarCartaADescartes(carta, jugador);
	}

	public void quitarCartaDeDescartes(Carta carta, int jugador, boolean enviar) {
		if (jugador == 0) {
			this.descarte.remove(carta);
		}
		if (enviar == true) {
			juego.quitarDeDescartes(carta);
		}
		this.vista.quitarCartaDescartes(carta, jugador);
	}

	public void agregarCartaALaMano(Carta carta, int jugador) {
		if (carta instanceof Unidad && ((Unidad) carta).esAgil()) {
			carta.setFila(-1);
		}
		this.mano.add(carta);
		this.vista.agregarALaMano(carta);
	}

	public void quitarCartaDeLaMano(Carta carta, int jugador) {
		this.mano.remove(carta);
		this.vista.quitarCartaDeLAMano(carta);
	}

	public void descartar(Carta carta, int jugador) {
		empezarMovimiento();

		agregarCartaADescartes(carta, jugador, false);

		if (carta instanceof Unidad) {
			Unidad unidad = (Unidad) carta;
			quitarCartaDelCampo(unidad, jugador, false);
		}

		if (carta.tieneEfecto()) {
			carta.getEfecto().salida(juego);
		}

		terminarMovimiento();
	}

	public void setJugador(Jugador jugador, int i) {
		if (i == 0) {
			this.mazo = jugador.getMazo();
			this.mazo.mezclar();
			repartir();
			this.vista.mostrarMano(this.mano);
		}
		this.vista.mostrarJugador(jugador, i);
	}

	public void repartir() {
		levantarCartas(10, 0);
	}

	public Carta cambiar(Carta carta) {
		Carta ret = this.mazo.getSiguiente();
		if (ret != null) {
			this.quitarCartaDeLaMano(carta, 0);
			this.mazo.devolver(carta);
			agregarCartaALaMano(ret, 0);
		}
		return ret;
	}

	public Carta getCarta(int idCarta) {
		Carta ret = mazo.getCarta(idCarta);// Busca la carta en el mazo
		if (ret == null) {// Si no esta en el mazo la busca en la mano
			for (Carta c : mano) {
				if (c.getId() == idCarta) {
					ret = c;
					quitarCartaDeLaMano(ret, 0);
					break;
				}
			}
		}
		return ret;
	}

	public void levantarCartas(int cantidad, int jugador) {
		if (jugador == 0) {
			for (int i = 0; i < cantidad; i++) {
				levantarCarta();
			}
		}
	}

	public void levantarCarta() {
		if (this.mazo.tieneSiguiente()) {
			Carta carta = this.mazo.getSiguiente();
			agregarCartaALaMano(carta, 0);
			informarCartas();
		}
	}

	public void seleccionarCarta(Carta carta) {
		this.seleccionada = carta;
		this.vista.zoom(carta);
		this.vista.apagarModosSeleccion();

		if (carta instanceof Unidad) {
			Unidad unidad = (Unidad) carta;
			if (unidad.esEspia()) {
				this.vista.modoSeleccionFila(1, carta.getFila(), true);
			} else {
				this.vista.modoSeleccionFila(0, carta.getFila(), true);
			}
		} else if (carta instanceof Climatica) {
			this.vista.modoSeleccionClimaticas(true);
		} else if (carta instanceof Lider) {
			this.vista.modoSeleccionCampo(true);
		} else if (carta instanceof Especial) {
			if (carta.getNombre().equals("Decoy")) {
				this.vista.modoSeleccionCarta(true);
			}
			if (carta.getNombre().equals("Commander's Horn") || carta.getNombre().equals("Mardroeme")) {
				this.vista.modoSeleccionCuerno(true);
			}
			if (carta.getNombre().equals("Scorch")) {
				this.vista.modoSeleccionCampo(true);
			}
		}
	}

	public void jugarCartaSeleccionada() {
		jugarCarta(seleccionada, 0);
	}

	public void sustituir(Carta carta) {
		Unidad unidad = (Unidad) carta;
		quitarCartaDelCampo(unidad, 0, false);
		this.seleccionada.setFila(carta.getFila());// seteo la fila al decoy. Seleccionada es un decoy SEGURO
		Senuelo senuelo = (Senuelo) this.seleccionada.getEfecto();
		senuelo.setCarta(unidad);
		jugarCartaSeleccionada();
	}

	public void deseleccionar() {
		this.seleccionada = null;
		this.vista.deseleccionar();
	}

	public void seleccionarFila(int fila) {
		seleccionada.setFila(fila);
		jugarCartaSeleccionada();
	}

	public void clima(int fila) {
		this.calculadoras[0].clima(fila);
		this.calculadoras[1].clima(fila);
		this.vista.clima(fila);
	}

	public void despejar() {
		for (Climatica climatica : climaticas) {
			agregarCartaADescartes(climatica, climatica.getJugador(), false);
		}
		this.climaticas.clear();
		this.calculadoras[0].despejar();
		this.calculadoras[1].despejar();
		this.vista.despejar();
	}

	public void despejar(int fila) {
		this.calculadoras[0].despejar(fila);
		this.calculadoras[1].despejar(fila);
	}

	public void agregarCompaneros(int jugador, int fila, int[] companeros) {
		this.calculadoras[jugador].agregarCompaneros(fila, companeros);
	}

	public void quitarCompaneros(int jugador, int fila, int[] companeros) {
		this.calculadoras[jugador].quitarCompaneros(fila, companeros);
	}

	public void subirMoral(int jugador, int fila, int idCarta) {
		this.calculadoras[jugador].subirMoral(fila, idCarta);
	}

	public void bajarMoral(int jugador, int fila, int idCarta) {
		this.calculadoras[jugador].bajarMoral(fila, idCarta);
	}

	public void doblar(int jugador, int fila, boolean estado) {
		this.calculadoras[jugador].doblar(fila, estado);
	}

	public void doblar(int jugador, int fila, boolean estado, int idCarta) {
		this.calculadoras[jugador].doblar(fila, estado, idCarta);
	}

	public void jugarCarta(Carta carta) {
		if (carta instanceof Unidad) {
			quitarCartaDeDescartes(carta, 0, true);
			if (((Unidad) carta).esAgil()) {
				int fila = this.calculadoras[0].getFilaOptima((Unidad) carta);
				carta.setFila(fila);
			}
		} else if (carta instanceof Climatica) {
			this.mazo.getCarta(carta.getId());
		}
		jugarCarta(carta, 0);
		terminarMovimiento();
	}

	public List<Carta> getCartasRevivibles() {
		List<Carta> ret = new ArrayList<>();
		for (Carta carta : descarte) {
			if (carta instanceof Unidad && ((Unidad) carta).esRevivible()) {
				ret.add(carta);
			}
		}
		return ret;
	}

	public boolean isMedicoRandom() {
		return this.medicoRandom;
	}

	public void mardroeme(boolean estado, int fila, int jugador) {
		this.campos[jugador].mardroeme(estado, fila);
	}

	public void empezarMovimiento() {
		if (turno == true) {
			this.movimientos++;
		}
	}

	public void terminarMovimiento() {
		if (turno == true) {
			informarCartas();
			this.movimientos--;
			if (movimientos == 0) {
				if (this.mano.isEmpty() && !liderDisponible) {
					pasar();
				} else {
					terminarTurno();
				}
			}
		}
	}

	public void informarCartas() {
		this.juego.enviarInfoCartas(this.mano.size(), this.mazo.getCantidad());
		this.vista.mostrarInfoCartas(0, this.mano.size(), this.mazo.getCantidad());
	}

	public void empezarTurno() {
		if (this.mano.isEmpty() && !liderDisponible) {
			pasar();
		} else {
			this.turno = true;
			this.movimientos = 0;
			this.vista.modoJugar(true, liderDisponible);
		}
	}

	public void terminarTurno() {
		this.vista.modoJugar(false, false);
		this.turno = false;
		this.juego.terminarTurno(0);
	}

	public int getFuerzaTotal(int jugador) {
		return this.calculadoras[jugador].getFuerzaTotal();
	}

	public void pasar() {
		deseleccionar();
		this.vista.modoJugar(false, false);
		this.turno = false;
		this.juego.pasar(0);
	}

	private void vaciarTablero() {
		for (int i = 0; i < 2; i++) {
			for (Unidad unidad : this.campos[i].getCartas()) {
				descartar(unidad, i);
			}
		}
		this.seleccionada = null;
	}

	public void siguienteRonda() {
		this.turno = false;
		this.vista.siguienteRonda();
		vaciarTablero();
		for (Climatica climatica : climaticas) {
			descartar(climatica, climatica.getJugador());
		}
		this.climaticas.clear();
		for (Especial especial : especiales) {
			agregarCartaADescartes(especial, especial.getJugador(), false);
			especial.getEfecto().salida(juego);
		}
		if (!getLiderDisponible()) {
			this.mazo.getLider().getEfecto().salida(juego);
		}
		if (this.liderRival != null) {
			this.liderRival.getEfecto().salida(juego);
		}
		this.especiales.clear();

		this.vista.mostrarPuntos(this.calculadoras[0], 0);
		this.vista.mostrarPuntos(this.calculadoras[1], 1);
	}

	public void reiniciar() {
		this.turno = false;
		vaciarTablero();
		this.descarte.clear();
		this.mano.clear();
		this.calculadoras[0].reiniciar();
		this.calculadoras[1].reiniciar();
		this.liderDisponible = true;
		this.medicoRandom = false;
		this.vista.reiniciar();
	}

	public List<Carta> getClimaticas() {
		List<Carta> ret = new ArrayList<>();
		for (Carta carta : this.mazo.getCartas()) {
			if (carta instanceof Climatica) {
				ret.add((Climatica) carta);
			}
		}
		return ret;
	}

	public void jugar(List<Carta> cartas) {
		if (!cartas.isEmpty()) {
			empezarMovimiento();
			this.vista.modoJugar(cartas);
		}
	}

	public void anularLider() {
		this.liderDisponible = false;
	}

	public void robarCarta(List<Carta> cartas) {
		if (!cartas.isEmpty()) {
			this.vista.modoRobar(cartas);
		} else {
			terminarMovimiento();
		}
	}

	public void medicoRandom() {
		this.medicoRandom = true;
	}

	public void doblarEspias() {
		this.calculadoras[0].doblarEspias();
		this.calculadoras[1].doblarEspias();
	}

	public void sacrificar() {
		if (this.mano.size() > 1 && this.mazo.getCantidad() > 0) {
			empezarMovimiento();
			this.vista.modoSacrificar(mano);
		}
	}

	public void devolverAlMazo() {
		for (Carta carta : getCartasRevivibles()) {
			mazo.devolver(carta);
			quitarCartaDeDescartes(carta, 0, true);
		}
		this.mazo.mezclar();
	}

	public void alterarClima(int jugador) {
		this.calculadoras[jugador].alterarClima();
	}

	public Unidad getCartaAlAzarDelCampo() {
		Unidad ret = this.campos[0].getCarta();
		quitarCartaDelCampo(ret, 0, true);
		return ret;
	}

	public void revivir() {
		List<Carta> revivibles = getCartasRevivibles();
		if (!revivibles.isEmpty()) {
			Random r = new Random();
			int indice = r.nextInt(revivibles.size());
			Unidad unidad = (Unidad) revivibles.get(indice);
			quitarCartaDeDescartes(unidad, 0, true);
			agregarCartaAlCampo(unidad, 0, true);
			if (unidad.tieneEfecto()) {
				unidad.getEfecto().entrada(juego);
			}
		}
	}

	public int getFuerzaMaxima(int jugador) {
		return this.calculadoras[jugador].getFuerzaMaxima();
	}

	public List<Integer> getCartasMasFuertes(int jugador) {
		return this.calculadoras[jugador].getCartasMasFuertes();
	}

	public List<Integer> getCartasMasFuertes(int fila, int jugador) {
		return this.calculadoras[jugador].getCartasMasFuertes(fila);
	}

	public void descartar(int idCarta, int jugador) {
		Carta carta = this.campos[jugador].getCarta(idCarta);
		descartar(carta, jugador);
	}

	public List<Carta> getMano() {
		return this.mano;
	}

	public List<Unidad> getCartasAgiles(int jugador) {
		return this.campos[jugador].getCartasAgiles();
	}

	public int getFilaOptima(Unidad carta, int jugador) {
		return this.calculadoras[jugador].getFilaOptima(carta);
	}

	public boolean getTurno() {
		return this.turno;
	}

	public boolean getLiderDisponible() {
		return this.liderDisponible;
	}

	public void robarCarta(Carta carta) {
		agregarCartaALaMano(carta, 0);
		if (carta.getJugador() == 1) {
			quitarCartaDeDescartes(carta, 1, true);
		} else {
			this.mazo.getCarta(carta.getId());
		}
		terminarMovimiento();
	}

	public void sacrificar(Carta carta) {
		quitarCartaDeLaMano(carta, 0);
		agregarCartaADescartes(carta, 0, true);
		if (sacrificios == 0) {
			this.sacrificios++;
		} else {
			this.vista.modoRobar(this.mazo.getCartas());
			this.sacrificios = 0;
		}
	}

	public void espiarMano(List<Carta> cartas) {
		vista.mostrarCartas(cartas, 0);
		terminarMovimiento();
	}

	public void abandonar() {
		this.juego.abandonar(0);
	}

	public void jugarCuerno(int jugador, int fila, boolean estado) {
		doblar(jugador, fila, estado);
		this.vista.jugarCuerno(fila, jugador);
	}
}
