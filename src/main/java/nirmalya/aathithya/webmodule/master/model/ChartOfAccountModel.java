package nirmalya.aathithya.webmodule.master.model;

import java.math.BigInteger;

public class ChartOfAccountModel {
	private String ccId;
	private String ccName;
	private String ccCode;
	private String chartAccDebitStatus;
	private String chartAccCreditStatus;
	private String createdBy;
	private String level;
	private String parentId;
	private BigInteger nodeCount;
	public ChartOfAccountModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCcId() {
		return ccId;
	}

	public void setCcId(String ccId) {
		this.ccId = ccId;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getCcCode() {
		return ccCode;
	}

	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

	public String getChartAccDebitStatus() {
		return chartAccDebitStatus;
	}
	public void setChartAccDebitStatus(String chartAccDebitStatus) {
		this.chartAccDebitStatus = chartAccDebitStatus;
	}
	public String getChartAccCreditStatus() {
		return chartAccCreditStatus;
	}
	public void setChartAccCreditStatus(String chartAccCreditStatus) {
		this.chartAccCreditStatus = chartAccCreditStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public BigInteger getNodeCount() {
		return nodeCount;
	}
	public void setNodeCount(BigInteger nodeCount) {
		this.nodeCount = nodeCount;
	}
	
	
}
