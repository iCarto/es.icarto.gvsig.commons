package es.icarto.gvsig.commons.gvsig2;

import java.sql.Time;

public class TimeValue extends Value {

	public TimeValue(Object t) {
		super(t);
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return valueWriter.getStatementString((Time) value);
	}

}
