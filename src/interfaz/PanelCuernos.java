package interfaz;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import negocio.carta.Especial;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelCuernos extends JPanel{
		
	private PanelCuerno cuernoCuerpoACuerpo;
	private PanelCuerno cuernoADistancia;
	private PanelCuerno cuernoAsedio;
	private Dimension tamano;
	private PanelCuerno[] cuernos;
		
	public PanelCuernos(VistaTablero vista, int jugador) {
		this.tamano = Graficos.getPosicionPanelCuernos(jugador);
		setPreferredSize(tamano);
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));	
		
		this.cuernoCuerpoACuerpo = new PanelCuerno(Fila.CUERPOACUERPO, vista);
		this.cuernoADistancia = new PanelCuerno(Fila.ADISTANCIA, vista);
		this.cuernoAsedio = new PanelCuerno(Fila.ASEDIO, vista);
		
		cuernos = new PanelCuerno[3];
		cuernos[0] = cuernoCuerpoACuerpo;
		cuernos[1] = cuernoADistancia;
		cuernos[2] = cuernoAsedio;
		
		if (jugador == 0) {
			add(cuernoCuerpoACuerpo);
			add(cuernoADistancia);
			add(cuernoAsedio);
		} 
		else if (jugador == 1) {	
			add(cuernoAsedio);
			add(cuernoADistancia);
			add(cuernoCuerpoACuerpo);
		}
	}
	
	public void jugarCuerno(Especial cuerno) {
		this.cuernos[cuerno.getFila()].jugarCarta(cuerno);
	}
	
	public void jugarCuerno(int fila) {
		this.cuernos[fila].jugarCuerno();
	}
	
	public void quitarCuerno(int fila) {
		this.cuernos[fila].quitarCuerno();
	}
			
	public void modoSeleccion(boolean estado) {
		for(int i=0;i<3;i++) {
			this.cuernos[i].modoSeleccion(estado);
		}
	}
	
	public void vaciar() {
		for (int i = 0; i < cuernos.length; i++) {
			cuernos[i].vaciar();
		}	
	}

	private class PanelCuerno extends JLabel{
		
		private MouseAdapter seleccion;
		private boolean ocupado;
		public PanelCuerno(int fila, VistaTablero vista) {		
			setOpaque(false);
			setPreferredSize(new Dimension(tamano.width, tamano.height/3));
			setHorizontalAlignment(SwingConstants.CENTER);
			
			this.seleccion = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					modoSeleccion(false);
					vista.seleccionarFila(fila);
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 165, 0), new Color(255, 69, 0)));
				}
					
				@Override
				public void mouseExited(MouseEvent e) {
					setBorder(null);
				}	
			};
		}		
		
		public void vaciar() {
			setIcon(null);
			this.ocupado = false;
		}

		public void jugarCarta(Especial cuerno) {
			if(!ocupado) {
				setIcon(Graficos.getImagenCartaChica(cuerno.getRutaImagen()));	
				this.ocupado = true;
			}
		}
		
		public void jugarCuerno() {
			if(!ocupado) {
				setIcon(Graficos.getImagenCartaChica("/Especial/Commander's Horn.png"));	
				this.ocupado = true;
			}
		}
		
		public void quitarCuerno() {
			setIcon(null);
			this.ocupado = false;
		}
		
		public void modoSeleccion(boolean estado) {
			if(estado == true && !ocupado) {
				this.addMouseListener(seleccion);
			}
			else {
				this.removeMouseListener(seleccion);
				setBorder(null);
			}
		}
	}

	
}
