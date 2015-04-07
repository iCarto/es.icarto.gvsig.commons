package es.icarto.gvsig.commons.gui;

import com.iver.cit.gvsig.fmap.layers.FLayer;

public interface LayerFilter {

    public boolean accept(FLayer layer);

}
