package es.icarto.gvsig.commons.locatorbycoords;

import java.util.ArrayList;
import java.util.List;

import es.icarto.gvsig.commons.map.ZoomTo;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class LocatorByCoordsModel {

	private List<LocatorByCoordsButton> btList = new ArrayList<LocatorByCoordsButton>();

	public final List<CoordProvider> projCodes = new ArrayList<CoordProvider>();

	private CoordProvider defaultInputProj;
	private CoordProvider defaultOuputProj;

	private ZoomTo zoomTo;

	private boolean zoomed;
	private GPoint point;

	public void addCoordProvider(CoordProvider prov) {
		projCodes.add(prov);
	}

	public CoordProvider getDefaultInputProj() {
		return defaultInputProj;
	}

	public void setDefaultInputProj(CoordProvider defaultInputProj) {
		this.defaultInputProj = defaultInputProj;
	}

	public CoordProvider getDefaultOuputProj() {
		return defaultOuputProj;
	}

	public void setDefaultOuputProj(CoordProvider defaultOuputProj) {
		this.defaultOuputProj = defaultOuputProj;
	}

	public List<CoordProvider> getProjCodes() {
		return projCodes;
	}

	public CoordProvider getProjCode(String epsg) {
		for (CoordProvider prov : projCodes) {
			String provEpsg = prov.getProj().getProj().getAbrev();
			if (provEpsg.equals(epsg)) {
				return prov;
			}
		}
		return null;
	}

	public void setZoomTo(ZoomTo zoomTo) {
		this.zoomTo = zoomTo;
	}

	public ZoomTo getZoomTo() {
		return zoomTo;
	}

	/*
	 * Valdrá true justo después de hacer zoom, y false cada vez que se modifique
	 * algo en el diálogo sin hacer zoom
	 */
	public void setZoomed(boolean b) {
		zoomed = b;
		fireMyEvent("setZoomed");
	}

	public boolean isZoomed() {
		return this.zoomed;
	}

	public void updateState(boolean validX, boolean validY, CoordProvider iProv, CoordProvider oProv, GPoint point,
			boolean zoomed) {
		this.point = point;
		this.zoomed = zoomed;
		fireMyEvent("updateState");

	}

	public GPoint getPoint() {
		return this.point;
	}

	private void fireMyEvent(String event) {
		for (LocatorByCoordsButton bt : btList) {
			bt.modelChanged(this, event);
		}
	}

	public void addBt(LocatorByCoordsButton bt) {
		btList.add(bt);
	}

	public List<LocatorByCoordsButton> getBts() {
		return btList;
	}

}