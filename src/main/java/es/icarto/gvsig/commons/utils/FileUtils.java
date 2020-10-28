package es.icarto.gvsig.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

public class FileUtils {

	private static final Logger logger = Logger.getLogger(FileUtils.class);

	private FileUtils() {
		throw new AssertionError("Not instantiable class");
	}

	/**
	 * @param contents to be write to disk
	 * @param file     to write
	 * @return false if there was any trouble, so if (! FileUtils.write(contents,
	 *         file)) { handle error }
	 *
	 */
	public static boolean write(String contents, File file) {
		PrintStream ps = null;
		try {
			ps = new PrintStream(new FileOutputStream(file));
			ps.println(contents);
		} catch (FileNotFoundException e) {
			logger.error(e.getStackTrace(), e);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
		return ps != null ? !ps.checkError() : false;
	}

	// http://stackoverflow.com/a/115086/930271

	public static boolean copyFile(File sourceFile, File destFile) {

		FileChannel source = null;
		FileChannel destination = null;

		try {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} catch (IOException e) {
			logger.error(e.getStackTrace(), e);
			return false;
		} finally {
			if (source != null) {
				try {
					source.close();
				} catch (IOException e) {
					logger.error(e.getStackTrace(), e);
				}
			}
			if (destination != null) {
				try {
					destination.close();
				} catch (IOException e) {
					logger.error(e.getStackTrace(), e);
				}
			}
		}
		return true;
	}

}
