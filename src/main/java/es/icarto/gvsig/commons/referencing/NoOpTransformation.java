package es.icarto.gvsig.commons.referencing;

import org.cresques.cts.IProjection;

import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class NoOpTransformation implements ITransformation {

    @Override
    public GPoint transform(GPoint p, IProjection proj) {
	if (p.getProjection().getAbrev().equals(proj.getAbrev())) {
	    return p;
	}
	throw new IllegalArgumentException(
		"Only allowed if p is in the same IProjection that proj");
    }

    @Override
    public GShape transform(GShape shape, IProjection proj) {
	if (shape.getProjection().getAbrev().equals(proj.getAbrev())) {
	    return shape;
	}
	throw new IllegalArgumentException(
		"Only allowed if p is in the same IProjection that proj");
    }

}
