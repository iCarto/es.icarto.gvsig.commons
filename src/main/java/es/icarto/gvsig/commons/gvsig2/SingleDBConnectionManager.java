package es.icarto.gvsig.commons.gvsig2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataParameters;
import org.gvsig.fmap.dal.DataServerExplorerParameters;
import org.gvsig.fmap.dal.DataServerExplorerPool;
import org.gvsig.fmap.dal.DataServerExplorerPoolEntry;
import org.gvsig.fmap.dal.exception.DataException;
import org.gvsig.fmap.dal.resource.ResourceParameters;
import org.gvsig.fmap.dal.resource.db.DBParameters;
import org.gvsig.fmap.dal.resource.spi.ResourceManagerProviderServices;
import org.gvsig.fmap.dal.resource.spi.ResourceProvider;
import org.gvsig.fmap.dal.store.jdbc.JDBCResource;
import org.gvsig.fmap.dal.store.jdbc.JDBCResourceParameters;
import org.gvsig.fmap.dal.store.jdbc.JDBCServerExplorerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleDBConnectionManager {

	private final Map<String, ConnectionWithParams> list = new HashMap<String, ConnectionWithParams>();
	
	private static final Logger logger = LoggerFactory
			.getLogger(SingleDBConnectionManager.class);
	
	private final static SingleDBConnectionManager ins = new SingleDBConnectionManager();
	
	private SingleDBConnectionManager() {
	}
	
	
	public static SingleDBConnectionManager instance() {
		return ins;
	}


	public void closeAndRemove(ConnectionWithParams conwp) {
		ConnectionWithParams cwp = list.get(conwp.getName());
		if (cwp != null) {
			ResourceManagerProviderServices manager = (ResourceManagerProviderServices) DALLocator.getResourceManager();
			try {
				manager.remove(cwp.getResource());
			} catch (DataException e) {
				logger.error(e.getMessage(), e);
			}
			cwp.closeResource();
			
			DataManager dataManager = DALLocator.getDataManager();
			DataServerExplorerPool pool = dataManager.getDataServerExplorerPool();
			pool.remove(conwp.getName());
		}
	}


	/**
	 * El identificador de conexiones es "conName" a todos los efectos
	 * Crea un nuevo DataServerExplorerParameters al pool si es necesario (Para usar en el panel de conexión a base de datos)
	 */
	public ConnectionWithParams getConnection(String storeProviderName, String serverExplorerName, String resourceName,
			String username, String password, String conName, String server,
			int port, String database, String schema, boolean connect) throws DataException {
		
		ConnectionWithParams cwp = list.get(conName);
		if (cwp != null) {
			return cwp;
		}
		JDBCServerExplorerParameters serverExplorerParams = createServerExplorerParams(conName, serverExplorerName, server, port, database, username, password, schema);
		// JDBCResource resource = createResource(serverExplorerParams, resourceName);
		Connection con = createConnection(serverExplorerParams);
				
//		return new ConnectionWithParams(serverExplorerParams, resource, storeProviderName, conName);
		return new ConnectionWithParams(serverExplorerParams, con, storeProviderName, conName);
	}
	
	private Connection createConnection(
			JDBCServerExplorerParameters serverExplorerParams) {
		try {
			Class klass = Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			logger.error(e1.getMessage(), e1);
		}
		String host = serverExplorerParams.getHost();
		Integer port = serverExplorerParams.getPort();
		String db = serverExplorerParams.getDBName();
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, serverExplorerParams.getUser(), serverExplorerParams.getPassword());
			con.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return con;
	}


	private JDBCServerExplorerParameters createServerExplorerParams(String conName, String serverExplorerName, String server,
			int port, String database, String username, String password,
			String schema) throws DataException {
		DataManager dataManager = DALLocator.getDataManager();
		
				
		DataServerExplorerPool pool = dataManager.getDataServerExplorerPool();
		// TODO. El pool persiste el nombre de conexión empleado. Por tanto sin con dbconnection
		// nos estamos conectando a una base de datos distinta, al pasar por aquí recuperará datos
		// erróneos
		// DataServerExplorerPoolEntry entry = pool.get(conName);
		DataServerExplorerPoolEntry entry = null;
		
		DataServerExplorerParameters params = null;
		if (entry != null) {
			 params = entry.getExplorerParameters();
		}
		if (params != null) {
			return (JDBCServerExplorerParameters) params;
		} else {
			params = dataManager.createServerExplorerParameters(serverExplorerName);
			params.setDynValue(DBParameters.HOST_PARAMTER_NAME, server);
			params.setDynValue(DBParameters.PORT_PARAMTER_NAME, port);
			params.setDynValue(DBParameters.DBNAME_PARAMTER_NAME, database);
			params.setDynValue(DBParameters.USER_PARAMTER_NAME, username);
			params.setDynValue(DBParameters.PASSWORD_PARAMTER_NAME, password);
			if ((schema != null) && (!schema.isEmpty())) {
				params.setDynValue("schema", schema);			
			}
			pool.add(conName, params);
		}
		return (JDBCServerExplorerParameters) params;
	}


	private JDBCResource createResource(JDBCServerExplorerParameters params, String resourceName) throws DataException {
		
//		Class klass = null;
//		try {
//			klass = Class.forName("org.postgresql.Driver");
//		} catch (ClassNotFoundException e) {
//			logger.error(e.getMessage(), e);
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//		}
//		if (klass == null) {
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//			System.out.println("*************************** NOT FOUND ********************");
//		}

		ResourceManagerProviderServices manager = (ResourceManagerProviderServices) DALLocator.getResourceManager();
		ResourceParameters resourceParams = (ResourceParameters) manager.createParameters(resourceName);
//		resourceParams.setDynValue(JDBCResourceParameters.URL_PARAMTER_NAME, params.getUrl());
		resourceParams.setDynValue(JDBCResourceParameters.HOST_PARAMTER_NAME, params.getHost());
		resourceParams.setDynValue(JDBCResourceParameters.PORT_PARAMTER_NAME, params.getPort());
		resourceParams.setDynValue(JDBCResourceParameters.DBNAME_PARAMTER_NAME, params.getDBName());
		resourceParams.setDynValue(JDBCResourceParameters.USER_PARAMTER_NAME, params.getUser());
		resourceParams.setDynValue(JDBCResourceParameters.PASSWORD_PARAMTER_NAME, params.getPassword());
		resourceParams.setDynValue(JDBCResourceParameters.JDBC_DRIVER_CLASS_PARAMTER_NAME, params.getJDBCDriverClassName());
		ResourceProvider resource = manager.createAddResource(resourceParams);
		
		return (JDBCResource) resource;
		
	}

}
