package es.icarto.gvsig.commons.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zip {

	private static final Logger logger = LoggerFactory.getLogger(Zip.class);

	private final Map<String, File> fileList;

	public Zip() {
		fileList = new HashMap<String, File>();
	}

	/**
	 *
	 * @param file
	 *            Could be a single file or a folder that will be zipped
	 */
	public Zip(File file) {
		this();
		generateFileList(file);
	}

	/**
	 * Adds a file or folder to be zipped
	 *
	 */
	public void addFile(File file) {
		generateFileList(file);
	}

	/**
	 * Zip it
	 *
	 * @param zipFile
	 *            output ZIP file location
	 * @throws IOException
	 */
	public void zipIt(String zipFile) throws IOException {

		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {

			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			for (String file : fileList.keySet()) {

				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(fileList.get(file));

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
		} finally {
			close(zos);
			close(fos);
		}
	}

	private void close(Closeable is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void generateFileList(File node) {
		if (node.isFile()) {
			fileList.put(node.getName(), node);
		}

		if (node.isDirectory()) {
			throw new RuntimeException("Not implemented");
		}

	}
}