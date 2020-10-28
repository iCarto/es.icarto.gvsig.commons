package es.icarto.gvsig.commons.geometries;

import java.awt.geom.Point2D;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;
import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.fmap.geom.Geometry.SUBTYPES;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.GeometryManager;
import org.gvsig.fmap.geom.exception.CreateEnvelopeException;
import org.gvsig.fmap.geom.exception.CreateGeometryException;
import org.gvsig.fmap.geom.primitive.Envelope;
import org.gvsig.fmap.geom.primitive.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEnvelope {
	/**
	 * Debería ser esta clase un Gshape?
	 */
	private final Envelope e;

	private IProjection proj;

	private static final Logger logger = LoggerFactory.getLogger(MyEnvelope.class);

	public static MyEnvelope create(double minX, double minY, double maxX, double maxY, IProjection proj) {

		GeometryManager geomManager = GeometryLocator.getGeometryManager();
		Envelope e;
		try {
			e = geomManager.createEnvelope(minX, minY, maxX, maxY, SUBTYPES.GEOM2D);
		} catch (CreateEnvelopeException e1) {
			logger.error(e1.getMessage(), e1);
			throw new RuntimeException(e1);
		}
		return new MyEnvelope(e, proj);
	}

	public static MyEnvelope create(double minX, double minY, double maxX, double maxY, String epsgCode) {
		return MyEnvelope.create(minX, minY, maxX, maxY, CRSFactory.getCRS(epsgCode));
	}

	public MyEnvelope(Envelope e, IProjection proj) {
		this.e = e;
		this.proj = proj;
	}

	public MyEnvelope(MyEnvelope e) {
		this.e = e.getEnvelope();
		this.proj = e.getProj();
	}

	public double getCenterX() {
		return this.e.getCenter(0);
	}

	public double getCenterY() {
		return this.e.getCenter(1);
	}

	public boolean contains(Point2D interiorPoint) {
		return this.contains(interiorPoint.getX(), interiorPoint.getY());
	}

	public boolean contains(double x, double y) {
		GeometryManager manager = GeometryLocator.getGeometryManager();
		Point p;
		try {
			p = manager.createPoint(x, y, SUBTYPES.GEOM2D);
		} catch (CreateGeometryException e1) {
			throw new RuntimeException(e1);
		}
		return this.e.intersects(p);
	}

	public MyEnvelope convert(IProjection newCRS) {
		ICoordTrans ct = this.proj.getCT(newCRS);
		Envelope e = this.e.convert(ct);
		return new MyEnvelope(e, newCRS);
	}

	public Envelope getEnvelope() {
		return this.e;
	}

	public IProjection getProj() {
		return this.proj;
	}

	public MyEnvelope toZoomable() {
		/*
		 * Este método ajusta el envelope a unos mínimos en función de la proyección. Se
		 * usa sobre todo cuando está basado en un punto y lo que se quiere es hacer
		 * zoom a ese punto pero cubriendo un área mayor.
		 *
		 * Ahora está hardcodeado y no funcionará con geográficas
		 */

		if (this.e.getLength(0) < 200) {

			try {
				GeometryManager manager = GeometryLocator.getGeometryManager();
				Point lowerTmp = this.e.getLowerCorner();
				Point lower = manager.createPoint(lowerTmp.getX(), lowerTmp.getY(), lowerTmp.getType());
				Point upperTmp = this.e.getUpperCorner();
				Point upper = manager.createPoint(upperTmp.getX(), upperTmp.getY(), upperTmp.getType());
				lower.move(-25, -25);
				upper.move(+25, +25);
				Envelope env = manager.createEnvelope(lowerTmp.getType());
				env.setLowerCorner(lower);
				env.setUpperCorner(upper);
				return new MyEnvelope(env, this.proj);
			} catch (CreateGeometryException e1) {
				throw new RuntimeException(e1);
			} catch (CreateEnvelopeException e1) {
				throw new RuntimeException(e1);
			}

		}
		return new MyEnvelope(this);

	}

	@Override
	public String toString() {
		String epsg = getProj().getAbrev();
		String env = e.toString();
		return epsg + ";" + env;
	}

}
