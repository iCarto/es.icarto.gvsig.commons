package es.icarto.gvsig.commons.gvsig2;

public class NullValue extends Value {

	@Override
	public String getStringValue(ValueWriter valueWriter) {
		return "";
	}

}
