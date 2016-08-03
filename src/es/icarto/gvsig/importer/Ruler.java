package es.icarto.gvsig.importer;

import java.util.List;

import es.icarto.gvsig.commons.utils.Field;

public interface Ruler {
    void processValue(String id, ImporterTM table, int i);

    List<Field> getFields();
}
