package es.icarto.gvsig.commons.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

/**
 * The api of this class will change in the future. Comment that you are going
 * to use
 */
@Deprecated
@SuppressWarnings("serial")
public class ChainedComboPanel extends JPanel implements ItemListener, PropertyChangeListener {

	private static final String prototypeDisplayValue = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	private final List<JComboBox> l = new ArrayList<JComboBox>();

	private final JPanel parentComp;

	public ChainedComboPanel(JPanel parentComp, DefaultMutableTreeNode top) {
		super(new MigLayout());
		this.parentComp = parentComp;
		DefaultMutableTreeNode node = top;
		List<String> labels = (List<String>) top.getUserObject();

		int childCount = node.getChildCount();
		while (childCount > 0) {
			Object[] values = Collections.list(node.children()).toArray();
			JComboBox c = new JComboBox(values);
			c.setPrototypeDisplayValue(prototypeDisplayValue);
			l.add(c);
			c.addItemListener(this);
			c.addPropertyChangeListener("model", this);
			parentComp.add(new JLabel(labels.remove(0)));
			parentComp.add(c, "wrap, growx");
			node = (DefaultMutableTreeNode) node.getFirstChild();
			childCount = node.getChildCount();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			JComboBox combo = (JComboBox) event.getSource();
			updateNextCombo(combo);
		}
	}

	private void updateNextCombo(JComboBox combo) {
		int indexOf = l.indexOf(combo);
		if (indexOf < l.size() - 1) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) combo.getSelectedItem();
			JComboBox nextCombo = l.get(indexOf + 1);
			Object[] values = Collections.list(node.children()).toArray();
			ComboBoxModel aModel = new DefaultComboBoxModel(values);
			nextCombo.setModel(aModel);
		}
	}

	public Object getSelected() {
		JComboBox jComboBox = l.get(l.size() - 1);
		DefaultMutableTreeNode item = (DefaultMutableTreeNode) jComboBox.getSelectedItem();
		return item.getUserObject();
	}

	public List<Object> getAllSelected() {
		List<Object> selected = new ArrayList<Object>(l.size());
		for (JComboBox j : l) {
			DefaultMutableTreeNode item = (DefaultMutableTreeNode) j.getSelectedItem();
			selected.add(item.getUserObject());
		}
		return selected;
	}

	public Component getDefaultFocusComponent() {
		return l.get(0);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JComboBox combo = (JComboBox) evt.getSource();
		updateNextCombo(combo);
	}

}
