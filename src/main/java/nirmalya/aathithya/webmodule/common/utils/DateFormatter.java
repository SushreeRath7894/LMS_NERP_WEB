/**
 * 
 */
package nirmalya.aathithya.webmodule.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ashis
 *
 */


public class DateFormatter {

	public static String getStringDate(String date) {

		String formattedDate = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date newDate = format.parse(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static String returnStringDateIndianFormat(String date) {

		String formattedDate = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date newDate = format.parse(date);

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static String getStringDateTime(String date) {

		String formattedDate = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date newDate = format.parse(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static Object returnStringDate(Object date) {

		Object[] formattedDate = null;

		try {
			formattedDate = date.toString().split(" ");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate[0];
	}

	public static Object returnStringDateTime(Object date) {

		Object formattedDate = null;

		try {
			Date newDate = (Date) date;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			formattedDate = formatter.format(newDate);
		} catch (Exception e) {
			formattedDate = "--";
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static String dateFormat(String date, String dateFormat) {

		String formattedDate = null;

		try {
			Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formattedDate = formatter.format(newDate);
		} catch (Exception e) {
			formattedDate = "--";
			e.printStackTrace();
		}

		return formattedDate;
	}

	public static String inputDateFormat(String date, String dateFormat) {

		String formattedDate = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Date newDate = format.parse(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}
	@SuppressWarnings("deprecation")
	public static String getStringNewDate(String date) {

		String formattedDate = null;

		try {

			//SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
			Date newDate = new Date(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}
	@SuppressWarnings("deprecation")
	public static String getStringNewDateTime(String date) {

		String formattedDate = null;

		try {

			//SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy HH:mm:ss");
			Date newDate = new Date(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}
	
	public static String getStringDateNew(String date) {

		String formattedDate = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			Date newDate = format.parse(date);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = formatter.format(newDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return formattedDate;
	}	
}
