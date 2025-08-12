package nirmalya.aathithya.webmodule.util;

import java.util.List;

public class FieldValidator {

	public static Boolean isEmptyString(String value) {
		return value == null || value.equals("") || value.equals("null");
	}

	public static Boolean isEmptyNumber(Long value) {
		return (value == null);
	}

	public static Boolean isEmptyNumeric(Double value) {
		return (value == null);
	}

	public static <E> boolean isEmptyList(List<E> list) {
		return list == null || list.isEmpty();
	}
	
	public static Boolean isNull(Object modelObject, Object emptyObject) {
		return modelObject == null || modelObject.equals(emptyObject);
	}
	
}
