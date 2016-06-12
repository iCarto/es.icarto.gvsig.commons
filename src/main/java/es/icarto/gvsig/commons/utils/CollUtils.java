package es.icarto.gvsig.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollUtils {

    private CollUtils() {
	throw new AssertionError("Non instantizable class");
    }

    public static <T> Collection<T> flat(T[][] array) {
	Collection<T> result = new ArrayList<T>();

	for (T[] e : array) {
	    Collections.addAll(result, e);
	}

	return result;
    }
}
