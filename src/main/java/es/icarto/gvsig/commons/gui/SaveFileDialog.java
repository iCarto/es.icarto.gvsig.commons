package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <code>SaveFileDialog</code> provides a simple mechanism for the user to
 * choose a file to write some content on it. It also provides methods to write
 * contents to disk. The user is prompted before overwrite an existing file and
 * a filter for show only some files based on their extension can be set. If a
 * filter is set the first extension provided to the constructor is used as
 * default extension. This is, if the user don't add it to the chosen file name
 * it's added.
 * <p>
 * The following code pops up a file chooser for the user's home directory that
 * sees only .html and .htm (and is equivalent in uppercase). .html is appended
 * to the name of the file. Then a String is writed to disk:
 *
 * <pre>
 * SaveFileDialog sfd = new SaveFileDialog(&quot;HTML files&quot;, &quot;html&quot;, &quot;htm&quot;);
 * File f = sfd.showDialog();
 * if (f != null) {
 * 	if (!sfd.writeFileToDisk(contents, f)) {
 * 		NotificationManager.showMessageError(&quot;error_saving_file&quot;, null);
 * 	}
 * }
 * </pre>
 *
 * </p>
 *
 */
@SuppressWarnings("serial")
@Deprecated
public class SaveFileDialog extends JFileChooser {

	private FileFilter filter = null;
	private String defaultExtension = null;
	private boolean askForOverwrite = true;

	/**
	 * if extensions are provided extensions[0] will be the appended to the fileName
	 * if it doesn't have jet
	 *
	 * @param description A string to be shown in the filter files combobox
	 * @param extensions  The extensions wanted to be filtered. Must be lowercase,
	 *                    uppercase validity is handled automaticaly. extensions[0]
	 *                    will be the default extension.
	 */
	public SaveFileDialog(String description, String... extensions) {
		super();
		this.filter = new FileNameExtensionFilter(description, extensions);
		this.defaultExtension = extensions[0];
	}

	public SaveFileDialog setAskForOverwrite(boolean askForOverwrite) {
		this.askForOverwrite = askForOverwrite;
		return this;
	}

	public SaveFileDialog() {
		super();
	}

	/**
	 * @return null if user cancel save operation, the File to save in other case.
	 */
	public File showDialog() {
		File file = null;
		if (filter != null) {
			setFileFilter(filter);
		}

		do {
			int returnVal = showSaveDialog(null);
			if (returnVal == JFileChooser.CANCEL_OPTION) {
				break;
			}

			File tmpFile = getSelectedFile();

			// Add the default extension
			if (defaultExtension != null) {
				if (!tmpFile.getName().toLowerCase().endsWith("." + defaultExtension)) {
					tmpFile = new File(tmpFile.getAbsolutePath() + "." + defaultExtension);
				}
			}

			if (askForOverwrite) {
				if (tmpFile.exists()) {
					int overwriteFile = JOptionPane.showConfirmDialog(null, _("file_already_exists"), _("warning"),
							JOptionPane.YES_NO_OPTION);
					if (overwriteFile == JOptionPane.NO_OPTION) {
						continue;
					}
				}
			}

			file = tmpFile;

		} while (file == null);

		return file;
	}

}