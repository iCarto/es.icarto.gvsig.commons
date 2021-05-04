package es.icarto.gvsig.commons;

import org.gvsig.andami.IconThemeHelper;
import org.gvsig.andami.plugins.Extension;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.andami.ui.mdiManager.MDIManagerFactory;
import org.gvsig.app.project.documents.view.gui.IView;

public abstract class AbstractExtension extends Extension {

	public final String id = this.getClass().getName();

	private void registerIcon(String iconName) {
		IconThemeHelper.registerIcon("action", iconName, this);
	}

	@Override
	public void initialize() {
		registerIcon(id);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	/**
	 * Returns the active window if is a View. Returns null elsewhere
	 */
	protected IView getView() {
		IWindow iWindow = MDIManagerFactory.getManager().getActiveWindow();
		if (iWindow instanceof IView) {
			return (IView) iWindow;
		}
		return null;
	}

	/**
	 *
	 * @return true if the active window is a View
	 */
	protected boolean isViewActive() {
		return getView() != null;
	}

}