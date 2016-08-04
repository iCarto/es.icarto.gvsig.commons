package es.icarto.gvsig.importer;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.utils.Field;

@SuppressWarnings("serial")
public class IJTable extends JTable {

    private final Ruler ruler;

    public IJTable(DefaultTableModel tableModel, Ruler ruler) {
	super(tableModel);
	this.ruler = ruler;
	// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	this.getTableHeader().setReorderingAllowed(false);
	autoFit();
	this.setPreferredScrollableViewportSize(this.getPreferredSize());
	this.setFillsViewportHeight(true);
	initTargetColumn();
	initRenderers();
    }

    private void initRenderers() {
	this.setDefaultRenderer(IGeometry.class, new IGeometryRenderer());
    }

    private void autoFit() {
	// TODO. Review:
	// http://stackoverflow.com/a/8478299/930271
	// https://tips4java.wordpress.com/2008/11/10/table-column-adjuster/
	// http://www.java2s.com/Code/Java/Swing-Components/CalculatedColumnTable.htm
	this.repaint();
	int avaliable = this.getColumnModel().getTotalColumnWidth();

	int[] maxLengths = getMaxLengths();
	double needed = 0.0;
	for (int i = 0; i < this.getColumnCount(); i++) {
	    int m = (this.getColumnName(i).length() > maxLengths[i]) ? this
		    .getColumnName(i).length() : maxLengths[i];
		    needed += m;
	}

	for (int i = 0; i < this.getModel().getColumnCount(); i++) {
	    double preferredWidth = avaliable * (maxLengths[i] / needed);

	    preferredWidth = 150;
	    this.getColumnModel().getColumn(i)
		    .setPreferredWidth((int) preferredWidth);
	}
    }

    public int[] getMaxLengths() {
	int[] maxLengths = new int[this.getColumnCount()];
	Arrays.fill(maxLengths, 50);

	for (int i = 0; i < this.getRowCount(); i++) {
	    for (int j = 0; j < this.getColumnCount(); j++) {
		final Object o = this.getValueAt(i, j);
		int l = (o == null) ? 0 : o.toString().length();
		if (l > maxLengths[j]) {
		    maxLengths[j] = l;
		}
	    }
	}
	return maxLengths;
    }

    private void initTargetColumn() {
	List<Field> targets = ruler.getFields();
	JComboBox combo = new JComboBox(targets.toArray(new Field[0]));
	setComboCellEditor("tablename", combo);
    }

    // private void foo() {
    // DomainValues unidadValues = ormLite.getAppDomain()
    // .getDomainValuesForComponent(DBFieldNames.UNIDAD);
    // JComboBox unidadComboBox = new JComboBox();
    // for (KeyValue value : unidadValues.getValues()) {
    // unidadComboBox.addItem(value.toString());
    // }
    // }

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
