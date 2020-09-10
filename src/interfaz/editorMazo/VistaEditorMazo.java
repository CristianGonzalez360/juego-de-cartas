package interfaz.editorMazo;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import interfaz.Graficos;
import negocio.Baraja;
import negocio.Mazo;
import negocio.carta.Carta;
import negocio.carta.Lider;
import negocio.habilidades.Habilidad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.FlowLayout;
import java.awt.Font;

public class VistaEditorMazo extends JPanel {
	
	private PanelBaraja vb;
	private PanelBaraja vm;
	private SelectorLider selectorLider;
	private MostradorLider mostradorLider;
	private EditorMazo editor;
	private JPanel panelBotones;
	private JButton btnJugar;
	private List<JButton> botones;
	private JButton resaltado;
	private boolean bloqueado;
	private MouseListener resaltar;
	private ActionListener apretar;
	private JButton btnRainosDelNorte;
	private String descripcion;
	private JLabel lblHabilidad;
	private JButton seleccionado;
	private PanelInformacionMazo informacionMazo;
	
	public VistaEditorMazo() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setBackground(Color.BLACK);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(Graficos.getTamañoTablero());
		add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		selectorLider = new SelectorLider(this);
		selectorLider.setVisible(false);
		selectorLider.setBounds(new Rectangle(new Point(0, 0), Graficos.getTamañoTablero()));
		panelPrincipal.add(selectorLider);
		
		vb = new PanelBaraja(PanelBaraja.BOTONES_IZQUIERDA, PanelBaraja.AGREGAR);	
		vb.setBounds(Graficos.getPosicionPanelBaraja());
		panelPrincipal.add(vb);
		
		vm = new PanelBaraja(PanelBaraja.BOTONES_DERECHA, PanelBaraja.QUITAR);
		vm.setBounds(Graficos.getPosicionPanelMazo());
		panelPrincipal.add(vm, BorderLayout.CENTER);
		
		mostradorLider = new MostradorLider(this);
		panelPrincipal.add(mostradorLider);
		
