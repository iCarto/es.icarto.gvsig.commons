package es.icarto.gvsig.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Based on http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
public class Unzip {

	private static final Logger logger = LoggerFactory.getLogger(Unzip.class);

	public static void unzip(File zipFile, File outputFolder) {

		byte[] buffer = new byte[1024];

		try {

			// create output directory is not exists
			if (!outputFolder.exists()) {
				outputFolder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				if (ze.isDirectory()) {
					if (!newFile.isDirectory()) {
						newFile.mkdir();
					}
				} else {
					FileOutputStream fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}