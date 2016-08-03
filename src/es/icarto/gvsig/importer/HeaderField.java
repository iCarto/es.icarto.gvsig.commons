package es.icarto.gvsig.importer;

import java.util.HashSet;
import java.util.Set;

public class HeaderField {

    /*
     * un campo puede tener varios posibles nombres en la fuente, pero s�lo
     * tendr� uno de cada vez. Por ejemplo a veces aparecer� como lng y otras
     * como lon, pero en la mismo fichero no estar� lng y lon
     */
    private Set<String> ruleNames = new HashSet<String>(1);

    /* El nombre tal cual aparece en la fuente */
    private String actualName;

    /* El �ndice del campo en la fuente */
    private int idx;

    /*
     * Representa si en las reglas se dice que es obligatorio que este campo
     * aparezca en el fichero fuente
     */
    private boolean mandatory;

    /*
     * Cuando hay un campo en la fuente que no est� definido en las reglas
     * estar� a a true
     */
    private boolean notDefinedField = false;

    /**
     * Indica que se debe parar de recorrer la cabecera si se encuentra este
     * campo
     */
    private boolean stopWhenFinded = false;

    public final String id;

    public HeaderField(String id) {
	this.id = id;
	// Los campos estar�n en general definidos desde fuera, bien por
	// nombre bien por posici�n. Es responsabilidad de esta clase o de
	// Header "rellenar" lo que falte sea la posici�n o el nombre

	// Hace falta una forma de marcar campos como mandatory y dar un
	// error si no est�n todos, con un buen mensaje

	// Hace falta una forma de marcar los campos que est�n en las reglas
	// y no est�n en la cabecera y viceversa

    }

    public HeaderField(String v, int i) {
	this.id = v;
	this.idx = i;
    }

    public void setNotDefinedField(boolean notDefinedField) {
	this.notDefinedField = notDefinedField;
    }

    public boolean isNotDefinedField() {
	return notDefinedField;
    }

    public void setRuleNames(Set<String> ruleNames) {
	this.ruleNames = ruleNames;
    }

    /**
     *
     * @param v
     *            real column name used in the source file
     * @param i
     *            real index in the source file
     */
    public void setActualValues(String v, int i) {
	actualName = v;
	idx = i;
    }

    public Set<String> getRuleNames() {
	return ruleNames;
    }

    public String getActualName() {
	return actualName;
    }

    public int getActualPos() {
	return idx;
    }

    @Override
    public String toString() {
	return id;
    }

}