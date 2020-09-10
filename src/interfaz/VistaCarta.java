package interfaz;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import negocio.carta.Carta;
import negocio.carta.Unidad;
import javax.swing.SwingConstants;

public class VistaCarta extends JLabel implements Comparable<VistaCarta>{
	public static int GRANDE = 0;
	public static int CHICA = 1;
	
	private Carta carta;
	private MouseAdapter devolver;
	private Fila fila;
	private int tamaño;
	private MouseAdapter mostrar;
	private boolean modoDevolver;
	private boolean mostrarCarta;
	private JLabel lblfuerza;
	private JLabel icono;
	
	public VistaCarta(Carta carta, int tamaño, VistaTablero vista) {
		setHorizontalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(null);
		setLayout(null);
		this.tamaño = tamaño;
		if(tamaño == GRANDE) {
			setPreferredSize(Graficos.getTamañoVistaCartaGrande());
		}
		else if(tamaño == CHICA) {
			setPreferredSize(Graficos.getTamañoVistaCartaChica());
		}
		icono = new JLabel();
		icono.setHorizontalAlignment(SwingConstants.LEFT);
		icono.setVerticalAlignment(SwingConstants.TOP);
		lblfuerza = new JLabel();
		lblfuerza.setHorizontalAlignment(SwingConstants.CENTER);
		if(tamaño == GRANDE) {
			lblfuerza.setFont(new Font("agency fb", Font.BOLD, Graficos.getTamañoFuente(40)));
		}
		else if(tamaño == CHICA) {
			lblfuerza.setFont(new Font("agency fb", Font.BOLD, Graficos.getTamañoFuente(25)));
		}
		add(lblfuerza);
		add(icono);
		setCarta(carta);
		
		this.devolver =  new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					vista.sustituir(carta);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(null);
			}
		};
		
		this.mostrar = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3) {
					vista.mostrarCartas(fila.getCartas(), fila.getCartas().indexOf(carta));
				}
				if(e.getButton() == MouseEvent.BUTTON1 && !modoDevolver) {
					fila.seleccionar();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {	
				if(!modoDevolver) {
					setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.BLUE));
					fila.resaltar(true);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(null);
				fila.resaltar(false);
			}
		};
	}
	
	public Carta getCarta() {
		return this.carta;
	}
	
	public void setFila(Fila fila) {
		this.fila = fila;
	}
	
	public void setCarta(Carta carta) {
		this.carta = carta;
		if(tamaño == GRANDE) {
			setIcon(Graficos.getImagenCartaGrande(carta.getRutaImagen()));
		}
		else if(tamaño == CHICA) {
			setIcon(Graficos.getImagenCartaChica(carta.getRutaImagen()));
		}
		
		if(carta instanceof Unidad) {
			boolean esHeroe = ((Unidad) carta).esHeroe();			
			if(tamaño == GRANDE) {
				lblfuerza.setBounds(Graficos.getPosicionFuerzaCartaGrande(esHeroe));
				icono.setBounds(Graficos.getPosicionFuerzaCartaGrande(esHeroe));
				this.icono.setIcon(Graficos.geticonoFuerzaGrande(esHeroe));
			}
			else if(tamaño == CHICA) {
				this.icono.setIcon(Graficos.geticonoFuerzaChica(esHeroe));
				lblfuerza.setBounds(Graficos.getPosicionFuerzaCartaChica(esHeroe));
				icono.setBounds(Graficos.getPosicionFuerzaCartaChica(esHeroe));
			}
			icono.setVisible(true);
			lblfuerza.setVisible(true);
			mostrarFuerza(((Unidad) carta).getFuerza());
		}
		else {
			icono.setVisible(false);
			lblfuerza.setVisible(false);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if(o instanceof VistaCarta) {
			VistaCarta vc = (VistaCarta)o;
			ret = this.getCarta().equals(vc.getCarta());
		}
		return ret;
	}
	
	public void modoDevolver(boolean estado) {
		if (this.carta instanceof Unidad && !((Unidad) this.carta).esHeroe()) {
			if (estado) {
				addMouseListener(devolver);
			} else {
				removeMouseListener(devolver);
			} 
		}
		this.modoDevolver = estado;
	}
	
	public void modoMostrar(boolean estado) {
		if (estado && !mostrarCarta) {
			addMouseListener(mostrar);
			mostrarCarta = true;
		} else if(!estado){
			removeMouseListener(mostrar);
			mostrarCarta = false;
		} 
	}

	@Override
	public int compareTo(VistaCarta vistaCarta) {
		return this.getCarta().compareTo(vistaCarta.getCarta());
	}
	
	public void mostrarFuerza(int fuerza) {
		if(carta instanceof Unidad) {
			int original = ((Unidad) carta).getFuerza();
			if(fuerza > original) {
				this.lblfuerza.setForeground(new Color(90, 150, 0));
			}
			else if(fuerza < original) {
				this.lblfuerza.setForeground(new Color(255, 0, 0));
			}
			else {
				if(((Unidad) carta).esHeroe()) {
					lblfuerza.setForeground(Color.WHITE);
				}
				else {
					lblfuerza.setForeground(Color.BLACK);
				}
			}
			this.lblfuerza.setText(fuerza + "");
		}
	}
}
