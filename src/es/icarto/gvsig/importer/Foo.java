package es.icarto.gvsig.importer;

import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;

import com.iver.cit.gvsig.fmap.core.FPoint2D;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.core.ShapeFactory;
import com.iver.cit.gvsig.fmap.crs.CRSFactory;

public class Foo {

    private static final Logger logger = Logger.getLogger(Foo.class);

    private IGeometry getGeometry(String xStr, String yStr) {
	double x = toNumeric(xStr).doubleValue();
	double y = toNumeric(yStr).doubleValue();

	FPoint2D fpoint2d = new FPoint2D(x, y);
	IGeometry geom = ShapeFactory.createPoint2D(fpoint2d);
	if ((Math.abs(x) < 180) && (Math.abs(y) < 180)) {
	    IProjection crs4326 = CRSFactory.getCRS("EPSG:4326");
	    IProjection crs32616 = CRSFactory.getCRS("EPSG:32616");
	    ICoordTrans ct = crs4326.getCT(crs32616);
	    geom.reProject(ct);
	}

	return geom;
    }

    private Number toNumeric(String v) {
	NumberFormat formatter = NumberFormat.getNumberInstance();
	try {
	    return formatter.parse(v);
	} catch (ParseException e) {
	    logger.error(e.getStackTrace(), e);
	}
	return null;
    }

    public IGeometry getGeometry(ImporterTM table, int row) {
	int xIdx = table.findColumn("x");
	int yIdx = table.findColumn("y");
	String xStr = table.getValueAt(row, xIdx).toString();
	String yStr = table.getValueAt(row, yIdx).toString();
	return getGeometry(xStr, yStr);
    }

}
