package interfaz.editorMazo;

import javax.swing.JPanel;
import negocio.carta.Lider;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.omg.PortableServer.ServantRetentionPolicyValue;

import interfaz.Graficos;
import interfaz.editorMazo.VistaCarta;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

public class SelectorLider extends JPanel{
	
	private JPanel panelCentral;
	private JLabel lblHabilidad;
	private VistaEditorMazo vista;
	
	public SelectorLider(VistaEditorMazo vista) {
		this.vista = vista;
		setBackground(new Color(255, 255, 255, 100));
		setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new LineBorder(Color.ORANGE));
		panelPrincipal.setBackground(Color.BLACK);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		panelPrincipal.setBounds(Graficos.getPosicionSelectorLider());
		add(panelPrincipal);
		
		panelCentral = new JPanel();
		panelCentral.setBackground(Color.BLACK);
		FlowLayout fl_panelCentral = (FlowLayout) panelCentral.getLayout();
		fl_panelCentral.setVgap(0);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		
		lblHabilidad = new JLabel();
		lblHabilidad.setFont(new Font("Tahoma", Font.BOLD, Graficos.getTamañoFuente(18)));
		lblHabilidad.setForeground(Color.ORANGE);
		lblHabilidad.setBackground(Color.BLACK);
		lblHabilidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblHabilidad.setVerticalAlignment(SwingConstants.CENTER);
		panelPrincipal.add(lblHabilidad, BorderLayout.SOUTH);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				vista.desbloquear();
			}
		});
		
	}
	
	public void mostrarLideres(List<Lider> lideres) {
		panelCentral.removeAll();
		for(Lider lider : lideres) {
			VistaCarta vc = new VistaCarta(lider);
			vc.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					vista.seleccionarLider(lider);
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					lblHabilidad.setText(lider.getDescripcion());
					vc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 165, 0), new Color(255, 69, 0)));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lblHabilidad.setText("");
					vc.setBorder(null);
				}
			});
			panelCentral.add(vc);
		}
		panelCentral.revalidate();
	}
}
