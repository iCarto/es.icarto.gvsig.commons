package es.icarto.gvsig.commons.referencing;

import java.awt.geom.Point2D;

import org.cresques.cts.IProjection;
import org.gvsig.fmap.crs.CRSFactory;

import es.icarto.gvsig.commons.geometries.MyEnvelope;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class ApplicableProjection {
	private final IProjection proj;
	private final MyEnvelope extent;
	private Point2D interiorPoint;
	private ITransformation transform;

	public ApplicableProjection(String epsgCode) {
		this(epsgCode, null);
	}

	public ApplicableProjection(String epsgCode, MyEnvelope extent) {
		this.proj = CRSFactory.getCRS(epsgCode);
		// this.transform = new NoOpTransformation();
		if (extent == null) {
			if (this.proj.isProjected()) {
				// https://www.maptools.com/tutorials/utm/details
				// https://en.wikipedia.org/wiki/Universal_Transverse_Mercator_coordinate_system
				extent = MyEnvelope.create(160000, 0, 834000, 9300000,
						this.proj);
			} else {
				// FIXME, que pasa si es geográfico
				extent = MyEnvelope.create(-360, -360, 360, 360, this.proj);
			}
		}

		this.extent = extent;

		this.interiorPoint = new Point2D.Double(extent.getCenterX(),
				extent.getCenterY());
	}

	public void setTransformation(ITransformation transform) {
		this.transform = transform;
	}

	public Point2D getInteriorPoint() {
		return interiorPoint;
	}

	/*
	 * Permite fijar el punto de ejemplo que usamos para representar esta zona
	 * Por defecto es el centro de la zona de trabajo, pero nos permite setear
	 * un punto de mayor valor simbólico.
	 */
	public void setInteriorPoint(Point2D interiorPoint) {
		if (!this.extent.contains(interiorPoint)) {
			throw new IllegalArgumentException("Not inside");
		}
		this.interiorPoint = new Point2D.Double(interiorPoint.getX(),
				interiorPoint.getY());
	}

	public GPoint transform(GPoint point, IProjection oProj) {
		if (this.transform != null) {
			return this.transform.transform(point, oProj);
		}
		point.reProject(oProj);
		return point;
	}

	public GPoint transform(GPoint point, ApplicableProjection oProj) {
		return this.transform(point, oProj.getProj());
	}

	public IProjection getProj() {
		return proj;
	}

	public GPoint getPoint(double x, double y) {
		return new GPoint(this.proj, x, y);
	}

	public MyEnvelope getExtent() {
		return extent;
	}

	@Override
	public String toString() {
		String epsg = getProj().getAbrev();
		String env = extent.getEnvelope().toString();
		return epsg + ";" + env;
	}
}
