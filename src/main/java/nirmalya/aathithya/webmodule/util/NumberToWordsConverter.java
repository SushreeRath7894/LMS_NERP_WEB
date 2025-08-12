package nirmalya.aathithya.webmodule.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberToWordsConverter {

	private static final String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
			"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Nineteen" };

	private static final String[] tens = { "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
			"Ninety" };

	private static String convertTwoDigit(int number) {
		if (number < 20)
			return units[number];
		return tens[number / 10] + (number % 10 != 0 ? " " + units[number % 10] : "");
	}

	private static String convertPart(long number, String suffix) {
		return number > 0 ? convert(number) + " " + suffix + " " : "";
	}

	public static String convert(long number) {
		if (number == 0)
			return "Zero";

		StringBuilder result = new StringBuilder();

		result.append(convertPart(number / 10000000, "Crore"));
		number %= 10000000;

		result.append(convertPart(number / 100000, "Lakh"));
		number %= 100000;

		result.append(convertPart(number / 1000, "Thousand"));
		number %= 1000;

		result.append(convertPart(number / 100, "Hundred"));
		number %= 100;

		if (number > 0 && result.length() > 0) {
			result.append("and ");
		}

		result.append(convertTwoDigit((int) number));
		return result.toString().trim();
	}

	public static String convertToWords(BigDecimal amount) {
		BigInteger rupees = amount.toBigInteger();
		int paise = amount.remainder(BigDecimal.ONE).movePointRight(2).intValue();

		StringBuilder words = new StringBuilder();
		words.append("Rupees ").append(convert(rupees.longValue()));

		if (paise > 0) {
			words.append(" and ").append(convert(paise)).append(" Paise");
		}

		words.append(" Only");
		return words.toString();
	}

}
