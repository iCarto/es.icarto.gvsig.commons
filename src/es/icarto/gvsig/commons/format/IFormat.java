package es.icarto.gvsig.commons.format;

public interface IFormat {

    String toString(Object o);

    Number toNumber(Object o, Number d);

    Number toNumber(Object o);

    /*
     * Returns true if the object o is a "number" according to the current
     * number formatted set. It could be a string that can be correctly parsed
     * or a instance number itself
     */
    boolean isNumber(Object o);

    /*
     * Returns true if the value is null, or if its string representation has 0
     * length or only have whitespace characters
     */
    boolean isEmpty(Object value);

}
