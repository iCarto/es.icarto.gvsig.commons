package es.icarto.gvsig.commons.utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

public class ImageUtils {

    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    public static ImageIcon getScaled(String path, Dimension boundary) {
	BufferedImage read;
	try {
	    URL sourceFile = new URL("file:" + path);
	    read = ImageIO.read(sourceFile);
	} catch (MalformedURLException e) {
	    logger.error(e.getStackTrace(), e);
	    return null;
	} catch (IOException e) {
	    logger.error(e.getStackTrace(), e);
	    return null;
	}

	Dimension imgSize = new Dimension(read.getWidth(), read.getHeight());
	Dimension scaledSize = getScaledDimension(imgSize, boundary);
	if (scaledSize.equals(imgSize)) {
	    return new ImageIcon(read);
	} else {
	    Image scaled = read.getScaledInstance((int) scaledSize.getWidth(),
		    (int) scaledSize.getHeight(), Image.SCALE_SMOOTH);
	    return new ImageIcon(scaled);
	}
    }

    /**
     * http://stackoverflow.com/a/10245583/930271 Returns a Dimension that fits
     * on boundary keeping the aspect ratio of imgSize
     *
     * @param imgSize
     * @param boundary
     * @return
     */
    public static Dimension getScaledDimension(Dimension imgSize,
	    Dimension boundary) {

	int original_width = imgSize.width;
	int original_height = imgSize.height;
	int bound_width = boundary.width;
	int bound_height = boundary.height;
	int new_width = original_width;
	int new_height = original_height;

	// first check if we need to scale width
	if (original_width > bound_width) {
	    // scale width to fit
	    new_width = bound_width;
	    // scale height to maintain aspect ratio
	    new_height = (new_width * original_height) / original_width;
	}

	// then check if we need to scale even with the new height
	if (new_height > bound_height) {
	    // scale height to fit instead
	    new_height = bound_height;
	    // scale width to maintain aspect ratio
	    new_width = (new_height * original_width) / original_height;
	}

	return new Dimension(new_width, new_height);
    }

}
