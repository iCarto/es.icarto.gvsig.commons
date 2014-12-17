package es.icarto.gvsig.commons.utils;

public class StrUtils {

    private StrUtils() {
	throw new AssertionError("Non instantizable class");
    }

    public static boolean isEmptyString(String str) {
	return str == null || str.trim().length() == 0;
    }
}
