package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeTaxDeclarationModel {

	private String userid;
	private String total;
	private String month;
	private String payItems;
	private String january;
	private String february;
	private String march;
	private String april;
	private String may;
	private String june;
	private String july;
	private String august;
	private String september;
	private String october;
	private String november;
	private String december;

	private String totalAdhoc;
	private String monthAdhoc;
	private String payItemsAdhoc;
	private String januaryAdhoc;
	private String februaryAdhoc;
	private String marchAdhoc;
	private String aprilAdhoc;
	private String mayAdhoc;
	private String juneAdhoc;
	private String julyAdhoc;
	private String augustAdhoc;
	private String septemberAdhoc;
	private String octoberAdhoc;
	private String novemberAdhoc;
	private String decemberAdhoc;

	private String totalOtherItem;
	private String payItemsOtherItem;
	private String januaryOtherItem;
	private String februaryOtherItem;
	private String marchOtherItem;
	private String aprilOtherItem;
	private String mayOtherItem;
	private String juneOtherItem;
	private String julyOtherItem;
	private String augustOtherItem;
	private String septemberOtherItem;
	private String octoberOtherItem;
	private String novemberOtherItem;
	private String decemberOtherItem;

	private String perquisiteItem;
	private String perquisiteAmount;
	

	private String sectionInvestment;
	private String section;
	private String gross;
	private String qualifying;
	private String deductable;
	public EmployeeTaxDeclarationModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPerquisiteItem() {
		return perquisiteItem;
	}

	public void setPerquisiteItem(String perquisiteItem) {
		this.perquisiteItem = perquisiteItem;
	}

	public String getPerquisiteAmount() {
		return perquisiteAmount;
	}

	public void setPerquisiteAmount(String perquisiteAmount) {
		this.perquisiteAmount = perquisiteAmount;
	}

	public String getTotalOtherItem() {
		return totalOtherItem;
	}

	public void setTotalOtherItem(String totalOtherItem) {
		this.totalOtherItem = totalOtherItem;
	}

	public String getPayItemsOtherItem() {
		return payItemsOtherItem;
	}

	public void setPayItemsOtherItem(String payItemsOtherItem) {
		this.payItemsOtherItem = payItemsOtherItem;
	}

	public String getJanuaryOtherItem() {
		return januaryOtherItem;
	}

	public void setJanuaryOtherItem(String januaryOtherItem) {
		this.januaryOtherItem = januaryOtherItem;
	}

	public String getFebruaryOtherItem() {
		return februaryOtherItem;
	}

	public void setFebruaryOtherItem(String februaryOtherItem) {
		this.februaryOtherItem = februaryOtherItem;
	}

	public String getMarchOtherItem() {
		return marchOtherItem;
	}

	public void setMarchOtherItem(String marchOtherItem) {
		this.marchOtherItem = marchOtherItem;
	}

	public String getAprilOtherItem() {
		return aprilOtherItem;
	}

	public void setAprilOtherItem(String aprilOtherItem) {
		this.aprilOtherItem = aprilOtherItem;
	}

	public String getMayOtherItem() {
		return mayOtherItem;
	}

	public void setMayOtherItem(String mayOtherItem) {
		this.mayOtherItem = mayOtherItem;
	}

	public String getJuneOtherItem() {
		return juneOtherItem;
	}

	public void setJuneOtherItem(String juneOtherItem) {
		this.juneOtherItem = juneOtherItem;
	}

	public String getJulyOtherItem() {
		return julyOtherItem;
	}

	public void setJulyOtherItem(String julyOtherItem) {
		this.julyOtherItem = julyOtherItem;
	}

	public String getAugustOtherItem() {
		return augustOtherItem;
	}

	public void setAugustOtherItem(String augustOtherItem) {
		this.augustOtherItem = augustOtherItem;
	}

	public String getSeptemberOtherItem() {
		return septemberOtherItem;
	}

	public void setSeptemberOtherItem(String septemberOtherItem) {
		this.septemberOtherItem = septemberOtherItem;
	}

	public String getOctoberOtherItem() {
		return octoberOtherItem;
	}

	public void setOctoberOtherItem(String octoberOtherItem) {
		this.octoberOtherItem = octoberOtherItem;
	}

	public String getNovemberOtherItem() {
		return novemberOtherItem;
	}

	public void setNovemberOtherItem(String novemberOtherItem) {
		this.novemberOtherItem = novemberOtherItem;
	}

	public String getDecemberOtherItem() {
		return decemberOtherItem;
	}

	public void setDecemberOtherItem(String decemberOtherItem) {
		this.decemberOtherItem = decemberOtherItem;
	}

	public String getTotalAdhoc() {
		return totalAdhoc;
	}

	public void setTotalAdhoc(String totalAdhoc) {
		this.totalAdhoc = totalAdhoc;
	}

	public String getMonthAdhoc() {
		return monthAdhoc;
	}

	public void setMonthAdhoc(String monthAdhoc) {
		this.monthAdhoc = monthAdhoc;
	}

	public String getPayItemsAdhoc() {
		return payItemsAdhoc;
	}

	public void setPayItemsAdhoc(String payItemsAdhoc) {
		this.payItemsAdhoc = payItemsAdhoc;
	}

	public String getJanuaryAdhoc() {
		return januaryAdhoc;
	}

	public void setJanuaryAdhoc(String januaryAdhoc) {
		this.januaryAdhoc = januaryAdhoc;
	}

	public String getFebruaryAdhoc() {
		return februaryAdhoc;
	}

	public void setFebruaryAdhoc(String februaryAdhoc) {
		this.februaryAdhoc = februaryAdhoc;
	}

	public String getMarchAdhoc() {
		return marchAdhoc;
	}

	public void setMarchAdhoc(String marchAdhoc) {
		this.marchAdhoc = marchAdhoc;
	}

	public String getAprilAdhoc() {
		return aprilAdhoc;
	}

	public void setAprilAdhoc(String aprilAdhoc) {
		this.aprilAdhoc = aprilAdhoc;
	}

	public String getMayAdhoc() {
		return mayAdhoc;
	}

	public void setMayAdhoc(String mayAdhoc) {
		this.mayAdhoc = mayAdhoc;
	}

	public String getJuneAdhoc() {
		return juneAdhoc;
	}

	public void setJuneAdhoc(String juneAdhoc) {
		this.juneAdhoc = juneAdhoc;
	}

	public String getJulyAdhoc() {
		return julyAdhoc;
	}

	public void setJulyAdhoc(String julyAdhoc) {
		this.julyAdhoc = julyAdhoc;
	}

	public String getAugustAdhoc() {
		return augustAdhoc;
	}

	public void setAugustAdhoc(String augustAdhoc) {
		this.augustAdhoc = augustAdhoc;
	}

	public String getSeptemberAdhoc() {
		return septemberAdhoc;
	}

	public void setSeptemberAdhoc(String septemberAdhoc) {
		this.septemberAdhoc = septemberAdhoc;
	}

	public String getOctoberAdhoc() {
		return octoberAdhoc;
	}

	public void setOctoberAdhoc(String octoberAdhoc) {
		this.octoberAdhoc = octoberAdhoc;
	}

	public String getNovemberAdhoc() {
		return novemberAdhoc;
	}

	public void setNovemberAdhoc(String novemberAdhoc) {
		this.novemberAdhoc = novemberAdhoc;
	}

	public String getDecemberAdhoc() {
		return decemberAdhoc;
	}

	public void setDecemberAdhoc(String decemberAdhoc) {
		this.decemberAdhoc = decemberAdhoc;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPayItems() {
		return payItems;
	}

	public void setPayItems(String payItems) {
		this.payItems = payItems;
	}

	public String getJanuary() {
		return january;
	}

	public void setJanuary(String january) {
		this.january = january;
	}

	public String getFebruary() {
		return february;
	}

	public void setFebruary(String february) {
		this.february = february;
	}

	public String getMarch() {
		return march;
	}

	public void setMarch(String march) {
		this.march = march;
	}

	public String getApril() {
		return april;
	}

	public void setApril(String april) {
		this.april = april;
	}

	public String getMay() {
		return may;
	}

	public void setMay(String may) {
		this.may = may;
	}

	public String getJune() {
		return june;
	}

	public void setJune(String june) {
		this.june = june;
	}

	public String getJuly() {
		return july;
	}

	public void setJuly(String july) {
		this.july = july;
	}

	public String getAugust() {
		return august;
	}

	public void setAugust(String august) {
		this.august = august;
	}

	public String getSeptember() {
		return september;
	}

	public void setSeptember(String september) {
		this.september = september;
	}

	public String getOctober() {
		return october;
	}

	public void setOctober(String october) {
		this.october = october;
	}

	public String getNovember() {
		return november;
	}

	public void setNovember(String november) {
		this.november = november;
	}

	public String getDecember() {
		return december;
	}

	public void setDecember(String december) {
		this.december = december;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSectionInvestment() {
		return sectionInvestment;
	}

	public void setSectionInvestment(String sectionInvestment) {
		this.sectionInvestment = sectionInvestment;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getGross() {
		return gross;
	}

	public void setGross(String gross) {
		this.gross = gross;
	}

	public String getQualifying() {
		return qualifying;
	}

	public void setQualifying(String qualifying) {
		this.qualifying = qualifying;
	}

	public String getDeductable() {
		return deductable;
	}

	public void setDeductable(String deductable) {
		this.deductable = deductable;
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
