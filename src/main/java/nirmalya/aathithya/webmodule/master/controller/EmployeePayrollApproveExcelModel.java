package nirmalya.aathithya.webmodule.master.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import nirmalya.aathithya.webmodule.master.model.PayrollModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeePayrollApproveExcelModel extends AbstractXlsView {

    Logger logger = LoggerFactory.getLogger(EmployeePayrollApproveExcelModel.class);
    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        logger.info("Method : buildExcelDocument function starts");
        try {
            List<PayrollModel> payrollModelList = (List<PayrollModel>) model.get("attendance");
         //   String days = payrollModelList.get(0).getDays();
            HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Employee Payroll Sheet");

            CellStyle headerStyle = workbook.createCellStyle();
          //  CellStyle style = createIndianNumberFormatStyle(workbook);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
    	    headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);

            headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            

			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			headerStyle.setBorderRight(BorderStyle.THIN);
			headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

            CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
	        style.setFont(font);

	        style.setBorderTop(BorderStyle.THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderRight(BorderStyle.THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            

            CellStyle defaultStyle = workbook.createCellStyle();
            defaultStyle.setAlignment(HorizontalAlignment.CENTER);

	        defaultStyle.setBorderTop(BorderStyle.THIN);
	        defaultStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        defaultStyle.setBorderBottom(BorderStyle.THIN);
	        defaultStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        defaultStyle.setBorderLeft(BorderStyle.THIN);
	        defaultStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        defaultStyle.setBorderRight(BorderStyle.THIN);
	        defaultStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
            
			
            CellStyle style1 = workbook.createCellStyle();
			Font font1 = workbook.createFont();
			style1.setAlignment(HorizontalAlignment.CENTER);
	        style1.setFont(font1);
	        font1.setBold(true);
	        style1.setBorderTop(BorderStyle.THIN);
	        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style1.setBorderBottom(BorderStyle.THIN);
	        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style1.setBorderLeft(BorderStyle.THIN);
	        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style1.setBorderRight(BorderStyle.THIN);
	        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
            
            CellStyle totalstyle = workbook.createCellStyle();
			Font totalfont = workbook.createFont();
			totalfont.setBold(true);
			totalstyle.setFont(totalfont);
			totalstyle.setAlignment(HorizontalAlignment.CENTER);
			totalstyle.setBorderTop(BorderStyle.THICK);
			totalstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			totalstyle.setBorderBottom(BorderStyle.THICK);
			totalstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			totalstyle.setBorderLeft(BorderStyle.THICK);
			totalstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			totalstyle.setBorderRight(BorderStyle.THICK);
			totalstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        
			int padding = 4; // Number of spaces for padding
//	        int[] columnWidths = {15 , 25 , 20 , 10 , 10 , 10 , 10 };

            int[] columnWidths = {15 , 30 , 30 , 25 , 25 , 25 , 15 , 15 , 15 ,
            					  15 , 19 , 15 , 15 , 15 , 25 , 19 , 25 , 25 ,
            					  25 , 25 , 15 , 15 , 15 , 15 , 19 , 19 , 19 ,
            					  19 , 15 , 15 , 15 , 15 , 20 , 22 , 20 , 19 ,
            					  19 , 19 , 19 , 19 };
            for (int i = 0; i < columnWidths.length; i++) {
                realSheet.setColumnWidth(i, columnWidths[i] * 256);
            }
            HSSFRow row = realSheet.createRow(0);
            
		//	HSSFCell cell = row.createCell(0);

			createCell(row, 0, "Employee Id", headerStyle);
            createCell(row, 1, "Employee Name", headerStyle);
            createCell(row, 2, "Father Name", headerStyle);

            createCell(row, 3, "Designation", headerStyle);
            createCell(row, 4, "Department", headerStyle);
            createCell(row, 5, "Sub-Department", headerStyle);
            createCell(row, 6, "Work Day", headerStyle);
            createCell(row, 7, "Present", headerStyle);
            createCell(row, 8, "Leave", headerStyle);
            createCell(row, 9, "Off Days", headerStyle);
            createCell(row, 10, "Working Day", headerStyle);
            createCell(row, 11, "Attendance", headerStyle);
            createCell(row, 12, "Basic", headerStyle);
            createCell(row, 13, "Hra", headerStyle);
            createCell(row, 14, "Conveinence Allowance", headerStyle);
            createCell(row, 15, "Wash Allowance", headerStyle);
            createCell(row, 16, "Medical Allowance", headerStyle);
            createCell(row, 17, "Special Allowance", headerStyle);
            createCell(row, 18, "Skill Development", headerStyle);
            createCell(row, 19, "Other Allowance", headerStyle);
            createCell(row, 20, "Arear", headerStyle);
            createCell(row, 21, "Bonus", headerStyle);
            createCell(row, 22, "Reward", headerStyle);
            createCell(row, 23, "OverTime", headerStyle);
            createCell(row, 24, "Total Earning", headerStyle);
            createCell(row, 25, "Employee EPF", headerStyle);
            createCell(row, 26, "Employee ESI", headerStyle);
            createCell(row, 27, "ProfTax", headerStyle);
            createCell(row, 28, "IncTax", headerStyle);
            createCell(row, 29, "Advance", headerStyle);
            createCell(row, 30, "Lic", headerStyle);
            createCell(row, 31, "Wel. Fund", headerStyle);
            createCell(row, 32, "Other Penalty", headerStyle);
            createCell(row, 33, "Other Deductions", headerStyle);
            createCell(row, 34, "Total Deduction", headerStyle);
            createCell(row, 35, "Net Payable", headerStyle);
            createCell(row, 36, "Company EPF", headerStyle);
            createCell(row, 37, "Company ESI", headerStyle);
            createCell(row, 38, "Admin Charge", headerStyle);
            createCell(row, 39, "Edli Charge", headerStyle);
			int i = 1;
			Double workDay=0.0;
			Double present=0.0;
			Double leave=0.0;
			Double offday=0.0;
			Double workingDay=0.0;
			Double basic=0.0;
			Double hra=0.0;
			Double medical=0.0;
			Double specialAllowance=0.0;
			Double otherAllow=0.0;
			Double conve=0.0;
			Double washAllow=0.0;
			Double skillDev=0.0;
			Double bonus =0.0;
			Double arear=0.0;
			Double overTime=0.0;
			Double reward=0.0;
			Double totalEarning=0.0;

			Double empEPF=0.0;
			Double empESI=0.0;
			Double compEPF=0.0;
			Double compESI=0.0;
			Double profTax=0.0;
			Double incTax=0.0;
			Double advance=0.0;
			Double welfund=0.0;
			Double lic=0.0;
			Double otherpenamnt=0.0;
			Double other=0.0;
			Double totalDeduction=0.0;
			Double netPay=0.0;
			Double adminCharge=0.0;
			Double edliCharge=0.0;
            for (PayrollModel payroll : payrollModelList) {
            	row = realSheet.createRow(i++);
                createCell(row, 0, payroll.getEmpId(), defaultStyle);
                createCell(row, 1, payroll.getEmpName(), style);
                createCell(row, 2, payroll.getFatherName(), style);
//                createCell(row, 3, payroll.getBandId(), defaultStyle);
                createCell(row, 3, payroll.getDesigName(), defaultStyle);
                createCell(row, 4, payroll.getDept(), defaultStyle);
                createCell(row, 5, payroll.getSubDept(), defaultStyle);
                
                createCellNum(row, 6, Double.parseDouble(payroll.getWorkDay()), defaultStyle);
                workDay= workDay + Double.parseDouble(payroll.getWorkDay());
                
                createCellNum(row, 7, Double.parseDouble(payroll.getPresent()), defaultStyle);
                present= present + Double.parseDouble(payroll.getPresent());
                
                createCellNum(row, 8, Double.parseDouble(payroll.getLeave()), defaultStyle);
                leave= leave + Double.parseDouble(payroll.getLeave());
                
                createCellNum(row, 9, Double.parseDouble(payroll.getOffday()), defaultStyle);
                offday= offday + Double.parseDouble(payroll.getOffday());
                
                createCellNum(row, 10, Double.parseDouble(payroll.getWorkingDay()), style1);
                workingDay= workingDay + Double.parseDouble(payroll.getWorkingDay());
                
                createCell(row, 11, (payroll.getAttendance().toString()+'%'), defaultStyle);
                
                createCellNum(row, 12, Double.parseDouble(payroll.getBasic()), defaultStyle);
                basic= basic + Double.parseDouble(payroll.getBasic());
                
                createCellNum(row, 13, Double.parseDouble(payroll.getHra()), defaultStyle);
                hra= hra + Double.parseDouble(payroll.getHra());
                
                createCellNum(row, 14, Double.parseDouble(payroll.getConve()), defaultStyle);
                conve= conve + Double.parseDouble(payroll.getConve());
                
                createCellNum(row, 15, Double.parseDouble(payroll.getWashAllow()), defaultStyle);
                washAllow= washAllow + Double.parseDouble(payroll.getWashAllow());
                
                createCellNum(row, 16, Double.parseDouble(payroll.getMedical()), defaultStyle);
                medical= medical + Double.parseDouble(payroll.getMedical());
                
                createCellNum(row, 17, Double.parseDouble(payroll.getSpecialAllowance()), defaultStyle);
                specialAllowance= specialAllowance + Double.parseDouble(payroll.getSpecialAllowance());
                
                createCellNum(row, 18, Double.parseDouble(payroll.getSkillDev()), defaultStyle);
                skillDev= skillDev + Double.parseDouble(payroll.getSkillDev());
                
                createCellNum(row, 19, Double.parseDouble(payroll.getOtherAllow()), defaultStyle);
                otherAllow= otherAllow + Double.parseDouble(payroll.getOtherAllow());
                
                createCellNum(row, 20, Double.parseDouble(payroll.getArear()), defaultStyle);
                arear= arear + Double.parseDouble(payroll.getArear());
                
                createCellNum(row, 21, Double.parseDouble(payroll.getBonus()), defaultStyle);
                bonus= bonus + Double.parseDouble(payroll.getBonus());
                
                createCellNum(row, 22, Double.parseDouble(payroll.getReward()), defaultStyle);
                reward= reward + Double.parseDouble(payroll.getReward());
                
                createCellNum(row, 23, Double.parseDouble(payroll.getOverTime()), defaultStyle);
                overTime= overTime + Double.parseDouble(payroll.getOverTime());
                
                createCellNum(row, 24, Double.parseDouble(payroll.getTotalEarning()), style1);
                totalEarning= totalEarning + Double.parseDouble(payroll.getTotalEarning());
                
                createCellNum(row, 25, Double.parseDouble(payroll.getEmpEPF()), defaultStyle);
                empEPF= empEPF + Double.parseDouble(payroll.getEmpEPF());
                
                createCellNum(row, 26, Double.parseDouble(payroll.getEmpESI()), defaultStyle);
                empESI= empESI + Double.parseDouble(payroll.getEmpESI());
                
                createCellNum(row, 27, Double.parseDouble(payroll.getProfTax()), defaultStyle);
                profTax= profTax + Double.parseDouble(payroll.getProfTax());
                
                createCellNum(row, 28, Double.parseDouble(payroll.getIncTax()), defaultStyle);
                incTax= incTax + Double.parseDouble(payroll.getIncTax());
                
                createCellNum(row, 29, Double.parseDouble(payroll.getAdvance()), defaultStyle);
                advance= advance + Double.parseDouble(payroll.getAdvance());
                
                createCellNum(row, 30, Double.parseDouble(payroll.getLic()), defaultStyle);
                lic= lic + Double.parseDouble(payroll.getLic());
                
                createCellNum(row, 31, Double.parseDouble(payroll.getWelfund()), defaultStyle);
                welfund= welfund + Double.parseDouble(payroll.getWelfund());
                
                createCellNum(row, 32, Double.parseDouble(payroll.getOtherpenamnt()), defaultStyle);
                otherpenamnt= otherpenamnt + Double.parseDouble(payroll.getOtherpenamnt());
                
                createCellNum(row, 33, Double.parseDouble(payroll.getOther()), defaultStyle);
                other= other + Double.parseDouble(payroll.getOther());
                
                createCellNum(row, 34, Double.parseDouble(payroll.getTotalDeduction()), style1);
                totalDeduction= totalDeduction + Double.parseDouble(payroll.getTotalDeduction());
                
                createCellNum(row, 35, Double.parseDouble(payroll.getNetPay()), style1);
                netPay= netPay + Double.parseDouble(payroll.getNetPay());
                
                createCellNum(row, 36, Double.parseDouble(payroll.getCompEPF()), defaultStyle);
                compEPF= compEPF + Double.parseDouble(payroll.getCompEPF());
                
                createCellNum(row, 37, Double.parseDouble(payroll.getCompESI()), defaultStyle);
                compESI= compESI + Double.parseDouble(payroll.getCompESI());
                
                createCellNum(row, 38, Double.parseDouble(payroll.getAdminCharge()), defaultStyle);
                adminCharge= adminCharge + Double.parseDouble(payroll.getAdminCharge());
                
                createCellNum(row, 39, Double.parseDouble(payroll.getEdliCharge()), defaultStyle);
                edliCharge= edliCharge + Double.parseDouble(payroll.getEdliCharge());
                
            }
            System.err.println("value of i==="+i);
            String attendance = String.format("%.2f%%", ((double) workingDay / workDay) * 100) ;
            
            row = realSheet.createRow(i++);
            createCell(row, 0, "Total", totalstyle);
            createCell(row, 1, "", totalstyle);
            createCell(row, 2, "", totalstyle);

            createCell(row, 3, "", totalstyle);
            createCell(row, 4, "", totalstyle);
            createCell(row, 5, "", totalstyle);
            createCellNum(row, 6, workDay, totalstyle);
            createCellNum(row, 7, present, totalstyle);
            createCellNum(row, 8, leave, totalstyle);
            createCellNum(row, 9, offday, totalstyle);
            createCellNum(row, 10, workingDay, totalstyle);
            createCell(row, 11, attendance, totalstyle);
            createCellNum(row, 12, basic, totalstyle);
            createCellNum(row, 13, hra, totalstyle);
            createCellNum(row, 14, conve, totalstyle);
            createCellNum(row, 15, washAllow, totalstyle);
            createCellNum(row, 16, medical, totalstyle);
            createCellNum(row, 17, specialAllowance, totalstyle);
            createCellNum(row, 18, skillDev, totalstyle);
            createCellNum(row, 19, otherAllow, totalstyle);
            createCellNum(row, 20, arear, totalstyle);
            createCellNum(row, 21, bonus, totalstyle);
            createCellNum(row, 22, reward, totalstyle);
            createCellNum(row, 23, overTime, totalstyle);
            createCellNum(row, 24, totalEarning, totalstyle);
            createCellNum(row, 25, empEPF, totalstyle);
            createCellNum(row, 26, empESI, totalstyle);
            createCellNum(row, 27, profTax, totalstyle);
            createCellNum(row, 28, incTax, totalstyle);
            createCellNum(row, 29, advance, totalstyle);
            createCellNum(row, 30, lic, totalstyle);
            createCellNum(row, 31, welfund, totalstyle);
            createCellNum(row, 32, otherpenamnt, totalstyle);
            createCellNum(row, 33, other, totalstyle);
            createCellNum(row, 34, totalDeduction, totalstyle);
            createCellNum(row, 35, netPay, totalstyle);
            createCellNum(row, 36, compEPF, totalstyle);
            createCellNum(row, 37, compESI, totalstyle);
            createCellNum(row, 38, adminCharge, totalstyle);
            createCellNum(row, 39, edliCharge, totalstyle);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Method : buildExcelDocument function ends==");
    }

    private void createCell(HSSFRow row, int column, String value, CellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
    private void createCellNum(HSSFRow row, int column, Double value, CellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

}
