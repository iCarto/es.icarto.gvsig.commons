package es.icarto.gvsig.importer;

import java.text.NumberFormat;
import java.util.Locale;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;

import com.iver.cit.gvsig.fmap.core.FPoint2D;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.core.ShapeFactory;
import com.iver.cit.gvsig.fmap.crs.CRSFactory;

public class GFactory {

    private final static NumberFormat formatter = NumberFormat
	    .getInstance(Locale.US);

    private IGeometry getGeometry(String xStr, String yStr) {
	double x = toNumeric(xStr.replace(",", ".")).doubleValue();
	double y = toNumeric(yStr.replace(",", ".")).doubleValue();

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
	try {
	    return formatter.parse(v);
	} catch (Exception e) {
	    return null;
	}
    }

    public IGeometry getGeometry(ImporterTM table, int row) {
	int xIdx = table.findColumn("x");
	int yIdx = table.findColumn("y");
	String xStr = table.getValueAt(row, xIdx).toString();
	String yStr = table.getValueAt(row, yIdx).toString();
	return getGeometry(xStr, yStr);
    }

}
