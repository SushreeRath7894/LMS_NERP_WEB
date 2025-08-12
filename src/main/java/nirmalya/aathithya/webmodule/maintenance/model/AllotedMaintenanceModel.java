package nirmalya.aathithya.webmodule.maintenance.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AllotedMaintenanceModel {

	private String ticketid;
	private String allocid;
	private String allocationid;
	private String policyName;
	private String policyid;
	private String status;
	private String priority;
	private String description;
	private String equipementCat;
	private String equipementScat;
	private String equipementQty;
	
	private String tasktype;
	private String uom;
	private String minrange;
	private String maxrange;
	private String result;
	
	private String assignid;
	private String occurancenop;
	private String occurancestarts;
	private String occuranceends;
	private String reviewSts;
	private String remark;
	private String slno;
	private String assignedDate;
	private String shift;
	private String occurancedate;
	
	private List<AllotedMaintenanceModel> equipementList;
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String equipementCatId;
	private String equipementScatId;
	
	private String equipementCatagory1;
	private String equipementScatagory1;

	public AllotedMaintenanceModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getTasktype() {
		return tasktype;
	}


	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}


	public String getUom() {
		return uom;
	}


	public void setUom(String uom) {
		this.uom = uom;
	}


	public String getMinrange() {
		return minrange;
	}


	public void setMinrange(String minrange) {
		this.minrange = minrange;
	}


	public String getMaxrange() {
		return maxrange;
	}


	public void setMaxrange(String maxrange) {
		this.maxrange = maxrange;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getAllocationid() {
		return allocationid;
	}


	public void setAllocationid(String allocationid) {
		this.allocationid = allocationid;
	}


	public List<AllotedMaintenanceModel> getEquipementList() {
		return equipementList;
	}


	public void setEquipementList(List<AllotedMaintenanceModel> equipementList) {
		this.equipementList = equipementList;
	}


	public String getEquipementQty() {
		return equipementQty;
	}


	public void setEquipementQty(String equipementQty) {
		this.equipementQty = equipementQty;
	}


	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPolicyid() {
		return policyid;
	}
	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}
	public String getAllocid() {
		return allocid;
	}
	public void setAllocid(String allocid) {
		this.allocid = allocid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public String getEquipementCat() {
		return equipementCat;
	}


	public void setEquipementCat(String equipementCat) {
		this.equipementCat = equipementCat;
	}


	public String getEquipementScat() {
		return equipementScat;
	}


	public void setEquipementScat(String equipementScat) {
		this.equipementScat = equipementScat;
	}


	public String getEquipementCatId() {
		return equipementCatId;
	}


	public void setEquipementCatId(String equipementCatId) {
		this.equipementCatId = equipementCatId;
	}


	public String getEquipementScatId() {
		return equipementScatId;
	}


	public void setEquipementScatId(String equipementScatId) {
		this.equipementScatId = equipementScatId;
	}


	public String getEquipementCatagory1() {
		return equipementCatagory1;
	}


	public void setEquipementCatagory1(String equipementCatagory1) {
		this.equipementCatagory1 = equipementCatagory1;
	}


	public String getEquipementScatagory1() {
		return equipementScatagory1;
	}


	public void setEquipementScatagory1(String equipementScatagory1) {
		this.equipementScatagory1 = equipementScatagory1;
	}


	public String getAssignid() {
		return assignid;
	}


	public void setAssignid(String assignid) {
		this.assignid = assignid;
	}


	public String getOccurancenop() {
		return occurancenop;
	}


	public void setOccurancenop(String occurancenop) {
		this.occurancenop = occurancenop;
	}


	public String getOccurancestarts() {
		return occurancestarts;
	}


	public void setOccurancestarts(String occurancestarts) {
		this.occurancestarts = occurancestarts;
	}


	public String getOccuranceends() {
		return occuranceends;
	}


	public void setOccuranceends(String occuranceends) {
		this.occuranceends = occuranceends;
	}


	public String getReviewSts() {
		return reviewSts;
	}


	public void setReviewSts(String reviewSts) {
		this.reviewSts = reviewSts;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSlno() {
		return slno;
	}


	public void setSlno(String slno) {
		this.slno = slno;
	}


	public String getAssignedDate() {
		return assignedDate;
	}


	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}


	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	public String getOccurancedate() {
		return occurancedate;
	}


	public void setOccurancedate(String occurancedate) {
		this.occurancedate = occurancedate;
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
