package es.icarto.gvsig.commons.utils;

import java.awt.GridBagConstraints;
import java.beans.PropertyVetoException;

import javax.swing.text.View;

import org.gvsig.andami.PluginServices;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.app.ApplicationLocator;
import org.gvsig.app.ApplicationManager;
import org.gvsig.app.project.ProjectManager;
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
     * If the active window is a View returns it, if not creates a new one, adds
     * it to the project and returns it
     */
    public static IView createViewIfNeeded(String viewName, String crs) {
	IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();
	if (iWindow instanceof View) {
	    return (IView) iWindow;
	}
	return createView(viewName, crs);
    }

    public static IView createView(String viewName, String crs) {
	I18nManager i18nManager = ToolsLocator.getI18nManager();
	ApplicationManager application = ApplicationLocator.getManager();

	ProjectManager projectManager = application.getProjectManager();

	// Create a new view and set the name.
	ViewManager viewManager = (ViewManager) projectManager
		.getDocumentManager(ViewManager.TYPENAME);
	ViewDocument view = (ViewDocument) viewManager.createDocument();

	view.setName(i18nManager.getTranslation(viewName));

	view.getMapContext().setProjection(CRSFactory.getCRS(crs));

	// Add the view to the current project.
	projectManager.getCurrentProject().add(view);

	// Force to show the view's window.
	IView viewWindow = (IView) viewManager.getMainWindow(view);

	application.getUIManager().addWindow(viewWindow,
		GridBagConstraints.CENTER);
	try {
	    application.getUIManager().setMaximum(viewWindow, true);
	} catch (PropertyVetoException e) {
	    logger.info("Can't maximize view.", e);
	}

	return viewWindow;
    }
}