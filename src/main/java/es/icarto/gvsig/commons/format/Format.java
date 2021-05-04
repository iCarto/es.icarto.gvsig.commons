package es.icarto.gvsig.commons.format;

import java.text.NumberFormat;
import java.text.ParsePosition;


public class Format implements IFormat {

	private static final NumberFormat nformat = NumberFormat.getNumberInstance();
	

	/**
	 * Sometime we want to return " " and others "". This is used to remember it
	 */
	private final String EMPTY_STRING = "";
	
	
	/*
    public Format() {
    	this.nformat = getDisplayingFormat();
    }

    private final NumberFormat getDisplayingFormat() {
    	DecimalFormat doubleFormatOnDisplay = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
    	final String pattern = "0.##########";
    	// Display a maximum of 10 decimals
    	doubleFormatOnDisplay.applyPattern(pattern);
    	return doubleFormatOnDisplay;
    }
    */

	@Override
	public String toString(Object o) {
		return o == null ? EMPTY_STRING : o.toString();
	}
	
    @Override
    public boolean isEmpty(Object value) {
	if (value == null) {
	    return true;
	}
	if (value.toString().trim().isEmpty()) {
	    return true;
	}
	return false;
    }


    @Override
    public Number toNumber(Object o, Number defaultValue) {
    	Number n = toNumber(o);
    	return n == null ? defaultValue : n;
    }


    @Override
    public Number toNumber(Object o) {
		if (o instanceof Number) {
		    return (Number) o;
		}
		String valueStr = o == null ? "" : o.toString().trim();
		ParsePosition pos = new ParsePosition(0);
		Number value = nformat.parse(valueStr, pos);
		if (valueStr.length() != pos.getIndex()) {
		    return null;
		}
		return value;
    }
	
    @Override
    public boolean isNumber(Object o) {
    	return toNumber(o) != null;
    }

}
