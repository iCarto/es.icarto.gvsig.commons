package es.icarto.gvsig.commons.gvsig2;

import java.sql.Timestamp;

public class TimestampValue extends Value {

    public TimestampValue(Object t) {
	super(t);
    }

    @Override
    public String getStringValue(ValueWriter valueWriter) {
	return valueWriter.getStatementString((Timestamp) value);
    }

}
