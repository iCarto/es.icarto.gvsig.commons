package es.icarto.gvsig.commons.locatorbycoords;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import es.icarto.gvsig.commons.format.CoordFormat;
import es.icarto.gvsig.commons.referencing.ApplicableProjection;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class CoordProvider {

	private final String name;
	private final DecimalFormat format;
	private final ApplicableProjection proj;
	private DecimalFormat outputformat;
	private final String[] placeholderText = new String[2];

	public CoordProvider(String name, String epsg) {
		this(name, new ApplicableProjection(epsg));
	}

	public CoordProvider(String name, ApplicableProjection proj) {
		this.name = name;
		this.format = (DecimalFormat) NumberFormat.getNumberInstance(Locale
				.getDefault());
		this.format.applyPattern("0.##########");
		this.outputformat = CoordFormat.utmFormatter();
		this.proj = proj;
	}

	public void setOuputFormat(DecimalFormat format) {
		this.outputformat = format;
	}

	public DecimalFormat getOutputformat() {
		return outputformat;
	}

	public boolean validX(String value) {
		Number v = normalize(value);
		if (v == null) {
			return false;
		}
		double y = proj.getInteriorPoint().getY();
		return proj.getExtent().contains(v.doubleValue(), y);
	}

	private Number normalize(String value) {

		ParsePosition pp = new ParsePosition(0);
		Number n = format.parse(value, pp);
		if (value.trim().length() != pp.getIndex() || n == null) {
			return null;
		}

		return n;
	}

	public boolean validY(String value) {
		Number v = normalize(value);
		if (v == null) {
			return false;
		}
		double x = proj.getInteriorPoint().getX();
		return proj.getExtent().contains(x, v.doubleValue());
	}

	public String[] transform(GPoint point, CoordProvider oProv) {
		// Take care. Esto modifica el "point" y luego pueden petar cosas
		// igual hay que hacer una copia
		NumberFormat oFormat = oProv.getOutputformat();
		ApplicableProjection oProj = oProv.getProj();
		GPoint oPoint = this.proj.transform(point, oProj);

		return new String[] { oFormat.format(oPoint.getX()),
				oFormat.format(oPoint.getY()) };
	}

	/*
	 * Returns the x and y positions of the point transformed to this proj if
	 * needed and with the decimal output format defined for this coordprovider
	 */
	public String[] textCoordinates(GPoint point) {
		GPoint thisPoint = new GPoint(point);
		thisPoint.reProject(this.proj.getProj());
		return new String[] { outputformat.format(thisPoint.getX()),
				outputformat.format(thisPoint.getY()) };
	}

	public GPoint toGPoint(String inputX, String inputY) {
		double x = normalize(inputX).doubleValue();
		double y = normalize(inputY).doubleValue();
		return this.proj.getPoint(x, y);
	}

	public ApplicableProjection getProj() {
		return proj;
	}

	public void setPlaceholderText(String x, String y) {
		this.placeholderText[0] = x;
		this.placeholderText[1] = y;
	}

	public String[] getPlaceholderText() {
		return this.placeholderText;
	}

	@Override
	public String toString() {
		return name;
	}

}
