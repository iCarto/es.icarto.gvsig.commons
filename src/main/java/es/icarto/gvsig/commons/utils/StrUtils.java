package es.icarto.gvsig.commons.utils;

public class StrUtils {

	private StrUtils() {
		throw new AssertionError("Non instantizable class");
	}

	public static boolean isEmptyString(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String join(String delimiter, Object... s) {
		if ((s == null) || (s.length == 0)) {
			return "";
		}
		StringBuilder out = new StringBuilder();
		out.append(s[0]);
		for (int x = 1, l = s.length; x < l; ++x) {
			out.append(delimiter).append(s[x]);
		}
		return out.toString();
	}
}
