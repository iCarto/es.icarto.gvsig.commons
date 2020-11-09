package es.icarto.gvsig.commons.format;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Format implements IFormat {

    protected NumberFormat nformat;
    protected SimpleDateFormat dateFormat;

    public Format() {
	this.nformat = getDisplayingFormat();
	this.dateFormat = getDateFormat();
    }

    private final NumberFormat getDisplayingFormat() {
	DecimalFormat doubleFormatOnDisplay = (DecimalFormat) NumberFormat
		.getNumberInstance(Locale.getDefault());
	final String pattern = "0.##########";
	// Display a maximum of 10 decimals
	doubleFormatOnDisplay.applyPattern(pattern);

	return doubleFormatOnDisplay;
    }

    /**
     * Sometime we want to return " " and others "". This is used to remember it
     */
    private final String EMPTY_STRING = "";

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

    @Override
    public SimpleDateFormat getDateFormat() {
	if (dateFormat == null) {
	    dateFormat = (SimpleDateFormat) DateFormat
		    .getDateInstance(DateFormat.SHORT);
	    String p = dateFormat.toLocalizedPattern();
	    if (!p.contains("yyyy")) {
		p = p.replaceAll("yy", "yyyy");
		dateFormat = new SimpleDateFormat(p);
	    }
	}
	return dateFormat;
    }

}
