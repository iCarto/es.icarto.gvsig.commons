package es.icarto.gvsig.commons.gvsig2;

import org.gvsig.fmap.dal.exception.DataException;
import org.gvsig.fmap.dal.feature.Feature;
import org.gvsig.fmap.geom.Geometry;

public interface IEditableSource {

    public String getName();

    public long posOfFeature(Feature feat);

    public Geometry getGeometry(long pos);

    public int getFieldType(int i);

    public boolean isWritable();

    public void modifyRow(int rowPosition, DefaultFeature newRow)
	    throws DataException;

    public DefaultFeature getRow(long pos);

    public void removeRow(long position) throws DataException;
    
    public boolean isEditing();
    
    public int getFieldIndexByName(String name);

	public long getRowCount() throws DataException;

	public void reload() throws DataException;

}
