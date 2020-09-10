package negocio;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaz.Gwent;
import interfaz.VistaDados;
import interfaz.editorMazo.EditorMazo;
import negocio.carta.Carta;
import negocio.carta.Lider;
import negocio.carta.Unidad;
import negocio.habilidades.Habilidad;
import negocio.habilidades.Nilfgaard;
import negocio.habilidades.Scoiatael;
import red.Red;
import red.accion.Abandonar;
import red.accion.AgregarACampo;
import red.accion.AgregarADescartes;
import red.accion.Continuar;
import red.accion.DecidirComienzo;
import red.accion.EnviarCarta;
import red.accion.InfoRival;
import red.accion.JugarCarta;
import red.accion.MostrarCartas;
import red.accion.Pasar;
import red.accion.PasarTurno;
import red.accion.QuitarDeCampo;
import red.accion.QuitarDeDescartes;
import red.accion.RegistrarJugador;
import red.accion.TirarDado;

public class Juego {

	private Juego instancia;
	private Tablero tablero;
	
	private Jugador miJugador;
	private Jugador[] jugadores;
	private Habilidad[] habilidades;
	
	private boolean tirarDados;
	private int quienDecideInicio;
	
	private int[] dados;
	private Red red;
	private Gwent gwent;
	
	private boolean[] paso;
	private int ronda;
	private int[] ganadorRonda;
	private boolean empezoRondaAnterior;
	
	private int[][] registroPuntos;
	
	public static final int REVANCHA = 1;
	public static final int EDITAR = 2;
	private int[] continuar;
	
	private Timer timer;
	private int tiempoMensajes;
	private int tiempoEspera;
	
	public Juego() {
		this.timer = new Timer();
		this.tablero = new Tablero();
		this.tablero.setJuego(this);
		this.jugadores = new Jugador[2];
		this.tirarDados = true;
		this.dados = new int[2];
		this.paso = new boolean[2];
		this.ronda = 0;
		this.ganadorRonda = new int[3];
		this.continuar = new int[2];
		this.habilidades = new Habilidad[2];
		this.registroPuntos = new int[3][2];
		this.instancia = this;
		this.tiempoEspera = 0;
		this.tiempoMensajes = 1500;
	}

	public void registrar(Jugador jugador, int i) {
		this.jugadores[i] = jugador;
		this.habilidades[i] = jugador.getMazo().getHabilidad();
		tablero.setJugador(jugador, i);
		
		if(i == 0) {
			miJugador = jugador;
			red.enviar(new RegistrarJugador(jugador));
		}
		else {
			gwent.getVistaDecidirComienzo().setIconoRival(jugador.getMazo().getRutaIcono());
		}
		gwent.getPantallaFinal().setIcono(jugador.getMazo().getRutaIcono(), i);
	
		if(jugadores[0] != null && jugadores[1] != null ) {
			comenzarPartida();
		}
	}
	
	public void comenzarPartida() {
		if (!(this.habilidades[0] instanceof Scoiatael && this.habilidades[1] instanceof Scoiatael)) {
			if (this.habilidades[0].inicioPartida(this)) {
				encolarMensaje("usaste tu habilidad de facción");
			}
			if (this.habilidades[1].inicioPartida(this)) {
				encolarMensaje("el rival usó su habilidad de facción");
			} 
		}
		
		if (jugadores[0].getMazo().getLider().getEfecto().activar(this)) {
			encolarMensaje("usaste tu líder");
		}
		if (jugadores[1].getMazo().getLider().getEfecto().activar(this)) {
			encolarMensaje("el rival usó su líder");
		}
		
		if(tirarDados == true) {
			gwent.getVistaDados().reiniciar();
			gwent.mostrar(Gwent.DADOS);
		}
		else if(quienDecideInicio == 0) {
			gwent.mostrar(Gwent.DECIDIR);
		}
	}
	
	public void jugarCarta(Carta carta, int jugador) {
		if(jugador == 1) {
			tablero.jugarCarta(carta, jugador);
			if(carta instanceof Lider) {
				mostrarMensaje("el rival usó su líder");
			}
		}
		else if(jugador == 0) {
			red.enviar(new JugarCarta(carta));
			if(carta instanceof Lider) {
				gwent.getVistaTablero().mostrarMensaje("usaste tu líder", tiempoMensajes);
			}
		}
	}
		
