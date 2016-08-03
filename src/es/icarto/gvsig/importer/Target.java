package es.icarto.gvsig.importer;

import java.util.List;

import es.icarto.gvsig.commons.utils.Field;

public interface Target {

    boolean matches(String value);

    boolean process(String value, ImporterTM table, int i);

    Field getField();

    String calculateCode(ImporterTM table, int i);

    List<ImportError> checkErrors(ImporterTM table, int i);

}
