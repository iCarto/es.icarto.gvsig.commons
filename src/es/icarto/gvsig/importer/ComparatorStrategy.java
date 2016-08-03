package es.icarto.gvsig.importer;

import java.text.Collator;
import java.util.List;

/**
 * Es la estrategía por defecto para la comparación de los campos de la fuente
 * con los campos definidos en las reglas. En esta estrategía se compara
 * mediante un collator.PRIMARY si el nombre del fichero fuente coincide con
 * alguno de los nombres descritos en las reglas
 *
 */
public class ComparatorStrategy {

    private Collator collator;

    public ComparatorStrategy() {
	collator = Collator.getInstance();
	collator.setStrength(Collator.PRIMARY);
    }

    public HeaderField find(List<HeaderField> fields, String v, int i) {
	for (HeaderField f : fields) {
	    for (String n : f.getRuleNames()) {
		if (collator.compare(v, n) == 0) {
		    return f;
		}
	    }
	}
	return null;
    }

}
