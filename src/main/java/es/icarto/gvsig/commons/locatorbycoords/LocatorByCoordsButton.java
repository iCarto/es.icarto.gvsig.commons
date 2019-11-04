package es.icarto.gvsig.commons.locatorbycoords;

import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class LocatorByCoordsButton extends JButton {

	/**
	 * Esto no está muy bien hecho así.
	 * 
	 * @param event
	 */
	public abstract void modelChanged(LocatorByCoordsModel model, String event);

}
