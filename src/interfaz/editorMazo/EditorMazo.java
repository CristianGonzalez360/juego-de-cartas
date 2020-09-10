package interfaz.editorMazo;

import negocio.Baraja;
import negocio.CreadorBarajas;
import negocio.Juego;
import negocio.Jugador;
import negocio.Mazo;
import negocio.Serializador;
import negocio.carta.*;

public class EditorMazo {
	
	private CreadorBarajas cb;
	private Baraja baraja;
	private Mazo mazo;
	private VistaEditorMazo vista;
	private Juego juego;

	public EditorMazo(Juego juego) {
		this.cb = new CreadorBarajas();
		this.juego = juego;
	}
	
	public void setBaraja(int baraja) {
		//Para no tener que rehacer todo el mazo desde cero.
		this.baraja = cb.getBaraja(baraja);
		//Si Ya hay un mazo editandose, lo guardamos.
		if(this.mazo != null) {
			Serializador.serializar(this.mazo);
		}
		//Buscamos el mazo guardado.
		this.mazo = Serializador.deserializar(this.baraja.getNombre());
		//Si habia mazo guardado, borramos las cartas de la baraja que ya estan en el mazo.
		if(this.mazo != null) {
			for(Carta carta: this.mazo.getCartas()) {
				this.baraja.quitarCarta(carta);
			}
		}
		//Si no habia mazo guardado, cramos uno nuevo.
		else if(this.mazo == null) {
			this.mazo = new Mazo(this.baraja.getId(), this.baraja.getNombre());
			this.mazo.setHabilidad(this.baraja.getHabilidad());
			this.mazo.setLider(this.baraja.getLideres().get(0));
		}		
		
		this.vista.mostrarBaraja(getBaraja());
		this.vista.mostrarMazo(this.mazo);
	}
	
	public void agregarCarta(Carta carta) {
		this.baraja.quitarCarta(carta);
		this.mazo.agregarCarta(carta);
		this.vista.agregarCarta(carta);
	}
	
	public void quitarCarta(Carta carta) {
		this.mazo.quitarCarta(carta);
		this.baraja.agregarCarta(carta);
		this.vista.quitarCarta(carta);
	}
	
	public void setLider(Lider lider) {
		this.mazo.setLider(lider);
		this.vista.mostrarLider(lider);
	}
	
	public Baraja getBaraja() {
		return baraja;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setVista(VistaEditorMazo vista) {
		this.vista = vista;
		this.vista.setEditor(this);
	}
	
	public boolean finalizar() {
		boolean valido = this.mazo.validar();
		if(valido) {
			Serializador.serializar(mazo);
			Jugador jugador = new Jugador("", mazo);
			juego.registrar(jugador, 0);
		}
		return valido;
	}

	public void abandonar() {
		juego.abandonar(0);
	}

	public String getHabilidadBaraja() {
		return this.baraja.getHabilidad().getDescripcion();
	}
	
}
