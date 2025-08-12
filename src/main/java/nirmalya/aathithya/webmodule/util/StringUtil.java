package nirmalya.aathithya.webmodule.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

public class StringUtil {

	public static boolean isNull(String stringValue) {
		return stringValue == null || stringValue.trim().equals("");
	}
	
	public static String roleHumanization(String role) {
		if(!isNull(role)) {
			role = role.replace("_", " ");
			return WordUtils.capitalize(role);
			
		}
		return null;
	}
	
	/**
	 * Method to trim given string 
	 * @param "foo" will return "foo"
	 * @param " foo " will return "foo"
	 * @param " foo bar " will return "foo-bar"
	 */
	
	public static String transformStringForDB(String domain) {
		if (!isNull(domain)) {
			domain = stripWhiteSpace(domain);
			domain = replaceWhiteSpaceWithDash(domain);
		}

		return domain;
	}

	public static String stripWhiteSpace(String domain) {
		return domain.trim();
	}


	public static String replaceWhiteSpaceWithDash(String domain) {
		if (!isNull(domain)) {		
			Pattern pattern = Pattern.compile("\\s");
			Matcher matcher = pattern.matcher(domain);
			boolean found = matcher.find();
			if (found) {
				domain = domain.replace(" ", "-");
			}
		}
		return domain;
	}
	
	public static String sanitizeXmlContent(String input) {
        if (input == null) return null;
        return input.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
    }
}
