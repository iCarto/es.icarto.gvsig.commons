package es.icarto.gvsig.importer.reader;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import es.icarto.gvsig.importer.SimpleHeaderField;

public class CSV implements Reader {

    @Override
    public void initReader(File file) {
    }

    @Override
    public List<SimpleHeaderField> getSimpleHeader() {
	return null;
    }

    @Override
    public DefaultTableModel getValues() {
	return null;
    }

    @Override
    public FileFilter getFileFilter() {
	return null;
    }

}
