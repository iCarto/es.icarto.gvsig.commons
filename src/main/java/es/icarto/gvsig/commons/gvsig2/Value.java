package es.icarto.gvsig.commons.gvsig2;

public class Value {

    protected Object value;

    public Value() {

    }

    public Value(Object o) {
	this.value = o;
    }

    public double doubleValue() {
	Number n = (Number) value;
	return n.doubleValue();
    }

    public float floatValue() {
	Number n = (Number) value;
	return n.floatValue();
    }

    public int intValue() {
	Number n = (Number) value;
	return n.intValue();
    }

    public long longValue() {
	Number n = (Number) value;
	return n.longValue();
    }

    public int getType() {
	throw new RuntimeException("Not implemented");
    }

    public String getStringValue(ValueWriter valueWriter) {
	return value.toString();
    }

    @Override
    public String toString() {
	return value.toString();
    }

    public Object getObjectValue() {
	return value;
    }

}