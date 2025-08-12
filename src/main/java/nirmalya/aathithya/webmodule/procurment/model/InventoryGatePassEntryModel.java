package nirmalya.aathithya.webmodule.procurment.model;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class InventoryGatePassEntryModel {
	private String getPassEntryId;
	private String poNumber;
	private String challanNo;
	private String entrydate;
	private String entryTime;
	private String vendorName;
	private String vechileNo;
	private String driverName;
	private String driverMobile;
	private Double grossWeight;
	private String quantitybags;
	private String description;
	private String itemCategory;
	private String itemSubCategory;
	private String itemName;
	private String vendorQuantity;
	private String netQuantity;
	private Double itemWeight;
	private String createdBy;
	private String createdOn;
	private String OrganizationName;
	private String OrganizationDivision;
	private String itemCategoryName;
	private String itemSubCategoryName;
	private String getPassExitId;
	private String invoice;
	private String exitDate;
	private String exitTime;
	
	
	public InventoryGatePassEntryModel() {
		super();
	}
	
	
	public String getExitDate() {
		return exitDate;
	}


	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}


	public String getExitTime() {
		return exitTime;
	}


	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}


	public String getGetPassExitId() {
		return getPassExitId;
	}

	public void setGetPassExitId(String getPassExitId) {
		this.getPassExitId = getPassExitId;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getGetPassEntryId() {
		return getPassEntryId;
	}
	public void setGetPassEntryId(String getPassEntryId) {
		this.getPassEntryId = getPassEntryId;
	}
	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVechileNo() {
		return vechileNo;
	}

	public void setVechileNo(String vechileNo) {
		this.vechileNo = vechileNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getQuantitybags() {
		return quantitybags;
	}

	public void setQuantitybags(String quantitybags) {
		this.quantitybags = quantitybags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getVendorQuantity() {
		return vendorQuantity;
	}

	public void setVendorQuantity(String vendorQuantity) {
		this.vendorQuantity = vendorQuantity;
	}

	public String getNetQuantity() {
		return netQuantity;
	}

	public void setNetQuantity(String netQuantity) {
		this.netQuantity = netQuantity;
	}

	public Double getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return OrganizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public String getItemSubCategoryName() {
		return itemSubCategoryName;
	}
	public void setItemSubCategoryName(String itemSubCategoryName) {
		this.itemSubCategoryName = itemSubCategoryName;
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
