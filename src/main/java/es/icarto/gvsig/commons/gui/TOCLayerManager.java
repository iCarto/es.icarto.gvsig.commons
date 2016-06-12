package es.icarto.gvsig.commons.gui;

import java.util.ArrayList;
import java.util.List;

import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontext.layers.FLayers;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;
import org.gvsig.fmap.mapcontrol.MapControl;

/**
 * The api of this class will change in the future. If you are going to use it
 * explain your use case first
 */
@Deprecated
public class TOCLayerManager {

    private MapControl mapControl = null;

    @SuppressWarnings("unused")
    private TOCLayerManager() {
	throw new AssertionError();
    }

    protected TOCLayerManager(MapControl mapControl) {
	this.mapControl = mapControl;
    }

    public FLyrVect getActiveLayer() {
	if (mapControl != null) {
	    FLayer[] activeLayers = mapControl.getMapContext().getLayers()
		    .getActives();
	    for (FLayer layer : activeLayers) {
		if (isFLyrVect(layer)) {
		    return (FLyrVect) layer;
		}
	    }
	}
	return null;
    }

    public FLyrVect[] getActiveLayers() {
	List<FLyrVect> layers = new ArrayList<FLyrVect>();
	if (mapControl != null) {
	    FLayer[] activeLayers = mapControl.getMapContext().getLayers()
		    .getActives();
	    for (FLayer layer : activeLayers) {
		if (isFLyrVect(layer)) {
		    layers.add((FLyrVect) layer);
		}
	    }
	}
	return layers.toArray(new FLyrVect[0]);
    }

    public FLyrVect[] getVisibleLayers() {
	List<FLyrVect> layers = new ArrayList<FLyrVect>();
	if (mapControl != null) {
	    FLayer[] activeLayers = mapControl.getMapContext().getLayers()
		    .getVisibles();
	    for (FLayer layer : activeLayers) {
		if (isFLyrVect(layer)) {
		    layers.add((FLyrVect) layer);
		}
	    }
	}
	return layers.toArray(new FLyrVect[0]);
    }

    public String getNameOfActiveLayer() {
	FLyrVect layer = getActiveLayer();
	if (layer != null) {
	    return layer.getName();
	}
	return null;
    }

    public <T extends FLayer> List<T> getAllLayers(Class<T> clasz) {
	List<T> layers = new ArrayList<T>();
	layers.addAll(getInnerLayers(mapControl.getMapContext().getLayers(),
		clasz));
	return layers;
    }

    public List<FLyrVect> getJoinedLayers() {
	throw new RuntimeException("Not implemented");
	// List<FLyrVect> layers = getAllLayers(FLyrVect.class);
	// for (FLyrVect l : layers) {
	// if (l.isJoined()) {
	// layers.remove(l);
	// }
	// }
	// return layers;
    }

    protected <T extends FLayer> List<T> getInnerLayers(FLayers layerGroup,
	    Class<T> clasz) {
	List<T> layers = new ArrayList<T>();
	// FLayers returns the layers in inverse order of how the appear in the
	// TOC
	for (int i = layerGroup.getLayersCount() - 1; i >= 0; i--) {
	    FLayer layer = layerGroup.getLayer(i);
	    if (isFLayers(layer)) {
		List<T> innerLayers = getInnerLayers((FLayers) layer, clasz);
		layers.addAll(innerLayers);
		continue;
	    }

	    if (clasz.isAssignableFrom(layer.getClass())) {
		layers.add((T) layer);
	    }
	}
	return layers;
    }

    public boolean hasName(FLayer layer, String layerName) {
	return layer.getName().equalsIgnoreCase(layerName);
    }

    public boolean isFLyrVect(FLayer layer) {
	return layer instanceof FLyrVect;
    }

    public boolean isFLayers(final FLayer layer) {
	return layer instanceof FLayers;
    }
}
