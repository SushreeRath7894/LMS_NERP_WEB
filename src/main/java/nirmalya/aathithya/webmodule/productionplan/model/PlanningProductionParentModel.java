package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanningProductionParentModel {
	private String planningid;
	private String plant;
	private String plantweek;
	private String fromdate;
	private String todate;
	private String planning;
	private String brand;
	private String totalqty;
	private String status;
	private String createdBy;
	private String org;
	private String orgDiv;
	private String scheduleCode;
	private List<ProductionPlanningProductList> productList;
	private List<ProductionPlanningmachineManpowerList> machineManpowerList;
	private List<ProductionPlanningTotalmanpowerList> totalmanpowerList;
	public List<ProductionPlanningTotalmanpowerList> getTotalmanpowerList() {
		return totalmanpowerList;
	}

	public void setTotalmanpowerList(List<ProductionPlanningTotalmanpowerList> totalmanpowerList) {
		this.totalmanpowerList = totalmanpowerList;
	}

	private List<PlanningProductionRawmaterialModel> rawmaterialist;
	private List<PlanningProductionVariantModel> variant;

	public String getPlanningid() {
		return planningid;
	}

	public void setPlanningid(String planningid) {
		this.planningid = planningid;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getPlantweek() {
		return plantweek;
	}

	public void setPlantweek(String plantweek) {
		this.plantweek = plantweek;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getPlanning() {
		return planning;
	}

	public void setPlanning(String planning) {
		this.planning = planning;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTotalqty() {
		return totalqty;
	}

	public void setTotalqty(String totalqty) {
		this.totalqty = totalqty;
	}

	public List<PlanningProductionRawmaterialModel> getRawmaterialist() {
		return rawmaterialist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public List<ProductionPlanningProductList> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductionPlanningProductList> productList) {
		this.productList = productList;
	}

	public List<ProductionPlanningmachineManpowerList> getMachineManpowerList() {
		return machineManpowerList;
	}

	public void setMachineManpowerList(List<ProductionPlanningmachineManpowerList> machineManpowerList) {
		this.machineManpowerList = machineManpowerList;
	}

	public void setRawmaterialist(List<PlanningProductionRawmaterialModel> rawmaterialist) {
		this.rawmaterialist = rawmaterialist;
	}

	public List<PlanningProductionVariantModel> getVariant() {
		return variant;
	}

	public void setVariant(List<PlanningProductionVariantModel> variant) {
		this.variant = variant;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

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
