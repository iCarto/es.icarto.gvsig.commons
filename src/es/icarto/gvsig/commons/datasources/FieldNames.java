package es.icarto.gvsig.commons.datasources;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.iver.cit.gvsig.fmap.layers.SelectableDataSource;

public class FieldNames {

    private static final Logger logger = Logger.getLogger(FieldNames.class);

    private FieldNames() {
	throw new AssertionError("Non instantizable class");
    }

    public static List<Integer> getIndexesOfColumns(FLyrVect layer,
	    List<String> colNames) {
	List<Integer> indexes = new ArrayList<Integer>();

	try {
	    SelectableDataSource sds = layer.getRecordset();
	    for (String col : colNames) {
		indexes.add(sds.getFieldIndexByName(col));
	    }
	} catch (ReadDriverException e1) {
	    logger.error(e1.getStackTrace(), e1);
	}

	return indexes;
    }

}
