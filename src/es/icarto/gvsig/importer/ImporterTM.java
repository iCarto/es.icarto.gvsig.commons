package es.icarto.gvsig.importer;

import java.sql.Connection;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.db.ConnectionWrapper;
import es.icarto.gvsig.commons.utils.Field;

@SuppressWarnings("serial")
public class ImporterTM extends DefaultTableModel {

    private final Connection con;

    // Esto está aquí sólo por el setComunidadFromCode
    public ImporterTM(Connection con) {
	this.con = con;
    }

    // Esto está aquí sólo por el setComunidadFromCode
    private String getComunidadCode(String codCom) {
	ConnectionWrapper conW = new ConnectionWrapper(con);

	String sql_tmp = "SELECT nombre FROM comunidades WHERE cod_com = '%s'";
	String sql = String.format(sql_tmp, codCom);
	DefaultTableModel r = conW.execute(sql);
	String text = "<html>Nombre comunidad: ";
	if ((r.getRowCount() > 0) && (r.getValueAt(0, 0) != null)) {
	    String name = r.getValueAt(0, 0).toString();
	    name = name.trim().isEmpty() ? codCom : name;
	    text += name;
	} else {
	    text += "-";
	}

	sql_tmp = "SELECT caserio FROM caserios_comunidades WHERE cod_caseri = '%s'";
	sql = String.format(sql_tmp, codCom);
	r = conW.execute(sql);
	text += "<br>Nombre caserío: ";
	if ((r.getRowCount() > 0) && (r.getValueAt(0, 0) != null)) {
	    text += r.getValueAt(0, 0).toString();
	} else {
	    text += "-";
	}

	return text + "</html>";
    }

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
	int idx = findColumn("Código");
	String code = aValue == null ? "" : aValue.toString().toUpperCase();
	super.setValueAt(code, row, idx);
	setComunidadFromCode(code, row);
    }

    public String getCode(int row) {
	int idx = findColumn("Código");
	Object code = super.getValueAt(row, idx);
	return code != null ? code.toString() : "";
    }

    // Esto debería ser parametrizable
    private void setComunidadFromCode(String code, int row) {
	int idx = findColumn("Comunidad");
	String nombreComunidad = "";
	if (code.length() >= 8) {
	    String codComunidad = code.substring(0, 8);
	    nombreComunidad = getComunidadCode(codComunidad);
	}
	super.setValueAt(nombreComunidad, row, idx);
    }

    public void setID(Object aValue, int row) {
	int idx = findColumn("id");
	String id = aValue == null ? "" : aValue.toString().toUpperCase();
	super.setValueAt(id, row, idx);
    }

    public String getID(int row) {
	int idx = findColumn("id");
	Object id = super.getValueAt(row, idx);
	return id != null ? id.toString() : "";
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
	final IGeometry geom = (IGeometry) getValueAt(row, geomIdx);
	return geom.cloneGeometry();
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

	int codeIdx = findColumn("Código");
	if (column == codeIdx) {
	    setCode(aValue, row);
	    reCheckErrors();
	    return;
	}

	super.setValueAt(aValue, row, column);

	int tablenameIdx = findColumn("Capa destino");
	if (column == tablenameIdx) {
	    if (aValue == null) {
		return;
	    }
	    Target target = (Target) ((Field) aValue).getValue();
	    String code = target.calculateCode(this, row);
	    setCode(code, row);
	    reCheckErrors();
	}

    }

    /**
     * Excludes the row "row"
     */
    public String maxCodeValueForTarget(Field field, int row, String prefix) {
	String maxValue = null;
	for (int i = 0; i < getRowCount(); i++) {
	    if (i == row) {
		continue;
	    }
	    Field t = getTarget(i);
	    if (t.equals(field)) {
		String value = getCode(i);
		if ((value == null) || (!value.toString().startsWith(prefix))) {
		    continue;
		}
		if (maxValue == null) {
		    maxValue = value.toString();
		} else {
		    if (value.toString().compareToIgnoreCase(maxValue) > 0) {
			maxValue = value.toString();
		    }
		}
	    }
	}
	return maxValue == null ? "" : maxValue;
    }
}
