package es.icarto.gvsig.importer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import es.icarto.gvsig.commons.gui.AbstractIWindow;
import es.icarto.gvsig.commons.gui.OkCancelPanel;
import es.icarto.gvsig.commons.gui.SelectFileWidget;
import es.icarto.gvsig.commons.gui.WidgetFactory;
import es.icarto.gvsig.importer.reader.Reader;

@SuppressWarnings("serial")
public class FileToImport extends AbstractIWindow implements ActionListener {

    private SelectFileWidget selectFile;
    private File file;
    private List<Reader> readers;

    /**
     *
     * @param initFile
     *            the initial file to be showed. Can be null
     */
    public FileToImport(String initFile) {
	selectFile = new SelectFileWidget(this, "Seleccione datos de entrada",
		initFile, false);
	WidgetFactory.okCancelPanel(this, this, this);
    }

    @Override
    protected JButton getDefaultButton() {
	return null;
    }

    @Override
    protected Component getDefaultFocusComponent() {
	return null;
    }

    @Override
    public void openDialog() {
	file = null;
	super.openDialog();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals(OkCancelPanel.OK_ACTION_COMMAND)) {
	    if (selectFile.isValidAndExist()) {
		file = selectFile.getFile();
	    }
	}
	closeDialog();
    }

    public File getFile() {
	return file;
    }

    public void setFilter(String description, String ext) {
	selectFile.setFilter(description, ext);
    }

    public void addChoosableFileFilter(FileFilter fileFilter) {
	selectFile.addChoosableFilter(fileFilter);
    }

    /**
     * When set the list of FileFilters will used the info provided by the
     * readers and the method getReader is allowed
     */
    public void setReaders(List<Reader> readers) {
	this.readers = readers;
	ORFileFilter all = new ORFileFilter();

	for (Reader r : readers) {
	    FileFilter filter = r.getFileFilter();
	    addChoosableFileFilter(filter);
	    all.addFileFilter(filter);
	}
	selectFile.setFilter(JFileChooser.FILES_ONLY, all);
    }

    public Reader getInitializedReader() {
	File choosedFile = getFile();

	if ((choosedFile == null) || !choosedFile.isFile()) {
	    return null;
	}
	for (Reader reader : this.readers) {
	    if (reader.getFileFilter().accept(choosedFile)) {
		reader.initReader(choosedFile);
		return reader;
	    }
	}
	return null;
    }

    private class ORFileFilter extends FileFilter {

	private final List<FileFilter> filters;

	public ORFileFilter() {
	    filters = new ArrayList<FileFilter>();
	}

	public void addFileFilter(FileFilter filter) {
	    filters.add(filter);
	}

	@Override
	public boolean accept(File f) {
	    for (FileFilter filter : filters) {
		if (filter.accept(f)) {
		    return true;
		}
	    }
	    return false;
	}

	@Override
	public String getDescription() {
	    return "Todos los tipos soportados";
	}
    }
}
