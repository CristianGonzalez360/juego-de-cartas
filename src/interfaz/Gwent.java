package interfaz;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import interfaz.editorMazo.VistaEditorMazo;
import negocio.Juego;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.Painter;
import javax.swing.UIManager;

public class Gwent extends JFrame {

	public final static String RED = "red";
	public final static String MAZO = "mazo";
	public final static String TABLERO = "tablero";
	public final static String DADOS = "dados";
	public final static String DECIDIR = "decidir";
	public final static String FINAL = "final";
	
	private Juego juego;
	
	private CardLayout layout;
	private JPanel panelVistas;
	private VistaEditorMazo vistaEditorMazo; 
	private VistaRed vistaRed;
	private VistaTablero vistaTablero;
	private VistaDados vistaDados;
	private VistaDecidirComienzo vistaDecidirComienzo;
	private VistaFinal pantallaFinal;
	
	public Gwent() {
		 
		prepararUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
				
		panelVistas = new JPanel();
		layout = new CardLayout(0, 0);
		panelVistas.setLayout(layout);
		
		this.vistaRed = new VistaRed();
		this.vistaEditorMazo = new VistaEditorMazo();
		this.vistaTablero = new VistaTablero();
		this.vistaDados = new VistaDados();
		this.vistaDecidirComienzo = new VistaDecidirComienzo();
		this.pantallaFinal = new VistaFinal();
		
		panelVistas.add(vistaRed, RED);
		panelVistas.add(vistaEditorMazo, MAZO);
		panelVistas.add(vistaTablero, TABLERO);
		panelVistas.add(vistaDados, DADOS);
		panelVistas.add(vistaDecidirComienzo, DECIDIR);
		panelVistas.add(pantallaFinal, FINAL);
		
				
		getContentPane().add(panelVistas,BorderLayout.CENTER);
		
		this.juego = new Juego();
		this.juego.setVista(this);
	}

	public VistaEditorMazo getVistaEditorMazo() {
		return vistaEditorMazo;
	}

	public VistaRed getVistaRed() {
		return vistaRed;
	}

	public VistaTablero getVistaTablero() {
		return vistaTablero;
	}

	public VistaDados getVistaDados() {
		return vistaDados;
	}

	public void mostrar(String vista) {
		this.layout.show(panelVistas, vista);
	}

	public VistaFinal getPantallaFinal() {
		return this.pantallaFinal;
	}

	public VistaDecidirComienzo getVistaDecidirComienzo() {
		return vistaDecidirComienzo;
	}
	
	private void prepararUI() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = new ImageIcon(getClass().getResource("/puntero.png")).getImage();
		Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
		setCursor (c);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fuentes/THEWITCHER.TTF")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fuentes/GWENT.TTF")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fuentes/AGENCYR.TTF")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(laf.getName())){
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                    UIManager.getLookAndFeelDefaults().put("Button[MouseOver].backgroundPainter", new BotonPainter(BotonPainter.MOUSE_OVER));
                    UIManager.getLookAndFeelDefaults().put("Button[Focused+MouseOver].backgroundPainter", new BotonPainter(BotonPainter.MOUSE_OVER));
                    UIManager.getLookAndFeelDefaults().put("Button[Enabled].backgroundPainter", new BotonPainter(BotonPainter.ENABLED));
                    UIManager.getLookAndFeelDefaults().put("Button[Focused].backgroundPainter", new BotonPainter(BotonPainter.ENABLED));
                    UIManager.getLookAndFeelDefaults().put("Button[Pressed].backgroundPainter", new BotonPainter(BotonPainter.ENABLED));
                    UIManager.getLookAndFeelDefaults().put("Button[Focused+Pressed].backgroundPainter", new BotonPainter(BotonPainter.ENABLED));
                    UIManager.getLookAndFeelDefaults().put("Button[Disabled].backgroundPainter", new BotonPainter(BotonPainter.DISABLED));
                    UIManager.getLookAndFeelDefaults().put("Button.contentMargins", new Insets(0, 0, 0, 0));
                    UIManager.getLookAndFeelDefaults().put("Button.font", new Font("thewitcher", Font.PLAIN, Graficos.getTamañoFuente(20)));
                    UIManager.getLookAndFeelDefaults().put("Button.textForeground", Color.ORANGE);
                    UIManager.getLookAndFeelDefaults().put("Panel.background", Color.WHITE);
                    UIManager.getLookAndFeelDefaults().put("TextPane[Enabled].backgroundPainter", new Color(0, 0, 0, 0));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new ScrollPainter(ScrollPainter.THUMB));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new ScrollPainter(ScrollPainter.THUMB));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarThumb[Pressed].backgroundPainter	", new ScrollPainter(ScrollPainter.THUMB));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar.background", Color.BLACK);
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\".size", 15);
                    UIManager.getLookAndFeelDefaults().put("ScrollBar.decrementButtonGap", 0);
                    UIManager.getLookAndFeelDefaults().put("ScrollBar.incrementButtonGap", 0);
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new ScrollPainter(ScrollPainter.TRACK));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter", new ScrollPainter(ScrollPainter.BUTTON));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\"[MouseOver].foregroundPainter", new ScrollPainter(ScrollPainter.BUTTON));
                    UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\"[Pressed].foregroundPainter", new ScrollPainter(ScrollPainter.BUTTON));
                    UIManager.getLookAndFeelDefaults().put("TextArea[Enabled].backgroundPainter", new Color(0,0,0,0));
                    UIManager.getLookAndFeelDefaults().put("ScrollPane[Enabled].borderPainter", new Color(0,0,0,0));
                    UIManager.getLookAndFeelDefaults().put("TextField.contentMargins", new Insets(0, 0, 0, 6));
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
