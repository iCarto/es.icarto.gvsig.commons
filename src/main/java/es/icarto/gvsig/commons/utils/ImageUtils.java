package es.icarto.gvsig.commons.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
			Image scaled = read.getScaledInstance((int) scaledSize.getWidth(), (int) scaledSize.getHeight(),
					Image.SCALE_SMOOTH);
			return new ImageIcon(scaled);
		}
	}

	public static Image getScaledAsImage(String path, Dimension boundary) {
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
			return read;
		} else {
			Image scaled = read.getScaledInstance((int) scaledSize.getWidth(), (int) scaledSize.getHeight(),
					Image.SCALE_SMOOTH);
			return scaled;
		}
	}

	public static BufferedImage getRenderedImage(Image in) {
		int w = in.getWidth(null);
		int h = in.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage out = new BufferedImage(w, h, type);
		Graphics2D g2 = out.createGraphics();
		g2.drawImage(in, 0, 0, null);
		g2.dispose();
		return out;
	}

	public static ByteArrayInputStream toInputStream(BufferedImage image) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "png", os);
		os.flush();
		return new ByteArrayInputStream(os.toByteArray());
	}

	/**
	 * http://stackoverflow.com/a/10245583/930271 Returns a Dimension that fits on
	 * boundary keeping the aspect ratio of imgSize
	 *
	 * @param imgSize
	 * @param boundary
	 * @return
	 */
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

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

	public static byte[] convertImageToBytea(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		baos.flush();
		byte[] imageBytes = baos.toByteArray();
		baos.close();
		return imageBytes;
	}

	public static byte[] convertImageToBytea(File image) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(image);
		return convertImageToBytea(bufferedImage);
	}

	public static BufferedImage convertByteaToImage(byte[] imageBytes) {
		InputStream in = new ByteArrayInputStream(imageBytes);
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int width, int height) {
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

}
