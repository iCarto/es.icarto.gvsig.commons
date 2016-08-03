package es.icarto.gvsig.importer;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private List<HeaderField> definedFields = new ArrayList<HeaderField>();

    private List<HeaderField> fields = new ArrayList<HeaderField>();

    private ComparatorStrategy comparatorStrategy;

    public Header() {

	comparatorStrategy = new ComparatorStrategy();
    }

    public void setFromRules(List<HeaderField> a) {
	definedFields = a;
    }

    public HeaderField addFromSource(String v, int i) {

	if (inFieldsList()) {
	    throw new RuntimeException("El campo " + v + " está repetido");
	}

	HeaderField hf = inDefinedList(v, i);
	if (hf != null) {
	    definedFields.remove(hf);
	    fields.add(hf);
	    hf.setActualValues(v, i);

	    // setInDefinedFields (aunque debería estar seteado de antes)
	    //

	    // if hf.stopWhenFinded hacer algo
	    return hf;
	} else {
	    HeaderField h = new HeaderField(v, i);
	    h.setNotDefinedField(definedFields.indexOf(v) == -1);
	    return h;
	}

    }

    private HeaderField inDefinedList(String v, int i) {
	return comparatorStrategy.find(definedFields, v, i);
    }

    private boolean inFieldsList() {
	return false;
    }

    protected List<HeaderField> getFields() {
	return fields;
    }

    public HeaderField getField(String string) {
	for (HeaderField hf : fields) {
	    if (hf.id.equals(string)) {
		return hf;
	    }
	}
	return null;
    }
}
