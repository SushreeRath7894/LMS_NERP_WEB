package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CCAccountMapModel {

	private String id;
	private String name;
	private String parentId;
	private String parentName;
	private String slNo;
	private BigInteger count;
	List<CCAccountMapModel> chartAccList = new ArrayList<CCAccountMapModel>();
	private Double m1;
	private Double m2;
	private Double m3;
	private Double m4;
	private Double m5;
	private Double m6;
	private Double m7;
	private Double m8;
	private Double m9;
	private Double m10;
	private Double m11;
	private Double m12;
	private Double total;
	
	public CCAccountMapModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}
	
	public List<CCAccountMapModel> getChartAccList() {
		return chartAccList;
	}

	public void setChartAccList(List<CCAccountMapModel> chartAccList) {
		this.chartAccList = chartAccList;
	}

	public Double getM1() {
		return m1;
	}

	public void setM1(Double m1) {
		this.m1 = m1;
	}

	public Double getM2() {
		return m2;
	}

	public void setM2(Double m2) {
		this.m2 = m2;
	}

	public Double getM3() {
		return m3;
	}

	public void setM3(Double m3) {
		this.m3 = m3;
	}

	public Double getM4() {
		return m4;
	}

	public void setM4(Double m4) {
		this.m4 = m4;
	}

	public Double getM5() {
		return m5;
	}

	public void setM5(Double m5) {
		this.m5 = m5;
	}

	public Double getM6() {
		return m6;
	}

	public void setM6(Double m6) {
		this.m6 = m6;
	}

	public Double getM7() {
		return m7;
	}

	public void setM7(Double m7) {
		this.m7 = m7;
	}

	public Double getM8() {
		return m8;
	}

	public void setM8(Double m8) {
		this.m8 = m8;
	}

	public Double getM9() {
		return m9;
	}

	public void setM9(Double m9) {
		this.m9 = m9;
	}

	public Double getM10() {
		return m10;
	}

	public void setM10(Double m10) {
		this.m10 = m10;
	}

	public Double getM11() {
		return m11;
	}

	public void setM11(Double m11) {
		this.m11 = m11;
	}

	public Double getM12() {
		return m12;
	}

	public void setM12(Double m12) {
		this.m12 = m12;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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
