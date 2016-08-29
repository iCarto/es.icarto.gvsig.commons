package es.icarto.gvsig.commons.gvsig2;

import java.sql.Types;

import org.gvsig.tools.dataTypes.DataTypes;

public class TypeConversion {

	private TypeConversion() {
		throw new RuntimeException("Only static methods");
	}

	public static int from2to1(int gvsig2Type) {
		switch (gvsig2Type) {
		case DataTypes.BOOLEAN:
			return Types.BOOLEAN;
		case DataTypes.BYTE:
			return Types.SMALLINT;
		case DataTypes.INT:
			return Types.INTEGER;
		case DataTypes.LONG:
			return Types.BIGINT;
		case DataTypes.CHAR:
			return Types.CHAR;
		case DataTypes.FLOAT:
			return Types.FLOAT;
		case DataTypes.DOUBLE:
			return Types.DOUBLE;
		case DataTypes.STRING:
			return Types.VARCHAR;
		case DataTypes.DATE:
			return Types.DATE;
		case DataTypes.TIME:
			return Types.TIME;
		case DataTypes.TIMESTAMP:
			return Types.TIMESTAMP;
		default:
			throw new RuntimeException("Not supported type");
		}
	}

}
