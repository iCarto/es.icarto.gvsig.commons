package es.icarto.gvsig.commons.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

// http://stackoverflow.com/a/16229082/930271

@SuppressWarnings("serial")
public class PlaceholderTextField extends JTextField implements FocusListener {

    private String placeholder;

    public PlaceholderTextField(final String placeholder) {
	super();
	this.placeholder = placeholder;
	addFocusListener(this);
    }

    @Override
    protected void paintComponent(final Graphics pG) {
	super.paintComponent(pG);

	if (placeholder.isEmpty()
		|| !getText().isEmpty()
		|| (KeyboardFocusManager.getCurrentKeyboardFocusManager()
			.getFocusOwner() == this)) {
	    return;
	}

	final Graphics2D g = (Graphics2D) pG;
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	g.setColor(getDisabledTextColor());
	g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
		.getMaxAscent() + getInsets().top);
    }

    public String getPlaceholder() {
	return placeholder;
    }

    public void setPlaceholder(final String s) {
	placeholder = s;
	repaint();
    }

    @Override
    public void focusGained(FocusEvent e) {
	repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
	repaint();
    }

}