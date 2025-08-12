package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountLedgerReportWebModel {
	private String ledgerId;
	private String ledgerName;
	private String voucherType;
	private String voucherId;
	private String debitAmount;
	private String creditAmount;
	private String voucherDate;
	private String monthName;
	private String grandDebitAmnt;
	private String grandCreditAmnt;
	private String receiptAmnt;
	private String paymentAmount;
	private String contraJournalAmount;
	private String saleReturnAmount;
	private String purchaseReturnAmount;
	private String totalEarningAmonut; 
	private String totalExpenditure;
	private String profitLossAmnt;
	
	private String vendorId;
	private String ledgerGroup;
	private String creditPeriod;
	private String outstandingAmt;
	private String paidAmt;
	private String payableAmt;
	private String overDueCount;
	private String invoiceId;
	private String totalAmt;
	private String remainAmt;
	private String invoiceDate;
	private String invoiceDueDate;
	
	private String openingBalance;
	private String intialbalance;
	private String proposeAmt;
	
	public AccountLedgerReportWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getMonthName() {
		return monthName;
	}



	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	
	



	public String getGrandDebitAmnt() {
		return grandDebitAmnt;
	}



	public void setGrandDebitAmnt(String grandDebitAmnt) {
		this.grandDebitAmnt = grandDebitAmnt;
	}



	public String getGrandCreditAmnt() {
		return grandCreditAmnt;
	}



	public void setGrandCreditAmnt(String grandCreditAmnt) {
		this.grandCreditAmnt = grandCreditAmnt;
	}



	public String getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(String ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	
	
	
	public String getReceiptAmnt() {
		return receiptAmnt;
	}



	public void setReceiptAmnt(String receiptAmnt) {
		this.receiptAmnt = receiptAmnt;
	}



	public String getPaymentAmount() {
		return paymentAmount;
	}



	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}



	public String getContraJournalAmount() {
		return contraJournalAmount;
	}



	public void setContraJournalAmount(String contraJournalAmount) {
		this.contraJournalAmount = contraJournalAmount;
	}



	public String getSaleReturnAmount() {
		return saleReturnAmount;
	}



	public void setSaleReturnAmount(String saleReturnAmount) {
		this.saleReturnAmount = saleReturnAmount;
	}



	public String getPurchaseReturnAmount() {
		return purchaseReturnAmount;
	}



	public void setPurchaseReturnAmount(String purchaseReturnAmount) {
		this.purchaseReturnAmount = purchaseReturnAmount;
	}



	public String getTotalEarningAmonut() {
		return totalEarningAmonut;
	}



	public void setTotalEarningAmonut(String totalEarningAmonut) {
		this.totalEarningAmonut = totalEarningAmonut;
	}



	public String getTotalExpenditure() {
		return totalExpenditure;
	}



	public void setTotalExpenditure(String totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}



	public String getProfitLossAmnt() {
		return profitLossAmnt;
	}



	public void setProfitLossAmnt(String profitLossAmnt) {
		this.profitLossAmnt = profitLossAmnt;
	}



	public String getVendorId() {
		return vendorId;
	}



	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}



	public String getLedgerGroup() {
		return ledgerGroup;
	}



	public void setLedgerGroup(String ledgerGroup) {
		this.ledgerGroup = ledgerGroup;
	}



	public String getCreditPeriod() {
		return creditPeriod;
	}



	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}



	public String getOutstandingAmt() {
		return outstandingAmt;
	}



	public void setOutstandingAmt(String outstandingAmt) {
		this.outstandingAmt = outstandingAmt;
	}



	public String getPaidAmt() {
		return paidAmt;
	}



	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}



	public String getPayableAmt() {
		return payableAmt;
	}



	public void setPayableAmt(String payableAmt) {
		this.payableAmt = payableAmt;
	}



	public String getOverDueCount() {
		return overDueCount;
	}



	public void setOverDueCount(String overDueCount) {
		this.overDueCount = overDueCount;
	}



	public String getInvoiceId() {
		return invoiceId;
	}



	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}



	public String getTotalAmt() {
		return totalAmt;
	}



	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}



	public String getRemainAmt() {
		return remainAmt;
	}



	public void setRemainAmt(String remainAmt) {
		this.remainAmt = remainAmt;
	}



	public String getInvoiceDate() {
		return invoiceDate;
	}



	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}



	public String getInvoiceDueDate() {
		return invoiceDueDate;
	}



	public void setInvoiceDueDate(String invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}



	public String getOpeningBalance() {
		return openingBalance;
	}



	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}



	public String getIntialbalance() {
		return intialbalance;
	}



	public void setIntialbalance(String intialbalance) {
		this.intialbalance = intialbalance;
	}
	
	



	public String getProposeAmt() {
		return proposeAmt;
	}



	public void setProposeAmt(String proposeAmt) {
		this.proposeAmt = proposeAmt;
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
