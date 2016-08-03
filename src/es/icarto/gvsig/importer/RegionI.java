package es.icarto.gvsig.importer;

import com.iver.cit.gvsig.fmap.core.IGeometry;

/**
 * Define el espacio territorial en el que se ubica un elemento. Por ejemplo la
 * geometría de una comunidad debe estar dentro de la geometría de la aldea que
 * indique su código
 *
 *
 */
public interface RegionI {

    public String getPKField();

    public String getPKValue();

    public double distanceTo(IGeometry geom);

}
