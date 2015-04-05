package es.icarto.gvsig.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

public class FileUtils {

    private static final Logger logger = Logger.getLogger(FileUtils.class);

    private FileUtils() {
	throw new AssertionError("Not instantiable class");
    }

    /**
     * @param contents
     *            to be write to disk
     * @param file
     *            to write
     * @return false if there was any trouble, so if (!
     *         FileUtils.write(contents, file)) { handle error }
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

}
