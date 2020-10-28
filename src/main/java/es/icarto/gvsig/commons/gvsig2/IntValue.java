package es.icarto.gvsig.commons.gvsig2;

public class IntValue extends NumericValue {

	public IntValue(Object o) {
		super(o);
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return valueWriter.getStatementString((Integer) value, -1);
	}

}
