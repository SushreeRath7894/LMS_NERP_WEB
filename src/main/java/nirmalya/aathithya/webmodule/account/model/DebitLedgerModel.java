package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DebitLedgerModel {

	private String dealerId;
	private String dname;
	private String transactionDate;
	private String transactionMode;
	private String transactionId;
	private String transactionAmnt;
	private String transactionDebitCredit;
	private String transactionVchType;
	private String totalBillAmnt;
	private String totalPaidAmnt;
	private String totalOutstandingAmnt;
	private String dealerName;
	private String dealerAddress;
	private String dealerMobile;

	public DebitLedgerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactionVchType() {
		return transactionVchType;
	}

	public void setTransactionVchType(String transactionVchType) {
		this.transactionVchType = transactionVchType;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionAmnt() {
		return transactionAmnt;
	}

	public void setTransactionAmnt(String transactionAmnt) {
		this.transactionAmnt = transactionAmnt;
	}

	public String getTransactionDebitCredit() {
		return transactionDebitCredit;
	}

	public void setTransactionDebitCredit(String transactionDebitCredit) {
		this.transactionDebitCredit = transactionDebitCredit;
	}

	public String getTotalBillAmnt() {
		return totalBillAmnt;
	}

	public void setTotalBillAmnt(String totalBillAmnt) {
		this.totalBillAmnt = totalBillAmnt;
	}

	public String getTotalPaidAmnt() {
		return totalPaidAmnt;
	}

	public void setTotalPaidAmnt(String totalPaidAmnt) {
		this.totalPaidAmnt = totalPaidAmnt;
	}

	public String getTotalOutstandingAmnt() {
		return totalOutstandingAmnt;
	}

	public void setTotalOutstandingAmnt(String totalOutstandingAmnt) {
		this.totalOutstandingAmnt = totalOutstandingAmnt;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}

	public String getDealerMobile() {
		return dealerMobile;
	}

	public void setDealerMobile(String dealerMobile) {
		this.dealerMobile = dealerMobile;
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
