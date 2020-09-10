package interfaz;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Graficos {

	private static final int ANCHO_REFERENCIA = 1920;
	private static final int ALTO_REFERENCIA = 1080;
	
	public static final int ANCHO_PANTALLA = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int ALTO_PANTALLA = Toolkit.getDefaultToolkit().getScreenSize().height;
	
//	public static final int ANCHO_PANTALLA = 1366;
//	public static final int ALTO_PANTALLA = 768;
	
	private static HashMap<String, ImageIcon> imagenesCartasGrandes = new HashMap<>(); 
	private static HashMap<String, ImageIcon> imagenesCartasChicas = new HashMap<>(); 
	private static ImageIcon[] dados = new ImageIcon[6];
	
	public static ImageIcon getImagenTablero() {
		Dimension d = getTamañoTablero();
		return cargarImagen(d.width, d.height, "/Tablero.png");
	}
	
	public static ImageIcon getImagenFondoEditor() {
		Dimension d = getTamañoTablero();
		return cargarImagen(d.width, d.height, "/FondoEditorMazo.png");
	}
	
	public static Image getImagenFondo() {
		return cargarImagen(ANCHO_PANTALLA, ALTO_PANTALLA, "/FondoInicio.png").getImage();
	}
	
	public static Image getImagenFinal() {
		return cargarImagen(ANCHO_PANTALLA, ALTO_PANTALLA, "/FondoFinal.png").getImage();
	}
	
	public static ImageIcon getImagenCartaGrande(String URL) {
		Dimension d = getTamañoVistaCartaGrande();
		int alto = d.height;
		int ancho = d.width;
		ImageIcon ret = imagenesCartasGrandes.get(URL);
		if(ret == null) {
			ret = cargarImagen(ancho, alto, URL);
			imagenesCartasGrandes.put(URL, ret);
		}
		return ret ;
	}
	
	public static ImageIcon getImagenCartaChica(String URL) {
		Dimension d = getTamañoVistaCartaChica();
		int alto = d.height;
		int ancho = d.width;
		ImageIcon ret = imagenesCartasChicas.get(URL);
		if(ret == null) {
			ret = cargarImagen(ancho, alto, URL);
			imagenesCartasChicas.put(URL, ret);
		}
		return ret ;
	}
	
	public static Dimension getTamañoVistaCartaGrande() {
		int alto = ALTO_PANTALLA * 352 / ALTO_REFERENCIA;
		int ancho = ANCHO_PANTALLA * 194 / ANCHO_REFERENCIA;
		return new Dimension(ancho,alto);
	}
	
	public static Dimension getTamañoVistaCartaChica() {
		int alto = ALTO_PANTALLA * 150 / ALTO_REFERENCIA;
		int ancho = ANCHO_PANTALLA * 83 / ANCHO_REFERENCIA;
		return new Dimension(ancho,alto);
	}
	
	public static Rectangle getPosicionPanelBaraja() {
		Dimension d = getTamañoVistaCartaGrande();
		int x =ANCHO_PANTALLA * 10 / ANCHO_REFERENCIA;
		int y = ALTO_PANTALLA * 159 / ALTO_REFERENCIA;
		int alto = ALTO_PANTALLA * 834 / ALTO_REFERENCIA;
		int ancho = (d.width *3) + 10 + 17;// 10 de gap, 17 de scrollbar.
		Rectangle ret = new Rectangle(x, y, ancho, alto);
		return ret;
	}
	
	public static Dimension getTamañoPanelDescripcionCarta() {
		Rectangle r = getPosicionPanelBaraja();
		int alto = ALTO_PANTALLA * 60 / ALTO_REFERENCIA;
		int ancho = r.width;
		Dimension ret = new Dimension(ancho, alto);
		return ret;
	}
	
	public static Rectangle getPosicionPanelMazo() {
		Rectangle tpb = getPosicionPanelBaraja();
		Dimension tem = getTamañoTablero();
		int x = tem.width - tpb.x - tpb.width;
		int y = tpb.y;
		int alto = tpb.height;
		int ancho = tpb.width;
		Rectangle ret = new Rectangle(x, y, ancho, alto);
		return ret;
	}
	
	public static Dimension getTamañoFiltros() {
		int lado = ALTO_PANTALLA * 62 / ALTO_REFERENCIA;
		return new Dimension(lado, lado);
	}
	
	public static ImageIcon getImagenFiltro(String URL) {
		Dimension d = getTamañoFiltros();
		return cargarImagen(d.width, d.height, URL);
	}
	
	public static Dimension getTamañoTablero() {
		int ancho = ANCHO_PANTALLA * 1600 / ANCHO_REFERENCIA;
		int alto = ALTO_PANTALLA;
		return new Dimension(ancho,alto);
	}
	
	public static Rectangle getPosicionPanelLider() {
		Dimension tvc = getTamañoVistaCartaGrande();
		Dimension tem = getTamañoTablero();
		int alto = tvc.height +(ALTO_PANTALLA * 60 / ALTO_REFERENCIA);
		int ancho = ANCHO_PANTALLA * 300 / ANCHO_REFERENCIA;
		int x = (tem.width / 2) - (ancho / 2);
		int y = ALTO_PANTALLA * 220 / ALTO_REFERENCIA;
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionPanelFacciones() {
		Dimension tem = getTamañoTablero();
		int alto = (ALTO_PANTALLA * 140 / ALTO_REFERENCIA) + 20;
		int ancho = ANCHO_PANTALLA * 600 / ANCHO_REFERENCIA;
		int x = (tem.width / 2) - (ancho / 2);
		int y = ALTO_PANTALLA * 8 / ALTO_REFERENCIA;		
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Dimension getTamañoBotonFaccion() {
		int alto =  (ALTO_PANTALLA * 140 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 120 / ANCHO_REFERENCIA);
		return new Dimension(ancho,alto);
	}
	
	public static ImageIcon getImagenFaccion(String URL) {
		int alto =  ALTO_PANTALLA * 112 / ALTO_REFERENCIA;
		int ancho =   ANCHO_PANTALLA * 100 / ANCHO_REFERENCIA;
		return cargarImagen(ancho, alto, URL);
	}
	
	public static Rectangle getPosicionSelectorLider() {
		Dimension tem = getTamañoTablero();
		Dimension tvc = getTamañoVistaCartaGrande();
		int alto =  (tvc.height + (ALTO_PANTALLA * 30 / ALTO_REFERENCIA));
		int ancho =   (tvc.width*5 + 30);//30 de gap.
		int x =  (tem.width/2)-(ancho/2);
		int y =  ((ALTO_PANTALLA/2)-(alto/2));
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionBotonJugar() {
		Dimension tem = getTamañoTablero();
		int alto = ALTO_PANTALLA * 30 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 150 / ANCHO_REFERENCIA;
		int x =  (tem.width/2)-(ancho/2);
		int y =  ALTO_PANTALLA * 846 / ALTO_REFERENCIA;		
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Dimension getTamañoFila() {
		int alto = ALTO_PANTALLA * 154 / ALTO_REFERENCIA;
		int ancho = ANCHO_PANTALLA * 760 / ANCHO_REFERENCIA;
		return new Dimension(ancho, alto);
	}
	
	public static Dimension getTamañoPanelCampo() {
		Dimension d = getTamañoFila();	
		int alto =  d.height*3;
		int ancho =   d.width;
		return new Dimension(ancho, alto);
	}
	
	public static Rectangle getPosicionCampo(int jugador) {
		Dimension d = getTamañoFila();	
		int alto =  d.height*3;
		int ancho =   ANCHO_PANTALLA * 895 / ANCHO_REFERENCIA;
		int x = ANCHO_PANTALLA * 437 / ANCHO_REFERENCIA;
		int y = 0;
		if(jugador == 0) {
			y = alto;
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Dimension getPosicionPanelCuernos(int jugador) {		
		Dimension d = getTamañoFila();	
		int alto =  d.height*3;
		int ancho =  ANCHO_PANTALLA * 135 / ANCHO_REFERENCIA;
		return new Dimension(ancho, alto);
	}
	
	public static Rectangle getPosicionMano() {		
		Dimension d = getTamañoTablero();
		int alto = ALTO_PANTALLA * 154 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 760 / ANCHO_REFERENCIA;
		int x =  ((d.width/2) - (ancho/2));
		int y = ALTO_PANTALLA - alto;
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionLider(int jugador) {
		int alto =  (ALTO_PANTALLA * 150 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 83 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 30 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 80 / ALTO_REFERENCIA);
		if(jugador == 0) {
			y =  (ALTO_PANTALLA * 695 / ALTO_REFERENCIA);
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionBaraja(int jugador) {
		int alto =  (ALTO_PANTALLA * 154 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 83 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 1484 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 41 / ALTO_REFERENCIA);
		if(jugador == 0) {
			y =  (ALTO_PANTALLA * 734 / ALTO_REFERENCIA);
		}
		return new Rectangle(x, y, ancho, alto);
	}
	public static Rectangle getPosicionDescarte(int jugador) {
		int alto = ALTO_PANTALLA * 150 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 83 / ANCHO_REFERENCIA;
		int x = ANCHO_PANTALLA * 1367 / ANCHO_REFERENCIA;
		int y = ALTO_PANTALLA * 41 / ALTO_REFERENCIA;
		if(jugador == 0) {
			y = ALTO_PANTALLA * 734 / ALTO_REFERENCIA;
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionZoom() {
		Dimension d = getTamañoVistaCartaGrande();
		int alto =  d.height;
		int ancho =   d.width;
		int x =  (ANCHO_PANTALLA * 1370 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 282 / ALTO_REFERENCIA);
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionClimaticas() {//(35, 387, 258, 150);
		int alto =  (ALTO_PANTALLA * 154 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 258 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 35 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 387 / ALTO_REFERENCIA);
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionMostradorCartas() {
		Dimension tem = getTamañoTablero();
		Dimension tvc = getTamañoVistaCartaGrande();
		int alto =  (tvc.height + 17 + (ALTO_PANTALLA * 30 / ALTO_REFERENCIA));// 17 de scrollBar.30 de descripcion.
		int ancho =   (tvc.width*5 + 32);//30 de gap.
		int x =  (tem.width/2)-(ancho/2);
		int y =  ((ALTO_PANTALLA/2)-(alto/2));
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Dimension getTamañoMarcador() {
		int alto =  (int) Math.ceil(ALTO_PANTALLA * 154f / ALTO_REFERENCIA);
		int ancho =  ANCHO_PANTALLA * 55 / ANCHO_REFERENCIA;
		return new Dimension(ancho, alto);
	}
	
	public static Rectangle getPosicionPanelPuntajes(int jugador) {		
		int alto =  (int) Math.ceil(ALTO_PANTALLA * 462f / ALTO_REFERENCIA);
		int ancho = ANCHO_PANTALLA * 55 / ANCHO_REFERENCIA;
		int x = ANCHO_PANTALLA * 376 / ANCHO_REFERENCIA;
		int y = 0;
		if(jugador == 0) {
			y = alto;
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionDados() {
		Dimension tem = getTamañoTablero();
		
		int alto =  (ALTO_PANTALLA * 250 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 500 / ANCHO_REFERENCIA);
		int x = ((tem.width/2) - (ancho/2));
		int y = ((tem.height/2) - (alto/2));
		
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static ImageIcon cargarImagen(int ancho, int alto, String URL) {
		ImageIcon imagen = new ImageIcon(Class.class.getResource(URL));
		ImageIcon ret = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH ));
		return ret;
	}

	public static Dimension getTamañoDado() {
		int alto =  (ALTO_PANTALLA * 200 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 200 / ANCHO_REFERENCIA);
		return new Dimension(ancho, alto);
	}
	
	public static Rectangle getPosicionMensajeDados() {
		Dimension tem = getTamañoTablero();
		Rectangle pd = getPosicionDados();
		
		int alto = tem.height / 30;
		int ancho =  tem.width;
		int x = 0;
		int y = pd.y - tem.height / 30;
		
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionMensajeMostradorCartas() {
		Dimension tem = getTamañoTablero();
		Rectangle pmc = getPosicionMostradorCartas();
		
		int alto = tem.height / 30;
		int ancho =  tem.width;
		int x = 0;
		int y = pmc.y - tem.height / 30;
		
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static ImageIcon getIconoMazo(String URL) {
		Dimension dado = getTamañoDado();
		ImageIcon ret = cargarImagen(dado.width, dado.height, URL);
		return ret;
	}

	public static ImageIcon getImagenDado(int valor) {
		if(dados[valor-1] == null) {
			Dimension d = getTamañoDado();
			dados[valor-1] = cargarImagen(d.width, d.height, "/Dados/" + valor +".png");
		}
		return dados[valor-1];
	}

	public static Rectangle getPosicionBotonPasar() {
		int alto =  (ALTO_PANTALLA * 30 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 100 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 22 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * (616 - (alto/2)) / ALTO_REFERENCIA);
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionPanelGemas(int jugador) {
		int alto =  (ALTO_PANTALLA * 44 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 88 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 178 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * (308 - (alto/2)) / ALTO_REFERENCIA);
		if(jugador == 0) {
			y =   (ALTO_PANTALLA * (616 - (alto/2)) / ALTO_REFERENCIA);
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionTotal(int jugador) {
		int alto =  (ALTO_PANTALLA * 55 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 55 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 221 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 126 / ALTO_REFERENCIA);
		if(jugador == 0) {
			y =  (ALTO_PANTALLA * 743 / ALTO_REFERENCIA);
		}
		return new Rectangle(x, y, ancho, alto);
	}
		
	public static Rectangle getPosicionPaso(int jugador) {
		int alto =  (ALTO_PANTALLA * 47 / ALTO_REFERENCIA);
		int ancho =   (ANCHO_PANTALLA * 150 / ANCHO_REFERENCIA);
		int x =  (ANCHO_PANTALLA * 22 / ANCHO_REFERENCIA);
		int y =  (ALTO_PANTALLA * 286 / ALTO_REFERENCIA);
		if(jugador == 0) {
			y =  (ALTO_PANTALLA * 591 / ALTO_REFERENCIA);
		}
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static ImageIcon getImagenGema(boolean estado) {
		Rectangle r = getPosicionPanelGemas(0);
		ImageIcon ret;
		if(estado) {
			ret = cargarImagen(r.height, r.height, "/Iconos/gema.png");
		}
		else {
			ret = cargarImagen(r.height, r.height, "/Iconos/gemaRota.png");
		}
		return ret;
	}

	public static ImageIcon getImagenClima(int fila) {
		Rectangle r = getPosicionCampo(0);
		int ancho = r.width;
		int alto = r.height/3;
		ImageIcon icono = null;
		if(fila == Fila.CUERPOACUERPO) {
			icono = cargarImagen(ancho, alto, "/Climas/Frio.png");
		}
		else if(fila == Fila.ADISTANCIA) {
			icono = cargarImagen(ancho, alto, "/Climas/Niebla.png");
		}
		if(fila == Fila.ASEDIO) {
			icono = cargarImagen(ancho, alto, "/Climas/Lluvia.png");
		}
		return icono;
	}
	
	public static int getTamañoFuente(int px) {		
		int size = (ALTO_PANTALLA * px /ALTO_REFERENCIA);
		int ppi = Toolkit.getDefaultToolkit().getScreenResolution();
		size =  (int) (size * (72f / ppi));
		return size;
	}
	
	public static ImageIcon getImagenBotonJugar() {
		Rectangle r = getPosicionBotonJugar();
		return cargarImagen(r.width, r.height, "/Iconos/Boton.png");
	}
	
	public static Rectangle getPosicionBotonSalir() {
		Dimension tablero = getTamañoTablero();
		int alto = ALTO_PANTALLA * 30 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 100 / ANCHO_REFERENCIA;
		int x = tablero.width - ancho -10;
		int y = tablero.height - alto - 10;
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionInformacionMazo() {
		Dimension tem = getTamañoTablero();
		int alto = ALTO_PANTALLA * 200 / ALTO_REFERENCIA;
		int ancho = ANCHO_PANTALLA * 200 / ANCHO_REFERENCIA;
		int x =  ((tem.width/2)-(ancho/2));
		int y =  (ALTO_PANTALLA * 640 / ALTO_REFERENCIA);
		return new Rectangle(x, y, ancho, alto);
	}
	
	public static Rectangle getPosicionFuerzaCartaGrande(boolean esHeroe) {
		int lado;
		int x;
		if(esHeroe) {
			lado = ALTO_PANTALLA * 69 /ALTO_REFERENCIA;
			x = 0;
		}
		else {
			lado = ALTO_PANTALLA * 53 /ALTO_REFERENCIA;
			x = ANCHO_PANTALLA * 8 / ANCHO_REFERENCIA;
		}
		return new Rectangle(x, x, lado, lado);
	}
	
	public static Rectangle getPosicionFuerzaCartaChica(boolean esHeroe) {
		int lado;
		int x;
		if(esHeroe) {
			lado = ALTO_PANTALLA * 32 /ALTO_REFERENCIA;
			x = 0;
		}
		else {
			lado = ALTO_PANTALLA * 23 /ALTO_REFERENCIA;
			x = ANCHO_PANTALLA * 3 / ANCHO_REFERENCIA;
		}
		return new Rectangle(x, x, lado, lado);
	}
		
	public static ImageIcon geticonoFuerzaGrande(boolean esHeroe) {
		Rectangle r = getPosicionFuerzaCartaGrande(esHeroe);
		ImageIcon ret;
		if(esHeroe) {
			ret = cargarImagen(r.width, r.height, "/Iconos/iconoFuerzaHeroe.png");
		}
		else {
			ret = cargarImagen(r.width, r.height, "/Iconos/iconoFuerza.png");
		}
		return ret;
	}
	
	public static ImageIcon geticonoFuerzaChica(boolean esHeroe) {
		Rectangle r = getPosicionFuerzaCartaChica(esHeroe);
		ImageIcon ret;
		if(esHeroe) {
			ret = cargarImagen(r.width, r.height, "/Iconos/iconoFuerzaHeroe.png");
		}
		else {
			ret = cargarImagen(r.width, r.height, "/Iconos/iconoFuerza.png");
		}
		return ret;
	}

	public static Rectangle getPosicionCartasMano(int jugador) {
		int alto = ALTO_PANTALLA * 42 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 55 / ANCHO_REFERENCIA;
		int x = ANCHO_PANTALLA * 221 / ANCHO_REFERENCIA;
		int y = ALTO_PANTALLA * 210 / ALTO_REFERENCIA;
		if(jugador == 0) {
			y = ALTO_PANTALLA * 673 / ALTO_REFERENCIA;
		}
		return new Rectangle(x, y, ancho, alto);
	}

	public static Rectangle getPosicionCartasMazo(int jugador) {
		int alto = ALTO_PANTALLA * 42 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 55 / ANCHO_REFERENCIA;
		int x = ANCHO_PANTALLA * 1498 / ANCHO_REFERENCIA;
		int y = ALTO_PANTALLA * 153 / ALTO_REFERENCIA;
		if(jugador == 0) {
			y = ALTO_PANTALLA * 845 / ALTO_REFERENCIA;
		}
		return new Rectangle(x, y, ancho, alto);
	}

	public static Dimension getTamañoPanelResultado() {
		int alto = ALTO_PANTALLA * 300 / ALTO_REFERENCIA;
		int ancho =  ANCHO_PANTALLA * 350 / ANCHO_REFERENCIA;
		
		return new Dimension(ancho, alto);
	}
}
