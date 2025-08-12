package nirmalya.aathithya.webmodule.samudyamproduction.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
 
public class ProductionOrderModel {
	private String salesOrder;
	private String custId;
	private String custName;
	private String orderReceiveDate;
	private String expectedShipmentDate;
	private String productionId;
	private String productionDate;
	private String productionStatus;
	private String orderStatus;
	private String organization;
	private String orgDivision;
	private String orderCreatedBy;
	List<ProductionOrderItemModel> itemList;
	public ProductionOrderModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getOrderReceiveDate() {
		return orderReceiveDate;
	}
	public void setOrderReceiveDate(String orderReceiveDate) {
		this.orderReceiveDate = orderReceiveDate;
	}
	public String getExpectedShipmentDate() {
		return expectedShipmentDate;
	}
	public void setExpectedShipmentDate(String expectedShipmentDate) {
		this.expectedShipmentDate = expectedShipmentDate;
	}
	public String getProductionId() {
		return productionId;
	}
	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getProductionStatus() {
		return productionStatus;
	}
	public void setProductionStatus(String productionStatus) {
		this.productionStatus = productionStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getOrderCreatedBy() {
		return orderCreatedBy;
	}
	public void setOrderCreatedBy(String orderCreatedBy) {
		this.orderCreatedBy = orderCreatedBy;
	}
	public List<ProductionOrderItemModel> getItemList() {
		return itemList;
	}
	public void setItemList(List<ProductionOrderItemModel> itemList) {
		this.itemList = itemList;
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
