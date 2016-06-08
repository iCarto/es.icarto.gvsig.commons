package es.icarto.gvsig.commons.gui;

import org.gvsig.fmap.mapcontext.layers.FLayer;

public interface LayerFilter {

    public boolean accept(FLayer layer);

}
