package es.icarto.gvsig.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
// Adapted by fpuga
public class Unzip {

    /**
     * Unzip it
     *
     * @param zipFile
     *            input zip file
     * @param output
     *            zip file output folder
     */
    public static void unzip(File zipFile, File outputFolder) {

	byte[] buffer = new byte[1024];

	try {

	    // create output directory is not exists
	    if (!outputFolder.exists()) {
		outputFolder.mkdir();
	    }

	    // get the zip file content
	    ZipInputStream zis = new ZipInputStream(
		    new FileInputStream(zipFile));
	    // get the zipped file list entry
	    ZipEntry ze = zis.getNextEntry();

	    while (ze != null) {

		String fileName = ze.getName();
		File newFile = new File(outputFolder + File.separator
			+ fileName);

		// create all non exists folders
		// else you will hit FileNotFoundException for compressed folder
		new File(newFile.getParent()).mkdirs();

		FileOutputStream fos = new FileOutputStream(newFile);

		int len;
		while ((len = zis.read(buffer)) > 0) {
		    fos.write(buffer, 0, len);
		}

		fos.close();
		ze = zis.getNextEntry();
	    }

	    zis.closeEntry();
	    zis.close();

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
}