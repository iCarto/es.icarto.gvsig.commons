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

public class FolderChooser {

	private static final String prototypeDisplayValue = "XXXXXXXXXXXXXXXXXXXXXXX";

	private JLabel label;
	private final JPanel parent;
	private final JTextField field;

	private final JFileChooser fileChooser;

	private final JButton bt;

	public FolderChooser(JPanel parentCmp, String labelText, String initFile) {
		this.parent = parentCmp;
		if (labelText != null) {
			label = new JLabel(labelText);
			parent.add(label);
		}

		field = new JTextField();
		field.setColumns(prototypeDisplayValue.length());
		if (initFile != null) {
			field.setText(initFile);
		}
		parent.add(field); // grow
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new DirFileFilter());

		bt = new JButton("...");
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser.setCurrentDirectory(new File(field.getText()));

				// setCurrentDirectory(field.getText().getParentFile());
				// setSelectedFile(field.getText);

				// fc.setDialogType(JFileChooser.OPEN_DIALOG);
				// fc.setDialogTitle(title);
				// fc.showDialog(parent, "Abrir")

				if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					field.setText(selectedFile.getAbsolutePath());
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

	public boolean isFolder() {
		File file = getFolder();
		return fileChooser.accept(file);
	}

	public File getFolder() {
		return new File(getFolderPath());
	}

	public String getFolderPath() {
		String path = field.getText().trim();
		return path.endsWith(File.separator) ? path : path + File.separator;
	}

	public void setEnabled(boolean enabled) {
		label.setEnabled(enabled);
		bt.setEnabled(enabled);
		field.setEnabled(enabled);
	}

	public void setSelectedFile(String file) {
		field.setText(file);
	}

	public class DirFileFilter extends javax.swing.filechooser.FileFilter {

		private String description = _("folders");

		public DirFileFilter() {
		}

		public DirFileFilter(String description) {
			this.description = description;
		}

		@Override
		public boolean accept(java.io.File f) {
			return f.isDirectory();
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

}
