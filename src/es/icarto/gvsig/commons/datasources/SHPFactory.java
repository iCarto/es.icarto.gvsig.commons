package es.icarto.gvsig.commons.datasources;

import java.io.File;

import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.DataStoreParameters;
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
import org.gvsig.tools.exception.BaseException;
import org.gvsig.tools.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHPFactory {

    private static final Logger logger = LoggerFactory
	    .getLogger(SHPFactory.class);

    public static void createSHP(File file, EditableFeatureType fieldsDesc,
	    int geomType, EditableFeature[] features, String crs)
	    throws BaseException {
	// new DefaultLibrariesInitializer().fullInitialize();
	DataManager manager = DALLocator.getDataManager();
	GeometryManager geometryManager = GeometryLocator.getGeometryManager();

	// Create a store
	NewFeatureStoreParameters destParams = (NewFeatureStoreParameters) manager
		.createNewStoreParameters("FilesystemExplorer", "Shape");

	// EditableFeatureType type = destParams.getDefaultFeatureType();
	// Geometry.TYPES.POINT
	GeometryType geometryType = geometryManager.getGeometryType(geomType,
		Geometry.SUBTYPES.GEOM2D);
	fieldsDesc.add("geom", org.gvsig.fmap.geom.DataTypes.GEOMETRY)
		.setGeometryType(geometryType);
	fieldsDesc.setDefaultGeometryAttributeName("geom");

	destParams.setDynValue("shpfile", file.getAbsoluteFile());
	destParams.setDynValue("crs", crs);
	destParams.setDefaultFeatureType(fieldsDesc);

	manager.newStore("FilesystemExplorer", "Shape", destParams, true);
	FeatureStore store = (FeatureStore) manager.openStore("Shape",
		destParams);

	store.edit();
	for (EditableFeature f : features) {
	    store.insert(f);
	}
	store.finishEditing();

	store.dispose();
    }

    public static FLyrVect getFLyrVectFromSHP(File file, String crs)
	    throws LoadLayerException {
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
     * @param shape
     *            file to be opened
     *
     * @return the feature store
     */
    private static FeatureStore getFeatureStore(File shape, String crs) {
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
