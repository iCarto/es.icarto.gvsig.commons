package es.icarto.gvsig.commons.datasources;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.iver.cit.gvsig.fmap.drivers.FieldDescription;

public class FieldDescriptionFactory {

    private int stringLength = 100;
    private int numericLength = 20;
    private int decimalCount = 6;

    private final List<FieldDescription> fields = new ArrayList<FieldDescription>();

    public void setDefaultStringLength(int defaultStringLength) {
        this.stringLength = defaultStringLength;
    }

    public void setDefaultNumericLength(int defaultNumericLength) {
        this.numericLength = defaultNumericLength;
    }

    public void setDefaultDecimalCount(int defaultDecimalCount) {
        this.decimalCount = defaultDecimalCount;
    }

    public FieldDescription addInteger(String name) {
        FieldDescription fd = getInteger(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription addBigInteger(String name) {
        FieldDescription fd = getInteger(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription getInteger(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldLength(numericLength);
        fd.setFieldDecimalCount(0);
        fd.setFieldType(Types.INTEGER);
        return fd;
    }

    public FieldDescription getBigInteger(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldLength(numericLength);
        fd.setFieldDecimalCount(0);
        fd.setFieldType(Types.BIGINT);
        return fd;
    }

    public FieldDescription addDouble(String name) {
        FieldDescription fd = getDouble(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription getDouble(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldLength(numericLength);
        fd.setFieldDecimalCount(decimalCount);
        fd.setFieldType(Types.DOUBLE);
        return fd;
    }

    public FieldDescription addString(String name) {
        FieldDescription fd = getString(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription getString(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldLength(stringLength);
        fd.setFieldDecimalCount(0);
        fd.setFieldType(Types.VARCHAR);
        return fd;
    }

    public FieldDescription addDate(String name) {
        FieldDescription fd = getDate(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription getDate(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldType(Types.DATE);
        return fd;
    }

    public FieldDescription addBoolean(String name) {
        FieldDescription fd = getBoolean(name);
        fields.add(fd);
        return fd;
    }

    public FieldDescription getBoolean(String name) {
        FieldDescription fd = new FieldDescription();
        fd.setFieldName(name);
        fd.setFieldType(Types.BIT);
        return fd;
    }

    public FieldDescription[] getFields() {
        return fields.toArray(new FieldDescription[0]);
    }

}
