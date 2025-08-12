package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 */
public class ContraVoucherModel {

	private String contraVoucherId;
	private String contraVoucherType;
	private String costCenterType;
	private String costCenter;
	private String contraVoucherDate;
	private String amount;
	private String debAccGroupId;
	private String creAccGroupId;
	private String debitedAccGroup;
	private String creditedAccGroup;
	private String fromAccountB2B;
	private String toAccountB2B;
	private String fromBankB2C;
	private String toCashB2C;
	private String fromCashC2B;
	private String toBankC2B;
	private String fromCashC2C;
	private String toCashC2C;
	private String description;
	private String createdBy;
	private String action;
	private String createdDate;
	private String createdTime;
	private String printedBy;

	private String curDate;
	private String dateFrom;
	private String dateTo;

	private String accountId;
	private String accountNo;
	private String accountBank;
	private String accountBranch;

	private String fromAccountB2BId;
	private String toAccountB2BId;
	private String fromBankB2CId;
	private String toBankC2BId;

	private String fromBank;
	private String fromBranch;
	private String fromAccount;
	private String fromCash;
	private String toBank;
	private String toBranch;
	private String toAccount;
	private String toCash;
	private String cvDescription;
	private String cvDate;
	private Double cvAmount;
	private String debitAccountGroup;
	private String creditAccountGroup;
	private String tds;

	public ContraVoucherModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getFromAccountB2BId() {
		return fromAccountB2BId;
	}

	public void setFromAccountB2BId(String fromAccountB2BId) {
		this.fromAccountB2BId = fromAccountB2BId;
	}

	public String getToAccountB2BId() {
		return toAccountB2BId;
	}

	public void setToAccountB2BId(String toAccountB2BId) {
		this.toAccountB2BId = toAccountB2BId;
	}

	public String getFromBankB2CId() {
		return fromBankB2CId;
	}

	public void setFromBankB2CId(String fromBankB2CId) {
		this.fromBankB2CId = fromBankB2CId;
	}

	public String getToBankC2BId() {
		return toBankC2BId;
	}

	public void setToBankC2BId(String toBankC2BId) {
		this.toBankC2BId = toBankC2BId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getDebAccGroupId() {
		return debAccGroupId;
	}

	public void setDebAccGroupId(String debAccGroupId) {
		this.debAccGroupId = debAccGroupId;
	}

	public String getCreAccGroupId() {
		return creAccGroupId;
	}

	public void setCreAccGroupId(String creAccGroupId) {
		this.creAccGroupId = creAccGroupId;
	}

	public String getContraVoucherId() {
		return contraVoucherId;
	}

	public void setContraVoucherId(String contraVoucherId) {
		this.contraVoucherId = contraVoucherId;
	}

	public String getContraVoucherType() {
		return contraVoucherType;
	}

	public void setContraVoucherType(String contraVoucherType) {
		this.contraVoucherType = contraVoucherType;
	}

	public String getCostCenterType() {
		return costCenterType;
	}

	public void setCostCenterType(String costCenterType) {
		this.costCenterType = costCenterType;
	}

	public String getContraVoucherDate() {
		return contraVoucherDate;
	}

	public void setContraVoucherDate(String contraVoucherDate) {
		this.contraVoucherDate = contraVoucherDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDebitedAccGroup() {
		return debitedAccGroup;
	}

	public void setDebitedAccGroup(String debitedAccGroup) {
		this.debitedAccGroup = debitedAccGroup;
	}

	public String getCreditedAccGroup() {
		return creditedAccGroup;
	}

	public void setCreditedAccGroup(String creditedAccGroup) {
		this.creditedAccGroup = creditedAccGroup;
	}

	public String getFromAccountB2B() {
		return fromAccountB2B;
	}

	public void setFromAccountB2B(String fromAccountB2B) {
		this.fromAccountB2B = fromAccountB2B;
	}

	public String getToAccountB2B() {
		return toAccountB2B;
	}

	public void setToAccountB2B(String toAccountB2B) {
		this.toAccountB2B = toAccountB2B;
	}

	public String getFromBankB2C() {
		return fromBankB2C;
	}

	public void setFromBankB2C(String fromBankB2C) {
		this.fromBankB2C = fromBankB2C;
	}

	public String getToCashB2C() {
		return toCashB2C;
	}

	public void setToCashB2C(String toCashB2C) {
		this.toCashB2C = toCashB2C;
	}

	public String getFromCashC2B() {
		return fromCashC2B;
	}

	public void setFromCashC2B(String fromCashC2B) {
		this.fromCashC2B = fromCashC2B;
	}

	public String getToBankC2B() {
		return toBankC2B;
	}

	public void setToBankC2B(String toBankC2B) {
		this.toBankC2B = toBankC2B;
	}

	public String getFromCashC2C() {
		return fromCashC2C;
	}

	public void setFromCashC2C(String fromCashC2C) {
		this.fromCashC2C = fromCashC2C;
	}

	public String getToCashC2C() {
		return toCashC2C;
	}

	public void setToCashC2C(String toCashC2C) {
		this.toCashC2C = toCashC2C;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}

	public String getFromBank() {
		return fromBank;
	}

	public void setFromBank(String fromBank) {
		this.fromBank = fromBank;
	}

	public String getFromBranch() {
		return fromBranch;
	}

	public void setFromBranch(String fromBranch) {
		this.fromBranch = fromBranch;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getFromCash() {
		return fromCash;
	}

	public void setFromCash(String fromCash) {
		this.fromCash = fromCash;
	}

	public String getToBank() {
		return toBank;
	}

	public void setToBank(String toBank) {
		this.toBank = toBank;
	}

	public String getToBranch() {
		return toBranch;
	}

	public void setToBranch(String toBranch) {
		this.toBranch = toBranch;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getToCash() {
		return toCash;
	}

	public void setToCash(String toCash) {
		this.toCash = toCash;
	}

	public String getCvDescription() {
		return cvDescription;
	}

	public void setCvDescription(String cvDescription) {
		this.cvDescription = cvDescription;
	}

	public String getCvDate() {
		return cvDate;
	}

	public void setCvDate(String cvDate) {
		this.cvDate = cvDate;
	}

	public Double getCvAmount() {
		return cvAmount;
	}

	public void setCvAmount(Double cvAmount) {
		this.cvAmount = cvAmount;
	}

	public String getDebitAccountGroup() {
		return debitAccountGroup;
	}

	public void setDebitAccountGroup(String debitAccountGroup) {
		this.debitAccountGroup = debitAccountGroup;
	}

	public String getCreditAccountGroup() {
		return creditAccountGroup;
	}

	public void setCreditAccountGroup(String creditAccountGroup) {
		this.creditAccountGroup = creditAccountGroup;
	}

	
	
	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
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
