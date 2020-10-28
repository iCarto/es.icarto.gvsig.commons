package es.icarto.gvsig.commons.utils;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FileExtensionFilter extends FileFilter implements FilenameFilter {

	private final FileNameExtensionFilter extensionFilter;

	public FileExtensionFilter(String description, String... extensions) {
		extensionFilter = new FileNameExtensionFilter(description, extensions);
	}

	@Override
	public boolean accept(File f, String name) {
		return extensionFilter.accept(f);
	}

	@Override
	public boolean accept(File f) {
		return extensionFilter.accept(f);
	}

	@Override
	public String getDescription() {
		return extensionFilter.getDescription();
	}

}
