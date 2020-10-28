package es.icarto.gvsig.commons.gvsig2;

public class BooleanValue extends Value {

	public BooleanValue(Object o) {
		super(o);
	}

	public boolean getValue() {
		return (Boolean) value;
	}

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return valueWriter.getStatementString((Boolean) value);
	}

}