	public void setRed(Red red) {
		this.red = red;
		red.setJuego(this);
		red.escuchar();
		editarMazo();
	}

	public void setVista(Gwent gwent) {
		this.gwent = gwent;
		this.tablero.setVista(gwent.getVistaTablero());
		this.gwent.getPantallaFinal().setJuego(this);
		this.gwent.getVistaDados().setJuego(this);
		this.gwent.getVistaDecidirComienzo().setJuego(this);
		configurarRed();
	}
	
	public void configurarRed() {
		this.red = new Red();
		this.red.setJuego(this);
		this.red.setVista(gwent.getVistaRed());
		this.gwent.mostrar(Gwent.RED);
	}
	
	public void editarMazo() {
		EditorMazo editorMazo = new EditorMazo(this);
		editorMazo.setVista(gwent.getVistaEditorMazo());
		this.gwent.getVistaEditorMazo().desbloquear();
		this.gwent.mostrar(Gwent.MAZO);
	}

	public void tirarDado(int valor, int jugador) {
		this.dados[jugador] = valor;
		if(jugador == 0) {
			red.enviar(new TirarDado(valor));
		}
		if(dados[0]>0 && dados[1]>0) {
			establecerTurno();
		}
		else if(dados[1] == 0) {
			gwent.getVistaDados().mostrarMensaje(VistaDados.mensajeEspera);
		}
	}
	
