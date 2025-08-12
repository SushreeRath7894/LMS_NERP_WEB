package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IncomeTaxMasterModel {
	private String organization;
	private String orgDivision;
 
	private String incomeId;
	private String incomeName;
	private String ifyearName;
	private String ifyear;
	private Double iminval;
	private Double imaxval;
	private String itax;
	private String incomeUpdatedBy;
	
	private String prId;
	private String pName;
	private String pyearName;
	private String pyear;
	private Double pminval;
	private Double pmaxval;
	private Double ptax;
	
	private String sId;
	private String sName;
	private String sdesc;
	private String status;
	public String getIfyearName() {
		return ifyearName;
	}
	public void setIfyearName(String ifyearName) {
		this.ifyearName = ifyearName;
	}
	public String getIncomeUpdatedBy() {
		return incomeUpdatedBy;
	}
	public void setIncomeUpdatedBy(String incomeUpdatedBy) {
		this.incomeUpdatedBy = incomeUpdatedBy;
	}
	public String getPyearName() {
		return pyearName;
	}
	public void setPyearName(String pyearName) {
		this.pyearName = pyearName;
	}
	public String getPrId() {
		return prId;
	}
	public void setPrId(String prId) {
		this.prId = prId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getPyear() {
		return pyear;
	}
	public void setPyear(String pyear) {
		this.pyear = pyear;
	}
 
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getSdesc() {
		return sdesc;
	}
	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}
	public String getIncomeName() {
		return incomeName;
	}
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}
	public String getIfyear() {
		return ifyear;
	}
	public void setIfyear(String ifyear) {
		this.ifyear = ifyear;
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
	
	public Double getIminval() {
		return iminval;
	}
	public void setIminval(Double iminval) {
		this.iminval = iminval;
	}
	public Double getImaxval() {
		return imaxval;
	}
	public void setImaxval(Double imaxval) {
		this.imaxval = imaxval;
	}
	public String getItax() {
		return itax;
	}
	public void setItax(String itax) {
		this.itax = itax;
	}
	public Double getPminval() {
		return pminval;
	}
	public void setPminval(Double pminval) {
		this.pminval = pminval;
	}
	public Double getPmaxval() {
		return pmaxval;
	}
	public void setPmaxval(Double pmaxval) {
		this.pmaxval = pmaxval;
	}
	public Double getPtax() {
		return ptax;
	}
	public void setPtax(Double ptax) {
		this.ptax = ptax;
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
