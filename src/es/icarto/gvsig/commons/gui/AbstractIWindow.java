package es.icarto.gvsig.commons.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.IWindowListener;
import com.iver.andami.ui.mdiManager.WindowInfo;

@SuppressWarnings("serial")
/**
 * A gvSIG IWindow that autocalculates its size
 * 
 */
public abstract class AbstractIWindow extends JPanel implements IWindow,
	IWindowListener {

    private WindowInfo windowInfo;
    private String title = "";
    private int windowInfoProperties = WindowInfo.MODALDIALOG;
    private IWindowActivated windowActivated;
    private IWindowClosed windowClosed;

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

    public void closeDialog() {
	PluginServices.getMDIManager().closeWindow(this);
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

	    // if the vertical scrollbar is displayed more width is need
	    width += 5;
	    if (heigth == maxHeight) {
		width += 15;
	    }

	    // andami adds 30 for modal windows. If modal look at
	    // NewSkin.addJDialog, if not FrameWindowSupport.
	    if (windowInfo.isModal()) {
		heigth -= 30;
	    }

	    windowInfo.setWidth(width);
	    windowInfo.setHeight(heigth);

	}
	return windowInfo;
    }

    protected void setWindowTitle(String title) {
	this.title = PluginServices.getText(this, title);
    }

    protected void setWindowInfoProperties(int properties) {
	this.windowInfoProperties = properties;
    }

    public void setWindowActivated(IWindowActivated windowActivated) {
	this.windowActivated = windowActivated;
    }

    @Override
    public void windowActivated() {
	if (windowActivated != null) {
	    windowActivated.windowActivated(this);
	}
    }

    public void setWindowClosed(IWindowClosed windowClosed) {
	this.windowClosed = windowClosed;
    }

    @Override
    public void windowClosed() {
	if (windowClosed != null) {
	    // windowClosed it's called before the window is effectively removed
	    // from andami. So we set it as not visible to avoid the user see
	    // it;
	    this.setVisible(false);
	    windowClosed.windowClosed(this);

	    // workaround. Andami seems to called twice this method
	    windowClosed = null;
	}
    }

}
