package es.icarto.gvsig.commons.i18n;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.gvsig.i18n.Messages;

public class I18n {

	private final static MessageFormat formatter = new MessageFormat("", Messages.getCurrentLocale());
	private final static boolean LOG_FAILED_TRANSLATIONS = false;

	public static String _(String text) {
		if (Messages.getText(text, LOG_FAILED_TRANSLATIONS) == null) {
			return text;
		} else {
			return Messages.getText(text, LOG_FAILED_TRANSLATIONS);
		}
	}

	public static String _(String text, Object... args) {
		String pattern = Messages.getText(text, LOG_FAILED_TRANSLATIONS);
		if (pattern == null) {
			return text;
		}
		if (args != null) {
			formatter.applyPattern(pattern);
			return formatter.format(args);
		}
		return pattern;
	}

	private static void foo() {

		Locale currentLocale = Locale.getDefault(); // cogerlo de gvSIG
		ResourceBundle messages = ResourceBundle.getBundle("MessageBundle", currentLocale);

		Object[] messageArguments = { messages.getString("planet"), new Integer(7), new Date() };
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(currentLocale);

		formatter.applyPattern(messages.getString("template"));
		String output = formatter.format(messageArguments);
	}

	public static List getNotTranslatedKeys() {
		return Messages.getNotTranslatedKeys();
	}

}
