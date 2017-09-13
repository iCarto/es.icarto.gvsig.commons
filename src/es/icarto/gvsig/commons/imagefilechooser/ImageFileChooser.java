package es.icarto.gvsig.commons.imagefilechooser;

import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class ImageFileChooser extends JFileChooser {

    public ImageFileChooser(File initFile) {
	super(initFile);
	this.addChoosableFileFilter(new ImageFilter());
	this.setAcceptAllFileFilterUsed(false);
	this.setAccessory(new ImagePreview(this));
    }

    public ImageFileChooser() {
	this(null);
    }

    public File showDialog() {
	File file = null;

	do {
	    int returnVal = this.showDialog(this, "Seleccionar");
	    if (returnVal == JFileChooser.CANCEL_OPTION) {
		break;
	    }

	    file = getSelectedFile();

	} while (file == null);

	return file;
    }

}
