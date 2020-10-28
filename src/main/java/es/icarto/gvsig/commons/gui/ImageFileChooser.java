package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.io.File;

import javax.swing.JFileChooser;

import es.icarto.gvsig.commons.utils.FileExtensionFilter;

@SuppressWarnings("serial")
public class ImageFileChooser extends JFileChooser {

	public final static String[] exts = { "jpeg", "jpg", "gif", "tiff", "tif", "png" };

	public ImageFileChooser() {
		FileExtensionFilter filter = new FileExtensionFilter(_("images"), exts);
		this.addChoosableFileFilter(filter);
		this.setAcceptAllFileFilterUsed(false);
		this.setAccessory(new ImagePreview(this));
	}

	public File showDialog() {
		File file = null;

		do {
			int returnVal = this.showOpenDialog(this);
			if (returnVal == JFileChooser.CANCEL_OPTION) {
				break;
			}

			file = getSelectedFile();

		} while (file == null);

		return file;
	}

}
