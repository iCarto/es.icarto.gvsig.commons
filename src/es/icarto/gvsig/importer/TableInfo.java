package es.icarto.gvsig.importer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiManager.WindowInfo;

import es.icarto.gvsig.commons.gui.AbstractIWindow;
import es.icarto.gvsig.commons.gui.OkCancelPanel;
import es.icarto.gvsig.commons.gui.WidgetFactory;

@SuppressWarnings("serial")
public class TableInfo extends AbstractIWindow implements ActionListener {

    private final OkCancelPanel btPanel;
    private final IJTable table;
    private boolean okClickedWithoutError = false;

    public TableInfo(DefaultTableModel tableModel, Ruler ruler) {
	super(new MigLayout("fill, insets 10"));
	setWindowTitle("Información procesada");
	final int wProps = WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE
		| WindowInfo.PALETTE;
	setWindowInfoProperties(wProps);
	btPanel = WidgetFactory.okCancelPanel(this, this, this);

	table = new IJTable(tableModel, ruler);
	MDIFrame mainFrame = (MDIFrame) PluginServices.getMainFrame();
	table.setTotalWidth(mainFrame.getWidth() - 300);
	JScrollPane scrollPane = new JScrollPane(table);

	this.add(scrollPane, "push, grow");
    }

    public void setContextMenu(MouseListener myMouseListener) {
	table.addMouseListener(myMouseListener);
    }

    @Override
    protected JButton getDefaultButton() {
	return btPanel.getOkButton();
    }

    @Override
    protected Component getDefaultFocusComponent() {
	return table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	okClickedWithoutError = false;
	if (e.getActionCommand().equals(OkCancelPanel.OK_ACTION_COMMAND)) {
	    okClickedWithoutError = thereAreNotErrors();
	    if (!okClickedWithoutError) {
		String errorMsg = "Los datos tienen errores. Corríjalos antes de continuar";
		JOptionPane.showMessageDialog(this, errorMsg);
		return;
	    }
	}

	closeDialog();
    }

    /**
     * @return true when there is no okClickedWithoutError and the user press ok
     */
    public boolean isGood() {
	return okClickedWithoutError;
    }

    private boolean thereAreNotErrors() {
	ImporterTM model = (ImporterTM) table.getModel();
	model.reCheckErrors();
	for (int i = 0; i < table.getRowCount(); i++) {
	    List<ImportError> error = model.getError(i);
	    if (!error.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

    public JTable getJTable() {
	return table;
    }

}
