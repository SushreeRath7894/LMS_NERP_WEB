package nirmalya.aathithya.webmodule.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidator {

	/*
	 * Arjun 9898029924, 8866329926 Shaival 9880089581 Suryansh 9340115199 Ashish
	 * 9696006200, 8235306200,9532709443
	 */
	private static final String ALLOWED_INDIAN_NUMBER = "9340115199|9898029924|8866329926|9696006200|"
			+ "8235306200|9880089581|9532709443";

	private static final String ALLOWED_CANADIAN_NUMBER = "6477079449|4167689555";

	public static final String MOBILE_PATTERN_IN = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";

	public static boolean validate(String mobileNumber) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(mobileNumber);
		return validate(strBuilder);

	}

	private static boolean validate(StringBuilder mobileBuilder) {

		Pattern pattern = Pattern.compile(MOBILE_PATTERN_IN);
		Matcher matcher = pattern.matcher(mobileBuilder);
		if (matcher.matches()) {
			String mobile = mobileBuilder.toString().replaceAll("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$",
					"0");
			mobileBuilder.replace(0, mobileBuilder.length(), mobile);
			return true;
		} else {
			return false;
		}
	}

	// few SA number starts with 0, but it cause issue while sending SMS by Cognito.
	public static String trimZero(String mobileNumber) {
		if (mobileNumber.startsWith("0") && mobileNumber.length() == 10) {
			return mobileNumber.substring(1, mobileNumber.length());
		}
		return mobileNumber;
	}

}
