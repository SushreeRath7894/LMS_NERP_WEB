package nirmalya.aathithya.webmodule.master.controller;

/**
 * Download Excel ViewFucntion
 */

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;
//import nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
/**
 * @author NirmalyaLabs
 *
 */
public class EmployeePayrollAttendanceExcelModel extends AbstractXlsView {
	Logger logger = LoggerFactory.getLogger(EmployeePayrollAttendanceExcelModel.class);

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument function starts");
		try {
			List<AttendanceDateModel> attendanceDateModel = (List<AttendanceDateModel>) model.get("attendance");
			String days = attendanceDateModel.get(0).getDays();

			String month = attendanceDateModel.get(0).getMonth();
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Employee Attendance Sheet");

			CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
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
            
			CellStyle defaultStyle = workbook.createCellStyle();
			defaultStyle.setAlignment(HorizontalAlignment.CENTER);  // Center-align the text

			defaultStyle.setBorderTop(BorderStyle.THIN);
			defaultStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			defaultStyle.setBorderBottom(BorderStyle.THIN);
			defaultStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			defaultStyle.setBorderLeft(BorderStyle.THIN);
			defaultStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			defaultStyle.setBorderRight(BorderStyle.THIN);
			defaultStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			
			 CellStyle totalstyle = workbook.createCellStyle();
				Font totalfont = workbook.createFont();
				totalfont.setBold(true);
				totalstyle.setFont(totalfont);
				totalstyle.setAlignment(HorizontalAlignment.CENTER);

				totalstyle.setBorderTop(BorderStyle.THIN);
				totalstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
				totalstyle.setBorderBottom(BorderStyle.THIN);
				totalstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				totalstyle.setBorderLeft(BorderStyle.THIN);
				totalstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				totalstyle.setBorderRight(BorderStyle.THIN);
				totalstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
			CellStyle style1 = workbook.createCellStyle();
			Font font1 = workbook.createFont();
			font1.setBold(true);
			style1.setFont(font1);
		
			CellStyle style2 = workbook.createCellStyle();
			Font font2 = workbook.createFont();
			style2.setFont(font2);
				
			realSheet.setColumnWidth(0, 15 * 256);
			realSheet.setColumnWidth(1, 30 * 256);
			realSheet.setColumnWidth(2, 20 * 256);
			realSheet.setColumnWidth(3, 20 * 256);
			realSheet.setColumnWidth(4, 11 * 256);
			realSheet.setColumnWidth(5, 11 * 256);
			realSheet.setColumnWidth(6, 11 * 256);
			realSheet.setColumnWidth(7, 11 * 256);
			realSheet.setColumnWidth(8, 11 * 256);
			realSheet.setColumnWidth(9, 11 * 256);
			realSheet.setColumnWidth(10, 11 * 256);
			realSheet.setColumnWidth(11, 11 * 256);
			realSheet.setColumnWidth(12, 11 * 256);
			realSheet.setColumnWidth(13, 11 * 256);
			realSheet.setColumnWidth(14, 11 * 256);
			realSheet.setColumnWidth(15, 11 * 256);
			realSheet.setColumnWidth(16, 11 * 256);
			realSheet.setColumnWidth(17, 11 * 256);
			realSheet.setColumnWidth(18, 11 * 256);
			realSheet.setColumnWidth(19, 11 * 256);
			realSheet.setColumnWidth(20, 11 * 256);
			realSheet.setColumnWidth(21, 11 * 256);
			realSheet.setColumnWidth(22, 11 * 256);
			realSheet.setColumnWidth(23, 11 * 256);
			realSheet.setColumnWidth(24, 11 * 256);
			realSheet.setColumnWidth(25, 11 * 256);
			realSheet.setColumnWidth(26, 11 * 256);
			realSheet.setColumnWidth(27, 11 * 256);
			realSheet.setColumnWidth(28, 11 * 256);
			realSheet.setColumnWidth(29, 11 * 256);
			realSheet.setColumnWidth(30, 11 * 256);
			if (days.equals("28")) {
				realSheet.setColumnWidth(31, 11 * 256);
				realSheet.setColumnWidth(32, 15 * 256);
				realSheet.setColumnWidth(33, 15 * 256);
				realSheet.setColumnWidth(33, 15 * 256);
				realSheet.setColumnWidth(34, 18 * 256);
				realSheet.setColumnWidth(35, 18 * 256);
				realSheet.setColumnWidth(36, 25 * 256);
				realSheet.setColumnWidth(37, 25 * 256);
				realSheet.setColumnWidth(38, 25 * 256);
			}

			if (days.equals("29")) {
				realSheet.setColumnWidth(31, 11 * 256);
				realSheet.setColumnWidth(32, 11 * 256);
				realSheet.setColumnWidth(33, 15 * 256);
				realSheet.setColumnWidth(34, 15 * 256);
				realSheet.setColumnWidth(35, 15 * 256);
				realSheet.setColumnWidth(36, 18 * 256);
				realSheet.setColumnWidth(37, 18 * 256);
				realSheet.setColumnWidth(38, 25 * 256);
				realSheet.setColumnWidth(39, 25 * 256);
				realSheet.setColumnWidth(40, 25 * 256);
			} else if (days.equals("30")) {
				realSheet.setColumnWidth(31, 11 * 256);
				realSheet.setColumnWidth(32, 11 * 256);
				realSheet.setColumnWidth(33, 11 * 256);
				realSheet.setColumnWidth(34, 15 * 256);
				realSheet.setColumnWidth(35, 15 * 256);
				realSheet.setColumnWidth(36, 15 * 256);
				realSheet.setColumnWidth(37, 18 * 256);
				realSheet.setColumnWidth(38, 18 * 256);
				realSheet.setColumnWidth(39, 25 * 256);
				realSheet.setColumnWidth(40, 25 * 256);
				realSheet.setColumnWidth(41, 25 * 256);
			} else if (days.equals("31")) {
				realSheet.setColumnWidth(31, 8 * 256);
				realSheet.setColumnWidth(32, 8 * 256);
				realSheet.setColumnWidth(33, 8 * 256);
				realSheet.setColumnWidth(34, 8 * 256);
				realSheet.setColumnWidth(35, 15 * 256);
				realSheet.setColumnWidth(36, 15 * 256);
				realSheet.setColumnWidth(37, 15 * 256);
				realSheet.setColumnWidth(38, 18 * 256);
				realSheet.setColumnWidth(39, 18 * 256);
				realSheet.setColumnWidth(40, 25 * 256);
				realSheet.setColumnWidth(41, 25 * 256);
				realSheet.setColumnWidth(42, 25 * 256);
			} else {
				// Handle other cases if needed
			}

			HSSFRow row = realSheet.createRow(0);
			createCell(row, 0, "Employee Id", headerStyle);
			createCell(row, 1, "Employee Name", headerStyle);
			createCell(row, 2, "Department", headerStyle);
			createCell(row, 3, "Designation", headerStyle);

		    createRichTextCell(row, 0, 0, "Employee Id", headerStyle);
		    createRichTextCell(row, 1, 0, "Employee Name", headerStyle);
		    createRichTextCell(row, 2, 0, "Department", headerStyle);
		    createRichTextCell(row, 3, 0, "Designation", headerStyle);
			/*
			 * for (int day = 1; day <= 31; day++) { if (day <= Integer.parseInt(days)) {
			 * createCell(row, day + 3, "Day " + day, headerStyle); } else { createCell(row,
			 * day + 3, "", headerStyle); // Empty cells for days beyond the current month's
			 * length } }
			 */
			createMonthlyCalendar(row, month, headerStyle, days);
 
			int i = 1;
			// int j = 1;

			Double present=0.0;
			Double leave=0.0;
			Double offAvail=0.0;
			Double lop=0.0;
			Double holiday=0.0;
			Double workingDay=0.0;
			Double workday=0.0; 
			
			for (AttendanceDateModel m : attendanceDateModel) {
				
				row = realSheet.createRow(i++);
				createCell(row, 0, m.getEmpId(), defaultStyle);
				createCell(row, 1, m.getName(), style1);
				createCell(row, 2, m.getDept(), style2);
				createCell(row, 3, m.getDesg(), style2);
				createCell(row, 4, m.getId1(), defaultStyle);
				createCell(row, 5, m.getId2(), defaultStyle);
				createCell(row, 6, m.getId3(), defaultStyle);
				createCell(row, 7, m.getId4(), defaultStyle);
				createCell(row, 8, m.getId5(), defaultStyle);
				createCell(row, 9, m.getId6(), defaultStyle);
				createCell(row, 10, m.getId7(), defaultStyle);
				createCell(row, 11, m.getId8(), defaultStyle);
				createCell(row, 12, m.getId9(), defaultStyle);
				createCell(row, 13, m.getId10(), defaultStyle);
				createCell(row, 14, m.getId11(), defaultStyle);
				createCell(row, 15, m.getId12(), defaultStyle);
				createCell(row, 16, m.getId13(), defaultStyle);
				createCell(row, 17, m.getId14(), defaultStyle);
				createCell(row, 18, m.getId15(), defaultStyle);
				createCell(row, 19, m.getId16(), defaultStyle);
				createCell(row, 20, m.getId17(), defaultStyle);
				createCell(row, 21, m.getId18(), defaultStyle);
				createCell(row, 22, m.getId19(), defaultStyle);
				createCell(row, 23, m.getId20(), defaultStyle);
				createCell(row, 24, m.getId21(), defaultStyle);
				createCell(row, 25, m.getId22(), defaultStyle);
				createCell(row, 26, m.getId23(), defaultStyle);
				createCell(row, 27, m.getId24(), defaultStyle);
				createCell(row, 28, m.getId25(), defaultStyle);
				createCell(row, 29, m.getId26(), defaultStyle);
				createCell(row, 30, m.getId27(), defaultStyle);
				createCell(row, 31, m.getId28(), defaultStyle);
				

				if (days.equals("29")) {
					createCell(row, 32, m.getId29(), defaultStyle);
					createCell1(row, 33, Double.parseDouble(m.getTotalPresent()), defaultStyle);
					createCell1(row, 34, Double.parseDouble(m.getLeave()), defaultStyle);
					createCell1(row, 35, Double.parseDouble(m.getHoliday()), defaultStyle);
					createCell1(row, 36, Double.parseDouble(m.getOffAvailed()), defaultStyle);
					createCell1(row, 37, Double.parseDouble(m.getLop()), defaultStyle);
					createCell1(row, 38, Double.parseDouble(m.getWorkingday()), defaultStyle);
					createCell1(row, 39, Double.parseDouble(m.getWorkday()), defaultStyle);
					createCell(row, 40, m.getApproveStatus(), defaultStyle);

				} else if (days.equals("30")) {
					createCell(row, 32, m.getId29(), defaultStyle);
					createCell(row, 33, m.getId30(), defaultStyle);
					createCell1(row, 34, Double.parseDouble(m.getTotalPresent()), defaultStyle);
					createCell1(row, 35, Double.parseDouble(m.getLeave()), defaultStyle);
					createCell1(row, 36, Double.parseDouble(m.getHoliday()), defaultStyle);
					createCell1(row, 37, Double.parseDouble(m.getOffAvailed()), defaultStyle);
					createCell1(row, 38, Double.parseDouble(m.getLop()), defaultStyle);
					createCell1(row, 39, Double.parseDouble(m.getWorkingday()), defaultStyle);
					createCell1(row, 40, Double.parseDouble(m.getWorkday()), defaultStyle);
					createCell(row, 41, m.getApproveStatus(), defaultStyle);
				} else if (days.equals("31")) {
					createCell(row, 32, m.getId29(), defaultStyle);
					createCell(row, 33, m.getId30(), defaultStyle);
					createCell(row, 34, m.getId31(), defaultStyle);
					createCell1(row, 35, Double.parseDouble(m.getTotalPresent()), defaultStyle);
					createCell1(row, 36, Double.parseDouble(m.getLeave()), defaultStyle);
					createCell1(row, 37, Double.parseDouble(m.getHoliday()), defaultStyle);
					createCell1(row, 38, Double.parseDouble(m.getOffAvailed()), defaultStyle);
					createCell1(row, 39, Double.parseDouble(m.getLop()), defaultStyle);
					createCell1(row, 40, Double.parseDouble(m.getWorkingday()), defaultStyle);
					createCell1(row, 41, Double.parseDouble(m.getWorkday()), defaultStyle);
					createCell(row, 42, m.getApproveStatus(), defaultStyle);
				} else {
					createCell1(row, 32, Double.parseDouble(m.getTotalPresent()), defaultStyle);
					createCell1(row, 33, Double.parseDouble(m.getLeave()), defaultStyle);
					createCell1(row, 34, Double.parseDouble(m.getHoliday()), defaultStyle);
					createCell1(row, 35, Double.parseDouble(m.getOffAvailed()), defaultStyle);
					createCell1(row, 36, Double.parseDouble(m.getLop()), defaultStyle);
					createCell1(row, 37, Double.parseDouble(m.getWorkingday()), defaultStyle);
					createCell1(row, 38, Double.parseDouble(m.getWorkday()), defaultStyle);
					createCell(row, 39, m.getApproveStatus(), defaultStyle);
				}

				present= present + Double.parseDouble(m.getTotalPresent());
				leave= leave + Double.parseDouble(m.getLeave());
				holiday= holiday + Double.parseDouble(m.getHoliday());
				offAvail= offAvail + Double.parseDouble(m.getOffAvailed());
				lop= lop + Double.parseDouble(m.getLop());
				workingDay= workingDay + Double.parseDouble(m.getWorkingday());
				workday= workday + Double.parseDouble(m.getWorkday());

			}
			row = realSheet.createRow(i++);
			createCell(row, 0, "Total", totalstyle);
			createCell(row, 1, "", totalstyle);
			createCell(row, 2, "", totalstyle);
			createCell(row, 3, "", totalstyle);
			for (int day = 1; day <= 28; day++) {
					createCell(row, day + 3, "", totalstyle);
			}

			if (days.equals("29")) {
				createCell(row, 32, "", totalstyle);
				createCell1(row, 33, present, totalstyle);
				createCell1(row, 34, leave, totalstyle);
				createCell1(row, 35, holiday, totalstyle);
				createCell1(row, 36, offAvail, totalstyle);
				createCell1(row, 37, lop, totalstyle);
				createCell1(row, 38, workingDay, totalstyle);
				createCell1(row, 39, workday, totalstyle);
				createCell(row, 40, "", totalstyle);
			} else if (days.equals("30")) {
				createCell(row, 32, "", totalstyle);
				createCell(row, 33, "", totalstyle);
				createCell1(row, 34, present, totalstyle);
				createCell1(row, 35, leave, totalstyle);
				createCell1(row, 36, holiday, totalstyle);
				createCell1(row, 37, offAvail, totalstyle);
				createCell1(row, 38, lop, totalstyle);
				createCell1(row, 39, workingDay, totalstyle);
				createCell1(row, 40, workday, totalstyle);
				createCell(row, 41, "", totalstyle);
			} else if (days.equals("31")) {
				createCell(row, 32, "", totalstyle);
				createCell(row, 33, "", totalstyle);
				createCell(row, 34, "", totalstyle);
				createCell1(row, 35, present, totalstyle);
				createCell1(row, 36, leave, totalstyle);
				createCell1(row, 37, holiday, totalstyle);
				createCell1(row, 38, offAvail, totalstyle);
				createCell1(row, 39, lop, totalstyle);
				createCell1(row, 40, workingDay, totalstyle);
				createCell1(row, 41, workday, totalstyle);
				createCell(row, 42, "", totalstyle);
			} else {
				System.err.println("in else");
				createCell1(row, 32, present, totalstyle);
				createCell1(row, 33, leave, totalstyle);
				createCell1(row, 34, holiday, totalstyle);
				createCell1(row, 35, offAvail, totalstyle);
				createCell1(row, 36, lop, totalstyle);
				createCell1(row, 37, workingDay, totalstyle);
				createCell1(row, 38, workday, totalstyle);
				createCell(row, 39, "", totalstyle);
			}
			logger.info("Method : buildExcelDocument function starts");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void createMonthlyCalendar(HSSFRow row, String monthYear, CellStyle style, String days) {
	    // Example monthYear format: "June 2024"
	    String[] parts = monthYear.split(" ");
	    Month month = Month.valueOf(parts[0].toUpperCase());
	    int year = Integer.parseInt(parts[1]);

	    // Determine the number of days in the specified month and year
	    int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();

	    // Create cells for each day of the month
	    for (int day = 1; day <= 31; day++) {
	        if (day <= daysInMonth) {
	            LocalDate date = LocalDate.of(year, month, day);
	            String dayWord = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
	            createRichTextCell(row, day + 3, day, dayWord, style);
	        } else {
	            createRichTextCell(row, day + 3, 0, "", style); // Empty cells for days beyond the current month's length
	        }
	    }

	    // Add additional cells based on the number of days
	    int dayCount = Integer.parseInt(days);
	    addAdditionalCells(row, style, dayCount, month, year);
	}

	private void addAdditionalCells(HSSFRow row, CellStyle style, int dayCount, Month month ,int year ) {
	    int startCol = 32;
	    for (int day = 29; day <= dayCount; day++) {LocalDate date = LocalDate.of(year, month, day);
        String dayWord = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
	        createRichTextCell(row, startCol++, day, dayWord, style);
	    }
	    createRichTextCell(row, startCol++, 0, "Present", style);
	    createRichTextCell(row, startCol++, 0, "Leave", style);
	    createRichTextCell(row, startCol++, 0, "Holiday", style);
	    createRichTextCell(row, startCol++, 0, "OFF Availed", style);
	    createRichTextCell(row, startCol++, 0, "Loss Of Pay", style);
	    createRichTextCell(row, startCol++, 0, "Employee Workday", style);
	    createRichTextCell(row, startCol++, 0, "Company Workday", style);
	    createRichTextCell(row, startCol++, 0, "Approval Status", style);
	}
	private void createCell(HSSFRow row, int column, String value, CellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
	private void createCell1(HSSFRow row, int column, Double value, CellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
	private void createRichTextCell(HSSFRow row, int column, int day, String dayWord, CellStyle style) {
	    HSSFCell cell = row.createCell(column);
	    cell.setCellStyle(style);

	    HSSFWorkbook workbook = row.getSheet().getWorkbook();

	    // Create fonts
	    HSSFFont dayFont = workbook.createFont();
	    dayFont.setBold(true);
	    dayFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
	    dayFont.setFontHeightInPoints((short) 12);
//
//	    HSSFFont wordFont = workbook.createFont();
//	    wordFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
//	    wordFont.setFontHeightInPoints((short) 12);

	    // Create rich text string
	    HSSFRichTextString richString;
	    if (day != 0) {
	        richString = new HSSFRichTextString(day + "(" + dayWord+") ");
	        richString.applyFont( dayFont);
	   //     richString.applyFont(Integer.toString(day).length() + 1, richString.length(), dayFont);
	    } else {
	        richString = new HSSFRichTextString(dayWord);
	        richString.applyFont(dayFont);
	    }

	    // Set the rich text string in the cell
	    cell.setCellValue(richString);
	}

}
