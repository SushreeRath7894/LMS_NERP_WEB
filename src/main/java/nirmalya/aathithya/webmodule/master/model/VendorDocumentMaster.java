package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VendorDocumentMaster {
	private String vendorId;
	private String documentId;
	private String documentType;
	private String documentNo;
	private String documentImage;
	private String status;
	private List<String> file = new ArrayList<String>();
	private String createdBy;
	private String OrganizationName; 
	private String OrganizationDivision;
	public VendorDocumentMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDocumentImage() {
		return documentImage;
	}
	public void setDocumentImage(String documentImage) {
		this.documentImage = documentImage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getFile() {
		return file;
	}
	public void setFile(List<String> file) {
		this.file = file;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
