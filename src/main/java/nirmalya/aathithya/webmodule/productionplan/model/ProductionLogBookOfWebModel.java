package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductionLogBookOfWebModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String logId;

	private String date;
	private String shift;

	private String floorSweep;
	private String asmGenerator;
	private String totalQuantity1;
	private String remark1;
	private String lamStarpac;
	private String lamSyntegon;
	private String totalQuantity2;
	private String remark2;
	private String rewgStarpac;
	private String rewgSyntegon;
	private String communication;

	private String gridNo;
	private String slno;
	private String variant;
	private String batchNo;
	private String noOfBags;
	private String rewQtyAdd;
	private String total;
	private String totalQty;
	private String remark;

	private String sku;
	private String skuType;
	private String l1Prod;
	private String l2Prod;
	private String totalCld;
	private String prodQty;
	private String sapBooking;
	private String qualityHold;
	
	private String jumboBagSize;
	private String cldWt;
	private String dewrappedLami;
	private String dewrapConst;
	private String dewrapCalc;
	

	private String l3Prod;

	List<ProductionLogBookOfWebModel> grid1Dtls;
	List<ProductionLogBookOfWebModel> grid2Dtls;

	public ProductionLogBookOfWebModel() {
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

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getFloorSweep() {
		return floorSweep;
	}

	public void setFloorSweep(String floorSweep) {
		this.floorSweep = floorSweep;
	}

	public String getAsmGenerator() {
		return asmGenerator;
	}

	public void setAsmGenerator(String asmGenerator) {
		this.asmGenerator = asmGenerator;
	}

	public String getTotalQuantity1() {
		return totalQuantity1;
	}

	public void setTotalQuantity1(String totalQuantity1) {
		this.totalQuantity1 = totalQuantity1;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getLamStarpac() {
		return lamStarpac;
	}

	public void setLamStarpac(String lamStarpac) {
		this.lamStarpac = lamStarpac;
	}

	public String getLamSyntegon() {
		return lamSyntegon;
	}

	public void setLamSyntegon(String lamSyntegon) {
		this.lamSyntegon = lamSyntegon;
	}

	public String getTotalQuantity2() {
		return totalQuantity2;
	}

	public void setTotalQuantity2(String totalQuantity2) {
		this.totalQuantity2 = totalQuantity2;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRewgStarpac() {
		return rewgStarpac;
	}

	public void setRewgStarpac(String rewgStarpac) {
		this.rewgStarpac = rewgStarpac;
	}

	public String getRewgSyntegon() {
		return rewgSyntegon;
	}

	public void setRewgSyntegon(String rewgSyntegon) {
		this.rewgSyntegon = rewgSyntegon;
	}

	public String getCommunication() {
		return communication;
	}

	public void setCommunication(String communication) {
		this.communication = communication;
	}

	public String getGridNo() {
		return gridNo;
	}

	public void setGridNo(String gridNo) {
		this.gridNo = gridNo;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getNoOfBags() {
		return noOfBags;
	}

	public void setNoOfBags(String noOfBags) {
		this.noOfBags = noOfBags;
	}

	public String getRewQtyAdd() {
		return rewQtyAdd;
	}

	public void setRewQtyAdd(String rewQtyAdd) {
		this.rewQtyAdd = rewQtyAdd;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuType() {
		return skuType;
	}

	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}

	public String getL1Prod() {
		return l1Prod;
	}

	public void setL1Prod(String l1Prod) {
		this.l1Prod = l1Prod;
	}

	public String getL2Prod() {
		return l2Prod;
	}

	public void setL2Prod(String l2Prod) {
		this.l2Prod = l2Prod;
	}

	public String getProdQty() {
		return prodQty;
	}

	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}

	public String getSapBooking() {
		return sapBooking;
	}

	public void setSapBooking(String sapBooking) {
		this.sapBooking = sapBooking;
	}

	public String getQualityHold() {
		return qualityHold;
	}

	public void setQualityHold(String qualityHold) {
		this.qualityHold = qualityHold;
	}

	public List<ProductionLogBookOfWebModel> getGrid1Dtls() {
		return grid1Dtls;
	}

	public void setGrid1Dtls(List<ProductionLogBookOfWebModel> grid1Dtls) {
		this.grid1Dtls = grid1Dtls;
	}

	public List<ProductionLogBookOfWebModel> getGrid2Dtls() {
		return grid2Dtls;
	}

	public void setGrid2Dtls(List<ProductionLogBookOfWebModel> grid2Dtls) {
		this.grid2Dtls = grid2Dtls;
	}

	public String getTotalCld() {
		return totalCld;
	}

	public void setTotalCld(String totalCld) {
		this.totalCld = totalCld;
	}

	public String getJumboBagSize() {
		return jumboBagSize;
	}

	public void setJumboBagSize(String jumboBagSize) {
		this.jumboBagSize = jumboBagSize;
	}

	public String getCldWt() {
		return cldWt;
	}

	public void setCldWt(String cldWt) {
		this.cldWt = cldWt;
	}

	public String getDewrappedLami() {
		return dewrappedLami;
	}

	public void setDewrappedLami(String dewrappedLami) {
		this.dewrappedLami = dewrappedLami;
	}

	public String getDewrapConst() {
		return dewrapConst;
	}

	public void setDewrapConst(String dewrapConst) {
		this.dewrapConst = dewrapConst;
	}

	public String getDewrapCalc() {
		return dewrapCalc;
	}

	public void setDewrapCalc(String dewrapCalc) {
		this.dewrapCalc = dewrapCalc;
	}

	public String getL3Prod() {
		return l3Prod;
	}

	public void setL3Prod(String l3Prod) {
		this.l3Prod = l3Prod;
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
