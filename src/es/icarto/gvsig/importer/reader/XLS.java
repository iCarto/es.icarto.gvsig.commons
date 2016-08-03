package es.icarto.gvsig.importer.reader;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import es.icarto.gvsig.importer.Header;
import es.icarto.gvsig.importer.SimpleHeaderField;
import es.icarto.gvsig.importer.XLSFormatUtils;

public class XLS implements Reader {

    private static final Logger logger = Logger.getLogger(XLS.class);

    private Sheet sheet;
    private Collator collator;
    private int headerLine = FIRST_NOT_EMPTY;
    private int realHeaderRowNumber;

    public XLS() {
	collator = Collator.getInstance();
	collator.setStrength(Collator.PRIMARY);
    }

    @Override
    public void initReader(File file) {
	Workbook wb;
	try {
	    wb = WorkbookFactory.create(file);
	} catch (EncryptedDocumentException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo excel", e);
	} catch (InvalidFormatException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo excel", e);
	} catch (IOException e) {
	    logger.error(e.getStackTrace(), e);
	    throw new RuntimeException("Error leyendo excel", e);
	}
	int sheetIdx = wb.getActiveSheetIndex();
	if (sheetIdx != 0) {
	    // addWarning("La última hoja empleada no es la primera del libro. Compruebe que esto es correcto");
	}
	sheet = wb.getSheetAt(sheetIdx);
    }

    public void read() {

    }

    private void headeradd(String v, int i) {

    }

    // First not empty | number of line | not header

    public final static int FIRST_NOT_EMPTY = -1;
    public final static int NO_HEADER = -2;

    /**
     * Negative values for "i" different that FIRST_NOT_EMPTY or NO_HEADER
     * throws an IllegalArgumentException If "i" is different that
     * FIRST_NOT_EMPTY or NO_HEADER that number will be used as the first valid
     * row in the sheet and it will contain the header
     *
     * @param i
     */
    public void setHeaderLine(int i) {
	if ((i < 0) && (i != FIRST_NOT_EMPTY && i != NO_HEADER)) {
	    throw new IllegalArgumentException();
	}
	headerLine = i;
    }

    public Header getHeader() {
	return null;
    }

    public List<SimpleHeaderField> getSimpleHeader() {
	Row row;
	if (headerLine == FIRST_NOT_EMPTY) {
	    realHeaderRowNumber = sheet.getFirstRowNum();
	    row = sheet.getRow(realHeaderRowNumber);
	} else if (headerLine == NO_HEADER) {
	    realHeaderRowNumber = -1;
	    return null;
	} else {
	    realHeaderRowNumber = headerLine;
	    row = sheet.getRow(headerLine);
	    if (row == null) {
		throw new IllegalArgumentException(
			"Esta línea está vacía, no puede ser la cabecera del fichero");
	    }
	}

	List<SimpleHeaderField> list = new ArrayList<SimpleHeaderField>();
	for (Cell cell : row) {
	    int pos = cell.getColumnIndex();
	    String name = cell.getStringCellValue();
	    SimpleHeaderField sh = new SimpleHeaderField(name, pos);
	    list.add(sh);
	}

	// sheet.getFirstRowNum()
	// sheet.getLastRowNum()
	//
	// sheet.getRow(arg0)
	//
	// sheet.iterator()
	// sheet.rowIterator()

	return list;

    }

    public DefaultTableModel getValues() {
	DefaultTableModel table = new DefaultTableModel();

	// TODO
	table.addColumn("id");
	table.addColumn("x");
	table.addColumn("y");

	for (int i = realHeaderRowNumber + 1; i <= sheet.getLastRowNum(); i++) {

	    Row row = sheet.getRow(i);
	    if (row == null) {
		continue;
	    }

	    Object rowData[] = new Object[3];
	    for (Cell cell : row) {
		int pos = cell.getColumnIndex();
		String val = XLSFormatUtils.getValueAsString(cell);
		rowData[pos] = val;
	    }
	    table.addRow(rowData);
	}

	return table;
    }

    @Override
    public FileFilter getFileFilter() {
	String description = "Ficheros Excel";
	String extensions = "xlsx";
	return new FileNameExtensionFilter(description, extensions);
    }
}
