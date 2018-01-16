package es.icarto.gvsig.commons.testutils;

import com.iver.cit.gvsig.project.documents.view.gui.View;

@SuppressWarnings("serial")
public class ViewStub extends View {
    public ViewStub() {
	m_MapControl = new MapControlStub();
    }
}