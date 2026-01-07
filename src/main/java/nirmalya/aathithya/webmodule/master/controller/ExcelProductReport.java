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

import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;

public class ExcelProductReport extends AbstractXlsView {
	
	Logger logger = LoggerFactory.getLogger(ExcelLocationReport.class);
	@SuppressWarnings("unchecked")
	@Override

	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument starts");
		
		try {
			List<ProductMasterModel> accountLedger = (List<ProductMasterModel>) model.get("product");
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Product Report");
		
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();

			font.setBold(true);
			font.setColor(HSSFColor.RED.index);

			style.setFont(font);

			realSheet.setDefaultColumnWidth(30);
			
			HSSFRow row = realSheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			
			cell = row.createCell(0);
			row.getCell(0).setCellStyle(style);
			cell.setCellValue("SKU");

			cell = row.createCell(1);
			row.getCell(1).setCellStyle(style);
			cell.setCellValue("BRAND");

			cell = row.createCell(2);
			row.getCell(2).setCellStyle(style);
			cell.setCellValue("NAME");
			
			cell = row.createCell(3);
			row.getCell(3).setCellStyle(style);
			cell.setCellValue("MODEL");
			
			cell = row.createCell(4);
			row.getCell(4).setCellStyle(style);
			cell.setCellValue("MANUFACTURE ITEM");
			
			cell = row.createCell(5);
			row.getCell(5).setCellStyle(style);
			cell.setCellValue("VARIATION TYPE");
			
			cell = row.createCell(6);
			row.getCell(6).setCellStyle(style);
			cell.setCellValue("VARIATION VALUE");
			
			cell = row.createCell(7);
			row.getCell(7).setCellStyle(style);
			cell.setCellValue("CATEGORY");
			
			cell = row.createCell(8);
			row.getCell(8).setCellStyle(style);
			cell.setCellValue("MODE");
			
			cell = row.createCell(9);
			row.getCell(8).setCellStyle(style);
			cell.setCellValue("UOM");
			
			cell = row.createCell(10);
			row.getCell(8).setCellStyle(style);
			cell.setCellValue("PURCHASE PRICE");
			
			cell = row.createCell(11);
			row.getCell(8).setCellStyle(style);
			cell.setCellValue("SALE PRICE");
			
			cell = row.createCell(12);
			row.getCell(9).setCellStyle(style);
			cell.setCellValue("STATUS");
			
			cell = row.createCell(13);
			row.getCell(10).setCellStyle(style);
			cell.setCellValue("CREATE DATE");
			
			int i = 2;
			int j = 1;
			
			for(ProductMasterModel m : accountLedger) {
				row = realSheet.createRow(i++);
				j++;
				
				if(m.getProductStatus().contentEquals("1")) {
					m.setProductStatus("Active");
				} else {
					m.setProductStatus("Inactive");
				}
				
				cell = row.createCell(0);
				cell.setCellValue(m.getSkuId());
				
				cell = row.createCell(1);
				cell.setCellValue(m.getBrand());
				
				cell = row.createCell(2);
				cell.setCellValue(m.getProductName());
				
				cell = row.createCell(3);
				cell.setCellValue(m.getModel());
				
				cell = row.createCell(4);
				cell.setCellValue(m.getManufacture());
				
				cell = row.createCell(5);
				cell.setCellValue(m.getVariationType());
				
				cell = row.createCell(6);
				cell.setCellValue(m.getVariationValue());
				
				cell = row.createCell(7);
				cell.setCellValue(m.getProductCategoryText());
				
				cell = row.createCell(8);
				cell.setCellValue(m.getMode());
				
				cell = row.createCell(9);
				cell.setCellValue(m.getUnit());
				
				cell = row.createCell(10);
				cell.setCellValue(m.getpPrice());
				
				cell = row.createCell(11);
				cell.setCellValue(m.getsPrice());
				
				cell = row.createCell(12);
				cell.setCellValue(m.getProductStatus());
				
				cell = row.createCell(12);
				cell.setCellValue(m.getCreatedDate());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : buildExcelDocument ends");
	}
}
