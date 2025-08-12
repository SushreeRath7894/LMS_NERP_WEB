package nirmalya.aathithya.webmodule.common.utils;

import java.text.DecimalFormat;



public class NumberToWordsConverter {

	 private static final String[] tensNames = {
		        "", " Ten", " Twenty", " Thirty", " Forty", " Fifty",
		        " Sixty", " Seventy", " Eighty", " Ninety"
		    };

		    private static final String[] numNames = {
		        "", " One", " Two", " Three", " Four", " Five",
		        " Six", " Seven", " Eight", " Nine", " Ten",
		        " Eleven", " Twelve", " Thirteen", " Fourteen",
		        " Fifteen", " Sixteen", " Seventeen", " Eighteen",
		        " Nineteen"
		    };

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " hundred" + current;
    }

    public static String convert(String numberStr) {
        if (numberStr == null || numberStr.isEmpty()) {
            return "zero";
        }

        // Remove commas from the string
        numberStr = numberStr.replace(",", "");

        double number;
        try {
            number = Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            return "Invalid number";
        }

        if (number == 0) {
            return "zero";
        }

        // Pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        String snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result += tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result += tradHundredThousands;

        String tradThousand = convertLessThanOneThousand(thousands);
        result += tradThousand;

        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }
    public static String convertWords(long number) {
        if (number == 0) { return "Zero Only"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        int millions  = Integer.parseInt(snumber.substring(0,3));
        int hundredThousands = Integer.parseInt(snumber.substring(3,6));
        int thousands = Integer.parseInt(snumber.substring(6,9));

        String tradMillions = millions == 0 ? "" : convertLessThanOneThousand(millions) + " Million ";
        String tradHundredThousands = hundredThousands == 0 ? "" :
            (hundredThousands == 1 ? "One Thousand " : convertLessThanOneThousand(hundredThousands) + " Thousand ");
        String tradThousand = convertLessThanOneThousand(thousands);

        return (tradMillions + tradHundredThousands + tradThousand).trim() + " Only";
    }
}



    

