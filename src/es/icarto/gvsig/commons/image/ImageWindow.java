package es.icarto.gvsig.commons.image;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.IWindowListener;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

import es.icarto.gvsig.commons.utils.ImageUtils;

@SuppressWarnings("serial")
public class ImageWindow extends JPanel implements SingletonWindow,
IWindowListener {

    private static final Logger logger = Logger.getLogger(ImageWindow.class);

    private final int imageWidth;
    private final int imageHeight;
    private final static int MARGIN = 50;
    private final String title;
    private String uniqueID;
    private WindowInfo viewInfo;
    private ResizeListener resizeListener = null;
    private ImageIcon imageIcon;
    private JLabel label;

    private class ResizeListener extends ComponentAdapter {
	private final String ident;

	public ResizeListener(String id) {
	    this.ident = id;
	}

	@Override
	public void componentResized(ComponentEvent e) {
	    Dimension thisSize = ImageWindow.this.getSize();
	    Dimension availableDim = new Dimension(thisSize.width - MARGIN,
		    thisSize.height - MARGIN);
	    Dimension imgDim = new Dimension(imageIcon.getIconWidth(),
		    imageIcon.getIconHeight());
	    Dimension dim = ImageUtils.getScaledDimension(imgDim, availableDim);
	    Image newImage = imageIcon.getImage().getScaledInstance(dim.width,
		    dim.height, Image.SCALE_SMOOTH);
	    ImageWindow.this.label.setIcon(new ImageIcon(newImage));

	}
    }

    public ImageWindow(String title, int imageWidth, int imageHeight) {
	super(new BorderLayout());
	this.imageWidth = imageWidth + MARGIN;
	this.imageHeight = imageHeight + MARGIN;
	this.title = title;
    }

    public ImageWindow(String title, ImageIcon imageIcon) {
	this(title, imageIcon.getIconWidth() + 5, imageIcon.getIconHeight() + 5);
	setImage(imageIcon);
    }

    public void setUniqueID(String uniqueID) {
	this.uniqueID = uniqueID;
    }

    @Override
    public WindowInfo getWindowInfo() {
	if (viewInfo == null) {
	    viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG
		    | WindowInfo.RESIZABLE);
	    viewInfo.setTitle(title);
	    viewInfo.setWidth(imageWidth);
	    viewInfo.setHeight(imageHeight);
	    IWindow[] windows = PluginServices.getMDIManager().getAllWindows();
	    int biggestX = -1;
	    int MAX_POSSIBLE_X = 600;
	    for (IWindow window : windows) {
		if (!(window instanceof ImageWindow)) {
		    continue;
		}
		int x = window.getWindowInfo().getX();
		if ((x > biggestX) && (x < MAX_POSSIBLE_X)) {
		    biggestX = x;
		}
	    }

	    viewInfo.updateX(biggestX + 20);
	    viewInfo.updateY(biggestX + 20);
	}
	return viewInfo;
    }

    @Override
    public Object getWindowProfile() {
	return null;
    }

    public void setImage(ImageIcon imageIcon) {
	this.imageIcon = imageIcon;
	this.label = new JLabel(imageIcon);
	this.add(new JScrollPane(this.label), BorderLayout.CENTER);

    }

    @Override
    public Object getWindowModel() {
	return uniqueID;
    }

    @Override
    public void windowActivated() {
	/*
	 * IWindowInfo no actualiza su tamaño al redimensionar la ventana a
	 * través de setWidth si no de updateWidth y por tanto no salta el
	 * listener de WindowInfo. Hay que escuchar al contenedor de este panel
	 * para escuchar el redimensionamiento. Pero el contenedor se crea
	 * "en el futuro" y no en la instanciación, de modo que la forma más
	 * segura de obtenerlo es en el windowActivated.
	 *
	 * Por prueba y error es en el 5 getParent en el que se pueden detectar
	 * estos cambios. Y la ventana tiene que ser "NO PALETTE" porque si no
	 * tampoco funciona
	 */
	if (resizeListener == null) {
	    resizeListener = new ResizeListener("internalFrame");
	    Container t = this.getParent();
	    for (int i = 0; i < 10; i++) {
		if (t instanceof com.iver.core.mdiManager.frames.InternalFrame) {
		    break;
		}
		t = t.getParent();
	    }
	    if (t instanceof com.iver.core.mdiManager.frames.InternalFrame) {
		t.addComponentListener(resizeListener);
	    } else {
		logger.info("Internal frame not found");
	    }
	}

    }

    @Override
    public void windowClosed() {
    }

}