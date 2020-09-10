package interfaz.editorMazo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import interfaz.Graficos;
import negocio.Mazo;
import java.awt.FlowLayout;
import java.awt.Font;

public class PanelInformacionMazo extends JPanel {
	
	private EditorMazo editor;
	private JLabel totalCartas;
	private JLabel cartasUnidad;
	private JLabel cartasEspeciales;
	private JLabel fuerzaTotal;
	private JLabel cartasHeroe;
	
	private Rectangle posicion;
	
	public PanelInformacionMazo() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setOpaque(false);
		setBounds(Graficos.getPosicionInformacionMazo());
		totalCartas = agregarParametro(("Cantidad de Cartas"));
		cartasUnidad = agregarParametro("Cartas de Unidad");
		cartasEspeciales = agregarParametro("Cartas Especiales");
		fuerzaTotal = agregarParametro("Fuerza Total");
		cartasHeroe = agregarParametro("Cartas Heroe");
	}
	
	public void actualizar() {
		Mazo mazo = editor.getMazo();
		if(mazo != null) {
			totalCartas.setText(mazo.getCantidad() + "");
			cartasUnidad.setText(mazo.getCantidadUnidades() + "/" + Mazo.MINIMO_UNIDADES);
			if(mazo.getCantidadUnidades()<Mazo.MINIMO_UNIDADES) {
				cartasUnidad.setForeground(Color.RED);
			}
			else {
				cartasUnidad.setForeground(Color.GREEN);
			}
			cartasEspeciales.setText(mazo.getCantidadEspeciales() + "/" + Mazo.MAXIMO_ESPECIALES);
			if(mazo.getCantidadEspeciales()>Mazo.MAXIMO_ESPECIALES) {
				cartasEspeciales.setForeground(Color.RED);
			}
			else {
				cartasEspeciales.setForeground(Color.GREEN);
			}
			fuerzaTotal.setText(mazo.getFuerzaMaxima() + "/" + Mazo.FUERZA_MAXIMA);
			if(mazo.getFuerzaMaxima()>Mazo.FUERZA_MAXIMA) {
				fuerzaTotal.setForeground(Color.RED);
			}
			else {
				fuerzaTotal.setForeground(Color.GREEN);
			}
			cartasHeroe.setText(mazo.getCantidadHeroes() + "/" + Mazo.MAXIMO_HEROES);
			if(mazo.getCantidadHeroes()>Mazo.MAXIMO_HEROES) {
				cartasHeroe.setForeground(Color.RED);
			}
			else {
				cartasHeroe.setForeground(Color.GREEN);
			}
		}
	}
	
	private JLabel agregarParametro(String nombre) {
		JLabel ret = new JLabel();
		ret.setForeground(Color.ORANGE);
		ret.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(18)));
		ret.setPreferredSize(new Dimension(posicion.width, posicion.height/10));
		ret.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel parametro = new JLabel(nombre);
		parametro.setForeground(Color.ORANGE);
		parametro.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(18)));
		parametro.setPreferredSize(new Dimension(posicion.width, posicion.height/10));
		parametro.setHorizontalAlignment(SwingConstants.CENTER);
		add(parametro);
		add(ret);
		return ret;
	}
	
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		this.posicion = r;
	}

	public void setEditor(EditorMazo editor) {
		this.editor = editor;
		actualizar();
	}
}
