package es.icarto.gvsig.commons.format;

import java.util.HashMap;
import java.util.Map;

public class FormatPool {

    private static final Map<String, IFormat> l = new HashMap<String, IFormat>();

    private FormatPool() {
    }

    public static IFormat instance() {
	return FormatPool.instance("default");
    }

    public static IFormat instance(String id) {
	IFormat format = l.get(id);
	if (format == null) {
	    format = new Format();
	    l.put(id, format);
	}
	return format;
    }

    public static void set(String id, IFormat format) {
	l.put(id, format);
    }
}