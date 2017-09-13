package es.icarto.gvsig.commons.utils;

import static es.icarto.gvsig.commons.utils.ImageUtils.getScaledDimension;
import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import org.junit.Test;

public class ImageUtilsTest {
    Dimension dim20_10 = new Dimension(20, 10);
    Dimension dim10_10 = new Dimension(10, 10);
    Dimension dim20_20 = new Dimension(20, 20);
    Dimension dim5_5 = new Dimension(5, 5);
    Dimension dim10_5 = new Dimension(10, 5);
    Dimension dim20_50 = new Dimension(20, 50);
    Dimension dim4_10 = new Dimension(4, 10);

    @Test
    public void testImgBiggerThanBoundary() {
	Dimension img = null;
	Dimension bound = null;
	Dimension actual = null;
	Dimension expected = null;

	img = new Dimension(dim10_10);
	bound = new Dimension(dim5_5);
	expected = new Dimension(dim5_5);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);

	img = new Dimension(dim20_10);
	bound = new Dimension(dim10_10);
	expected = new Dimension(dim10_5);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);

	img = new Dimension(dim20_50);
	bound = new Dimension(dim10_10);
	expected = new Dimension(dim4_10);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);
    }

    @Test
    public void testImgEqualsBoundary() {
	Dimension img = null;
	Dimension bound = null;
	Dimension actual = null;
	Dimension expected = null;

	img = new Dimension(dim20_50);
	bound = new Dimension(dim20_50);
	expected = new Dimension(dim20_50);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);
    }

    @Test
    public void testImgLesserThanBoundary() {
	Dimension img = null;
	Dimension bound = null;
	Dimension actual = null;
	Dimension expected = null;

	img = new Dimension(dim5_5);
	bound = new Dimension(dim10_10);
	expected = new Dimension(dim10_10);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);

	img = new Dimension(dim10_10);
	bound = new Dimension(dim20_10);
	expected = new Dimension(dim10_10);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);

	img = new Dimension(dim10_10);
	bound = new Dimension(dim20_50);
	expected = new Dimension(dim20_20);
	actual = getScaledDimension(img, bound);
	assertEquals(expected, actual);
    }

}
