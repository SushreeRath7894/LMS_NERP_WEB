package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaWbcrModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String wbcrId;
	private String date;

	private String type;
	private String pckMcinDesc;
	private String sku;
	private String wsSlNo;
	private String shiftA;
	private String shiftB;
	private String shiftC;
	private String remarks;

	List<QaWbcrModel> itemDtls;

	public QaWbcrModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getWbcrId() {
		return wbcrId;
	}

	public void setWbcrId(String wbcrId) {
		this.wbcrId = wbcrId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPckMcinDesc() {
		return pckMcinDesc;
	}

	public void setPckMcinDesc(String pckMcinDesc) {
		this.pckMcinDesc = pckMcinDesc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getWsSlNo() {
		return wsSlNo;
	}

	public void setWsSlNo(String wsSlNo) {
		this.wsSlNo = wsSlNo;
	}

	public String getShiftA() {
		return shiftA;
	}

	public void setShiftA(String shiftA) {
		this.shiftA = shiftA;
	}

	public String getShiftB() {
		return shiftB;
	}

	public void setShiftB(String shiftB) {
		this.shiftB = shiftB;
	}

	public String getShiftC() {
		return shiftC;
	}

	public void setShiftC(String shiftC) {
		this.shiftC = shiftC;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<QaWbcrModel> getItemDtls() {
		return itemDtls;
	}

	public void setItemDtls(List<QaWbcrModel> itemDtls) {
		this.itemDtls = itemDtls;
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
