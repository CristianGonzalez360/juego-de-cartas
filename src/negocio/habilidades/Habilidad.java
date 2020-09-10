package negocio.habilidades;

import negocio.Juego;

public abstract class Habilidad {
	
	
	public static String REINOS_DEL_NORTE = "Levanta una carta extra al ganar la ronda";
	public static String NILFGAARD = "Gana las rondas finalizadas en empate";
	public static String MONSTRUOS = "Mantiene una Unidad al azar en el campo al finalizar la ronda";
	public static String SCOIATAEL = "Decide quién comienza la partida";
	public static String SKELLIGE = "Revive 2 Unidades al azar al comenzar la tercer ronda";
	
	private int jugador;

	public abstract boolean inicioPartida(Juego juego);
	public abstract void FinPartida(Juego juego);	
	public abstract boolean inicioRonda(Juego juego, int ronda);
	public abstract boolean finRonda(Juego juego, int ronda);
	
	public int getJugador() {
		return jugador;
	}
	
	public void setJugador(int jugador) {
		this.jugador = jugador;
	}
		
	public abstract String getDescripcion();

}
