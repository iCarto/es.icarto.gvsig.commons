package es.icarto.gvsig.commons.gui.tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ValidatableNotEditableRenderer extends DefaultTableCellRenderer {
    private final CellValidator validator;

    private final Color notEditableFgColor = Color.gray;
    private final Color notEditableFgColorSelected = Color.lightGray;
    public static final Color INVALID_COLOR = new Color(249, 112, 140);

    public ValidatableNotEditableRenderer() {
	super();
	this.validator = new CellValidator() {
	    @Override
	    public boolean isValid(int row, int column) {
		return true;
	    }
	};
    }

    public ValidatableNotEditableRenderer(CellValidator validator) {
	super();
	this.validator = validator;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
	// Forces to recalculate the default background and foregrounds value of
	// the cells
	setBackground(null);
	setForeground(null);

	super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		row, column);

	if (!validator.isValid(row, column)) {
	    setBackground(INVALID_COLOR);
	} else if (!table.isCellEditable(row, column)) {
	    // default font is always recalculated in super
	    setFont(getFont().deriveFont(Font.ITALIC));
	    if (isSelected) {
		setForeground(notEditableFgColorSelected);
	    } else {
		setForeground(notEditableFgColor);
	    }
	}

	return this;
    }
}