package es.icarto.gvsig.commons.gui;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import es.icarto.gvsig.commons.widgets.IntOrEmptyTest;
import es.icarto.gvsig.commons.widgets.MyBaseFilter;
import es.icarto.gvsig.commons.widgets.NumberOrEmpyTest;

public class TexfieldFactory {

    private static JTextField getTextField(int columns) {
	JTextField textField = new JTextField();
	if (columns != -1) {
	    textField.setColumns(columns);
	}
	return textField;
    }

    public static JTextField getNumberTextField(int columns) {
	JTextField textField = getTextField(columns);
	PlainDocument doc = (PlainDocument) textField.getDocument();
	doc.setDocumentFilter(new MyBaseFilter(new NumberOrEmpyTest()));
	return textField;
    }

    public static JTextField getIntegerTextField(int columns) {
	JTextField textField = getTextField(columns);
	PlainDocument doc = (PlainDocument) textField.getDocument();
	doc.setDocumentFilter(new MyBaseFilter(new IntOrEmptyTest()));
	return textField;
    }

}
