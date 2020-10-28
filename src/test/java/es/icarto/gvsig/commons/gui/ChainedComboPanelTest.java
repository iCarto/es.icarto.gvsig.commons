package es.icarto.gvsig.commons.gui;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

public class ChainedComboPanelTest {

	public static final class BookInfo {
		public String bookName;
		public URL bookURL;

		public BookInfo(String book, String filename) {
			bookName = book;
			bookURL = getClass().getResource(filename);
			if (bookURL == null) {
				// System.err.println("Couldn't find file: " + filename);
			}
		}

		@Override
		public String toString() {
			return bookName;
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Dual List Box Tester");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
		createNodes(top);
		JPanel pane = new JPanel(new MigLayout("insets 10"));
		ChainedComboPanel dialog = new ChainedComboPanel(pane, top);

		f.getContentPane().add(pane, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}

	private static void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		category = new DefaultMutableTreeNode("Books for Java Programmers");
		top.add(category);

		// original Tutorial
		book = new DefaultMutableTreeNode(
				new BookInfo("The Java Tutorial: A Short Course on the Basics", "tutorial.html"));
		category.add(book);

		// Tutorial Continued
		book = new DefaultMutableTreeNode(
				new BookInfo("The Java Tutorial Continued: The Rest of the JDK", "tutorialcont.html"));
		category.add(book);

		// Swing Tutorial
		book = new DefaultMutableTreeNode(
				new BookInfo("The Swing Tutorial: A Guide to Constructing GUIs", "swingtutorial.html"));
		category.add(book);

		// ...add more books for programmers...

		category = new DefaultMutableTreeNode("Books for Java Implementers");
		top.add(category);

		// VM
		book = new DefaultMutableTreeNode(new BookInfo("The Java Virtual Machine Specification", "vm.html"));
		category.add(book);

		// Language Spec
		book = new DefaultMutableTreeNode(new BookInfo("The Java Language Specification", "jls.html"));
		category.add(book);
	}

}
