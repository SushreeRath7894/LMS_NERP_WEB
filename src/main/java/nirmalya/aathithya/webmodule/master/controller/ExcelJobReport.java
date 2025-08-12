package nirmalya.aathithya.webmodule.master.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.view.document.AbstractXlsView;

import nirmalya.aathithya.webmodule.master.model.HrMasterModel;

public class ExcelJobReport extends AbstractXlsView {

	Logger logger = LoggerFactory.getLogger(ExcelJobReport.class);

	@SuppressWarnings("unchecked")
	@Override

	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument starts");

		try {
			List<HrMasterModel> accountLedger = (List<HrMasterModel>) model.get("JobExcelReport");
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("JobType ExcelReport");

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();

			font.setBold(true);
			font.setColor(HSSFColor.BLUE.index);

			style.setFont(font);

			realSheet.setDefaultColumnWidth(30);

			HSSFRow row = realSheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			cell = row.createCell(0);
			row.getCell(0).setCellStyle(style);
			cell.setCellValue("ID");

			cell = row.createCell(1);
			row.getCell(1).setCellStyle(style);
			cell.setCellValue("Order");

			cell = row.createCell(2);
			row.getCell(2).setCellStyle(style);
			cell.setCellValue("Name");

			cell = row.createCell(3);
			row.getCell(3).setCellStyle(style);
			cell.setCellValue("Status");

			int i = 1;

			for (HrMasterModel m : accountLedger) {
				row = realSheet.createRow(i++);

				cell = row.createCell(0);
				cell.setCellValue(m.getJobTypeId());

				cell = row.createCell(1);
				cell.setCellValue(m.getJobTypeOrder());

				cell = row.createCell(2);
				cell.setCellValue(m.getJobTypeName());

				cell = row.createCell(3);
				cell.setCellValue(m.getJobTypeStatus());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : buildExcelDocument ends");
	}
}
