package es.icarto.gvsig.commons.locatorbycoords;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.gvsig.andami.ui.mdiManager.IWindow;
import org.gvsig.andami.ui.mdiManager.WindowInfo;

import es.icarto.gvsig.commons.gui.AbstractIWindow;
import es.icarto.gvsig.commons.gui.IWindowClosed;
import es.icarto.gvsig.commons.gui.PlaceholderTextField;
import es.icarto.gvsig.commons.referencing.gvsig.GPoint;
import es.icarto.gvsig.commons.utils.Style;

@SuppressWarnings("serial")
public class LocatorByCoordsDialog extends AbstractIWindow implements
		DocumentListener {

	private final static int WIDGET_SIZE = 23;
	private final LocatorByCoordsModel model;

	private JComboBox<CoordProvider> inputProj;
	private PlaceholderTextField inputX;
	private PlaceholderTextField inputY;

	private JComboBox<CoordProvider> outputProj;
	private JTextField outputY;
	private JTextField outputX;

	public LocatorByCoordsDialog(LocatorByCoordsModel locatorByCoordsModel) {
		super();
		this.model = locatorByCoordsModel;
		setupUI();
		setWindowTitle("es.icarto.gvsig.siga.locatorbycoords.LocatorByCoordsDialog.title");
		setWindowInfoProperties(WindowInfo.MODELESSDIALOG | WindowInfo.PALETTE);
		setWindowClosed(new IWindowClosed() {
			@Override
			public void windowClosed(IWindow window) {
				model.getZoomTo().clearGraphics();
				;
			}
		});
	}

	private void setupUI() {
		// setupMagicField();
		setupInput();
		setupOuput();
	}

	private void setupMagicField() {
		JTextField input = new PlaceholderTextField(
				"Pegue o escriba la localización");
		this.add(input, "wrap, span, growx, pushx");
	}

	private void setupInput() {

		inputX = new PlaceholderTextField("utm X (548076,36) o Long (-8,4062)");
		inputX.setColumns(WIDGET_SIZE);
		inputX.getDocument().addDocumentListener(this);
		this.add(inputX, "growx");

		inputY = new PlaceholderTextField("utm Y (4803838,61) o Lat (43,386)");
		inputY.setColumns(WIDGET_SIZE);
		inputY.getDocument().addDocumentListener(this);
		this.add(inputY, "growx");

		inputProj = new JComboBox<CoordProvider>();
		this.add(inputProj, "wrap, growx, sizegroup bttn");

		CoordProvider[] array = model.getProjCodes().toArray(
				new CoordProvider[0]);
		ComboBoxModel<CoordProvider> aModel = new DefaultComboBoxModel<CoordProvider>(
				array);
		inputProj.setModel(aModel);
		// inputProj.setPrototypeDisplayValue("EPSG:XXXXXXXXX");
		inputProj.setSelectedItem(model.getDefaultInputProj());
		inputProj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valueChanged();
			}
		});

		resetPlaceHolder();

	}

	private void resetPlaceHolder() {
		CoordProvider coordProv = (CoordProvider) inputProj.getSelectedItem();
		String[] placeholder = coordProv.getPlaceholderText();
		if (placeholder[0] != null) {
			inputX.setPlaceholder(placeholder[0]);
			inputY.setPlaceholder(placeholder[1]);
		}
	}

	private void setupOuput() {

		outputX = new JTextField();
		outputX.setColumns(WIDGET_SIZE);
		outputX.setEditable(false);
		this.add(outputX, "growx");

		outputY = new JTextField();
		outputY.setColumns(WIDGET_SIZE);
		outputY.setEditable(false);
		this.add(outputY, "growx");

		outputProj = new JComboBox<CoordProvider>();
		this.add(outputProj, "wrap, growx, sizegroup bttn");

		outputProj.setModel(new DefaultComboBoxModel<CoordProvider>(model
				.getProjCodes().toArray(new CoordProvider[0])));
		// outputProj.setPrototypeDisplayValue("EPSG:XXXXXXX");
		outputProj.setSelectedItem(model.getDefaultOuputProj());
		outputProj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valueChanged();
			}
		});
	}

	public void addButton(LocatorByCoordsButton bt) {
		model.addBt(bt);
		this.add(bt, "span, split, sizegroup bttn, align right");
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		valueChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		valueChanged();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// nothing to do here
	}

	private void valueChanged() {
		resetPlaceHolder();

		CoordProvider iProv = (CoordProvider) inputProj.getSelectedItem();
		CoordProvider oProv = (CoordProvider) outputProj.getSelectedItem();
		GPoint point = null;

		final boolean validX = iProv.validX(inputX.getText());
		final boolean validY = iProv.validY(inputY.getText());

		if (validX) {
			inputX.setBackground(UIManager.getColor("TextField.background"));
		} else {
			inputX.setBackground(Style.INVALID_COLOR);
		}

		if (validY) {
			inputY.setBackground(UIManager.getColor("TextField.background"));
		} else {
			inputY.setBackground(Style.INVALID_COLOR);
		}

		if (validX && validY) {
			point = iProv.toGPoint(inputX.getText(), inputY.getText());
			String[] o = oProv.textCoordinates(point);
			outputX.setText(o[0]);
			outputY.setText(o[1]);

		} else {
			outputX.setText("");
			outputY.setText("");
		}
		model.updateState(validX, validY, iProv, oProv, point, false);

	}

	@Override
	protected JButton getDefaultButton() {
		return model.getBts().get(0);
	}

	@Override
	protected Component getDefaultFocusComponent() {
		return model.getBts().get(0);
	}

	public LocatorByCoordsModel getModel() {
		return model;
	}

}
