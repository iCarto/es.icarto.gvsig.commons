package es.icarto.gvsig.commons.gvsig2;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Fernando Gonz�lez Cort�s
 */
class ValueWriterImpl implements ValueWriter {

	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public String getStatementString(long i) {
		return Long.toString(i);
	}

	@Override
	public String getStatementString(int i, int sqlType) {
		return Integer.toString(i);
	}

	@Override
	public String getStatementString(double d, int sqlType) {
		return Double.toString(d);
	}

	@Override
	public String getStatementString(String str, int sqlType) {
		return "'" + escapeString(str) + "'";
	}

	@Override
	public String getStatementString(Date d) {
		return "'" + d.toString() + "'";
	}

	@Override
	public String getStatementString(Time t) {
		return "'" + timeFormat.format(t) + "'";
	}

	@Override
	public String getStatementString(Timestamp ts) {
		return "'" + ts.toString() + "'";
	}

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

	@Override
	public String getStatementString(boolean b) {
		return Boolean.toString(b);
	}

	@Override
	public String getNullStatementString() {
		return "null";
	}

	static String escapeString(String string) {
		return string.replaceAll("\\Q'\\E", "''");
	}

}
