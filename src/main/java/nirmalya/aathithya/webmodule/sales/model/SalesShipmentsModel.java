package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesShipmentsModel {
	private String salesShipmentId;
	private String saleDeliveryChallan;
	private String custId;
	private String custName;
	private String salesOrder;
	private String poId;
	private String salesOrderId;
	private String salePackageId;
	private String shipmentOrder;
	private String carrierId;
	private String shipmentDate;
	private String trackingId;
	private String trackingUrlId;
	private String contactNo;
	private Double shippingCharge;
	private String internalNotes;
	private String shipmentDeliverdId;
	private String sendStatusNotification;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String ShipmentStatus;
	private String orgName;
	private String orgDiv;
	private String shippingHiddenId;

	public SalesShipmentsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSalesShipmentId() {
		return salesShipmentId;
	}

	public void setSalesShipmentId(String salesShipmentId) {
		this.salesShipmentId = salesShipmentId;
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

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getSalePackageId() {
		return salePackageId;
	}

	public void setSalePackageId(String salePackageId) {
		this.salePackageId = salePackageId;
	}

	public String getShipmentOrder() {
		return shipmentOrder;
	}

	public void setShipmentOrder(String shipmentOrder) {
		this.shipmentOrder = shipmentOrder;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getTrackingUrlId() {
		return trackingUrlId;
	}

	public void setTrackingUrlId(String trackingUrlId) {
		this.trackingUrlId = trackingUrlId;
	}

	public Double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public String getInternalNotes() {
		return internalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}

	public String getShipmentDeliverdId() {
		return shipmentDeliverdId;
	}

	public void setShipmentDeliverdId(String shipmentDeliverdId) {
		this.shipmentDeliverdId = shipmentDeliverdId;
	}

	public String getSendStatusNotification() {
		return sendStatusNotification;
	}

	public void setSendStatusNotification(String sendStatusNotification) {
		this.sendStatusNotification = sendStatusNotification;
	}

	public String getQutCreatedBy() {
		return qutCreatedBy;
	}

	public void setQutCreatedBy(String qutCreatedBy) {
		this.qutCreatedBy = qutCreatedBy;
	}

	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}

	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
	}

	public String getShipmentStatus() {
		return ShipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		ShipmentStatus = shipmentStatus;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}
	

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	

	public String getSaleDeliveryChallan() {
		return saleDeliveryChallan;
	}

	public void setSaleDeliveryChallan(String saleDeliveryChallan) {
		this.saleDeliveryChallan = saleDeliveryChallan;
	}
	
	

	public String getShippingHiddenId() {
		return shippingHiddenId;
	}

	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
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
