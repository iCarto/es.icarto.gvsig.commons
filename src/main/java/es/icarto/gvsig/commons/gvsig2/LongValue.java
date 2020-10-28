package es.icarto.gvsig.commons.gvsig2;

public class LongValue extends NumericValue {

	public LongValue(Object o) {
		super(o);
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return valueWriter.getStatementString((Long) value);
	}

}
