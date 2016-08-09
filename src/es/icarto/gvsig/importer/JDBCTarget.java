package es.icarto.gvsig.importer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.icarto.gvsig.commons.db.ConnectionWrapper;
import es.icarto.gvsig.commons.utils.Field;

public abstract class JDBCTarget implements Target {

    // TODO. El campo field se está usando sólo para rellenar el combo de
    // targets en la tabla de reporte. No tengo claro que esto tenga sentido.
    // Seguramente el combo se tiene que rellenar con objetos Target
    // directamente
    protected Field field;

    private final Connection con;

    public JDBCTarget(Connection con) {
	this.con = con;
    }

    @SuppressWarnings("unchecked")
    // TODO. To be moved to another object
    protected void addWarning(DefaultTableModel table, int row, String msg) {
	int errorIdx = table.findColumn("Errores");
	List<String> l = (List<String>) table.getValueAt(row, errorIdx);
	if (l == null) {
	    l = new ArrayList<String>();
	    table.setValueAt(l, row, errorIdx);

	}
	l.add(msg);
    }

    protected boolean existsInProcessed(ImporterTM table, String tablename,
	    String code, int rowToIgnore) {

	for (int row = 0; row < table.getRowCount(); row++) {
	    if (row == rowToIgnore) {
		continue;
	    }
	    Field c = table.getTarget(row);
	    if ((c != null) && c.toString().equalsIgnoreCase(tablename)) {
		if (code.equalsIgnoreCase(table.getCode(row))) {
		    return true;
		}
	    }
	}
	return false;
    }

    protected Entity getParent(ImporterTM table, EntityFactory factory,
	    String code) {

	// TODO. ¿Podría estar esto en EntityFactory
	Entity parent = factory.from("", "0", "0");
	for (int row = 0; row < table.getRowCount(); row++) {
	    Field target = table.getTarget(row);
	    if ((target != null)
		    && target.toString().equalsIgnoreCase(parent.tablename())) {
		final String codCom = table.getCode(row);
		if (code.equalsIgnoreCase(codCom)) {
		    parent.setPK(codCom);
		    parent.setGeom(table.getGeom(row));
		    return parent;
		}
	    }
	}

	return factory.fromDB(code);
    }

    protected boolean existsInDB(String tablename, String fieldname, String code) {

	ConnectionWrapper conW = new ConnectionWrapper(con);
	String query = String.format("SELECT %s FROM %s WHERE %s = '%s'",
		fieldname, tablename, fieldname, code);
	DefaultTableModel result = conW.execute(query);
	return result.getRowCount() > 0;
    }

    protected DefaultTableModel intersects(String tablename, String point,
	    String... fields) {
	JDBCUtils jdbcUtils = new JDBCUtils(con);
	return jdbcUtils.intersects(tablename, point, fields);
    }

    protected DefaultTableModel closest(String tablename, String point,
	    String where, String... fields) {
	JDBCUtils jdbcUtils = new JDBCUtils(con);
	return jdbcUtils.closest(tablename, point, where, fields);
    }

    protected DefaultTableModel closestInTable(String tablename, String point,
	    String where, String... fields) {
	return null;
    }

    protected DefaultTableModel maxCode(String tablename, String codeFieldName,
	    String fkName, String fkValue) {
	ConnectionWrapper conW = new ConnectionWrapper(con);

	String query = String.format(
		"SELECT %s from %s WHERE %s = '%s' ORDER BY %s DESC LIMIT 1;",
		codeFieldName, tablename, fkName, fkValue, codeFieldName);

	DefaultTableModel table = conW.execute(query);
	if (table == null) {
	    throw new RuntimeException("Error desconocido");
	}
	if (table.getRowCount() < 1) {
	    throw new RuntimeException("Sin resultados");
	}
	if (table.getRowCount() > 1) {
	    throw new RuntimeException("Más de un resultado");
	}
	return table;
    }

    protected DefaultTableModel maxCode(String tablename, String codeFieldName,
	    int ncharacters, String fkValue) {
	ConnectionWrapper conW = new ConnectionWrapper(con);

	String where = String.format("WHERE substr(%s, 1, %d) = '%s'",
		codeFieldName, ncharacters, fkValue);
	String query = String.format(
		"SELECT %s from %s %s ORDER BY %s DESC LIMIT 1;",
		codeFieldName, tablename, where, codeFieldName);

	DefaultTableModel table = conW.execute(query);
	if (table == null) {
	    throw new RuntimeException("Error desconocido");
	}
	if (table.getRowCount() < 1) {
	    table = new DefaultTableModel(1, 1);
	    table.setValueAt("", 0, 0);
	}
	if (table.getRowCount() > 1) {
	    throw new RuntimeException("Más de un resultado");
	}
	return table;

    }

    @Override
    public Field getField() {
	return field;
    }

    public abstract String getInsertSQL(String parentCode, String code,
	    String geomAsWKT);
}
