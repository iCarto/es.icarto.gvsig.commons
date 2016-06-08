package es.icarto.gvsig.commons.datasources;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.gvsig.fmap.dal.exception.DataException;
import org.gvsig.fmap.mapcontext.layers.vectorial.FLyrVect;

import es.icarto.gvsig.commons.gvsig2.SelectableDataSource;

public class FieldNames {

    private static final Logger logger = Logger.getLogger(FieldNames.class);

    private FieldNames() {
	throw new AssertionError("Non instantizable class");
    }

    public static List<Integer> getIndexesOfColumns(FLyrVect layer,
	    List<String> colNames) {
	List<Integer> indexes = new ArrayList<Integer>();

	try {
	    SelectableDataSource sds = new SelectableDataSource(
		    layer.getFeatureStore());
	    for (String col : colNames) {
		indexes.add(sds.getFieldIndexByName(col));
	    }
	} catch (DataException e1) {
	    logger.error(e1.getStackTrace(), e1);
	}

	return indexes;
    }

}
