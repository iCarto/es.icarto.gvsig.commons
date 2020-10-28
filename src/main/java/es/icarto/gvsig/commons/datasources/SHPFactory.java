package es.icarto.gvsig.commons.datasources;

import java.io.File;

import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStoreParameters;
import org.gvsig.fmap.dal.OpenDataStoreParameters;
import org.gvsig.fmap.dal.exception.InitializeException;
import org.gvsig.fmap.dal.exception.ProviderNotRegisteredException;
import org.gvsig.fmap.dal.exception.ValidateDataParametersException;
import org.gvsig.fmap.dal.feature.EditableFeature;
import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.FeatureStore;
import org.gvsig.fmap.dal.feature.NewFeatureStoreParameters;
import org.gvsig.fmap.geom.Geometry;
import org.gvsig.fmap.geom.GeometryLocator;
import org.gvsig.fmap.geom.GeometryManager;
import org.gvsig.fmap.geom.type.GeometryType;
import org.gvsig.fmap.mapcontext.exceptions.LoadLayerException;
import org.gvsig.fmap.mapcontext.layers.FLayer;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.dispose.DisposeUtils;
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHPFactory {

	private static final Logger logger = LoggerFactory.getLogger(SHPFactory.class);

	public static void createSHP(File file, EditableFeatureType featType, String crs) throws BaseException {
		NewFeatureStoreParameters newStoreParams = newStoreParams(file, featType, crs);
		DataManager manager = DALLocator.getDataManager();
		manager.newStore("FilesystemExplorer", "Shape", newStoreParams, true);
	}

	public static void createSHP(File file, EditableFeatureType featType, int geomType, String crs)
			throws BaseException {

		NewFeatureStoreParameters newStoreParams = newStoreParams(file, featType, geomType, crs);
		DataManager manager = DALLocator.getDataManager();
		manager.newStore("FilesystemExplorer", "Shape", newStoreParams, true);
	}

	public static void insertIntoStore(File file, String crs, Object[][] features) throws BaseException {
		DataStoreParameters openStoreParams = openStoreParams(file, crs);
		DataManager manager = DALLocator.getDataManager();
		FeatureStore store = null;
		try {
			store = (FeatureStore) manager.openStore("Shape", openStoreParams);
			store.edit(FeatureStore.MODE_APPEND);
			for (Object[] fValues : features) {
				EditableFeature f = store.createNewFeature();
				for (int i = 0; i < fValues.length; i++) {
					f.set(i, fValues[i]);
				}
				store.insert(f);
			}

			store.finishEditing();
		} finally {
			DisposeUtils.disposeQuietly(store);
		}
	}

	public static NewFeatureStoreParameters newStoreParams(File file, EditableFeatureType featType, String crs)
			throws BaseException {
		DataManager manager = DALLocator.getDataManager();
		NewFeatureStoreParameters params = (NewFeatureStoreParameters) manager
				.createNewStoreParameters("FilesystemExplorer", "Shape");
		params.setDynValue("shpfile", file.getAbsoluteFile());
		params.setDynValue("crs", crs);
		params.setDefaultFeatureType(featType);
		return params;
	}

	public static NewFeatureStoreParameters newStoreParams(File file, EditableFeatureType featType, int geomType,
			String crs) throws BaseException {

		GeometryManager geomManager = GeometryLocator.getGeometryManager();

		GeometryType geometryType = geomManager.getGeometryType(geomType, Geometry.SUBTYPES.GEOM2D);
		featType.add("geom", org.gvsig.fmap.geom.DataTypes.GEOMETRY).setGeometryType(geometryType);
		featType.setDefaultGeometryAttributeName("geom");

		return newStoreParams(file, featType, crs);
	}

	public static OpenDataStoreParameters openStoreParams(File file, String crs) throws BaseException {
		DataManager manager = DALLocator.getDataManager();
		OpenDataStoreParameters params = (OpenDataStoreParameters) manager.createStoreParameters("Shape");
		params.setDynValue("shpfile", file.getAbsoluteFile());
		params.setDynValue("crs", crs);
		return params;
	}

	public static FLyrVect getFLyrVectFromSHP(File file, String crs) throws LoadLayerException {
		I18nManager i18nManager = ToolsLocator.getI18nManager();
		ApplicationManager application = ApplicationLocator.getManager();
		String name = i18nManager.getTranslation(file.getName());
		FeatureStore fs = getFeatureStore(file, crs);
		FLayer layer = application.getMapContextManager().createLayer(name, fs);
		return (FLyrVect) layer;
	}

	/**
	 * Open the file as a feature store of type shape.
	 *
	 * @param shape file to be opened
	 *
	 * @return the feature store
	 */
	public static FeatureStore getFeatureStore(File shape, String crs) {
		try {

			DataStoreParameters parameters;
			DataManager manager = DALLocator.getDataManager();

			parameters = manager.createStoreParameters("Shape");
			parameters.setDynValue("shpfile", shape);
			parameters.setDynValue("crs", crs);
			return (FeatureStore) manager.openStore("Shape", parameters);

		} catch (InitializeException e) {
			logger.error(e.getMessageStack());
			throw new RuntimeException(e);
		} catch (ProviderNotRegisteredException e) {
			logger.error(e.getMessageStack());
			throw new RuntimeException(e);
		} catch (ValidateDataParametersException e) {
			logger.error(e.getMessageStack());
			throw new RuntimeException(e);
		}
	}

}
