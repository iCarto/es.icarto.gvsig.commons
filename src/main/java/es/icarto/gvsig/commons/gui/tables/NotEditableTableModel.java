package es.icarto.gvsig.commons.gui.tables;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class NotEditableTableModel extends DefaultTableModel {
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}