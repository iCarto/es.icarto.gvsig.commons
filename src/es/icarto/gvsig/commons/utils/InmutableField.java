package es.icarto.gvsig.commons.utils;

import javax.swing.SortOrder;

public class InmutableField extends Field {

    @Override
    public void setKey(String key) {
	throw new AssertionError("Inmutable Field");
    }

    @Override
    public void setLongName(String longname) {
	throw new AssertionError("Inmutable Field");
    }

    @Override
    public void setValue(Object value) {
	throw new AssertionError("Inmutable Field");
    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {
	throw new AssertionError("Inmutable Field");
    }

    @Override
    public boolean equals(Object obj) {
	return obj == Field.EMPTY_FIELD;
    }
}
