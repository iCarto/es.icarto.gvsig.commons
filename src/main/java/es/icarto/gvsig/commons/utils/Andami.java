package es.icarto.gvsig.commons.utils;

import java.awt.GridBagConstraints;
import java.beans.PropertyVetoException;

import org.cresques.cts.IProjection;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.andami.ui.mdiManager.MDIManagerFactory;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.ProjectManager;
import org.gvsig.app.project.documents.Document;
import org.gvsig.app.project.documents.view.ViewDocument;
import org.gvsig.app.project.documents.view.ViewManager;
import org.gvsig.app.project.documents.view.gui.IView;
import org.gvsig.fmap.crs.CRSFactory;
import org.gvsig.tools.ToolsLocator;
import org.gvsig.tools.i18n.I18nManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Andami {

	private static final Logger logger = LoggerFactory.getLogger(Andami.class);

	/**
	 * If the active window is a View closes it. Then creates a new one, adds it to
	 * the project and returns it
	 *
	 * @param crs If null the default projection will be used
	 */
	public static IView createViewIfNeeded(String viewName, String crs) {
		final IWindow iWindow = MDIManagerFactory.getManager().getActiveWindow();
		IView view = null;
		if (iWindow instanceof IView) {
			view = (IView) iWindow;
			if (view.getMapControl().getProjection().getAbrev().equals(crs)) {
				return view;
			}
			final ApplicationManager application = ApplicationLocator.getManager();
			final Document doc = ((IView) iWindow).getDocument();
			MDIManagerFactory.getManager().closeSingletonWindow(doc);
			application.getCurrentProject().removeDocument(doc);
		}
		return createView(viewName, crs);
	}

	public static IView createView(String viewName, String crs) {
		final I18nManager i18nManager = ToolsLocator.getI18nManager();
		final ApplicationManager application = ApplicationLocator.getManager();

		final ProjectManager projectManager = application.getProjectManager();

		// Create a new view and set the name.
		final ViewManager viewManager = (ViewManager) projectManager.getDocumentManager(ViewManager.TYPENAME);
		final ViewDocument view = (ViewDocument) viewManager.createDocument();

		view.setName(i18nManager.getTranslation(viewName));

		IProjection proj;
		if (crs == null) {
			proj = projectManager.getCurrentProject().getProjection();
		} else {
			proj = CRSFactory.getCRS(crs);
		}

		view.getMapContext().setProjection(proj);

		// Add the view to the current project.
		projectManager.getCurrentProject().addDocument(view);

		// Force to show the view's window.
		final IView viewWindow = (IView) viewManager.getMainWindow(view);

		application.getUIManager().addWindow(viewWindow, GridBagConstraints.CENTER);
		try {
			application.getUIManager().setMaximum(viewWindow, true);
		} catch (final PropertyVetoException e) {
			logger.info("Can't maximize view.", e);
		}

		return viewWindow;
	}
}
