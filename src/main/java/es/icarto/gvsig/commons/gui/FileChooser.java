package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FileChooser extends JPanel {

	public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
	public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;

	private static final String prototypeDisplayValue = "XXXXXXXXXXXXXXXXXXXX";

	private JLabel label;
	private final JTextField field;
	private final JButton bt;
	private final JPanel parent;

	private final JFileChooser fileChooser;

	public FileChooser(JPanel parentComp, String labelText, String initFile) {
		super(new MigLayout());
		this.parent = parentComp;

		if (labelText != null) {
			label = new JLabel(_(labelText));
			parent.add(label);
		}

		field = new JTextField();
		field.setColumns(prototypeDisplayValue.length());
		if (initFile != null) {
			field.setText(initFile);
		}

		parent.add(field);

		fileChooser = new JFileChooser();

		bt = new JButton("...");
		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(field.getText()));
				if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					field.setText(selectedFile.getAbsolutePath());
				}

			}
		});

		parent.add(bt, "wrap");
	}

	public Component getDefaultFocusComponent() {
		return field;
	}

	public void setToolTip2Components(String tooltip) {
		String translated = _(tooltip);
		if (label != null) {
			label.setToolTipText(translated);
		}
		field.setToolTipText(translated);
		bt.setToolTipText(translated);
	}

	/**
	 *
	 * @param description : Appears in the select file type combo
	 * @param extensions  without the '.'
	 */
	public void setFilter(String description, String... extensions) {
		fileChooser.setFileSelectionMode(FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter(_(description), extensions));
		// setAcceptAllFileFilterUsed(false);
	}

	public void addChoosableFilter(FileFilter filter) {
		fileChooser.addChoosableFileFilter(filter);
	}

	public void setFilter(int mode, FileFilter filter) {
		fileChooser.setFileSelectionMode(mode);
		fileChooser.setFileFilter(filter);
	}

	public boolean isValidAndExist() {
		File file = getFile();
		return file.exists() && fileChooser.accept(file);
	}

	public boolean isValidFile() {
		File file = getFile();
		if (fileChooser.getFileSelectionMode() == JFileChooser.FILES_ONLY) {
			if (file.isDirectory()) {
				return false;
			}
		}
		return fileChooser.accept(file);
	}

	public File getFile() {
		return new File(getFilePath());
	}

	public String getFilePath() {
		return field.getText().trim();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		label.setEnabled(enabled);
		bt.setEnabled(enabled);
		field.setEnabled(enabled);
	}

}
