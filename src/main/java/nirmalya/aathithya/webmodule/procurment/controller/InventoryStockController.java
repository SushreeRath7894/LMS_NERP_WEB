package nirmalya.aathithya.webmodule.procurment.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.inventory.filedownload.InventoryStockReportExcel;
import nirmalya.aathithya.webmodule.inventory.model.InventoryStockDailyReportFinalModel;
import nirmalya.aathithya.webmodule.inventory.model.InventoryStockModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class InventoryStockController {
	Logger logger = LoggerFactory.getLogger(InventoryStockController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("view-stock")
	public String generateInventoryStockReport(Model model, HttpSession session) {

		logger.info("Method : generateInventoryStockReport starts");
		
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] brand = restTemplate.getForObject(
					env.getMasterUrl() + "getBrandListForProduct?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			System.err.println("DATAbrandList" + brandList);

			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() + "getModeListForProduct",
					DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] hsnCode = restTemplate.getForObject(env.getMasterUrl() + "getHSNCodeListForProduct",
					DropDownModel[].class);
			List<DropDownModel> hsnCodeList = Arrays.asList(hsnCode);

			model.addAttribute("hsnCodeList", hsnCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] vendor = restTemplate.getForObject(env.getMasterUrl() + "getVendorListForProduct",
					DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendor);

			model.addAttribute("vendorList", vendorList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : generateInventoryStockReport ends");
		return "purchase/view-stock";

	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-stock-trough-ajax" })
	public @ResponseBody List<InventoryStockModel> viewStockThroughAjax(HttpSession session,@RequestParam String type){
		logger.info("Method : viewStockThroughAjax starts");
 
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JsonResponse<List<InventoryStockModel>> jsonResponse = new
				  JsonResponse<List<InventoryStockModel>>();
		try {
         jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "get-stock-details?userid=" +userId +
        		 "&orgName=" + orgName + "&orgDiv=" + orgDivision+"&type="+type,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			List<InventoryStockModel> form = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<InventoryStockModel>>() {
					});

			jsonResponse.setBody(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewStockThroughAjax ends");
		return jsonResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-stock-daily-report" })
	public @ResponseBody JsonResponse<InventoryStockDailyReportFinalModel> getSalesReportGraph(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getSalesReportGraph starts");
		JsonResponse<InventoryStockDailyReportFinalModel> res = new JsonResponse<InventoryStockDailyReportFinalModel>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "view-stock-daily-report?id=" + id + "&empId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSalesReportGraph ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-stock-daily-report-excel")
	public ModelAndView generateStockChartExcel(HttpServletResponse servResponse, HttpSession session,
			@RequestParam String id) {
		logger.info("Method : generateStockChartExcel start");

		Map<String, Object> map = new HashMap<String, Object>();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			jsonResponse = restTemplate.getForObject(
					env.getPurchaseUrl() + "view-stock-daily-report?id=" + id + "&empId=" + userId,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			InventoryStockDailyReportFinalModel excelData = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<InventoryStockDailyReportFinalModel>() {
					});

			map.put("excelData", excelData);
			servResponse.setContentType("application/ms-excel");
			servResponse.setHeader("Content-disposition",
					"attachment; filename= Stock_Report_" + new Date().getTime() + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EmployeeReportController -> generateStockChartExcel GET", e);
		}
		logger.info("Method : generateStockChartExcel ends");
		return new ModelAndView(new InventoryStockReportExcel(), map);
	}

///////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-stock-get-product-details" })
	public @ResponseBody JsonResponse<ProductMasterModel> getProductDetails(Model model, @RequestBody String tCountry,
			BindingResult result,HttpSession session) {
		logger.info("Method : getProductDetails starts");

		JsonResponse<ProductMasterModel> res = new JsonResponse<ProductMasterModel>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry+"&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getProductDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-stock-get-sku-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUListingById(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUListingById starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();

		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUListingById?id=" + id +"&orgName=" + orgName + "&orgDiv=" + orgDivision , JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
			}
			res.setBody(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSKUListingById ends");
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-stock-get-purchase-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUPurchaseListing(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUPurchaseListing starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUPurchaseListingById?id=" + id +"&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			String dateFormat = "";

			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
				m.setsMoq(NumberFormatter.doubleToStringWithComma(m.getMoq()));
			}
			res.setBody(product);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getSKUPurchaseListing ends");
		return res.getBody();

	}

	///////////////////////////////////////////////

	@SuppressWarnings({ "unchecked", "restriction" })
	@PostMapping("view-stock-save")
	public @ResponseBody JsonResponse<Object> saveProductMaster(@RequestBody ProductMasterModel product,
			HttpSession session) {
		logger.info("Method : saveProductMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);
	

		/*
		 * List<String> productImg = new ArrayList<String>();
		 * logger.info(product.getImgList()); if(product.getImgList().size() > 0)
		 * { logger.info("Len==="+product.getImgList().size()); for(int i = 0; i
		 * < product.getImgList().size(); i++) { try { byte[] bytes = new
		 * sun.misc.BASE64Decoder().decodeBuffer(product.getImgList().get(i).split(
		 * "base64,")[1]); String ext =
		 * product.getImgList().get(i).split("base64,")[0].split("/")[1].split(";")[0].
		 * trim(); String imageName = saveAllImage(bytes,ext);
		 * productImg.add(imageName); } catch (IOException e) { e.printStackTrace();
		 * resp.setCode("Error");
		 * resp.setMessage("Unexpected Error : Unable To Save Images."); return resp; }
		 * } }
		 */
		MultipartFile inputFile = (MultipartFile) session.getAttribute("productPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				product.setpImgName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductMaster", product,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveProductMaster starts");
		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	/////////////////////////
	@SuppressWarnings({ "unchecked" })
	@PostMapping("view-stock-save-sku-dtls")
	public @ResponseBody JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel product,
			HttpSession session) {
		logger.info("Method : saveProductDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductDetails", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductDetails starts");
		return resp;
	}

	/////////////////////////////////
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-stock-save-purchase-dtls")
	public @ResponseBody JsonResponse<Object> saveProductPurchaseDetails(@RequestBody ProductDetailsModel product,
			HttpSession session) {
		logger.info("Method : saveProductPurchaseDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductPurchaseDetails", product,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductPurchaseDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-stock-get-sku-details" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUDetails(Model model,
			@RequestBody DropDownModel tCountry, BindingResult result) {
		logger.info("Method : getSKUDetails starts");

		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();

		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "getSKUDetailsById?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSKUDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-stock-get-purchase-details-edit" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUPurchaseDetails(Model model,
			@RequestBody ProductDetailsModel tCountry, BindingResult result) {
		logger.info("Method : getSKUPurchaseDetails starts");

		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUPurchaseDetails?id=" + tCountry.getProductId()
					+ "&skuid=" + tCountry.getSku() + "&vendorId=" + tCountry.getVendorId(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSKUPurchaseDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-stock-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModal(HttpSession session) {
		logger.info("Method : getProductCategoryDataListModal starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getProductCategoryDataListModal starts");
		return resp;
	}

	///////////////////////////////////////

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping(value = { "view-stock-get-sku-listing" }) public @ResponseBody
	 * List<ProductMasterModel> getProductSKUListing(Model model, HttpServletRequest
	 * request, HttpSession session) {
	 * logger.info("Method : getProductSKUListing starts");
	 * 
	 * JsonResponse<List<ProductMasterModel>> res = new
	 * JsonResponse<List<ProductMasterModel>>();
	 * 
	 * try { res = restTemplate.getForObject(env.getMasterUrl() +
	 * "getProductSKUListing", JsonResponse.class);
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * List<ProductMasterModel> product = mapper.convertValue(res.getBody(), new
	 * TypeReference<List<ProductMasterModel>>() { });
	 * 
	 * String dateFormat = "";
	 * 
	 * try { dateFormat = (String) session.getAttribute("DATEFORMAT"); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * for(ProductMasterModel m : product) {
	 * 
	 * if(m.getProductStatus().contentEquals("1")) { m.setProductStatus("Active"); }
	 * else { m.setProductStatus("Inactive"); }
	 * 
	 * m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
	 * 
	 * m.setpPrice(NumberFormatter.doubleToStringWithComma(m.getPurchasePrice()));
	 * m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice())); }
	 * 
	 * res.setBody(product);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } if (res.getMessage() != null)
	 * { res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); }
	 * 
	 * logger.info("Method : getProductSKUListing ends"); return res.getBody();
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-stock-delete")
	public @ResponseBody JsonResponse<Object> deletesku(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deletesku starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		// logger.info(env.getEmployeeUrl() + "deletework?id="+ id);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletesku?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : deletesku ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-stock-purchase-delete")
	public @ResponseBody JsonResponse<Object> deletepurchase(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : deletesku starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		// logger.info(env.getEmployeeUrl() + "deletework?id="+ id);
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deletepurchase?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {

		}

		logger.info("Method : deletepurchase ends");

		return resp;
	}
	@PostMapping("view-stock-upload-file")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("productPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}
	
	@PostMapping("view-stock-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}
}
