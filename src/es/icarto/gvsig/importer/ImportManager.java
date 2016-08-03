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
	int initColumn = importerTM.getColumnCount();
	for (int i = 0; i < table.getColumnCount(); i++) {
	    importerTM.addColumn(table.getColumnName(i));
	}
	for (int i = 0; i < table.getRowCount(); i++) {
	    importerTM.addRow(new Object[importerTM.getColumnCount()]);
	    for (int j = 0; j < table.getColumnCount(); j++) {
		final Object o = table.getValueAt(i, j);
		importerTM.setValueAt(o, i, initColumn + j);
	    }
	}
	importerTM.addColumn("tablename");
	importerTM.addColumn("geom");
	importerTM.addColumn("Errores");
	int idIdx = importerTM.findColumn("id");
	for (int i = 0; i < importerTM.getRowCount(); i++) {
	    String id = importerTM.getValueAt(i, idIdx).toString();
	    HeaderField field = header.getField("id");
	    ruler.processValue(id, importerTM, i);
	}

	output.process(importerTM, ruler);
    }

}
