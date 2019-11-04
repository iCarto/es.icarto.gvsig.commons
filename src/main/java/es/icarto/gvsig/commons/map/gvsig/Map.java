package es.icarto.gvsig.commons.map.gvsig;

import org.cresques.cts.IProjection;
import org.gvsig.fmap.mapcontext.ViewPort;
import org.gvsig.fmap.mapcontext.layers.vectorial.GraphicLayer;
import org.gvsig.fmap.mapcontrol.MapControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.icarto.gvsig.commons.geometries.MyEnvelope;

public class Map {

	private static final Logger logger = LoggerFactory.getLogger(Map.class);

	private final MapControl mapControl;

	public Map(MapControl mapControl) {
		this.mapControl = mapControl;
	}

	public IProjection getProjection() {
		return mapControl.getProjection();
	}

	/**
	 * Sets the extend/bounds/envolope of the Map (View, ViewpPort, MapControl)
	 *
	 * Usage example. From a Geometry: Feature f; Geometry g =
	 * f.getDefaultGeometry(); Envelope env = g.getEnvelope(); setExtend(env)
	 *
	 * Usage example. To cover all the l ayers MapContext mapa =
	 * document.getMapContext(); Envelope env = mapa.getFullEnvelope();
	 * setExtend(env)
	 *
	 * To the selected bounds (with the mouse): MapContext mapa =
	 * document.getMapContext(); Envelope env = = mapa.getSelectionBounds();
	 * setExtend(env)
	 */
	public void setExtent(MyEnvelope env) {
		ViewPort viewPort = mapControl.getViewPort();
		viewPort.setEnvelope(env.getEnvelope());
		// Already called from setEnvelope
		// viewPort.refreshExtent();
	}

	public void drawMap(boolean b) {
		mapControl.drawMap(b);
	}

	public GraphicLayer getGraphicsLayer() {
		return mapControl.getMapContext().getGraphicsLayer();
	}

}
