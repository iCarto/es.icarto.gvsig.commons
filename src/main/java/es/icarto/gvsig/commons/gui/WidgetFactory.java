package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import com.jeta.forms.gui.beans.factories.ComboBoxBeanFactory;
import com.jeta.forms.gui.common.FormException;

public class WidgetFactory {

	private static final Color ENABLED_DISABLED_TEXT_COLOR = Color.BLACK;
	private static final Color ENABLED_BACKGROUND_COLOR = Color.WHITE;
	private static final Color ENABLED_FOREGROUND_COLOR = Color.BLACK;

	private static final Color DISABLED_DISABLED_TEXT_COLOR = new Color(189,
			190, 176);
	private static final Color DISABLED_BACKGROUND_COLOR = new Color(240, 240,
			240);
	private static final Color DISABLED_FOREGROUND_COLOR = new Color(102, 102,
			102);

	// private static final Font DISABLED_FONT = new Font("Arial", Font.PLAIN,
	// 11);

	private WidgetFactory() {
		throw new AssertionError("Only static methods");
	}

	public static Border borderTitled(String title) {
		TitledBorder border = BorderFactory.createTitledBorder(_(title));
		border.setTitleColor(new Color(0, 60, 140));
		// default for title border
		// javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=bold,size=12]
		Font font = new Font("Arial", Font.BOLD, 12);
		border.setTitleFont(font);

		return border;
	}

	public static JLabel labelTitled(String text) {
		JLabel label = new JLabel(_(text));
		Font font = new Font("Arial", Font.BOLD, 11);
		label.setFont(font);
		label.setForeground(new Color(0, 60, 140));
		return label;
	}

	public static JComboBox combobox() {
		JComboBox combo = null;
		try {
			Component bean = new ComboBoxBeanFactory().instantiateBean();
			combo = (JComboBox) bean;
			combo.setBorder(null);
		} catch (FormException e) {
			e.printStackTrace();
		}

		return combo;
	}

	/**
	 * Adds an OkCancelPanel 'docked south' of the panel param, and returns the
	 * OkCancelPanel added
	 */
	public static OkCancelPanel okCancelPanel(JPanel panel, ActionListener ok,
			ActionListener cancel) {
		OkCancelPanel okCancelPanel = new OkCancelPanel(ok, cancel);
		// TODO Al usar dock el componente se sale del flujo normal, e ignora
		// los insets especificados. En lugar de poner 10 directamente,
		// deberíamos extraer el valor de los insets. Si no usamos el gap, el
		// botón de cancelar queda más a la derecha que el resto de componentes
		panel.add(okCancelPanel, "gapright 10, dock south");
		return okCancelPanel;
	}

	public static void enableComponent(JComponent c, boolean enable) {
		if (enable) {
			enableComponent(c);
		} else {
			disableComponent(c);
		}
	}

	public static void disableComponent(JComponent c) {
		c.setEnabled(false);
		if (c instanceof JTextComponent) {
			JTextComponent tc = (JTextComponent) c;
			tc.setEditable(false);
			tc.setBackground(DISABLED_BACKGROUND_COLOR);
			tc.setDisabledTextColor(ENABLED_DISABLED_TEXT_COLOR);
			tc.setForeground(DISABLED_FOREGROUND_COLOR);
		}
	}

	public static void enableComponent(JComponent c) {
		c.setEnabled(true);
		if (c instanceof JTextComponent) {
			JTextComponent tc = (JTextComponent) c;
			tc.setEditable(true);
			tc.setBackground(ENABLED_BACKGROUND_COLOR);
			tc.setDisabledTextColor(ENABLED_DISABLED_TEXT_COLOR);
			tc.setForeground(ENABLED_FOREGROUND_COLOR);
		}
	}
}
