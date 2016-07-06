package es.icarto.gvsig.commons.gui;

import static es.icarto.gvsig.commons.i18n.I18n._;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gvsig.andami.PluginServices;
import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.app.project.documents.view.gui.IView;
import org.gvsig.fmap.mapcontext.layers.FLayer;

@SuppressWarnings("serial")
public class ChooseLayerPanel<T extends FLayer> extends JPanel {

	public enum Orientation {
		VERTICAL, HORIZONTAL, PLACEHOLDER, NONE
	}

	private final LayerItem EMPTY = new LayerItem(null);

	private final class LayerItem {

		/**
		 * FLayer classes overrides toString method for some opaque reason, so
		 * we can not put it directly on a combo This is a wrapper
		 */
		private final T layer;
		private final String name;

		public LayerItem(T layer) {
			this.layer = layer;
			this.name = layer != null ? layer.getName() : "";
		}

		public T getLayer() {
			return this.layer;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	private final JComboBox layerCombo;

	private final Class<T> clasz;

	private final JPanel parentComp;

	private static final String prototypeDisplayValue = "XXXXXXXXXXXXXXXXXXXX";

	/**
	 * @text the text to use for the label. If null or empty Orientation.NONE
	 *       will be assumed. The text will be automatically i18n through
	 *       PluginServices
	 *
	 * @param orientation
	 *            if the label of the combo should be in the left of the combo
	 *            (HORIZONTAL), over the combo (VERTICAL) or instead of use a
	 *            label a placeholder should be used in the combo itself
	 *            (PLACEHOLDER), or even with no label at all (NONE)
	 */
	public ChooseLayerPanel(JPanel parentComp, String text,
			Orientation orientation, Class<T> clasz) {

		this.clasz = clasz;
		this.parentComp = parentComp;

		JLabel label = null;
		if ((text == null) || text.isEmpty()) {
			orientation = Orientation.NONE;
		} else {
			label = new JLabel(_(text));
		}

		layerCombo = new JComboBox();
		layerCombo.setPrototypeDisplayValue(prototypeDisplayValue);

		switch (orientation) {
		case HORIZONTAL:
			parentComp.add(label);
			parentComp.add(layerCombo, "wrap, growx");
			break;

		default:
			throw new AssertionError("Not implemented jet");
		}

	}

	/*
	 * Preselects in the combo the first layer active of type T and returns it
	 * or null if there is no active layer
	 */
	public T preselectFirstActive() {
		for (int i = 0; i < layerCombo.getItemCount(); i++) {
			LayerItem itemAt = (LayerItem) layerCombo.getItemAt(i);

			T l = itemAt.getLayer();
			if ((l != null) && l.isActive()) {
				layerCombo.setSelectedIndex(i);
				return l;
			}
		}
		return null;
	}

	/**
	 * Adds an empty first item in the combo and preselects it [preselect] is
	 * true Take care with the order in which preselectFirstActive and this
	 * method is used
	 *
	 * Should be called before populateFrom
	 *
	 * @param preselect
	 */
	public void addEmptyFirst(boolean preselect) {
		layerCombo.addItem(EMPTY);
	}

	/**
	 * Returns the selected layer or null if none is selected
	 */
	public T getSelected() {
		LayerItem sel = (LayerItem) layerCombo.getSelectedItem();
		return sel != null ? sel.getLayer() : null;
	}

	/**
	 * Returns a list with the layers used to fill the combo
	 *
	 * @param filter
	 *            can be null to populate with all layers of the class defined
	 *            in the constructor
	 */
	public List<T> populateFrom(IView view, LayerFilter filter) {
		TOCLayerManager toc = new TOCLayerManager(view.getMapControl());
		List<T> allLayers = toc.getAllLayers(clasz);

		for (T t : allLayers) {
			if ((filter == null) || (filter.accept(t))) {
				layerCombo.addItem(new LayerItem(t));
			}
		}
		return allLayers;
	}

	public List<T> populateFromActiveView() {
		return populateFromActiveView(null);
	}

	private IView getView() {
		IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();
		if (iWindow instanceof IView) {
			return (IView) iWindow;
		}
		return null;
	}

	public List<T> populateFromActiveView(LayerFilter filter) {
		IView view = getView();
		if (view != null) {
			return populateFrom(view, filter);
		}
		return null;
	}

	public Component getDefaultFocusComponent() {
		return layerCombo;
	}
}
