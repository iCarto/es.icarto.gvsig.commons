package es.icarto.gvsig.commons.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileNameUtilsTest {

    @Test
    public void testEnsureExtension() {
	assertEquals("foo.bar", FileNameUtils.ensureExtension("foo", "bar"));
	assertEquals("Foo.bar", FileNameUtils.ensureExtension("Foo", ".bar"));
	assertEquals("foO.Bar", FileNameUtils.ensureExtension("foO.Bar", "baR"));
    }

    @Test
    public void testRemoveExtension() {
	assertEquals("foo", FileNameUtils.removeExtension("foo.bar"));
	assertEquals("Foo.bar", FileNameUtils.removeExtension("Foo.bar.ext"));
	assertEquals("foO", FileNameUtils.removeExtension("foO"));
    }

}