	public void establecerTurno() {
		gwent.getVistaDados().agregarDado(dados[1]);
		boolean empate = true;
		
		if (dados[0] > dados[1]) {
			gwent.getVistaDados().mostrarMensaje(VistaDados.mensajeGana);
			tiempoEspera += 1500;
			empezoRondaAnterior = true;
			empezarTurno(false);
			empate = false;
		} else if (dados[0] < dados[1]) {
			gwent.getVistaDados().mostrarMensaje(VistaDados.mensajePierde);
			encolarMensaje("turno del rival");
			empate = false;
		} else {
			gwent.getVistaDados().mostrarMensaje(VistaDados.mensajeEmpate);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					dados[0] = 0;
					dados[1] = 0;
					gwent.getVistaDados().reiniciar();
				}
			}, tiempoMensajes);
		} 
		if (!empate) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					gwent.mostrar(Gwent.TABLERO);
					gwent.getVistaTablero().mostrarMensajes();
					tiempoEspera = 0;
				}
			}, tiempoMensajes);
		}
	}
	
	public void decidirComienzo(boolean b, int jugador) {
		if(jugador == 0) {
			red.enviar(new DecidirComienzo(b));
		}
		
		gwent.mostrar(Gwent.TABLERO);
		gwent.getVistaTablero().mostrarMensajes();
		
		if((b == true && jugador == 0) || (b == false && jugador == 1)) {
			empezarTurno(true);
			empezoRondaAnterior = true;
		}
		else {
			gwent.getVistaTablero().mostrarMensaje("turno del rival", tiempoMensajes);
			tiempoEspera = 0;
		}
	}
		
	public void terminarTurno(int jugador) {
		if(jugador == 0 ) {
			if(paso[1] == false) {
				gwent.getVistaTablero().mostrarMensaje("turno del rival", tiempoMensajes);
				red.enviar(new PasarTurno());
			}
			else if(paso[0] == false) {
				tablero.empezarTurno();
			}
		}
		else if(jugador == 1 ) {
			if(paso[0] == false) {
				empezarTurno(true);
			}
		}			
	}
	
	private void empezarTurno(boolean mostrandoTablero) {
		if(mostrandoTablero) {
			mostrarMensaje("tu turno");
		}
		else {
			encolarMensaje("tu turno");
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tiempoEspera = 0;
				tablero.empezarTurno();
			}
		}, tiempoEspera);
	}
	
	private void encolarMensaje(String mensaje) {
		gwent.getVistaTablero().encolarMensaje(mensaje, tiempoMensajes);
		tiempoEspera += tiempoMensajes;
	}
	
	private void mostrarMensaje(String mensaje) {
		gwent.getVistaTablero().mostrarMensaje(mensaje, tiempoMensajes);
		this.tiempoEspera += tiempoMensajes;
	}
	
	public void pasar(int jugador) {
		this.paso[jugador] = true;
		
		if(jugador == 0) {
			red.enviar(new Pasar());
			if(paso[1] == true) {
				mostrarMensaje("pasaste");
			}
			else {
				gwent.getVistaTablero().mostrarMensaje("pasaste", tiempoMensajes);
				gwent.getVistaTablero().mostrarMensaje("turno del rival", tiempoMensajes);
			}
		}
		else if(paso[0] == false){
			mostrarMensaje("el rival paso");
			empezarTurno(true);
		}
		else {
			mostrarMensaje("el rival paso");			
		}
		
		gwent.getVistaTablero().paso(jugador);
		
		if(paso[0] == true && paso[1] == true) {
			establecerGanadorRonda();
		}
	}

	private void establecerGanadorRonda() {
		this.registroPuntos[ronda][0] = this.tablero.getFuerzaTotal(0);
		this.registroPuntos[ronda][1] = this.tablero.getFuerzaTotal(1);
		
		if(!(this.habilidades[0] instanceof Nilfgaard && this.habilidades[1] instanceof Nilfgaard)) {
			if(this.habilidades[0].finRonda(this, ronda)){
				mostrarMensaje("usaste tu habilidad de faccion");
			}
			if(this.habilidades[1].finRonda(this, ronda)) {
				mostrarMensaje("el rival uso su habilidad de faccion");
			}
		}
				
		int fuerza0 = this.registroPuntos[ronda][0];
		int fuerza1 = this.registroPuntos[ronda][1];
		
		if(fuerza0>fuerza1) {
			this.ganadorRonda[ronda] = 0;
			gwent.getVistaTablero().ganar();
			mostrarMensaje("ganaste la ronda: " + (ronda + 1));
		}
		else if(fuerza0<fuerza1) {
			this.ganadorRonda[ronda] = 1;
			gwent.getVistaTablero().perder();
			mostrarMensaje("perdiste la ronda: " + (ronda + 1));
		}
		else {
			this.ganadorRonda[ronda] = 2;
			gwent.getVistaTablero().empatar();
			mostrarMensaje("empatada la ronda: " + (ronda + 1));
		}
		
		establecerGanador();
	}

	private void establecerGanador() {
		int[] ganados = new int[3];
		for (int i = 0; i <= ronda; i++) {
			ganados[ganadorRonda[i]]++;
		}
		
		if(ronda == 0) {
			siguienteRonda();	
		}
		else if(ronda == 1) {
			if(ganados[0]>ganados[1]) {
				ganar();
			}
			else if(ganados[0]<ganados[1]) {
				perder();
			}
			else if(ganados[2] == 0){
				siguienteRonda();
			}
			else if(ganados[2] == 2){
				empatar();
			}
		}
		else if(ronda == 2) {
			if(ganados[0]>ganados[1]) {
				ganar();
			}
			else if(ganados[0]<ganados[1]) {
				perder();
			}
			else {
				empatar();
			}
		}
	}
	
	public void siguienteRonda() {
		int rondaAnterior = ronda;
		ronda++;
		tablero.siguienteRonda();
		paso[0] = false;
		paso[1] = false;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tiempoEspera = 0;
				mostrarMensaje("ronda: " + (ronda + 1));
				if(habilidades[0].inicioRonda(instancia, ronda)) {
					mostrarMensaje("usaste tu habilidad de faccion");
				}
				if(habilidades[1].inicioRonda(instancia, ronda)) {
					mostrarMensaje("el rival uso su habilidad de faccion");
				}
				if(ganadorRonda[rondaAnterior] == 0 || (ganadorRonda[rondaAnterior] == 2 && empezoRondaAnterior == false)) {
					empezoRondaAnterior = true;
					empezarTurno(true);
				}
				else {
					gwent.getVistaTablero().mostrarMensaje("turno del rival", tiempoMensajes);
					empezoRondaAnterior = false;
					tiempoEspera = 0;
				}
			}
		}, tiempoEspera);
				
	}
	
	public void ganar() {
		mostrarMensaje("victoria");
		gwent.getPantallaFinal().mostrarResultado("victoria", this.registroPuntos);
		finalizar();
	}
	
	public void perder() {
		mostrarMensaje("derrota");
		gwent.getPantallaFinal().mostrarResultado("derrota", this.registroPuntos);
		finalizar();
	}
	
	public void empatar() {
		mostrarMensaje("empate");
		gwent.getPantallaFinal().mostrarResultado("empate", this.registroPuntos);
		finalizar();
	}
	
	public void finalizar() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				gwent.getPantallaFinal().reiniciar();
				gwent.mostrar(Gwent.FINAL);
				reiniciar();
			}
		}, tiempoEspera);
	}
	
	public void reiniciar() {
		this.tirarDados = true;
		this.dados[0] = 0;
		this.dados[1] = 0;
		this.empezoRondaAnterior = false;
		this.ganadorRonda = new int[3]; 
		this.paso[0] = false;
		this.paso[1] = false;
		this.ronda = 0;
		this.continuar[0] = 0;
		this.continuar[1] = 0;
		this.tiempoEspera = 0;
		if(miJugador != null && this.jugadores[0] != null) {
			miJugador.setMazo(Serializador.deserializar(this.jugadores[0].getMazo().getNombre()));
		}
		jugadores[1] = null;
		jugadores[0] = null;
		gwent.getVistaDados().reiniciar();
		this.tablero.reiniciar();
	}
	
	public Tablero getTablero() {
		return this.tablero;
	}
	
	public void continuar(int como, int jugador) {
		this.continuar[jugador] = como;
	
		if(continuar[0]>0 && continuar[1]>0) {
			continuar();
		}
	}

	private void continuar() {
		int como = continuar[0];
		if(continuar[0]<continuar[1]) {
			como = continuar[1];
		}
		if(como == REVANCHA) {
			registrar(miJugador, 0);
		}
		if(como == EDITAR) {
			editarMazo();
			gwent.getVistaDados().reiniciar();
		}
	}
	
	public void salir(String mensaje) {
		reiniciar();
		gwent.getVistaRed().mostrarMensaje(mensaje);
		gwent.getVistaRed().reiniciar();
		configurarRed();
	}
	
	public void revancha() {
		continuar(REVANCHA, 0);
		red.enviar(new Continuar(REVANCHA));
	}
	
	public void editar() {
		continuar(EDITAR, 0);
		red.enviar(new Continuar(EDITAR));
	}
	
	public void abandonar(int jugador) {
		if(jugador == 0) {
			red.enviar(new Abandonar());
			salir("Abandonaste la Partida");
		}
		else {
			salir("El rival abandonó la partida.");
		}
	}

	public void mostrarCartas(List<Carta> cartas) {
		red.enviar(new MostrarCartas(cartas));
	}

	public void enviarCartas(List<Carta> revivibles) {
		red.enviar(new EnviarCarta(revivibles));	
	}

	public void quitarDeDescartes(Carta carta) {
		red.enviar(new QuitarDeDescartes(carta));
	}
	
	public void agregarADescartes(Carta carta) {
		this.red.enviar(new AgregarADescartes(carta));
	}

	public void agregarACampo(Unidad unidad) {
		this.red.enviar(new AgregarACampo(unidad));
	}
	
	public void quitarDeCampo(Unidad unidad) {
		this.red.enviar(new QuitarDeCampo(unidad));
	}

	public int getGanadorRonda(int ronda) {
		int ret;
		int fuerza0 = this.registroPuntos[ronda][0];
		int fuerza1 = this.registroPuntos[ronda][1];
		
		if(fuerza0>fuerza1) {
			ret = 0;
		}
		else if(fuerza0<fuerza1) {
			ret = 1;
		}
		else {
			ret = 2;
		}
		return ret;
	}
	
	public void decidirInicio(int jugador) {
		this.tirarDados = !this.tirarDados;
		this.quienDecideInicio = jugador;
	}

	public void mostrarInfoCartasRival(int cantMano, int cantMazo) {
		gwent.getVistaTablero().mostrarInfoCartas(1, cantMano, cantMazo);
	}

	public void enviarInfoCartas(int cantMano, int cantMazo) {
		this.red.enviar(new InfoRival(cantMano, cantMazo));
	}

	public void desempatar(int jugador) {
		this.registroPuntos[this.ronda][jugador] += 1;
	}
}
