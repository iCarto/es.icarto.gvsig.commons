package es.icarto.gvsig.commons.gvsig2;

import java.util.Date;

public class DateValue extends Value {

	public DateValue(Object o) {
		super(o);
	}

	public Date getValue() {
		return (Date) value;
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return valueWriter.getStatementString((Date) value);
	}

}
