package es.icarto.gvsig.commons.map;

import java.awt.Color;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.core.IGeometry;
import com.iver.cit.gvsig.fmap.core.ShapeFactory;
import com.iver.cit.gvsig.fmap.core.SymbologyFactory;
import com.iver.cit.gvsig.fmap.core.symbols.IMarkerSymbol;
import com.iver.cit.gvsig.fmap.layers.GraphicLayer;
import com.iver.cit.gvsig.fmap.rendering.FGraphic;

public class OverlayPoint {

    private final MapControl mapControl;
    private final GraphicLayer lyr;
    private Color color = Color.red;
    private double size = 4;

    public OverlayPoint(MapControl mapControl) {
	this.mapControl = mapControl;
	lyr = mapControl.getMapContext().getGraphicsLayer();
    }

    public void draw(double x, double y) {
	drawPoint(x, y);
    }

    public void redrawGraphicLayer() {
	// Diría que esto hace falta si se pone el punto pero no se mueve el
	// mapa. En el momento en que se mueve el mapa ya se fuerza el
	// repintado de la capa
	mapControl.drawGraphics();
    }

    private void drawPoint(double x, double y) {
	lyr.clearAllGraphics();
	IMarkerSymbol symbol = SymbologyFactory.createDefaultMarkerSymbol();
	symbol.setColor(color);
	symbol.setSize(size);

	int idSymbol = lyr.addSymbol(symbol);
	IGeometry geom = ShapeFactory.createPoint2D(x, y);
	FGraphic theGraphic = new FGraphic(geom, idSymbol);
	lyr.addGraphic(theGraphic);
    }

    public void clearAllGraphics() {
	/**
	 * Este método se crea por compatibilidad con el código existinte de
	 * InputCoordinatesPanel y ZoomTo en el que siempre se borran todos los
	 * gráficos. Probablemente lo lógico sería únicamente eliminar el creado
	 */
	lyr.clearAllGraphics();
    }

    public void setColor(Color color) {
	this.color = color;
    }

    public void setSize(double size) {
	this.size = size;
    }

}
