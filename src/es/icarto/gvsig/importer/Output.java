package es.icarto.gvsig.importer;

import javax.swing.table.DefaultTableModel;

public interface Output {
    void process(DefaultTableModel table, Ruler ruler);
}
