package es.icarto.gvsig.importer;

import java.awt.Component;

import javax.swing.JTable;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.gui.tables.ValidatableNotEditableRenderer;

@SuppressWarnings("serial")
public class GeometryRenderer extends ValidatableNotEditableRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {

	IGeometry geom = (IGeometry) value;
	String geomToShow = geom.toJTSGeometry().toText();
	return super.getTableCellRendererComponent(table, geomToShow,
		isSelected, hasFocus, row, column);
    }

}
