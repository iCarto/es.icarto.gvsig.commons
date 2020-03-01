package es.icarto.gvsig.commons.widgets;

import es.icarto.gvsig.commons.format.FormatPool;
import es.icarto.gvsig.commons.format.IFormat;

public class NumberOrEmpyTest implements BaseFilterTest {

    private final IFormat format = FormatPool.instance();

    @Override
    public boolean test(String text) {
	if (format.isEmpty(text)) {
	    return true;
	}
	if (format.isNumber(text)) {
	    return true;
	}
	return false;
    }
}
