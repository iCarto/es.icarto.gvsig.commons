package javax.swing;

import java.text.ParseException;

import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;

/**
 * Editor for use with LinearSearchSpinnerListModel
 *
 * @author jlopez
 *
 */
public class LinearSearchListEditor extends DefaultEditor {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a <code>JSpinner</code> editor that supports displaying and
     * editing the value of a <code>SpinnerListModel</code> with a
     * <code>JFormattedTextField</code>. <code>This</code>
     * <code>ListEditor</code> becomes both a <code>ChangeListener</code> on the
     * spinner and a <code>PropertyChangeListener</code> on the new
     * <code>JFormattedTextField</code>.
     *
     * @param spinner
     *            the spinner whose model <code>this</code> editor will monitor
     * @exception IllegalArgumentException
     *                if the spinners model is not an instance of
     *                <code>SpinnerListModel</code>
     *
     * @see #getModel
     * @see SpinnerListModel
     */
    public LinearSearchListEditor(JSpinner spinner) {
	super(spinner);
	if (!(spinner.getModel() instanceof SpinnerListModel)) {
	    throw new IllegalArgumentException("model not a SpinnerListModel");
	}
	getTextField().setEditable(true);
	getTextField().setFormatterFactory(
		new DefaultFormatterFactory(new ListFormatter()));
    }

    /**
     * Return our spinner ancestor's <code>SpinnerNumberModel</code>.
     *
     * @return <code>getSpinner().getModel()</code>
     * @see #getSpinner
     * @see #getTextField
     */
    public LinearSearchSpinnerListModel getModel() {
	return (LinearSearchSpinnerListModel) (getSpinner().getModel());
    }

    /**
     * ListFormatter provides completion while text is being input into the
     * JFormattedTextField. Completion is only done if the user is inserting
     * text at the end of the document. Completion is done by way of the
     * SpinnerListModel method findNextMatch.
     */
    private class ListFormatter extends
	    javax.swing.JFormattedTextField.AbstractFormatter {

	private static final long serialVersionUID = 1L;
	private DocumentFilter filter;

	@Override
	public String valueToString(Object value) throws ParseException {
	    if (value == null) {
		return "";
	    }
	    return value.toString();
	}

	@Override
	public Object stringToValue(String string) throws ParseException {
	    return string;
	}

	@Override
	protected DocumentFilter getDocumentFilter() {
	    if (filter == null) {
		filter = new Filter();
	    }
	    return filter;
	}

	protected JFormattedTextField getTextField() {
	    return getFormattedTextField();
	}

	private class Filter extends javax.swing.text.DocumentFilter {
	    @Override
	    public void replace(FilterBypass fb, int offset, int length,
		    String string, AttributeSet attrs)
		    throws BadLocationException {
		if (string != null
			&& (offset + length) == fb.getDocument().getLength()) {
		    Object next = getModel().findNextMatch(
			    fb.getDocument().getText(0, offset) + string);
		    String value = (next != null) ? next.toString() : null;

		    if (value != null) {
			fb.remove(0, offset + length);
			fb.insertString(0, value, null);
			getTextField().select(offset + string.length(),
				value.length());
			return;
		    }
		}
		super.replace(fb, offset, length, string, attrs);
	    }

	    @Override
	    public void insertString(FilterBypass fb, int offset,
		    String string, AttributeSet attr)
		    throws BadLocationException {
		replace(fb, offset, 0, string, attr);
	    }
	}
    }
}
