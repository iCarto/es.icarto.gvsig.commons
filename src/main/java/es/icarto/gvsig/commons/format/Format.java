package es.icarto.gvsig.commons.format;

import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

public class Format implements IFormat {

	private static final Logger logger = Logger.getLogger(Format.class);
	private static final NumberFormat nformat = NumberFormat.getNumberInstance();

	/**
	 * Sometime we want to return " " and others "". This is used to remember it
	 */
	private final String EMPTY_STRING = "";

	@Override
	public String toString(Object o) {
		return o == null ? EMPTY_STRING : o.toString();
	}

	@Override
	public Number toNumber(Object o, Number d) {
		Number n = toNumber(o);
		return n == null ? d : n;
	}

	@Override
	public Number toNumber(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Number) {
			return (Number) o;
		}
		try {
			return nformat.parse(o.toString());
		} catch (ParseException e) {
			logger.error(e.getStackTrace(), e);
		}
		return null;
	}

}
