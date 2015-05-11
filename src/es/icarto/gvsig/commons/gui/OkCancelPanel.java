package es.icarto.gvsig.commons.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;

@SuppressWarnings("serial")
/**
 * A panel with two buttons: Ok and Cancel
 *
 */
public class OkCancelPanel extends JPanel {

    public static final String CANCEL_ACTION_COMMAND = "AcceptCancelPanel.CANCEL";
    public static String OK_ACTION_COMMAND = "AcceptCancelPanel.OK";
    private JButton btnOk = null;
    private JButton btnCancel = null;
    private final JPanel btPanel;

    public OkCancelPanel(ActionListener okAction, ActionListener cancelAction) {
	super(new FlowLayout(FlowLayout.TRAILING));
	btPanel = new JPanel(new GridLayout(1, 0, 5, 5));
	addOkButton(okAction);
	addCancelButton(cancelAction);
	add(btPanel);
    }

    public OkCancelPanel() {
	this(null, null);
    }

    private void addOkButton(ActionListener okAction) {
	btnOk = new JButton();
	btnOk.setText(PluginServices.getText(this, "ok"));
	btnOk.setActionCommand(OK_ACTION_COMMAND);
	if (okAction != null) {
	    btnOk.addActionListener(okAction);
	}
	btPanel.add(btnOk);
    }

    private void addCancelButton(ActionListener cancelAction) {
	btnCancel = new JButton();
	btnCancel.setText(PluginServices.getText(this, "cancel"));
	btnCancel.setActionCommand(CANCEL_ACTION_COMMAND);
	if (cancelAction != null) {
	    btnCancel.addActionListener(cancelAction);
	}
	btPanel.add(btnCancel);
    }

    public void addCancelButtonActionListener(ActionListener l) {
	btnCancel.addActionListener(l);
    }

    public void setOkButtonActionListener(ActionListener l) {
	ActionListener[] listeners = btnOk.getActionListeners();
	for (int i = 0; i < listeners.length; i++) {
	    btnOk.removeActionListener(listeners[i]);
	}
	btnOk.addActionListener(l);
    }

    public void setCancelButtonActionListener(ActionListener l) {
	ActionListener[] listeners = btnCancel.getActionListeners();
	for (int i = 0; i < listeners.length; i++) {
	    btnCancel.removeActionListener(listeners[i]);
	}
	btnCancel.addActionListener(l);
    }

    public void addOkButtonActionListener(ActionListener l) {
	btnOk.addActionListener(l);
    }

    public boolean isOkButtonEnabled() {
	return btnOk.isEnabled();
    }

    public boolean isCancelButtonEnabled() {
	return btnCancel.isEnabled();
    }

    public void setOkButtonEnabled(boolean b) {
	btnOk.setEnabled(b);
    }

    public void setCancelButtonEnabled(boolean b) {
	btnCancel.setEnabled(b);
    }

    public JButton getOkButton() {
	return btnOk;
    }
}
