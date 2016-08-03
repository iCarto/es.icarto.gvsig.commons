package es.icarto.gvsig.importer;

import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.cresques.cts.IProjection;

import com.iver.cit.gvsig.fmap.core.FPoint2D;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.core.ShapeFactory;

public class XYPointBuilder {

    private static final Logger logger = Logger.getLogger(XYPointBuilder.class);

    private final String xCol;
    private final String yCol;

    public XYPointBuilder(String xCol, String yCol) {
	this.xCol = xCol;
	this.yCol = yCol;
    }

    public XYPointBuilder(String xCol, String yCol, IProjection from,
	    IProjection to) {
	this.xCol = xCol;
	this.yCol = yCol;
    }

    public IGeometry getGeometry(ImporterTM table, int row) {
	int xIdx = table.findColumn(xCol);
	int yIdx = table.findColumn(yCol);
	Object x = table.getValueAt(row, xIdx);
	Object y = table.getValueAt(row, yIdx);

	// return getGeometry(xStr, yStr);
	return null;
    }

    private IGeometry getGeometry(Object xO, Object yO) {
	Number x = toNumeric(xO);
	Number y = toNumeric(yO);

	FPoint2D fpoint2d = new FPoint2D(x.doubleValue(), y.doubleValue());
	IGeometry geom = ShapeFactory.createPoint2D(fpoint2d);

	// TODO
	// IProjection crs4326 = CRSFactory.getCRS("EPSG:4326");
	// IProjection crs32616 = CRSFactory.getCRS("EPSG:32616");
	// ICoordTrans ct = crs4326.getCT(crs32616);
	// geom.reProject(ct);

	return geom;
    }

    private Number toNumeric(Object o) {
	NumberFormat formatter = NumberFormat.getNumberInstance();
	// try {
	// return formatter.parse(v);
	// } catch (ParseException e) {
	// logger.error(e.getStackTrace(), e);
	// }
	return null;
    }

}
