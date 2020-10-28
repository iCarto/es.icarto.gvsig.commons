package es.icarto.gvsig.commons.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class GenericGUITest {

	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pane = new JPanel(new MigLayout("insets 10"));

		f.getContentPane().add(pane, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}

}
