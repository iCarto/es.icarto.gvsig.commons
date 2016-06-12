package es.icarto.gvsig.commons.gvsig2;

public class DoubleValue extends NumericValue {

    public DoubleValue(Object o) {
	super(o);
    }

    @Override
    public String getStringValue(ValueWriter valueWriter) {
	return valueWriter.getStatementString((Double) value, -1);
    }

}
