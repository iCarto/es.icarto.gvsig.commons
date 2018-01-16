package es.icarto.gvsig.commons.testutils;

import com.iver.cit.gvsig.fmap.MapContext;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLayers;

@SuppressWarnings("serial")
public class MapControlStub extends MapControl {

    public MapControlStub() {
	super();
	ViewPort vp = null;
	MapContext mapContext = new MapContext(vp);
	setMapContext(mapContext);
    }

    public void addLayer(FLayer layer) {

	if (layer instanceof FLayers) {
	    ((FLayers) layer).setMapContext(getMapContext());
	}
	getMapContext().getLayers().addLayer(layer);
    }
}
