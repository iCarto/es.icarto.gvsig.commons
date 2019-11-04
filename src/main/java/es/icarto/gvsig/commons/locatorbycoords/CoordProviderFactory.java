package es.icarto.gvsig.commons.locatorbycoords;

import java.awt.geom.Point2D;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;
import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.fmap.dal.exception.ReadException;
import org.gvsig.fmap.geom.primitive.Envelope;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;

import es.icarto.gvsig.commons.geometries.MyEnvelope;
import es.icarto.gvsig.commons.referencing.ApplicableProjection;
import es.icarto.gvsig.commons.referencing.ApplicableProjectionFactory;

public class CoordProviderFactory {
	public static CoordProvider fromLayer(FLyrVect layer, String id)
			throws ReadException {
		ApplicableProjection appProj = ApplicableProjectionFactory
				.fromLayer(layer);
		CoordProvider cProv = new CoordProvider(id, appProj);
		Point2D intPoint = appProj.getInteriorPoint();
		String x_long = cProv.getOutputformat().format(intPoint.getX());
		String y_lat = cProv.getOutputformat().format(intPoint.getY());
		String placeholderX = String.format("utm X (%s)", x_long);
		String placeholderY = String.format("utm Y (%s)", y_lat);
		cProv.setPlaceholderText(placeholderX, placeholderY);
		return cProv;
	}

	public static CoordProvider fromReprojectedLayer(FLyrVect layer,
			String newEpsg, String id) throws ReadException {
		IProjection newProj = CRSFactory.getCRS(newEpsg);
		ICoordTrans ct = layer.getProjection().getCT(newProj);
		Envelope envelope = layer.getFullEnvelope().convert(ct);
		MyEnvelope extent = new MyEnvelope(envelope, newProj);
		ApplicableProjection appProj = new ApplicableProjection(newEpsg, extent);
		if (newProj.isProjected()) {
			CoordProvider cProv = new CoordProvider(id, appProj);
			Point2D intPoint = appProj.getInteriorPoint();
			String x_long = cProv.getOutputformat().format(intPoint.getX());
			String y_lat = cProv.getOutputformat().format(intPoint.getY());
			String placeholderX = String.format("utm X (%s)", x_long);
			String placeholderY = String.format("utm Y (%s)", y_lat);
			cProv.setPlaceholderText(placeholderX, placeholderY);
			return cProv;
		} else {
			CoordProvider cProv = new GeoCoordProvider(id, appProj);
			Point2D intPoint = appProj.getInteriorPoint();
			String x_long = cProv.getOutputformat().format(intPoint.getX());
			String y_lat = cProv.getOutputformat().format(intPoint.getY());
			String placeholderX = String.format("Long (%s)", x_long);
			String placeholderY = String.format("Lat (%s)", y_lat);
			cProv.setPlaceholderText(placeholderX, placeholderY);
			return cProv;
		}

	}

	public static CoordProvider reproject(CoordProvider orgCProv,
			String reprojectTo, String id) {
		CoordProvider cProv;
		IProjection newCRS = CRSFactory.getCRS(reprojectTo);
		MyEnvelope orgExtent = orgCProv.getProj().getExtent();
		MyEnvelope newExtent = orgExtent.convert(newCRS);

		ApplicableProjection newAppProj = new ApplicableProjection(reprojectTo,
				newExtent);
		Point2D intPoint = newAppProj.getInteriorPoint();

		if (orgCProv.getProj().getProj().isProjected()) {
			cProv = new CoordProvider(id, newAppProj);
			String x_long = cProv.getOutputformat().format(intPoint.getX());
			String y_lat = cProv.getOutputformat().format(intPoint.getY());
			String placeholderX = String.format("utm X (%s)", x_long);
			String placeholderY = String.format("utm Y (%s)", y_lat);
			cProv.setPlaceholderText(placeholderX, placeholderY);
		} else {
			cProv = new GeoCoordProvider(id, newAppProj);
			String x_long = cProv.getOutputformat().format(intPoint.getX());
			String y_lat = cProv.getOutputformat().format(intPoint.getY());
			String placeholderX = String.format("Long (%s)", x_long);
			String placeholderY = String.format("Lat (%s)", y_lat);
			cProv.setPlaceholderText(placeholderX, placeholderY);
		}
		return cProv;

	}
}
