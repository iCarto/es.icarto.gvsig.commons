package es.icarto.gvsig.commons;

import com.iver.cit.gvsig.fmap.MapControl;

import es.icarto.gvsig.commons.gui.TOCLayerManager;

public abstract class SingleLayerAbstractExtension extends AbstractExtension {

    protected boolean isLayerLoaded() {
	MapControl mapControl = getView().getMapControl();
	TOCLayerManager toc = new TOCLayerManager(mapControl);
	return toc.hasLayer(getLayerName());
    }

    protected abstract String getLayerName();
}
