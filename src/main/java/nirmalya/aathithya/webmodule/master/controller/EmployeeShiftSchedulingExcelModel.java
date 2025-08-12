/*
 * package nirmalya.aathithya.webmodule.master.controller;
 * 
 *//**
	 * Download Excel ViewFucntion
	 */
/*
 * 
 * import java.util.List; import java.util.Map;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.web.servlet.view.document.AbstractXlsView;
 * 
 * import
 * nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;
 * 
 * import org.apache.poi.hssf.usermodel.HSSFCell; import
 * org.apache.poi.hssf.usermodel.HSSFRow; import
 * org.apache.poi.hssf.usermodel.HSSFSheet; import
 * org.apache.poi.hssf.usermodel.HSSFWorkbook; import
 * org.apache.poi.hssf.util.HSSFColor; import
 * org.apache.poi.ss.usermodel.CellStyle; import
 * org.apache.poi.ss.usermodel.Font; import
 * org.apache.poi.ss.usermodel.Workbook; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory;
 * 
 *//**
	 * @author NirmalyaLabs
	 *
	 *//*
		 * public class EmployeeShiftSchedulingExcelModel extends AbstractXlsView {
		 * Logger logger =
		 * LoggerFactory.getLogger(EmployeeShiftSchedulingExcelModel.class);
		 * 
		 * @Override
		 * 
		 * @SuppressWarnings("unchecked") protected void buildExcelDocument(Map<String,
		 * Object> model, Workbook workbook, HttpServletRequest request,
		 * HttpServletResponse response) throws Exception {
		 * logger.info("Method : buildExcelDocument function starts"); try {
		 * List<EmployeeShiftSchedulingModel> employeeShiftSchedulingModel =
		 * (List<EmployeeShiftSchedulingModel>) model.get("shift"); String
		 * days=employeeShiftSchedulingModel.get(0).getDays();
		 * 
		 * HSSFSheet realSheet = ((HSSFWorkbook)
		 * workbook).createSheet("Employee Shift Scheduling Sheet");
		 * 
		 * CellStyle style = workbook.createCellStyle(); Font font =
		 * workbook.createFont();
		 * 
		 * font.setBold(true); font.setColor(HSSFColor.RED.index);
		 * 
		 * style.setFont(font);
		 * 
		 * realSheet.setDefaultColumnWidth(20); HSSFRow row = realSheet.createRow(0);
		 * HSSFCell cell = row.createCell(0);
		 * 
		 * row.getCell(0).setCellStyle(style); cell.setCellValue("Employee Id");
		 * 
		 * 
		 * cell = row.createCell(1); row.getCell(1).setCellStyle(style);
		 * cell.setCellValue("Employee Name");
		 * 
		 * cell = row.createCell(2); row.getCell(2).setCellStyle(style);
		 * cell.setCellValue("Assign Status");
		 * 
		 * cell = row.createCell(3); row.getCell(3).setCellStyle(style);
		 * cell.setCellValue("Day 1");
		 * 
		 * cell = row.createCell(4); row.getCell(4).setCellStyle(style);
		 * cell.setCellValue("Day 2");
		 * 
		 * cell = row.createCell(5); row.getCell(5).setCellStyle(style);
		 * cell.setCellValue("Day 3");
		 * 
		 * cell = row.createCell(6); row.getCell(6).setCellStyle(style);
		 * cell.setCellValue("Day 4");
		 * 
		 * cell = row.createCell(7); row.getCell(7).setCellStyle(style);
		 * cell.setCellValue("Day 5");
		 * 
		 * cell = row.createCell(8); row.getCell(8).setCellStyle(style);
		 * cell.setCellValue("Day 6");
		 * 
		 * cell = row.createCell(9); row.getCell(9).setCellStyle(style);
		 * cell.setCellValue("Day 7");
		 * 
		 * cell = row.createCell(10); row.getCell(10).setCellStyle(style);
		 * cell.setCellValue("Day 8");
		 * 
		 * cell = row.createCell(11); row.getCell(11).setCellStyle(style);
		 * cell.setCellValue("Day 9");
		 * 
		 * cell = row.createCell(12); row.getCell(12).setCellStyle(style);
		 * cell.setCellValue("Day 10");
		 * 
		 * cell = row.createCell(13); row.getCell(13).setCellStyle(style);
		 * cell.setCellValue("Day 11");
		 * 
		 * cell = row.createCell(14); row.getCell(14).setCellStyle(style);
		 * cell.setCellValue("Day 12");
		 * 
		 * cell = row.createCell(15); row.getCell(15).setCellStyle(style);
		 * cell.setCellValue("Day 13");
		 * 
		 * cell = row.createCell(16); row.getCell(16).setCellStyle(style);
		 * cell.setCellValue("Day 14");
		 * 
		 * cell = row.createCell(17); row.getCell(17).setCellStyle(style);
		 * cell.setCellValue("Day 15");
		 * 
		 * cell = row.createCell(18); row.getCell(18).setCellStyle(style);
		 * cell.setCellValue("Day 16");
		 * 
		 * cell = row.createCell(19); row.getCell(19).setCellStyle(style);
		 * cell.setCellValue("Day 17");
		 * 
		 * cell = row.createCell(20); row.getCell(20).setCellStyle(style);
		 * cell.setCellValue("Day 18");
		 * 
		 * cell = row.createCell(21); row.getCell(21).setCellStyle(style);
		 * cell.setCellValue("Day 19");
		 * 
		 * cell = row.createCell(22); row.getCell(22).setCellStyle(style);
		 * cell.setCellValue("Day 20");
		 * 
		 * cell = row.createCell(23); row.getCell(23).setCellStyle(style);
		 * cell.setCellValue("Day 21");
		 * 
		 * cell = row.createCell(24); row.getCell(24).setCellStyle(style);
		 * cell.setCellValue("Day 22");
		 * 
		 * cell = row.createCell(25); row.getCell(25).setCellStyle(style);
		 * cell.setCellValue("Day 23");
		 * 
		 * cell = row.createCell(26); row.getCell(26).setCellStyle(style);
		 * cell.setCellValue("Day 24");
		 * 
		 * cell = row.createCell(27); row.getCell(27).setCellStyle(style);
		 * cell.setCellValue("Day 25");
		 * 
		 * cell = row.createCell(28); row.getCell(28).setCellStyle(style);
		 * cell.setCellValue("Day 26");
		 * 
		 * cell = row.createCell(29); row.getCell(29).setCellStyle(style);
		 * cell.setCellValue("Day 27");
		 * 
		 * cell = row.createCell(30); row.getCell(30).setCellStyle(style);
		 * cell.setCellValue("Day 28");
		 * 
		 * if(days.equals("29")) { cell = row.createCell(31);
		 * row.getCell(31).setCellStyle(style); cell.setCellValue("Day 29"); }else
		 * if(days.equals("30")) { cell = row.createCell(31);
		 * row.getCell(31).setCellStyle(style); cell.setCellValue("Day 29");
		 * 
		 * cell = row.createCell(32); row.getCell(32).setCellStyle(style);
		 * cell.setCellValue("Day 30"); }else if(days.equals("31")) { cell =
		 * row.createCell(31); row.getCell(31).setCellStyle(style);
		 * cell.setCellValue("Day 29");
		 * 
		 * cell = row.createCell(32); row.getCell(32).setCellStyle(style);
		 * cell.setCellValue("Day 30");
		 * 
		 * cell = row.createCell(33); row.getCell(33).setCellStyle(style);
		 * cell.setCellValue("Day 31"); }else {
		 * 
		 * }
		 * 
		 * 
		 * int i = 1; //int j = 1;
		 * 
		 * 
		 * for (EmployeeShiftSchedulingModel m : employeeShiftSchedulingModel) {
		 * 
		 * row = realSheet.createRow(i++);
		 * 
		 * cell = row.createCell(0); cell.setCellValue(j++);
		 * 
		 * 
		 * cell = row.createCell(0); cell.setCellValue(m.getEmpId());
		 * 
		 * cell = row.createCell(1); cell.setCellValue(m.getName());
		 * 
		 * cell = row.createCell(2); cell.setCellValue(m.getStatus());
		 * 
		 * cell = row.createCell(3); cell.setCellValue(m.getId1());
		 * 
		 * cell = row.createCell(4); cell.setCellValue(m.getId2());
		 * 
		 * cell = row.createCell(5); cell.setCellValue(m.getId3());
		 * 
		 * cell = row.createCell(6); cell.setCellValue(m.getId4());
		 * 
		 * cell = row.createCell(7); cell.setCellValue(m.getId5());
		 * 
		 * cell = row.createCell(8); cell.setCellValue(m.getId6());
		 * 
		 * cell = row.createCell(9); cell.setCellValue(m.getId7());
		 * 
		 * cell = row.createCell(10); cell.setCellValue(m.getId8());
		 * 
		 * cell = row.createCell(11); cell.setCellValue(m.getId9());
		 * 
		 * cell = row.createCell(12); cell.setCellValue(m.getId10());
		 * 
		 * cell = row.createCell(13); cell.setCellValue(m.getId11());
		 * 
		 * cell = row.createCell(14); cell.setCellValue(m.getId12());
		 * 
		 * cell = row.createCell(15); cell.setCellValue(m.getId13());
		 * 
		 * cell = row.createCell(16); cell.setCellValue(m.getId14());
		 * 
		 * cell = row.createCell(17); cell.setCellValue(m.getId15());
		 * 
		 * cell = row.createCell(18); cell.setCellValue(m.getId16());
		 * 
		 * cell = row.createCell(19); cell.setCellValue(m.getId17());
		 * 
		 * cell = row.createCell(20); cell.setCellValue(m.getId18());
		 * 
		 * cell = row.createCell(21); cell.setCellValue(m.getId19());
		 * 
		 * cell = row.createCell(22); cell.setCellValue(m.getId20());
		 * 
		 * cell = row.createCell(23); cell.setCellValue(m.getId21());
		 * 
		 * cell = row.createCell(24); cell.setCellValue(m.getId22());
		 * 
		 * cell = row.createCell(25); cell.setCellValue(m.getId23());
		 * 
		 * cell = row.createCell(26); cell.setCellValue(m.getId24());
		 * 
		 * cell = row.createCell(27); cell.setCellValue(m.getId25());
		 * 
		 * cell = row.createCell(28); cell.setCellValue(m.getId26());
		 * 
		 * cell = row.createCell(29); cell.setCellValue(m.getId27());
		 * 
		 * cell = row.createCell(30); cell.setCellValue(m.getId28());
		 * 
		 * if(days.equals("29")) { cell = row.createCell(31);
		 * cell.setCellValue(m.getId29()); }else if(days.equals("30")) { cell =
		 * row.createCell(31); cell.setCellValue(m.getId29());
		 * 
		 * cell = row.createCell(32); cell.setCellValue(m.getId30());
		 * 
		 * }else if(days.equals("31")) {
		 * 
		 * cell = row.createCell(31); cell.setCellValue(m.getId29());
		 * 
		 * cell = row.createCell(32); cell.setCellValue(m.getId30());
		 * 
		 * 
		 * cell = row.createCell(33); cell.setCellValue(m.getId31()); }else {
		 * 
		 * }
		 * 
		 * } logger.info("Method : buildExcelDocument function starts");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * } }
		 */