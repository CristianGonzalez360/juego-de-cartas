package negocio.efecto;

import negocio.Juego;

public interface Efecto {
	
	public static int MEDICO = 0;
	public static int MORAL = 1;
	public static int ESPIA = 2;
	public static int QUEMAR = 3;
	public static int CUERNO = 4;
	public static int MARDROEME = 5;
	public static int INVOCAR = 6;
	public static int BERSERKER = 7;
	public static int REUNION = 8;
	public static int COMPAÑERO = 9;
	public static int CLIMA = 10;
	public static int SENUELO = 11;
	public static int JUGAR_CLIMATICA = 12;
	public static int ESPIAR_MANO = 13;
	public static int ANULAR_LIDER = 14;
	public static int ROBAR = 15;
	public static int MEDICO_RANDOM = 16;
	public static int RESUSITAR = 17;
	public static int CUERNO_ESPIAS = 18;
	public static int SACRIFICAR = 19;
	public static int CARTA_EXTRA = 20;
	public static int OPTIMIZAR = 21;
	public static int DEVOLVER_AL_MAZO = 22;
	public static int ALTERAR_CLIMA = 23;
	
	public void entrada(Juego juego);
	public void salida(Juego juego);
	public boolean activar(Juego juego);
	public String getDescripcion();
}
