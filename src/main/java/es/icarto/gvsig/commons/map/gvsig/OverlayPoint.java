package es.icarto.gvsig.commons.map.gvsig;

import java.awt.Color;

import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.Geometry.SUBTYPES;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.GeometryManager;
import org.gvsig.fmap.geom.exception.CreateGeometryException;
import org.gvsig.fmap.mapcontext.layers.vectorial.GraphicLayer;
import org.gvsig.symbology.SymbologyLocator;
import org.gvsig.symbology.SymbologyManager;
import org.gvsig.symbology.fmap.mapcontext.rendering.symbol.marker.ISimpleMarkerSymbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverlayPoint {
	private static final Logger logger = LoggerFactory.getLogger(Map.class);
	private final Map map;
	private final GraphicLayer lyr;
	private Color color = Color.red;
	private double size = 4;
	private final String groupId;

	public OverlayPoint(Map mapControl) {
		this.map = mapControl;
		lyr = map.getGraphicsLayer();
		groupId = this.getClass().getName();
	}

	public void draw(double x, double y) {
		drawPoint(x, y);
	}

	public void redrawGraphicLayer() {
		// Diría que esto hace falta si se pone el punto pero no se mueve el
		// mapa. En el momento en que se mueve el mapa ya se fuerza el
		// repintado de la capa
		map.drawMap(false);
	}

	private void drawPoint(double x, double y) {
		lyr.clearAllGraphics();
		SymbologyManager symbologyManager = SymbologyLocator.getSymbologyManager();
		ISimpleMarkerSymbol symbol = symbologyManager.createSimpleMarkerSymbol();
		symbol.setColor(color);
		symbol.setSize(size);

		int idSymbol = lyr.addSymbol(symbol);
		GeometryManager geometryManager = GeometryLocator.getGeometryManager();
		Geometry geom;
		try {
			geom = geometryManager.createPoint(x, y, SUBTYPES.GEOM2D);
			lyr.addGraphic(groupId, geom, idSymbol);
		} catch (CreateGeometryException e) {
			logger.error("Error creating point", e);
		}
	}

	public void clearGraphics() {
		lyr.removeGraphics(groupId);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setSize(double size) {
		this.size = size;
	}

}