		btnJugar = new JButton("JUGAR");
		btnJugar.setEnabled(false);
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.finalizar()) {
					btnJugar.setText("ESPERANDO RIVAL");
					bloquear();
				}
			}
		});
		btnJugar.setBounds(Graficos.getPosicionBotonJugar());
		panelPrincipal.add(btnJugar);
				
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelBotones.setOpaque(false);
		
		JPanel panelFacciones = new JPanel(new BorderLayout());
		panelFacciones.setOpaque(false);
		panelFacciones.setBounds(Graficos.getPosicionPanelFacciones());
		panelFacciones.add(panelBotones, BorderLayout.CENTER);
		panelPrincipal.add(panelFacciones);
		
		lblHabilidad = new JLabel();
		lblHabilidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblHabilidad.setPreferredSize(new Dimension(panelFacciones.getBounds().width, 20));
		lblHabilidad.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(16)));
		lblHabilidad.setForeground(Color.ORANGE);
		lblHabilidad.setOpaque(false);
		panelFacciones.add(lblHabilidad, BorderLayout.SOUTH);
		
		this.botones = new ArrayList<>();
		resaltar = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!bloqueado) {
					JButton boton = (JButton)e.getSource();
					boton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
					lblHabilidad.setText(getHabilidad(Integer.parseInt(boton.getActionCommand())));
					if(seleccionado != null && !seleccionado.equals(boton)) {
						seleccionado.setBorder(null);
					}
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton boton = (JButton)e.getSource();
				if(resaltado == null || !boton.equals(resaltado)) {
					boton.setBorder(null);
				}
				if(seleccionado != null) {
					seleccionado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
				lblHabilidad.setText(descripcion);
			}
		};
		apretar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!bloqueado) {
					JButton boton = (JButton) e.getSource();
					if (resaltado != null && !resaltado.equals(boton)) {
						resaltado.setBorder(null);
						descripcion = getHabilidad(Integer.parseInt(boton.getActionCommand()));
					}
					seleccionado = boton;
					resaltado = boton;
					editor.setBaraja(Integer.parseInt(boton.getActionCommand()));
					btnJugar.setEnabled(editor.getMazo().validar());
				}
			}
		};
		
		JButton btnMonstruos = crearBoton("Monstruos", "/Iconos/Monstruos.png");
		btnMonstruos.setActionCommand(Baraja.MONSTRUOS+"");
		
		JButton btnNilgaard = crearBoton("Nilfgaarf", "/Iconos/Nilfgaard.png");
		btnNilgaard.setActionCommand(Baraja.NILFGAARD+"");
		
		btnRainosDelNorte = crearBoton("Rainos del Norte", "/Iconos/Reinos del Norte.png");
		btnRainosDelNorte.setActionCommand(Baraja.REINOS_DEL_NORTE+"");
		
		JButton btnScoiatael = crearBoton("Scoia'tael", "/Iconos/Scoiatael.png");
		btnScoiatael.setActionCommand(Baraja.SCOIATAEL+"");

		JButton btnSkellige = crearBoton("Skellige", "/Iconos/Skellige.png");
		btnSkellige.setActionCommand(Baraja.SKELLIGE+"");
		
		JButton botonSalir = new JButton("ABANDONAR");
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editor.abandonar();
			}
		});
		botonSalir.setBounds(Graficos.getPosicionBotonSalir());
		panelPrincipal.add(botonSalir);
		
		informacionMazo = new PanelInformacionMazo();
		panelPrincipal.add(informacionMazo);
		
		JLabel fondo = new JLabel();
		fondo.setHorizontalAlignment(SwingConstants.CENTER);
		fondo.setBounds(new Rectangle(new Point(0, 0), Graficos.getTamañoTablero()));
		fondo.setIcon(Graficos.getImagenFondoEditor());
		panelPrincipal.add(fondo);
	}
	
	private String getHabilidad(int idBaraja) {
		String ret = "";
		if (idBaraja == Baraja.REINOS_DEL_NORTE){
			ret = Habilidad.REINOS_DEL_NORTE;
		}
		else if (idBaraja == Baraja.MONSTRUOS){
			ret = Habilidad.MONSTRUOS;
		}
		else if (idBaraja == Baraja.NILFGAARD){
			ret = Habilidad.NILFGAARD;
		}
		else if (idBaraja == Baraja.SCOIATAEL){
			ret = Habilidad.SCOIATAEL;
		}
		else if (idBaraja == Baraja.SKELLIGE){
			ret = Habilidad.SKELLIGE;
		}
		return ret;
	}
	public JButton crearBoton(String nombre, String URLicono) {
		JButton ret  = new JButton(nombre);
		ret.setFocusPainted(false);
		ret.setPreferredSize(Graficos.getTamañoBotonFaccion());
		ret.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(15)));
		ret.setForeground(Color.ORANGE);
		ret.setBorder(null);
		ret.setContentAreaFilled(false);
		ret.setVerticalTextPosition(SwingConstants.TOP);
		ret.setHorizontalTextPosition(SwingConstants.CENTER);
		ImageIcon icono = Graficos.getImagenFaccion(URLicono);
		ret.setIcon(icono);
		ret.setDisabledIcon(icono);
		ret.addMouseListener(resaltar);
		ret.addActionListener(apretar);
		panelBotones.add(ret);
		this.botones.add(ret);
		return ret;
	}
	
	public void setEditor(EditorMazo editor) {
		this.editor = editor;
		this.vb.setEditor(editor);
		this.vm.setEditor(editor);
		this.informacionMazo.setEditor(editor);
		editor.setBaraja(Baraja.REINOS_DEL_NORTE);
		this.resaltado = btnRainosDelNorte;
		this.seleccionado = btnRainosDelNorte;
		this.resaltado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
		this.lblHabilidad.setText(getHabilidad(Baraja.REINOS_DEL_NORTE));
		this.descripcion = getHabilidad(Baraja.REINOS_DEL_NORTE);
	}
	
	public void mostrarBaraja(Baraja baraja) {
		this.vb.mostrarBaraja(baraja);
		mostrarLider(baraja.getLideres().get(0));
		this.selectorLider.mostrarLideres(baraja.getLideres());
	}
	
	public void mostrarMazo(Mazo mazo) {
		vm.mostrarBaraja(mazo);
		if(mazo.getLider() != null) {
			mostrarLider(mazo.getLider());
		}
		informacionMazo.actualizar();
	}
	
	public void mostrarLider(Lider lider) {
		this.mostradorLider.setLider(lider);
	}
	
	public void agregarCarta(Carta carta){
		vm.agregarCarta(carta);
		vm.actualizar();
		vb.quitarCarta(carta);
		vb.actualizar();
		informacionMazo.actualizar();
		btnJugar.setEnabled(editor.getMazo().validar());
	}
	
	public void quitarCarta(Carta carta) {
		vb.agregarCarta(carta);
		vb.actualizar();
		vm.quitarCarta(carta);
		vm.actualizar();
		informacionMazo.actualizar();
		btnJugar.setEnabled(editor.getMazo().validar());
	}
	
	public void bloquear() {
		this.bloqueado = true;
		for (JButton boton : botones) {
			boton.setEnabled(false);
		}
		mostradorLider.bloquear();
		vm.bloquear();
		vb.bloquear();
		btnJugar.setEnabled(false);
	}
	
	public void desbloquear() {
		this.bloqueado = false;
		for (JButton boton : botones) {
			boton.setEnabled(true);
		}
		mostradorLider.desbloquear();
		vm.desbloquear();
		vb.desbloquear();
		btnJugar.setEnabled(editor.getMazo().validar());
		btnJugar.setText("JUGAR");
	}
	
	public void seleccionarLider(Lider lider) {
		editor.setLider(lider);
		desbloquear();
		this.selectorLider.setVisible(false);
	}

	public void editarLider() {
		bloquear();
		selectorLider.setVisible(true);
	}
}
