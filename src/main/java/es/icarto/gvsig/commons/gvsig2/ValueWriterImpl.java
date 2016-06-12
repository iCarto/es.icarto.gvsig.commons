package es.icarto.gvsig.commons.gvsig2;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Fernando Gonz�lez Cort�s
 */
class ValueWriterImpl implements ValueWriter {

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(long)
     */
    @Override
    public String getStatementString(long i) {
	return Long.toString(i);
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(int,
     *      int)
     */
    @Override
    public String getStatementString(int i, int sqlType) {
	return Integer.toString(i);
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(double,
     *      int)
     */
    @Override
    public String getStatementString(double d, int sqlType) {
	return Double.toString(d);
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(java.lang.String,
     *      int)
     */
    @Override
    public String getStatementString(String str, int sqlType) {
	return "'" + escapeString(str) + "'";
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(java.sql.Date)
     */
    @Override
    public String getStatementString(Date d) {
	return "'" + d.toString() + "'";
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(java.sql.Time)
     */
    @Override
    public String getStatementString(Time t) {
	return "'" + timeFormat.format(t) + "'";
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(java.sql.Timestamp)
     */
    @Override
    public String getStatementString(Timestamp ts) {
	return "'" + ts.toString() + "'";
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(byte[])
     */
    @Override
    public String getStatementString(byte[] binary) {
	StringBuffer sb = new StringBuffer("'");
	for (int i = 0; i < binary.length; i++) {
	    int byte_ = binary[i];
	    if (byte_ < 0) {
		byte_ = byte_ + 256;
	    }
	    String b = Integer.toHexString(byte_);
	    if (b.length() == 1) {
		sb.append("0").append(b);
	    } else {
		sb.append(b);
	    }

	}
	sb.append("'");

	return sb.toString();
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getStatementString(boolean)
     */
    @Override
    public String getStatementString(boolean b) {
	return Boolean.toString(b);
    }

    /**
     * @see es.icarto.gvsig.navtable.gvsig2.hardcode.gdbms.engine.values.ValueWriter#getNullStatementString()
     */
    @Override
    public String getNullStatementString() {
	return "null";
    }

    static String escapeString(String string) {
	return string.replaceAll("\\Q'\\E", "''");
    }

}