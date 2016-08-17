package es.icarto.gvsig.importer;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.icarto.gvsig.importer.reader.Reader;

public class ImportManager {

    private final Reader reader;
    private final Header header;
    private final Output output;
    private final Ruler ruler;

    public ImportManager(Reader reader, Header header, Output output,
	    Ruler ruler) {
	this.reader = reader;
	this.header = header;
	this.output = output;
	this.ruler = ruler;
    }

    public void readHeader() {
	List<SimpleHeaderField> readHead = reader.getSimpleHeader();
	for (SimpleHeaderField s : readHead) {
	    header.addFromSource(s.name, s.pos);
	    // Si lo devuelto tiene el campo de stop debería parar
	}

    }

    public void processFile() {
	DefaultTableModel table = reader.getValues();
	ImporterTM importerTM = new ImporterTM();
	importerTM.addColumn("Código");

	addColumn(importerTM, "id", table);
	addColumn(importerTM, "x", table);
	addColumn(importerTM, "y", table);

	importerTM.addColumn("Capa destino");
	importerTM.addColumn("Geometría destino");
	importerTM.addColumn("Errores");
	int idIdx = importerTM.findColumn("id");
	for (int i = 0; i < importerTM.getRowCount(); i++) {
	    String id = importerTM.getValueAt(i, idIdx).toString();
	    ruler.processValue(id, importerTM, i);
	}

	output.process(importerTM, ruler);
    }

    private void addColumn(ImporterTM importer, String columnName,
	    DefaultTableModel table) {
	HeaderField field = header.getField(columnName);
	final int column = field.getActualPos();
	final int rowCount = table.getRowCount();
	Object[] columnData = new Object[rowCount];
	for (int row = 0; row < rowCount; row++) {
	    columnData[row] = table.getValueAt(row, column);
	}
	importer.addColumn(columnName, columnData);
    }

}
