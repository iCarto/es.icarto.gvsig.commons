package es.icarto.gvsig.commons.utils;

import java.text.Collator;
import java.util.ArrayList;

import javax.swing.SortOrder;

public class Field implements Comparable<Field> {

    public static final Field EMPTY_FIELD = new InmutableField();

    private String column;
    private String longName;
    private Object value;
    private SortOrder sortOrder;

    private ArrayList<String> foreignKeys = new ArrayList<String>();

    public Field() {
    }

    public Field(String name) {
	this.column = name;
	this.longName = name;
    }

    public Field(String key, String longname, String fk) {
	this.column = key;
	this.longName = longname;
	this.foreignKeys.add(fk);
    }

    public Field(String key, String longname, ArrayList<String> fk) {
	this.column = key;
	this.longName = longname;
	this.foreignKeys = fk;
    }

    public Field(String key, String longname) {
	this.column = key;
	this.longName = longname;
    }

    public String getKey() {
	return this.column;
    }

    public void setKey(String key) {
	this.column = key;
    }

    public void addForeignKey(String d) {
	this.foreignKeys.add(d);
    }

    public ArrayList<String> getForeignKeys() {
	return this.foreignKeys;
    }

    public String getLongName() {
	return this.longName;
    }

    public void setLongName(String longname) {
	this.longName = longname;
    }

    public void setValue(Object value) {
	this.value = value;
    }

    public Object getValue() {
	return this.value;
    }

    public SortOrder getSortOrder() {
	return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
	this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
	return this.longName;
    }

    /**
     * Returns true if obj is a KeyValue object with the same key and value
     * fields, or if obj is a string equals to the value field
     */
    @Override
    public boolean equals(Object obj) {
	if (obj instanceof String) {
	    return getLongName().equals(obj);
	} else if (obj instanceof Field) {
	    Field kvObj = (Field) obj;
	    return getLongName().equals(kvObj.getLongName())
		    && getKey().equals(kvObj.getKey());
	}
	return false;
    }

    @Override
    public int compareTo(Field o) {
	Collator usCollator = Collator.getInstance();
	usCollator.setStrength(Collator.IDENTICAL);
	int comparison = usCollator.compare(longName, o.longName);
	if (comparison == 0) {
	    comparison = usCollator.compare(column, o.column);
	}
	return comparison;
    }

}