package es.icarto.gvsig.commons.widgets;

public class IntOrEmptyTest implements BaseFilterTest {

    @Override
    public boolean test(String text) {
	try {
	    if (text.length() == 0) {
		return true;
	    }
	    Integer.parseInt(text);
	    return true;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

}
