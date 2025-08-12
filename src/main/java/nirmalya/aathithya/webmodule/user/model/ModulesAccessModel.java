package nirmalya.aathithya.webmodule.user.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;

public class ModulesAccessModel {

	private String key;
	private String name;
	private Boolean status;
	private String value;
	private List<ModulesAccessModel> dataList= new ArrayList<ModulesAccessModel>();
	private String createdBy;
	private List<DropDownModel> funcList= new ArrayList<DropDownModel>();
	
	public ModulesAccessModel() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<ModulesAccessModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<ModulesAccessModel> dataList) {
		this.dataList = dataList;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<DropDownModel> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<DropDownModel> funcList) {
		this.funcList = funcList;
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
