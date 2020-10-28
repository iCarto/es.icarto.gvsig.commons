package es.icarto.gvsig.commons.gui;

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
public class SaveFileChooser extends JPanel {

	public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
	public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;

	private static final String prototypeDisplayValue = "XXXXXXXXXXXXXXXXXXXX";

	private JLabel label;
	private final JTextField field;
	private final JButton bt;
	private final JPanel parent;

	private final JFileChooser fileChooser;

	public SaveFileChooser(JPanel parentComp, String labelText, String initFile, final boolean saveDialog) {
		super(new MigLayout());
		this.parent = parentComp;

		if (labelText != null) {
			label = new JLabel(labelText);
			parent.add(label);
		}

		field = new JTextField();
		field.setColumns(prototypeDisplayValue.length());
		if (initFile != null) {
			field.setText(initFile);
		}

		parent.add(field);
		if (saveDialog) {
			fileChooser = new SaveFileDialog().setAskForOverwrite(false);

		} else {
			fileChooser = new JFileChooser();
		}
		bt = new JButton("...");
		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(field.getText()));
				if (saveDialog) {
					File selectedFile = ((SaveFileDialog) fileChooser).showDialog();
					if (selectedFile != null) {
						String absolutePath = selectedFile.getAbsolutePath();
						if (fileChooser.getFileFilter() instanceof FileNameExtensionFilter) {
							FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
							if (filter.getExtensions().length == 1) {
								String ext = filter.getExtensions()[0].toLowerCase();
								if (!absolutePath.toLowerCase().endsWith(ext)) {
									absolutePath += "." + ext;
								}
							}
						}
						field.setText(absolutePath);
					}
				} else {
					if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						field.setText(selectedFile.getAbsolutePath());
					}
				}

			}
		});

		parent.add(bt);
	}

	public Component getDefaultFocusComponent() {
		return field;
	}

	public void setToolTip2Components(String tooltip) {
		if (label != null) {
			label.setToolTipText(tooltip);
		}
		field.setToolTipText(tooltip);
		bt.setToolTipText(tooltip);
	}

	/**
	 *
	 * @param description : Appears in the select file type combo
	 * @param extensions  without the '.'
	 */
	public void setFilter(String description, String... extensions) {
		fileChooser.setFileSelectionMode(FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
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
