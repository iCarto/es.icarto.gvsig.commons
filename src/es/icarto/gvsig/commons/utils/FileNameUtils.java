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
	int lastDotIndex = name.lastIndexOf(".");
	int l = lastDotIndex != -1 ? lastDotIndex : name.length();
	return name.substring(0, l);
    }

    /**
     * Returns the substring after the last '.' (not included) in the "name" in
     * lowercase
     */
    public static String getExtension(String name) {
	return name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    }

    public static String replaceExtension(String file, String ext) {
	file = removeExtension(file);
	ext = ext.startsWith(".") ? ext : "." + ext;
	return file + ext;
    }

    /*
     * If "name" ends with the "extension" ignoring case returns the same name
     * If not, returns "name" + "extension".toLowerCase() If the passed extesion
     * does not have a "." at the beginning adds it first
     */
    public static String ensureExtension(String name, String extension) {
	extension = extension.startsWith(".") ? extension : "." + extension;
	extension = extension.toLowerCase();

	String nameLowercase = name.toLowerCase();

	return nameLowercase.endsWith(extension) ? name : name + extension;

    }

}
