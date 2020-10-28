package es.icarto.gvsig.commons.format;

public interface IFormat {

	String toString(Object o);

	Number toNumber(Object o, Number d);

	Number toNumber(Object o);

}
