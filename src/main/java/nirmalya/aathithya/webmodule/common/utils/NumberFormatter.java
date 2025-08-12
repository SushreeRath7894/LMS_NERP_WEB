package nirmalya.aathithya.webmodule.common.utils;

import java.text.DecimalFormat;

public class NumberFormatter {

public static String doubleToStringWithComma(Double value) {
		
		String doubleValue = "";
		
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		
		if(value != null) {
			doubleValue = decimalFormat.format((double) value);
		}
		
		return doubleValue;
	}
public static String formatIndianNumber(double amount) {
    String str = String.format("%.2f", amount);
    String[] parts = str.split("\\.");
    String number = parts[0];
    String decimal = parts[1];
 
    StringBuilder sb = new StringBuilder();
    int len = number.length();
 
    if (len > 3) {
        sb.append(number.substring(len - 3));
        int i = len - 3;
        while (i > 0) {
            int start = Math.max(i - 2, 0);
            sb.insert(0, "," + number.substring(start, i));
            i = start;
        }
    } else {
        sb.append(number);
    }
 
    return sb.toString() + "." + decimal;
}
}
