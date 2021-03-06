package es.icarto.gvsig.importer.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.iver.cit.gvsig.fmap.drivers.dbf.DBFDriver;

import es.icarto.gvsig.importer.SimpleHeaderField;

public class DBF implements Reader {

    private static final Logger logger = Logger.getLogger(DBF.class);
    private DBFDriver driver;

    private List<SimpleHeaderField> simpleHeader;
    private DefaultTableModel values;

    public DBF() {
    }

    @Override
    public void initReader(File file) {
	try {
	    driver = new DBFDriver();
	    // driver.setCharSet(charSet);
	    driver.open(file);
	    initSimpleHeader();
	    initValues();
	    driver.close();
	} catch (ReadDriverException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo el fichero dbf", e);
	}
    }

    private void initSimpleHeader() {
	simpleHeader = new ArrayList<SimpleHeaderField>();
	try {
	    for (int i = 0; i < driver.getFieldCount(); i++) {
		String fieldName = driver.getFieldName(i);
		SimpleHeaderField sh = new SimpleHeaderField(fieldName, i);
		simpleHeader.add(sh);
	    }
	} catch (ReadDriverException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo el fichero dbf", e);
	}
    }

    private void initValues() {
	values = new DefaultTableModel();

	try {
	    final int columnCount = driver.getFieldCount();
	    final int rowCount = (int) driver.getRowCount();

	    for (int column = 0; column < columnCount; column++) {
		values.addColumn(driver.getFieldName(column));
	    }
	    for (int row = 0; row < rowCount; row++) {
		Object rowData[] = new Object[columnCount];
		for (int c = 0; c < columnCount; c++) {
		    rowData[c] = driver.getFieldValue(row, c).toString();
		}
		values.addRow(rowData);
	    }
	} catch (ReadDriverException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo el fichero dbf", e);
	}
    }

    @Override
    public List<SimpleHeaderField> getSimpleHeader() {
	return simpleHeader;
    }

    @Override
    public DefaultTableModel getValues() {
	return values;
    }

    @Override
    public FileFilter getFileFilter() {
	String description = "Ficheros DBF";
	String extensions = "dbf";
	return new FileNameExtensionFilter(description, extensions);
    }

}
