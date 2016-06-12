package es.icarto.gvsig.commons.gvsig2;

public class StringValue extends Value {

    public StringValue(Object o) {
	super(o);
    }

    @Override
    public String getStringValue(ValueWriter valueWriter) {
	return valueWriter.getStatementString((String) value, -1);
    }

}
