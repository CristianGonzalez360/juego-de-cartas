package interfaz.editorMazo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import interfaz.Graficos;
import negocio.carta.Lider;
import java.awt.Font;

public class MostradorLider extends JPanel {

	private JLabel descripcion;
	private JPanel central;
	private boolean bloqueado;
	private VistaEditorMazo vistaEditorMazo;
	
	public MostradorLider(VistaEditorMazo vistaEditorMazo) {
		this.vistaEditorMazo = vistaEditorMazo;
		setBounds(Graficos.getPosicionPanelLider());
		setOpaque(false);
		setLayout(new BorderLayout(0,0));
		this.central = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.central.setOpaque(false);
		add(this.central, BorderLayout.CENTER);
		
		this.descripcion = new JLabel();
		this.descripcion.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(18)));
		this.descripcion.setForeground(Color.ORANGE);
		this.descripcion.setHorizontalAlignment(SwingConstants.CENTER);
		this.descripcion.setVerticalAlignment(SwingConstants.TOP);
		add(this.descripcion, BorderLayout.SOUTH);
	}
		
	public void setLider(Lider lider) {
		this.central.removeAll();
		VistaCarta vistaLider = new VistaCarta(lider);
		this.descripcion.setText("<html><div style='text-align: center;'>" + lider.getEfecto().getDescripcion() + "</div></html>");
		vistaLider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(bloqueado == false && e.getButton() == MouseEvent.BUTTON1) {
					vistaLider.setBorder(null);
					vistaEditorMazo.editarLider();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (bloqueado == false) {
					vistaLider.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.ORANGE, Color.RED));
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				vistaLider.setBorder(null);
			}
		});
		this.central.add(vistaLider);
		this.central.revalidate();
		this.central.repaint();
	}

	public void bloquear() {
		this.bloqueado = true;	
	}
	
	public void desbloquear() {
		this.bloqueado = false;
	}
}
