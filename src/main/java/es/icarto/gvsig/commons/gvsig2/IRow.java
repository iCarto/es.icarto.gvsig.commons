package es.icarto.gvsig.commons.gvsig2;

import org.gvsig.fmap.geom.Geometry;

public interface IRow {
	public Object getID();

	public Value[] getAttributes();

	public Geometry getGeometry();

	public Value getAttribute(int fieldIndex);

}
