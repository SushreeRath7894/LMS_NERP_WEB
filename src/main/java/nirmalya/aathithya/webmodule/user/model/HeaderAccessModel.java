package nirmalya.aathithya.webmodule.user.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HeaderAccessModel {

	private String headerName;
	private List<HeaderAccessModel> children;
	private String field;
	private String columnGroupShow;
	
	public HeaderAccessModel() {
		super();
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public List<HeaderAccessModel> getChildren() {
		return children;
	}

	public void setChildren(List<HeaderAccessModel> children) {
		this.children = children;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public String getColumnGroupShow() {
		return columnGroupShow;
	}

	public void setColumnGroupShow(String columnGroupShow) {
		this.columnGroupShow = columnGroupShow;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapperObj.writeValueAsString(this);
		} catch (IOException ex) {

			jsonStr = ex.toString();
		}
		return jsonStr;
	}
}
