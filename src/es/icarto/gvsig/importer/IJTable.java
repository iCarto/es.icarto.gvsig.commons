package es.icarto.gvsig.importer;

import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.gui.tables.ValidatableNotEditableRenderer;
import es.icarto.gvsig.commons.utils.Field;

@SuppressWarnings("serial")
public class IJTable extends JTable {

    private final Ruler ruler;

    public IJTable(DefaultTableModel tableModel, Ruler ruler) {
	super(tableModel);
	this.ruler = ruler;
	// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	this.getTableHeader().setReorderingAllowed(false);
	this.setPreferredScrollableViewportSize(this.getPreferredSize());
	this.setFillsViewportHeight(true);
	initTargetColumn();
	initRenderers();
    }

    public void setTotalWidth(int totalWidth) {
	autoFit(totalWidth);
	this.setPreferredScrollableViewportSize(this.getPreferredSize());
	this.setFillsViewportHeight(true);
    }

    private void initRenderers() {
	this.setDefaultRenderer(IGeometry.class, new GeometryRenderer());
	this.setDefaultRenderer(Object.class,
		new ValidatableNotEditableRenderer());
    }

    private void autoFit(int avaliable) {
	// TODO
	// This need to be reviewed
	// http://stackoverflow.com/a/8478299/930271
	// https://tips4java.wordpress.com/2008/11/10/table-column-adjuster/
	// http://www.java2s.com/Code/Java/Swing-Components/CalculatedColumnTable.htm
	// http://stackoverflow.com/questions/18378506/set-a-column-width-of-jtable-according-to-the-text-in-a-header
	this.repaint();
	if (avaliable <= 0) {
	    avaliable = this.getColumnModel().getTotalColumnWidth();
	}

	int[] maxLengths = getMaxLengths();
	double needed = 0.0;
	for (int i = 0; i < maxLengths.length; i++) {
	    needed += maxLengths[i];
	}

	for (int i = 0; i < this.getModel().getColumnCount(); i++) {
	    double preferredWidth = avaliable * (maxLengths[i] / needed);
	    this.getColumnModel().getColumn(i)
		    .setPreferredWidth((int) preferredWidth);
	}
    }

    private int[] getMaxLengths() {
	int[] maxLengths = getMinLengths();

	for (int i = 0; i < this.getRowCount(); i++) {
	    for (int j = 0; j < this.getColumnCount(); j++) {
		final Object o = this.getValueAt(i, j);
		int l = (o == null) ? 0 : o.toString().length();
		if (l > maxLengths[j]) {
		    maxLengths[j] = l;
		}
	    }
	}
	for (int i = 0; i < maxLengths.length; i++) {
	    maxLengths[i] = maxLengths[i] + 2;
	}
	return maxLengths;
    }

    private int[] getMinLengths() {
	int[] minLengths = new int[this.getColumnCount()];
	for (int i = 0; i < this.getColumnCount(); i++) {
	    minLengths[i] = this.getColumnName(i).length();
	}
	return minLengths;
    }

    private void initTargetColumn() {
	List<Field> targets = ruler.getFields();
	JComboBox combo = new JComboBox(targets.toArray(new Field[0]));
	setComboCellEditor("Capa destino", combo);
    }

    private void setComboCellEditor(String columnName, JComboBox combo) {
	TableColumn column = myFindColumn(columnName);
	column.setCellEditor(new DefaultCellEditor(combo));
    }

    private TableColumn myFindColumn(String name) {
	Enumeration<TableColumn> columns = this.getColumnModel().getColumns();
	while (columns.hasMoreElements()) {
	    TableColumn column = columns.nextElement();
	    if (column.getHeaderValue().toString().equals(name)) {
		return column;
	    }
	}
	return null;
    }
}
