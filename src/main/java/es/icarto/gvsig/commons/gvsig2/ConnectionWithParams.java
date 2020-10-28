package es.icarto.gvsig.commons.gvsig2;

import java.sql.Connection;
import java.sql.SQLException;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataServerExplorerParameters;
import org.gvsig.fmap.dal.exception.InitializeException;
import org.gvsig.fmap.dal.exception.ProviderNotRegisteredException;
import org.gvsig.fmap.dal.resource.Resource;
import org.gvsig.fmap.dal.resource.ResourceAction;
import org.gvsig.fmap.dal.resource.exception.AccessResourceException;
import org.gvsig.fmap.dal.resource.exception.ResourceExecuteException;
import org.gvsig.fmap.dal.resource.spi.ResourceConsumer;
import org.gvsig.fmap.dal.resource.spi.ResourceProvider;
import org.gvsig.fmap.dal.spi.DataManagerProviderServices;
import org.gvsig.fmap.dal.store.jdbc.JDBCResource;
import org.gvsig.fmap.dal.store.jdbc.JDBCServerExplorerParameters;
import org.gvsig.fmap.dal.store.jdbc.JDBCStoreParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionWithParams implements ResourceConsumer {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionWithParams.class);

	private final DataServerExplorerParameters explorerParams;
	private final String storeProviderName;
	private final JDBCResource resource;
	private final String name;

	private final Connection con;

	public ConnectionWithParams(DataServerExplorerParameters params, JDBCResource resource, String storeProviderName,
			String conName) {
		this.explorerParams = params;
		this.storeProviderName = storeProviderName;
		this.resource = resource;
		this.resource.addConsumer(this);
		this.name = conName;
		this.con = null;
	}

	public ConnectionWithParams(JDBCServerExplorerParameters serverExplorerParams, Connection con,
			String storeProviderName, String conName) {
		this.explorerParams = serverExplorerParams;
		this.storeProviderName = storeProviderName;
		this.con = con;
//		this.resource.addConsumer(this);
		this.name = conName;
		this.resource = null;
	}

	public String getName() {
		return name;
	}

	public Connection getConnection() {
		return con;
//		try {
//			return resource.getJDBCConnection();
//		} catch (AccessResourceException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return null;
	}

	public DataServerExplorerParameters getExplorerParams() {
		return explorerParams;
	}

	// Taken from JDBCServerExplorer.createStoreParams that can not be used as is a
	// protected method
	public JDBCStoreParameters getStoreParams() throws InitializeException, ProviderNotRegisteredException {
		DataManagerProviderServices manager = (DataManagerProviderServices) DALLocator.getDataManager();

		JDBCServerExplorerParameters parameters = (JDBCServerExplorerParameters) explorerParams;
		JDBCStoreParameters orgParams = (JDBCStoreParameters) manager.createStoreParameters(storeProviderName);
		orgParams.setHost(parameters.getHost());
		orgParams.setPort(parameters.getPort());
		orgParams.setDBName(parameters.getDBName());
		orgParams.setUser(parameters.getUser());
		orgParams.setPassword(parameters.getPassword());
		orgParams.setCatalog(parameters.getCatalog());
		orgParams.setJDBCDriverClassName(parameters.getJDBCDriverClassName());
		orgParams.setSchema(parameters.getSchema());
		orgParams.setUrl(parameters.getUrl());
		return orgParams;
	}

	public void closeResource() {
		try {
			resource.execute(new ResourceAction() {
				public Object run() throws Exception {
					resource.notifyClose();
					return null;
				}
			});
			resource.getJDBCConnection().close();
			resource.removeConsumer(this);
		} catch (ResourceExecuteException e) {
			logger.error(e.getMessage(), e);
		} catch (AccessResourceException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void close(Connection con) {
//		resource.closeConnection(con);
	}

	@Override
	public boolean closeResourceRequested(ResourceProvider resource) {
		return false;
	}

	@Override
	public void resourceChanged(ResourceProvider resource) {
	}

	public Resource getResource() {
		return resource;
	}

}
