package es.icarto.gvsig.commons.datasources;

import java.io.File;

import org.cresques.cts.IProjection;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStoreParameters;
import org.gvsig.fmap.dal.exception.InitializeException;
import org.gvsig.fmap.dal.exception.ProviderNotRegisteredException;
import org.gvsig.fmap.dal.exception.ValidateDataParametersException;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.mapcontext.exceptions.LoadLayerException;
import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GPXFactory {
	
	private static final Logger logger = LoggerFactory
			.getLogger(GPXFactory.class);

	public static FLyrVect getWaypointLyrFromGPX(File file, String crs)
			throws LoadLayerException {
		I18nManager i18nManager = ToolsLocator.getI18nManager();
		ApplicationManager application = ApplicationLocator.getManager();
		String name = i18nManager.getTranslation(file.getName());
		FeatureStore fs = getFeatureStore(file, crs);
		FLayer layer = application.getMapContextManager().createLayer(name, fs);
		return (FLyrVect) layer;
	}
	
	private static FeatureStore getFeatureStore(File file, String crsStr) {
		try {
			DataManager manager = DALLocator.getDataManager();
			DataStoreParameters parameters = manager.createStoreParameters("OGRDataStoreProvider");
			parameters.setDynValue("file", file);
			IProjection crs = CRSFactory.getCRS(crsStr); 
			parameters.setDynValue("crs", crs);
			parameters.setDynValue("layerName", "waypoints");
			return (FeatureStore) manager.openStore(parameters.getDataStoreName(), parameters);
		} catch (InitializeException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (ProviderNotRegisteredException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (ValidateDataParametersException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
