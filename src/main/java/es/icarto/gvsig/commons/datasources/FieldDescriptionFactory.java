package es.icarto.gvsig.commons.datasources;

import java.util.ArrayList;
import java.util.List;

import org.gvsig.fmap.dal.DALLocator;
import org.gvsig.fmap.dal.DataManager;
import org.gvsig.fmap.dal.feature.EditableFeatureAttributeDescriptor;
import org.gvsig.fmap.dal.feature.EditableFeatureType;
import org.gvsig.fmap.dal.feature.FeatureAttributeDescriptor;
import org.gvsig.fmap.dal.feature.impl.DefaultEditableFeatureAttributeDescriptor;
import org.gvsig.tools.dataTypes.DataTypes;

/**
 * TODO Probably this class is no longer need in gvSIG 2 as it provides an easy
 * way to create new Fields:
 *
 * <pre>
 * EditableFeatureType featType = new DefaultEditableFeatureType();
 * EditableFeatureAttributeDescriptor attDesc = featType.add(fieldName, DataTypes.INT, numericLength);
 * </pre>
 *
 */
public class FieldDescriptionFactory {

	private final DataManager dataManager;

	private int stringLength = 100;
	private int numericLength = 20;
	private int decimalCount = 6;

	private final List<EditableFeatureAttributeDescriptor> fields = new ArrayList<EditableFeatureAttributeDescriptor>();

	public FieldDescriptionFactory() {
		dataManager = DALLocator.getDataManager();
	}

	public void setDefaultStringLength(int defaultStringLength) {
		this.stringLength = defaultStringLength;
	}

	public void setDefaultNumericLength(int defaultNumericLength) {
		this.numericLength = defaultNumericLength;
	}

	public void setDefaultDecimalCount(int defaultDecimalCount) {
		this.decimalCount = defaultDecimalCount;
	}

	public EditableFeatureAttributeDescriptor addInteger(String name) {
		EditableFeatureAttributeDescriptor fd = getInteger(name);
		fields.add(fd);
		return fd;
	}

	public EditableFeatureAttributeDescriptor getInteger(String name) {
		EditableFeatureAttributeDescriptor fd = dataManager.createFeatureAttributeDescriptor();
		fd.setName(name);
		fd.setSize(numericLength);
		fd.setPrecision(0);
		fd.setDataType(DataTypes.INT);
		return fd;
	}

	public EditableFeatureAttributeDescriptor addDouble(String name) {
		EditableFeatureAttributeDescriptor fd = getDouble(name);
		fields.add(fd);
		return fd;
	}

	public EditableFeatureAttributeDescriptor getDouble(String name) {
		EditableFeatureAttributeDescriptor fd = dataManager.createFeatureAttributeDescriptor();
		fd.setName(name);
		fd.setSize(numericLength);
		fd.setPrecision(decimalCount);
		fd.setDataType(DataTypes.DOUBLE);
		return fd;
	}

	public EditableFeatureAttributeDescriptor addString(String name) {
		EditableFeatureAttributeDescriptor fd = getString(name);
		fields.add(fd);
		return fd;
	}

	public EditableFeatureAttributeDescriptor getString(String name) {
		EditableFeatureAttributeDescriptor fd = dataManager.createFeatureAttributeDescriptor();
		fd.setName(name);
		fd.setSize(stringLength);
		fd.setPrecision(0);
		fd.setDataType(DataTypes.STRING);
		return fd;
	}

	public EditableFeatureAttributeDescriptor addDate(String name) {
		EditableFeatureAttributeDescriptor fd = getDate(name);
		fields.add(fd);
		return fd;
	}

	public EditableFeatureAttributeDescriptor getDate(String name) {
		EditableFeatureAttributeDescriptor fd = dataManager.createFeatureAttributeDescriptor();
		fd.setName(name);
		fd.setDataType(DataTypes.DATE);
		return fd;
	}

	public EditableFeatureAttributeDescriptor addBoolean(String name) {
		EditableFeatureAttributeDescriptor fd = getBoolean(name);
		fields.add(fd);
		return fd;
	}

	public EditableFeatureAttributeDescriptor getBoolean(String name) {
		EditableFeatureAttributeDescriptor fd = dataManager.createFeatureAttributeDescriptor();
		fd.setName(name);
		fd.setDataType(DataTypes.BOOLEAN);
		return fd;
	}

	public EditableFeatureAttributeDescriptor[] getFields() {
		return fields.toArray(new DefaultEditableFeatureAttributeDescriptor[0]);
	}

	public EditableFeatureType getFeatureType() {
		EditableFeatureType fType = dataManager.createFeatureType();
		for (FeatureAttributeDescriptor at : fields) {
			fType.addLike(at);
		}
		return fType;
	}

}
