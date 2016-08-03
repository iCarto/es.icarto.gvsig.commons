package es.icarto.gvsig.importer.reader;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import es.icarto.gvsig.importer.SimpleHeaderField;

public interface Reader {

    /**
     * Must be called previously to any other processing action
     */
    void initReader(File file);

    List<SimpleHeaderField> getSimpleHeader();

    DefaultTableModel getValues();

    FileFilter getFileFilter();

}
