package es.icarto.gvsig.commons.utils;

//Simulates apache commons class
public class FileNameUtils {

    /**
     * Suppress default constructor for noninstantiability. AssertionError
     * avoids accidentally invoke the constructor within the class
     */
    private FileNameUtils() {
	throw new AssertionError();
    }

    public static String removeExtension(String name) {
	return name.substring(0, name.lastIndexOf("."));
    }

    public static String replaceExtension(String file, String ext) {
	file = removeExtension(file);
	ext = ext.startsWith(".") ? ext : "." + ext;
	return file + ext;
    }

}
