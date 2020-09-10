package interfaz.editorMazo;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import interfaz.Graficos;
import interfaz.editorMazo.VistaCarta;
import negocio.Baraja;
import negocio.Campo;
import negocio.carta.Carta;
import negocio.carta.Climatica;
import negocio.carta.Especial;
import negocio.carta.Unidad;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

public class PanelBaraja extends JPanel {

	private JPanel principal;
	private JPanel botonera;

	public static int BOTONES_IZQUIERDA = 0;
	public static int BOTONES_DERECHA = 1;
	public static int AGREGAR = 2;
	public static int QUITAR = 3;
	
	private int funcion;
	private EditorMazo editor;
	
	private List<Carta> heroes;
	private List<Carta> cuarpoAcuerpo;
	private List<Carta> aDistancia;
	private List<Carta> asedio;
	private List<Carta> especiales;
	private List<Carta> climaticas;
	private List<Carta> todas;

	private List<Carta> mostrando;
	
	private List<JButton> botones;
	private JButton resaltado;
	private List<VistaCarta> vistasCarta;
	private JTextPane panelDescripcion;
	private boolean bloqueado;
	private MouseAdapter realtar;
	private ActionListener apretar;
	private JButton btnTodas;
	private JButton seleccionado;
	
	public PanelBaraja(int alineacionBotones, int funcion) {
		
		this.funcion = funcion;
		setOpaque(false);
		setBorder(null);
		this.heroes = new ArrayList<>();
		this.cuarpoAcuerpo = new ArrayList<>();
		this.aDistancia = new ArrayList<>();
		this.asedio = new ArrayList<>();
		this.especiales = new ArrayList<>();
		this.todas = new ArrayList<>();
		this.mostrando = new ArrayList<>();
		this.climaticas = new ArrayList<>();

		setLayout(new BorderLayout()); 
		this.principal = new JPanel();
		
		principal.setBorder(null);
		JScrollPane scroll = new JScrollPane(principal, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(null);
		scroll.setViewportBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(Graficos.getTamañoVistaCartaGrande().height + 5);//5 de gap.		
		
		this.principal.setLayout(new GridLayout(0, 3, 5, 5));
		add(scroll, BorderLayout.CENTER);
		
		this.botonera = new JPanel();
		botonera.setOpaque(false);
		botonera.setBorder(null);
		if (alineacionBotones == BOTONES_IZQUIERDA) {
			botonera.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		}
		else if(alineacionBotones == BOTONES_DERECHA) {
			botonera.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
			scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		add(botonera, BorderLayout.NORTH);
		this.principal.setOpaque(false);
		
		this.botones = new ArrayList<>();
		
		realtar = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!bloqueado) {
					JButton boton = (JButton) e.getSource();
					boton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
					if(seleccionado != null && !seleccionado.equals(boton)) {
						seleccionado.setBorder(null);
					}
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton boton = (JButton) e.getSource();
				if(resaltado == null || !boton.equals(resaltado)) {
					boton.setBorder(null);
				}
				if(seleccionado != null) {
					seleccionado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
			}
		};
		
		apretar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!bloqueado) {
					JButton boton = (JButton) e.getSource();
					if (resaltado != null && !resaltado.equals(boton)) {
						resaltado.setBorder(null);
					}
					seleccionado = boton;
					resaltado = boton;
				}
			}
		};
		
		btnTodas = crearBoton("/Iconos/iconoTodas.png");
		btnTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Collections.sort(todas);
				mostrarCartas(todas);
			}
		});

		JButton btnCuerpoACuerpo = crearBoton("/Iconos/iconoCuerpoACuerpo.png");
		btnCuerpoACuerpo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(cuarpoAcuerpo);
				mostrarCartas(cuarpoAcuerpo);
			}
		});

		JButton btnAdistancia = crearBoton("/Iconos/iconoADistancia.png");
		btnAdistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(aDistancia);
				mostrarCartas(aDistancia);
			}
		});
		
		JButton btnAsedio = crearBoton("/Iconos/iconoAsedio.png");
		btnAsedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(asedio);
				mostrarCartas(asedio);
			}
		});
		
		JButton btnHeroes = crearBoton("/Iconos/iconoHeroes.png");
		btnHeroes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(heroes);
				mostrarCartas(heroes);
			}
		});

		JButton btnClimaticas = crearBoton("/Iconos/iconoClimaticas.png");
		btnClimaticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(climaticas);
				mostrarCartas(climaticas);
			}
		});
		JButton btnEspeciales = crearBoton("/Iconos/iconoEspeciales.png");
		btnEspeciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(especiales);
				mostrarCartas(especiales);
			}
		});
		
		this.vistasCarta = new ArrayList<>();
		
		panelDescripcion = new JTextPane();
		panelDescripcion.setPreferredSize(Graficos.getTamañoPanelDescripcionCarta());
		panelDescripcion.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(20)));
		panelDescripcion.setEditable(false);
		panelDescripcion.setForeground(Color.ORANGE);
		panelDescripcion.setOpaque(false);
		add(panelDescripcion, BorderLayout.SOUTH);
	}
	
	public void setEditor(EditorMazo editor) {
		this.editor = editor;
	}

	public void mostrarCartas(List<Carta> cartas) {
		principal.removeAll();
		vistasCarta.clear();
		for (Carta carta : cartas) {
			VistaCarta vc = crearVIstaCarta(carta);
			vistasCarta.add(vc);
			principal.add(vc);
		}
		
		if(cartas.size()<6 && cartas.size()>0) {
			for(int e = cartas.size();e<6;e++) {
				JLabel relleno = new JLabel();
				principal.add(relleno);
			}
		}
		mostrando = cartas;
		principal.revalidate();
		principal.repaint();
	}

	private VistaCarta crearVIstaCarta(Carta carta) {
		VistaCarta ret = new VistaCarta(carta);
		if(funcion == AGREGAR) {
			ret.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (!bloqueado) {
						if (isAlreadyOneClick) {
							editor.agregarCarta(carta);
							isAlreadyOneClick = false;
						} else {
							isAlreadyOneClick = true;
							Timer t = new Timer("doubleclickTimer", false);
							t.schedule(new TimerTask() {
								@Override
								public void run() {
									isAlreadyOneClick = false;
								}
							}, 500);
						} 
					}
				}
			});
		}
		else if(funcion == QUITAR){
			ret.addMouseListener(new MouseAdapter() {
				boolean isAlreadyOneClick;
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					if (!bloqueado) {
						if (isAlreadyOneClick) {
							editor.quitarCarta(carta);
							isAlreadyOneClick = false;
						} else {
							isAlreadyOneClick = true;
							Timer t = new Timer("doubleclickTimer", false);
							t.schedule(new TimerTask() {
								@Override
								public void run() {
									isAlreadyOneClick = false;
								}
							}, 500);
						} 
					}
				}
			});
		}		
		ret.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!bloqueado) {
					ret.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 165, 0), new Color(255, 69, 0)));
					panelDescripcion.setText(carta.getDescripcion());
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ret.setBorder(null);
				panelDescripcion.setText("");
			}
		});
		return ret;
	}
	
	public void actualizar() {
		Collections.sort(mostrando);
		mostrarCartas(mostrando);
		if(mostrando.isEmpty()) {
			this.panelDescripcion.setText("");
		}
	}

	public void agregarCarta(Carta carta) {
		if(carta instanceof Climatica) {
			this.climaticas.add(carta);
			this.especiales.add(carta);
		}		
		else if (carta instanceof Especial) {
			this.especiales.add(carta);
		}  
		if (carta instanceof Unidad) {
			Unidad unidad = (Unidad)carta;
			if(unidad.esHeroe()) {
				this.heroes.add(unidad);
			}
			if(unidad.esAgil()) {
				this.cuarpoAcuerpo.add(carta);
				this.aDistancia.add(carta);
			}
			else if (carta.getFila() == Campo.CUERPOACUERPO) {
				this.cuarpoAcuerpo.add(carta);
			} else if (carta.getFila() == Campo.ADISTANCIA) {
				this.aDistancia.add(carta);
			} else if (carta.getFila() == Campo.ASEDIO) {
				this.asedio.add(carta);
			}
		}
		this.todas.add(carta);
	}
	
	public void quitarCarta(Carta carta) {
		if(carta instanceof Climatica) {
			this.climaticas.remove(carta);
			this.especiales.remove(carta);
		}		
		else if (carta instanceof Especial || carta instanceof Climatica) {
			this.especiales.remove(carta);
		}
		if (carta instanceof Unidad) {
			Unidad unidad = (Unidad)carta;
			if(unidad.esHeroe()) {
				this.heroes.remove(unidad);
			}
			if(unidad.esAgil()) {
				this.cuarpoAcuerpo.remove(carta);
				this.aDistancia.remove(carta);
			}
			else if (carta.getFila() == Campo.CUERPOACUERPO) {
				this.cuarpoAcuerpo.remove(carta);
			} else if (carta.getFila() == Campo.ADISTANCIA) {
				this.aDistancia.remove(carta);
			} else if (carta.getFila() == Campo.ASEDIO) {
				this.asedio.remove(carta);
			}
		}
		this.todas.remove(carta);
	}

	public void mostrarBaraja(Baraja baraja) {
		this.heroes.clear();
		this.cuarpoAcuerpo.clear();
		this.aDistancia.clear();
		this.asedio.clear();
		this.especiales.clear();
		this.climaticas.clear();
		this.todas.clear();
		this.mostrando.clear();
		
		if(resaltado != null) {
			resaltado.setBorder(null);
		}
		this.resaltado = btnTodas;
		this.seleccionado = btnTodas;
		this.resaltado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		
		for (Carta c : baraja.getCartas()) {
			agregarCarta(c);
		}
		mostrando = todas;
		mostrarCartas(todas);
	}
	
	private JButton crearBoton(String imagenURL) {
		JButton ret = new JButton();
		ret.setBorder(null);
		ret.setFocusPainted(false);
		ret.setContentAreaFilled(false);
		ret.setPreferredSize(Graficos.getTamañoFiltros());
		ImageIcon icono = Graficos.getImagenFiltro(imagenURL);
		ret.setIcon(icono);
		ret.setDisabledIcon(icono);
		ret.addActionListener(apretar);
		ret.addMouseListener(realtar);
		botonera.add(ret);
		botones.add(ret);
		return ret;
	}

	public void bloquear() {
		this.bloqueado = true;
		for (JButton boton : botones) {
			boton.setEnabled(false);
		}
	}
	
	public void desbloquear(){
		this.bloqueado = false;
		for (JButton boton : botones) {
			boton.setEnabled(true);
		}
	}
	
}
