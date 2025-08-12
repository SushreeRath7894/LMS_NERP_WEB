package nirmalya.aathithya.webmodule.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtil {

	/*
	 * this method is created for the purpose of converting Double to string
	 * 
	 * @param amount,sign
	 * 
	 * @return "1.5"
	 */
	public static String covertDoubleToString(Double amount) {
		String result = "0.00";
		try {
			if (amount != null) {
				amount = Math.round(amount * 100.0) / 100.0;
				DecimalFormat f = new DecimalFormat("##0.00");
				result = f.format(amount);
			}
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	/*
	 * this method is created for the purpose of converting Double to string and
	 * prepending positive or negative symbol
	 * 
	 * @param amount,sign
	 * 
	 * @return "+ 1.5"
	 */
	public static String covertDoubleToString(Double amount, String mathematicalSymbol) {
		String result = "0.00";
		try {
			if (amount != null && amount != 0) {
				amount = Math.round(amount * 100.0) / 100.0;
				DecimalFormat f = new DecimalFormat("##0.00");
				result = f.format(amount);
			}
		} catch (Exception e) {
			return result;
		}
		return mathematicalSymbol + result;
	}

	/* Format Double upto 2 decimal place */
	public static Double formatDouble(Double number) {
		Double result = 0.0;
		try {
			if (!Util.isNull(number)) {
				number = Math.round(number * 100.0) / 100.0;
				DecimalFormat f = new DecimalFormat("##.00");
				String formatted = f.format(number);
				result = Double.valueOf(formatted);
			}
		} catch (Exception e) {

		}
		return result;
	}

	// converts 0.0322 to 0.03
	public static BigDecimal formatDoubleRoundDown(Double amount) {
		if (amount != null) {
			return new BigDecimal(amount).setScale(2, RoundingMode.DOWN);
		} else {
			return new BigDecimal(0.0);
		}
	}

	// ONLY FOR DISPLAY of BALANCE : for tx hist display purpose
	// converts to 2 decimal place double value
	static String covertToDouble(long number) {
		String val = String.valueOf(number);
		double d = Double.valueOf(val);
		d = d / 100; // waves decimal places

		// fix the remainder 0.01 issue.
		if (Double.compare(d, 0.02) <= 0) {
			d = 0.00;
		} else {
			// reduce the Balance Display by 0.01, as at sign up we give user 0.01 extra
			d = d - 0.01;
		}

		DecimalFormat f = new DecimalFormat("##.00");
		String result = f.format(d);
		return result;
	}

}
