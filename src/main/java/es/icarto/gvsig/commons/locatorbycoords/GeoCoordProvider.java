package es.icarto.gvsig.commons.locatorbycoords;

import es.icarto.gvsig.commons.format.CoordFormat;
import es.icarto.gvsig.commons.referencing.ApplicableProjection;

public class GeoCoordProvider extends CoordProvider {

	public GeoCoordProvider(String name, String epsg) {
		this(name, new ApplicableProjection(epsg));
	}

	public GeoCoordProvider(String name, ApplicableProjection proj) {
		super(name, proj);
		setOuputFormat(CoordFormat.latLngFormatter());
	}

}
