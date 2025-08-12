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

import nirmalya.aathithya.webmodule.master.model.ProcurementMasterModel;


public class ExcelMeasureReport extends AbstractXlsView{
	Logger logger = LoggerFactory.getLogger(ExcelMeasureReport.class);

	@SuppressWarnings("unchecked")
	@Override

	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument starts");

		try {
			List<ProcurementMasterModel> accountLedger = (List<ProcurementMasterModel>) model.get("MeasureExcelReport");
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Measurement Type ExcelReport");

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
			cell.setCellValue("Code");


			cell = row.createCell(3);
			row.getCell(3).setCellStyle(style);
			cell.setCellValue("Measurement Name");

			cell = row.createCell(4);
			row.getCell(4).setCellStyle(style);
			cell.setCellValue("Status");

			int i = 1;

			for (ProcurementMasterModel m : accountLedger) {
				row = realSheet.createRow(i++);

				cell = row.createCell(0);
				cell.setCellValue(m.getMeasurementId());

				cell = row.createCell(1);
				cell.setCellValue(m.getMeasurementOrder());
				
				cell = row.createCell(2);
				cell.setCellValue(m.getMeasurementCode());

				cell = row.createCell(3);
				cell.setCellValue(m.getMeasurementName());

				cell = row.createCell(4);
				cell.setCellValue(m.getMeasurementStatus());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : buildExcelDocument ends");
	}
}
