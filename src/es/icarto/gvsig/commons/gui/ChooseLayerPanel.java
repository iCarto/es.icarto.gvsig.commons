package es.icarto.gvsig.commons.gui;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.gui.View;

@SuppressWarnings("serial")
/**
 * The api of this class will change in the future. Comment that you are going
 * to use
 */
@Deprecated
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
	    label = new JLabel(PluginServices.getText(this, text));
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
	return sel.getLayer();
    }

    /**
     * Returns a list with the layers used to fill the combo
     */
    public List<T> populateFrom(View view, LayerFilter filter) {
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

    private View getView() {
	IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();
	if (iWindow instanceof View) {
	    return (View) iWindow;
	}
	return null;
    }

    public List<T> populateFromActiveView(LayerFilter filter) {
	View view = getView();
	if (view != null) {
	    return populateFrom(view, filter);
	}
	return null;
    }

    public Component getDefaultFocusComponent() {
	return layerCombo;
    }
}
