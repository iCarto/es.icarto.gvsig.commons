package es.icarto.gvsig.commons.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CoordFormat {

	public static DecimalFormat latLngFormatter() {
		DecimalFormat fGeo = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
		fGeo.applyPattern("0.#####");
		fGeo.setMaximumFractionDigits(6);
		fGeo.setMinimumFractionDigits(5);
		return fGeo;
	}

	public static DecimalFormat utmFormatter() {
		DecimalFormat fUtm = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
		fUtm.applyPattern("0.###");
		fUtm.setMaximumFractionDigits(3);
		return fUtm;
	}

}
