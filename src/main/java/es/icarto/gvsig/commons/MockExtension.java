package es.icarto.gvsig.commons;

import org.gvsig.andami.plugins.Extension;

public class MockExtension extends Extension {

    @Override
    public void initialize() {
    }

    @Override
    public void execute(String actionCommand) {
    }

    @Override
    public boolean isEnabled() {
	return false;
    }

    @Override
    public boolean isVisible() {
	return false;
    }

}
