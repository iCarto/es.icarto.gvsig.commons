package es.icarto.gvsig.commons.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class WidgetFactory {

    private WidgetFactory() {
	throw new AssertionError("Only static methods");
    }

    public static Border borderTitled(String title) {
	TitledBorder border = BorderFactory.createTitledBorder(title);
	border.setTitleColor(new Color(0, 60, 140));
	// default for title border
	// javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=bold,size=12]
	Font font = new Font("Arial", Font.BOLD, 12);
	border.setTitleFont(font);

	return border;
    }

    public static JLabel labelTitled(String text) {
	JLabel label = new JLabel(text);
	Font font = new Font("Arial", Font.BOLD, 11);
	label.setFont(font);
	label.setForeground(new Color(0, 60, 140));
	return label;
    }

    /**
     * Adds an OkCancelPanel 'docked south' of the panel param, and returns the
     * OkCancelPanel added
     */
    public static OkCancelPanel acceptCancelPanel(JPanel panel,
	    ActionListener ok, ActionListener cancel) {
	OkCancelPanel okCancelPanel = new OkCancelPanel(ok, cancel);
	// TODO Al usar dock el componente se sale del flujo normal, e ignora
	// los insets especificados. En lugar de poner 10 directamente,
	// deberíamos extraer el valor de los insets. Si no usamos el gap, el
	// botón de cancelar queda más a la derecha que el resto de componentes
	panel.add(okCancelPanel, "gapright 10, gapleft 10, dock south");
	return okCancelPanel;
    }
}
