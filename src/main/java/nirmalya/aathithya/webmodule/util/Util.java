package nirmalya.aathithya.webmodule.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Util {

	public static <E> boolean isNull(E object) {
		return object == null;
	}
	
	public static String toString(Exception object) {
		String json = null;
		try {
			StackTraceElement[] traceObj = object.getStackTrace();
			json = "FileName: "+traceObj[0].getFileName()+"\nLineNumber: "+traceObj[0].getLineNumber()+"\ncause: "+object.getMessage()
			+"\nmethodName: "+traceObj[0].getMethodName()+"\nException: "+object.fillInStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static String parseExceptionToString(Exception ex) {
		String parsedException = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(out));
		parsedException = new String(out.toByteArray());
		return parsedException;
	}
	
}
