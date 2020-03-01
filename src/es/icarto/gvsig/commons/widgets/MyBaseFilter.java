package es.icarto.gvsig.commons.widgets;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class MyBaseFilter extends DocumentFilter {
    private final BaseFilterTest test;

    public MyBaseFilter(BaseFilterTest test) {
	this.test = test;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
	    AttributeSet attr) throws BadLocationException {

	Document doc = fb.getDocument();
	StringBuilder sb = new StringBuilder();
	sb.append(doc.getText(0, doc.getLength()));
	sb.insert(offset, string);

	if (test.test(sb.toString())) {
	    super.insertString(fb, offset, string, attr);
	} else {
	    // warn the user and don't allow the insert
	}
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
	    AttributeSet attrs) throws BadLocationException {

	Document doc = fb.getDocument();
	StringBuilder sb = new StringBuilder();
	sb.append(doc.getText(0, doc.getLength()));
	sb.replace(offset, offset + length, text);

	if (test.test(sb.toString())) {
	    super.replace(fb, offset, length, text, attrs);
	} else {
	    // warn the user and don't allow the insert
	}

    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
	    throws BadLocationException {
	Document doc = fb.getDocument();
	StringBuilder sb = new StringBuilder();
	sb.append(doc.getText(0, doc.getLength()));
	sb.delete(offset, offset + length);

	if (test.test(sb.toString())) {
	    super.remove(fb, offset, length);
	} else {
	    // warn the user and don't allow the insert
	}

    }
}
