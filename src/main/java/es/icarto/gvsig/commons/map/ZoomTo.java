package es.icarto.gvsig.commons.map;

import es.icarto.gvsig.commons.geometries.MyEnvelope;
import es.icarto.gvsig.commons.map.gvsig.Map;
import es.icarto.gvsig.commons.map.gvsig.OverlayPoint;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;

public class ZoomTo {

	private final Map map;
	private final OverlayPoint overlayPoint;

	public ZoomTo(Map map) {
		this.map = map;
		this.overlayPoint = new OverlayPoint(map);
		this.overlayPoint.setSize(8);
	}

	public void zoom(GPoint point, boolean drawPoint) {
		if (point == null) {
			return;
		}

		GPoint reShape = new GPoint(point);
		reShape.reProject(map.getProjection());

		MyEnvelope envelope = reShape.getEnvelope();
		MyEnvelope bbox = getGeometry(envelope);

		if (drawPoint) {
			overlayPoint.draw(reShape.getX(), reShape.getY());
		}
		map.setExtent(bbox);

	}

	public void zoom(MyEnvelope rectangle) {
		if (rectangle == null) {
			return;
		}
		MyEnvelope bbox = getGeometry(rectangle);
		if (bbox != null) {
			map.setExtent(bbox);
		}
	}

	private MyEnvelope getGeometry(MyEnvelope envelope) {
		return envelope.toZoomable();
	}

	public void clearGraphics() {
		this.overlayPoint.clearGraphics();
	}
}
