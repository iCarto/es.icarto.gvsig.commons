package es.icarto.gvsig.commons.referencing;

import org.gvsig.fmap.dal.exception.ReadException;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;

import es.icarto.gvsig.commons.geometries.MyEnvelope;

public class ApplicableProjectionFactory {
	public static ApplicableProjection SIGA_EXTENT_25829() {
		MyEnvelope e = MyEnvelope.create(472637.79, 4626294.5, 690330.44, 4850713.62, "EPSG:25829");
		return new ApplicableProjection("EPSG:25829", e);
	}

	public static ApplicableProjection SIGA_EXTENT_23029() {
		MyEnvelope e = MyEnvelope.create(472175.48, 4751978.58, 691493.78, 4851029.48, "EPSG:23029");
		return new ApplicableProjection("EPSG:23029", e);
	}

	public static ApplicableProjection SIGA_EXTENT_4326() {
		MyEnvelope e = MyEnvelope.create(-9.29885967, 41.8072541522, -6.734324529, 43.7924041112, "EPSG:4326");
		return new ApplicableProjection("EPSG:4326", e);
	}

	public static ApplicableProjection fromLayer(FLyrVect layer) throws ReadException {
		MyEnvelope extent = new MyEnvelope(layer.getFullEnvelope(), layer.getProjection());
		String epsgCode = layer.getProjection().getAbrev();
		ApplicableProjection applicableProjection = new ApplicableProjection(epsgCode, extent);
		return applicableProjection;
	}

}
