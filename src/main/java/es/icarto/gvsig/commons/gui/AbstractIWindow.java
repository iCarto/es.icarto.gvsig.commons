package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.gvsig.andami.PluginServices;
import org.gvsig.andami.ui.mdiFrame.MDIFrame;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.andami.ui.mdiManager.IWindowListener;
import org.gvsig.andami.ui.mdiManager.MDIManagerFactory;
import org.gvsig.andami.ui.mdiManager.WindowInfo;

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
			MDIManagerFactory.getManager().addCentredWindow(this);
			if (getDefaultButton() != null) {
				getRootPane().setDefaultButton(getDefaultButton());
			}
			// getRootPane().setFocusTraversalPolicyProvider(true);
			setFocusCycleRoot(true);
			if (getDefaultFocusComponent() != null) {
				getDefaultFocusComponent().requestFocusInWindow();
			}
		} else {
			// Si la ventana es modal el c�digo se queda bloqueado tras
			// a�adir
			// la ventana hasta que el usuario la cierra, y antes de que la
			// ventana sea a�adida el JRootPane no es creado, por lo que con
			// ventanas modales no se puede user un default button. A no ser que
			// se haga algo un poco distinto
			MDIManagerFactory.getManager().addCentredWindow(this);
		}
	}

	protected abstract JButton getDefaultButton();

	protected abstract Component getDefaultFocusComponent();

	public void closeDialog() {
		MDIManagerFactory.getManager().closeWindow(this);
	}

	@Override
	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

	@Override
	public WindowInfo getWindowInfo() {
		if (windowInfo == null) {
			MDIFrame mainFrame = (MDIFrame) PluginServices.getMainFrame();
			windowInfo = getWindowInfo(mainFrame);
		}
		return windowInfo;
	}

	protected WindowInfo getWindowInfo(Component frame) {
		WindowInfo wInfo = new WindowInfo(windowInfoProperties);

		wInfo.setTitle(title);
		Dimension dim = getPreferredSize();
		// To calculate the maximum size of a form we take the size of the
		// main frame minus a "magic number" for the menus, toolbar, state
		// bar
		// Take into account that in edition mode there is less available
		// space
		final int MENU_TOOL_STATE_BAR = 205;
		int maxHeight = frame.getHeight() - MENU_TOOL_STATE_BAR;
		int maxWidth = frame.getWidth() - 15;

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
		if (wInfo.isModal()) {
			heigth -= 30;
		}

		wInfo.setWidth(width);
		wInfo.setHeight(heigth);
		return wInfo;
	}

	public void setWindowTitle(String title) {
		this.title = _(title);
	}

	public void setWindowInfoProperties(int properties) {
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
