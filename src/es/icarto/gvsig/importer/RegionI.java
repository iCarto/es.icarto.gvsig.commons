package es.icarto.gvsig.importer;

import com.iver.cit.gvsig.fmap.core.IGeometry;

/**
 * Define el espacio territorial en el que se ubica un elemento. Por ejemplo la
 * geometr�a de una comunidad debe estar dentro de la geometr�a de la aldea que
 * indique su c�digo
 *
 *
 */
public interface RegionI {

    public String getPKField();

    public String getPKValue();

    public double distanceTo(IGeometry geom);

}
