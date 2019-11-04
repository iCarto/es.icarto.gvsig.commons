package es.icarto.gvsig.commons.locatorbycoords;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import es.icarto.gvsig.commons.map.ZoomTo;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

@SuppressWarnings("serial")
public class LocatonByCoordsZoomButton extends LocatorByCoordsButton {

	private final LocatorByCoordsModel lModel;

	public LocatonByCoordsZoomButton(LocatorByCoordsModel lModel_) {
		super();
		this.lModel = lModel_;
		Action zoomAction = new AbstractAction("Zoom") {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZoomTo zoom = lModel.getZoomTo();
				GPoint point = lModel.getPoint();
				zoom.zoom(point, true);
				lModel.setZoomed(true);
			}
		};
		zoomAction.setEnabled(false);
		this.setAction(zoomAction);
	}

	@Override
	public void modelChanged(LocatorByCoordsModel model, String event) {
		if (event.equals("setZoomed")) {
			return;
		}
		boolean enabled = model.getPoint() != null;
		this.getAction().setEnabled(enabled);
	}

}
