package es.icarto.gvsig.commons.locatorbycoords;

import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class LocatorByCoordsButton extends JButton {

	/**
	 * Esto no est� muy bien hecho as�.
	 * 
	 * @param event
	 */
	public abstract void modelChanged(LocatorByCoordsModel model, String event);

}
