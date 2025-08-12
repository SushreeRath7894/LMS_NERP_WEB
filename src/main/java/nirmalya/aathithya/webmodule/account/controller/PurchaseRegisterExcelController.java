package nirmalya.aathithya.webmodule.account.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import nirmalya.aathithya.webmodule.account.model.PurchaseRegisterExcelModel;
import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;
import nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseRegisterExcelController extends AbstractXlsView {
	Logger logger = LoggerFactory.getLogger(PurchaseRegisterExcelController.class);

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument function starts");
		try {
			List<PurchaseRegisterExcelModel> listModel = (List<PurchaseRegisterExcelModel>) model.get("listModel");
			//String days = attendanceDateModel.get(0).getDays();
			//System.err.println("DAYss" + days);
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Employee Attendance Sheet");

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			// System.err.println("DAYss"+days.equals("28"));
			font.setBold(true);
			//font.setColor(HSSFColor.RED.index);
			style.setFont(font);
			realSheet.setColumnWidth(0, 15 * 256);
			realSheet.setColumnWidth(1, 40 * 256);
			realSheet.setColumnWidth(2, 15 * 256);
			realSheet.setColumnWidth(3, 15 * 256);
			realSheet.setColumnWidth(4, 15 * 256);
			realSheet.setColumnWidth(5, 15 * 256);
			realSheet.setColumnWidth(6, 15 * 256);
		
			

			HSSFRow row = realSheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			row.getCell(0).setCellStyle(style);
			cell.setCellValue("Date");

			cell = row.createCell(1);
			row.getCell(1).setCellStyle(style);
			cell.setCellValue("Particular");

			cell = row.createCell(2);
			row.getCell(2).setCellStyle(style);
			cell.setCellValue("Voucher type");

			cell = row.createCell(3);
			row.getCell(3).setCellStyle(style);
			cell.setCellValue("Voucher No");

			cell = row.createCell(4);
			row.getCell(4).setCellStyle(style);
			cell.setCellValue("GSTIN/UIN");
			
			cell = row.createCell(5);
			row.getCell(5).setCellStyle(style);
			cell.setCellValue("Quantity");
			
			cell = row.createCell(6);
			row.getCell(6).setCellStyle(style);
			cell.setCellValue("Rate");


			int i = 1;
			// int j = 1;

			for (PurchaseRegisterExcelModel m : listModel) {

				row = realSheet.createRow(i++);
				/*
				 * cell = row.createCell(0); cell.setCellValue(j++);
				 */

				cell = row.createCell(0);
				cell.setCellValue(m.getVoucherDate());

				cell = row.createCell(1);
				cell.setCellValue(m.getParticulars());
				
				cell = row.createCell(2);
				cell.setCellValue(m.getVoucherType());

				cell = row.createCell(3);
				cell.setCellValue(m.getVoucherNo());

				cell = row.createCell(4);
				cell.setCellValue(m.getGstIn());

				cell = row.createCell(5);
				cell.setCellValue(m.getQuantity());
				
				cell = row.createCell(6);
				cell.setCellValue(m.getUnitRate());

				

			}
			logger.info("Method : buildExcelDocument function ends");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
