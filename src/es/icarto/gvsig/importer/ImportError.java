package es.icarto.gvsig.importer;

public class ImportError {

    private final String errorMsg;
    private final int row;

    public ImportError(String errorMsg, int row) {
	this.errorMsg = errorMsg;
	this.row = row;
    }

    @Override
    public String toString() {
	return errorMsg;
    }

}
