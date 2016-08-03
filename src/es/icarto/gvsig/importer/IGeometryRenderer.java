package es.icarto.gvsig.importer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.iver.cit.gvsig.fmap.core.IGeometry;

@SuppressWarnings("serial")
public class IGeometryRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {

	IGeometry geom = (IGeometry) value;
	String geomToShow = geom.toJTSGeometry().toText();
	return super.getTableCellRendererComponent(table, geomToShow,
		isSelected, hasFocus, row, column);
    }

}
