package es.icarto.gvsig.importer;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.utils.Field;

@SuppressWarnings("serial")
public class ImporterTM extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
	int tablenameIdx = findColumn("Capa destino");
	int codeIdx = findColumn("Código");
	if ((column == tablenameIdx) || (column == codeIdx)) {
	    return true;
	}
	return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	int geomIdx = findColumn("Geometría destino");
	int orgGeomIdx = findColumn("orggeom");
	if ((geomIdx == columnIndex) || (orgGeomIdx == columnIndex)) {
	    return IGeometry.class;
	}
	return super.getColumnClass(columnIndex);
    }

    public void setCode(Object aValue, int row) {
	super.setValueAt(aValue, row, 0);
    }

    public String getCode(int row) {
	Object code = super.getValueAt(row, 0);
	return code != null ? code.toString() : "";
    }

    public void setTarget(Object aValue, int row) {
	int tablenameIdx = findColumn("Capa destino");
	super.setValueAt(aValue, row, tablenameIdx);
    }

    public Field getTarget(int row) {
	int tablenameIdx = findColumn("Capa destino");
	return (Field) super.getValueAt(row, tablenameIdx);
    }

    public void setGeom(IGeometry aValue, int row) {
	int geomIdx = findColumn("Geometría destino");
	super.setValueAt(aValue, row, geomIdx);
    }

    public IGeometry getGeom(int row) {
	int geomIdx = findColumn("Geometría destino");
	return (IGeometry) getValueAt(row, geomIdx);
    }

    public void setError(List<ImportError> l, int row) {
	int errorsIdx = findColumn("Errores");
	super.setValueAt(l, row, errorsIdx);
    }

    public List<ImportError> getError(int row) {
	int errorsIdx = findColumn("Errores");
	return (List<ImportError>) super.getValueAt(row, errorsIdx);
    }

    public void reCheckErrors() {
	for (int i = 0; i < this.getRowCount(); i++) {
	    Target target = (Target) getTarget(i).getValue();
	    target.checkErrors(this, i);
	}
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
	super.setValueAt(aValue, row, column);
	int tablenameIdx = findColumn("Capa destino");
	if (column == tablenameIdx) {
	    if (aValue == null) {
		return;
	    }
	    Target target = (Target) ((Field) aValue).getValue();
	    String code = target.calculateCode(this, row);
	    setCode(code, row);
	    target.checkErrors(this, row);
	}

	int codeIdx = findColumn("Código");
	if (column == codeIdx) {
	    Field field = getTarget(row);
	    Target target = (Target) field.getValue();
	    target.checkErrors(this, row);
	}
    }

    /**
     * Excludes the row "row"
     */
    public String maxCodeValueForTarget(Field field, int row) {
	int codeIdx = 0;
	String maxValue = null;
	for (int i = 0; i < getRowCount(); i++) {
	    if (i == row) {
		continue;
	    }
	    Object t = getTarget(row);
	    if ((t != null) && t.equals(field)) {
		Object value = getValueAt(i, codeIdx);
		if (value == null) {
		    continue;
		}
		if (maxValue == null) {
		    maxValue = value.toString();
		} else {
		    if (value.toString().compareTo(maxValue) > 0) {
			maxValue = value.toString();
		    }
		}
	    }
	}
	return maxValue == null ? "" : maxValue;
    }

}
