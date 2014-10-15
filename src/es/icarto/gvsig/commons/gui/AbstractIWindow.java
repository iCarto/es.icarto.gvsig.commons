package es.icarto.gvsig.commons.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

@SuppressWarnings("serial")
/**
 * A gvSIG IWindow that autocalculates its size
 * 
 */
public abstract class AbstractIWindow extends JPanel implements IWindow {

    private WindowInfo windowInfo;
    private String title = "";
    private int windowInfoProperties = WindowInfo.MODALDIALOG;

    public AbstractIWindow() {
	super(new MigLayout("insets 10"));
    }

    public AbstractIWindow(LayoutManager layout) {
	super(layout);
    }

    public void openDialog() {
	if (getWindowInfo().isModeless()) {
	    PluginServices.getMDIManager().addCentredWindow(this);
	} else {
	    PluginServices.getMDIManager().addWindow(this);
	}
    }

    @Override
    public Object getWindowProfile() {
	return WindowInfo.DIALOG_PROFILE;
    }

    @Override
    public WindowInfo getWindowInfo() {
	if (windowInfo == null) {
	    windowInfo = new WindowInfo(windowInfoProperties);

	    windowInfo.setTitle(title);
	    Dimension dim = getPreferredSize();
	    // To calculate the maximum size of a form we take the size of the
	    // main frame minus a "magic number" for the menus, toolbar, state
	    // bar
	    // Take into account that in edition mode there is less available
	    // space
	    MDIFrame a = (MDIFrame) PluginServices.getMainFrame();
	    final int MENU_TOOL_STATE_BAR = 205;
	    int maxHeight = a.getHeight() - MENU_TOOL_STATE_BAR;
	    int maxWidth = a.getWidth() - 15;

	    int width, heigth = 0;
	    if (dim.getHeight() > maxHeight) {
		heigth = maxHeight;
	    } else {
		heigth = new Double(dim.getHeight()).intValue();
	    }
	    if (dim.getWidth() > maxWidth) {
		width = maxWidth;
	    } else {
		width = new Double(dim.getWidth()).intValue();
	    }

	    // If the window are so big, and slides under andami, touch the code
	    // before here, not the following lines
	    windowInfo.setWidth(width);
	    if (windowInfo.isModal()) {
		// andami adds 30 for modal windows. If modal look at
		// NewSkin.addJDialog, if not FrameWindowSupport.
		windowInfo.setHeight(heigth - 30);
	    } else {
		windowInfo.setHeight(heigth);
	    }
	}
	return windowInfo;
    }

    protected void setWindowTitle(String title) {
	this.title = title;
    }

    protected void setWindowInfoProperties(int properties) {
	this.windowInfoProperties = properties;
    }

}
