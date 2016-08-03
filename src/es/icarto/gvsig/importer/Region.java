package es.icarto.gvsig.importer;

import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class Region implements RegionI {

    private final String pk;
    private final Geometry geom;

    private Region(String pk, IGeometry geom) {
	this.pk = pk;
	this.geom = geom.toJTSGeometry();
    }

    private Region(String pk, String xStr, String yStr) {
	this.pk = pk;
	GeometryFactory factory = new GeometryFactory();
	Coordinate c = new Coordinate(Double.parseDouble(xStr),
		Double.parseDouble(yStr));
	this.geom = factory.createPoint(c);
    }

    @Override
    public String getPKField() {
	throw new RuntimeException("Not implemented yet");
    }

    @Override
    public String getPKValue() {
	return pk;
    }

    @Override
    public double distanceTo(IGeometry geom) {
	return this.geom.distance(geom.toJTSGeometry());
    }

    public static RegionI from(String pk, IGeometry geom) {
	return new Region(pk, geom);
    }

    public static RegionI from(String pk, String xStr, String yStr) {
	return new Region(pk, xStr, yStr);
    }

}
