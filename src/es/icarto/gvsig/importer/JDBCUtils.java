package es.icarto.gvsig.importer;

import java.sql.Connection;

import javax.swing.table.DefaultTableModel;

import es.icarto.gvsig.commons.db.ConnectionWrapper;
import es.icarto.gvsig.commons.utils.StrUtils;

public class JDBCUtils {

    private final Connection con;

    public JDBCUtils(Connection con) {
	this.con = con;
    }

    public DefaultTableModel intersects(String tablename, String point,
	    String... fields) {
	ConnectionWrapper conW = new ConnectionWrapper(con);
	String fieldStr = StrUtils.join(", ", (Object[]) fields);
	String query = String.format(
		"SELECT %s FROM %s WHERE ST_Intersects(geom, %s)", fieldStr,
		tablename, point);
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

    public DefaultTableModel closest(String tablename, String point,
	    String where, String... fields) {
	where = where == null ? "" : where;
	ConnectionWrapper conW = new ConnectionWrapper(con);
	String fieldStr = StrUtils.join(", ", (Object[]) fields);

	String query = String
		.format("SELECT %s,  ST_Distance(%s, geom) from %s %s ORDER BY ST_Distance(%s, geom) LIMIT 1;",
			fieldStr, point, tablename, where, point);

	DefaultTableModel table = conW.execute(query);
	if (table == null) {
	    throw new RuntimeException("Error desconocido");
	}
	return table;
    }

}
