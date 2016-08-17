package es.icarto.gvsig.importer;

import java.sql.Connection;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.iver.cit.gvsig.fmap.core.IGeometry;

import es.icarto.gvsig.commons.db.ConnectionWrapper;

public class EntityFactory<T extends Entity> {

    private static final Logger logger = Logger.getLogger(EntityFactory.class);

    private final Connection con;

    private final Class<T> clazz;

    public EntityFactory(Connection con, Class<T> clazz) {
	this.con = con;
	this.clazz = clazz;
    }

    public T thatIntersectsWith(String pointStr) {
	T entity = getInstance();

	JDBCUtils jdbcUtils = new JDBCUtils(con);
	DefaultTableModel result = jdbcUtils.intersects(entity.tablename(),
		pointStr, entity.pkname(), entity.descname());
	if ((result == null) || (result.getRowCount() < 1)) {
	    return null;
	}
	String pk = result.getValueAt(0, 0).toString();
	String desc = result.getValueAt(0, 1).toString();
	entity.setPK(pk);
	entity.setDesc(desc);
	return entity;
    }

    private T getInstance() {
	T entity = null;
	try {
	    entity = clazz.newInstance();
	} catch (InstantiationException e) {
	    logger.error(e.getStackTrace(), e);
	} catch (IllegalAccessException e) {
	    logger.error(e.getStackTrace(), e);
	}
	return entity;
    }

    public T from(String pk, IGeometry geom) {
	T entity = getInstance();
	entity.setPK(pk);
	entity.setGeom(geom);
	return entity;
    }

    public T from(String pk, String xStr, String yStr) {
	T entity = getInstance();
	entity.setPK(pk);
	entity.setGeom(xStr, yStr);
	return entity;
    }

    /**
     * Retrieves the Entity with pk = code from the database or null if not
     * exists an Entity with that pk
     */
    public T fromDB(String pk) {
	T entity = getInstance();

	ConnectionWrapper conW = new ConnectionWrapper(con);
	final String whereClause = String.format("WHERE %s = '%s'",
		entity.pkname(), pk);
	String sql = String.format(
		"SELECT %s, st_x(geom), st_y(geom) FROM %s %s",
		entity.pkname(), entity.tablename(), whereClause);
	DefaultTableModel r = conW.execute(sql);
	if (r.getRowCount() > 0) {
	    entity.setPK(r.getValueAt(0, 0).toString());
	    final String xStr = r.getValueAt(0, 1).toString();
	    final String yStr = r.getValueAt(0, 2).toString();
	    entity.setGeom(xStr, yStr);
	    return entity;
	}
	return null;
    }

    public T closestTo(String pointStr, Entity region) {
	T entity = getInstance();
	JDBCUtils jdbcUtils = new JDBCUtils(con);
	String closestWhere = String.format(" WHERE substr(%s, 1, 6) = '%s'",
		entity.pkname(), region.getPK());
	DefaultTableModel closest = jdbcUtils.closest(entity.tablename(),
		pointStr, closestWhere, entity.pkname(), entity.descname(),
		"st_x(geom)", "st_y(geom)");

	if (closest.getRowCount() == 1) {
	    String pk = closest.getValueAt(0, 0).toString();
	    String desc = closest.getValueAt(0, 1).toString();
	    String xStr = closest.getValueAt(0, 2).toString();
	    String yStr = closest.getValueAt(0, 3).toString();
	    entity.setPK(pk);
	    entity.setDesc(desc);
	    entity.setGeom(xStr, yStr);
	    return entity;
	}
	return null;
    }
}
