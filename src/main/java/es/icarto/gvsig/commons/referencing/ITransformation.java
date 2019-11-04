package es.icarto.gvsig.commons.referencing;

import org.cresques.cts.IProjection;

import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public interface ITransformation {

    public GPoint transform(GPoint p, IProjection proj);

    public GShape transform(GShape shape, IProjection proj);

}