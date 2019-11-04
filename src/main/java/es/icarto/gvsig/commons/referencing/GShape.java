package es.icarto.gvsig.commons.referencing;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;
import org.cresques.geo.Projected;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.primitive.Point;
import org.gvsig.tools.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class GShape implements Projected {

	private static final Logger logger = LoggerFactory.getLogger(GPoint.class);

	private final IProjection proj;
	private final Geometry shp;

	public GShape(IProjection proj, Geometry shp) {
		this.proj = proj;
		this.shp = shp.cloneGeometry();
	}

	@Override
	public IProjection getProjection() {
		return this.proj;
	}

	@Override
	public void reProject(ICoordTrans ct) {
		shp.reProject(ct);
	}

	public void reProject(IProjection destProj) {
		ICoordTrans ct = this.proj.getCT(destProj);
		shp.reProject(ct);
	}

	public GPoint getInsidePoint() {
		try {
			Point point = shp.getInteriorPoint();
			return new GPoint(this.proj, point.getX(), point.getY());
		} catch (BaseException e) {
			logger.error("Operation not available", e);
			throw new RuntimeException(e);
		}
	}

	public boolean contains(double x, double y) {
		return shp.contains(x, y);
	}
}
