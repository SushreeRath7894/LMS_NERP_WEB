package nirmalya.aathithya.webmodule.common.utils;

import java.util.Locale;

public class IndianNumberFormat {

	public static String formatIndianNumber(double value) {
		String[] parts = String.format(Locale.US, "%.2f", value).split("\\.");
		String integerPart = parts[0];
		String decimalPart = parts.length > 1 ? parts[1] : "00";
		StringBuilder formattedInteger = new StringBuilder();
		int len = integerPart.length();
		if (len > 3) {
			formattedInteger.append(integerPart.substring(len - 3));
			integerPart = integerPart.substring(0, len - 3);
		} else {
			formattedInteger.append(integerPart);
			integerPart = "";
		}
		while (integerPart.length() > 0) {
			if (integerPart.length() > 2) {
				formattedInteger.insert(0, integerPart.substring(integerPart.length() - 2) + ",");
				integerPart = integerPart.substring(0, integerPart.length() - 2);
			} else {
				formattedInteger.insert(0, integerPart + ",");
				integerPart = "";
			}
		}
		if (formattedInteger.charAt(0) == ',') {
			formattedInteger.deleteCharAt(0);
		}
		formattedInteger.append('.').append(decimalPart);
		return formattedInteger.toString();
	}
}
