package es.icarto.gvsig.importer;

import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public abstract class Entity {

    private String pk;
    private Geometry geom;
    private String desc;

    public abstract String tablename();

    public abstract String pkname();

    // Representa el nombre del campo en la base de datos que contiene el nombre
    // común de la región (por ejemplo el nombre de la aldea)
    public abstract String descname();

    public String getPK() {
	return pk;
    }

    public void setPK(String pk) {
	this.pk = pk;
    }

    public Geometry getGeom() {
	return geom;
    }

    public void setGeom(IGeometry geom) {
	this.geom = geom.toJTSGeometry();
    }

    public void setGeom(String xStr, String yStr) {
	GeometryFactory factory = new GeometryFactory();
	Coordinate c = new Coordinate(Double.parseDouble(xStr),
		Double.parseDouble(yStr));
	this.geom = factory.createPoint(c);
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public double distanceTo(IGeometry geom) {
	return this.geom.distance(geom.toJTSGeometry());
    }

    public double distanceTo(Geometry geom) {
	return this.geom.distance(geom);
    }

}
