package es.icarto.gvsig.commons.referencing.gvsig;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;
import org.cresques.geo.Projected;
import org.gvsig.fmap.geom.Geometry.SUBTYPES;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.GeometryManager;
import org.gvsig.fmap.geom.exception.CreateGeometryException;
import org.gvsig.fmap.geom.primitive.Envelope;
import org.gvsig.fmap.geom.primitive.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.icarto.gvsig.commons.geometries.MyEnvelope;

public class GPoint implements Projected {

	private static final Logger logger = LoggerFactory.getLogger(GPoint.class);

	private IProjection proj;
	private Point p;

	public GPoint(IProjection proj, double x, double y) {
		this.proj = proj;
		GeometryManager geometryManager = GeometryLocator.getGeometryManager();
		try {
			p = geometryManager.createPoint(x, y, SUBTYPES.GEOM2D);
		} catch (CreateGeometryException e) {
			logger.error("Not possible to create the point", e);
			throw new RuntimeException(e);
		}
	}

	public GPoint(GPoint p) {
		this(p.getProjection(), p.getX(), p.getY());
	}

	@Override
	public IProjection getProjection() {
		return proj;
	}

	public Point getPoint() {
		return p;
	}

	@Override
	public void reProject(ICoordTrans ct) {
		p.reProject(ct);
		proj = ct.getPDest();
	}

	public GPoint reProject(IProjection destProj) {
		ICoordTrans ct = this.proj.getCT(destProj);
		reProject(ct);
		return this;
	}

	public double getX() {
		return p.getX();
	}

	public double getY() {
		return p.getY();
	}

	public MyEnvelope getEnvelope() {
		Envelope gvEnv = p.getEnvelope();
		MyEnvelope e = new MyEnvelope(gvEnv, proj);
		return e;
	}

	@Override
	public String toString() {
		String epsg = proj.getAbrev();
		String pStr = p.toString();
		return epsg + "; " + pStr;
	}
}