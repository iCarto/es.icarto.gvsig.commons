package es.icarto.gvsig.commons.gvsig2;

public class FloatValue extends NumericValue {

	public FloatValue(Object o) {
		super(o);
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		Float v = (Float) value;
		return valueWriter.getStatementString(v.floatValue(), -1);
	}

}