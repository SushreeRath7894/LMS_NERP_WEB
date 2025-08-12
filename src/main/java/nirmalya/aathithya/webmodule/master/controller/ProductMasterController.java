package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
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
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class ProductMasterController {

	Logger logger = LoggerFactory.getLogger(ProductMasterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-product" })
	public String manageProduct(Model model, HttpSession session) {
		logger.info("Method : manageProduct starts");
		
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] brand = restClient.getForObject(env.getMasterUrl()+"getBrandListForProduct?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			
			model.addAttribute("brandList", brandList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] mode = restClient.getForObject(env.getMasterUrl()+"getModeListForProduct", DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);
			
			model.addAttribute("modeList", modeList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] hsnCode = restClient.getForObject(env.getMasterUrl()+"getHSNCodeListForProduct", DropDownModel[].class);
			List<DropDownModel> hsnCodeList = Arrays.asList(hsnCode);
			
			model.addAttribute("hsnCodeList", hsnCodeList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restClient.getForObject(env.getMasterUrl()+"getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);
			
			model.addAttribute("variationTypeList", variationTypeList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl()+"getUOMListForProduct", DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);
			
			model.addAttribute("unitList", unitList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] vendor = restClient.getForObject(env.getMasterUrl()+"getVendorListForProductWise?org=" + org + "&orgDiv=" + orgDiv,DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendor);
			model.addAttribute("vendorList", vendorList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : manageProduct ends");
		return "master/manageProduct";
	}
 
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-product-get-category-list-by-id")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getProductCategoryListById starts");
		
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getProductCategoryListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getProductCategoryListById starts");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-product-save")
	public @ResponseBody JsonResponse<Object> saveProductMaster(@RequestBody ProductMasterModel product, HttpSession session) {
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
			resp = restClient.postForObject(env.getMasterUrl() + "saveProductMaster", product,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		if(resp.getCode().equals("success")) {
			session.removeAttribute("productPFile");
		}
		
		logger.info("Method : saveProductMaster starts");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-product-save-sku-dtls")
	public @ResponseBody JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel product, HttpSession session) {
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
			resp = restClient.postForObject(env.getMasterUrl() + "saveProductDetails", product,
					JsonResponse.class);
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
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping("/manage-product-save-purchase-dtls")
	public @ResponseBody JsonResponse<Object> saveProductPurchaseDetails(@RequestBody ProductDetailsModel product, HttpSession session) {
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
			resp = restClient.postForObject(env.getMasterUrl() + "saveProductPurchaseDetails", product,
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
	
	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");
		
		String imageName = null;
		
		try {
			
			if(imageBytes!=null) {
				long nowTime = new Date().getTime();
				if(ext.contentEquals("jpeg")) {
					imageName = nowTime+".jpg";
				} else {
					imageName = nowTime+"."+ext;
				}
				
			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if(imageBytes !=null) {
				Files.write(path, imageBytes);
				
				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;
				
				try{
					BufferedImage img = ImageIO.read(in);
					if(height == 0){
						height = (width * img.getHeight())/img.getWidth();
					}
					if(width == 0){
						width = (height * img.getWidth())/img.getHeight();
					}
					
					BufferedImage outputImage = new BufferedImage(width,
							height, img.getType());
			 
			        Graphics2D g2d = outputImage.createGraphics();
			        g2d.drawImage(img, 0, 0, width, height, null);
			        g2d.dispose();
			        String outputImagePath = env.getFileUploadMaster()+"thumb/"+imageName;
			        ImageIO.write(outputImage, ext, new File(outputImagePath));

				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-product-get-sku-listing" })
	public @ResponseBody List<ProductMasterModel> getProductSKUListing(Model model, HttpServletRequest request,@RequestParam String type, HttpSession session) {
		logger.info("Method : getProductSKUListing starts");
		
		JsonResponse<List<ProductMasterModel>> res = new JsonResponse<List<ProductMasterModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getProductSKUListing?type="+type +"&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();

			List<ProductMasterModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductMasterModel>>() {
					});
			for(ProductMasterModel m : product) {
				
				if(m.getProductStatus().contentEquals("1")) {
					m.setProductStatus("Active");
				} else {
					m.setProductStatus("Inactive");
				}
			    m.setpPrice(NumberFormatter.doubleToStringWithComma(m.getPurchasePrice()));
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
		
		logger.info("Method : getProductSKUListing ends"+res.getBody());
		return res.getBody();
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "manage-product-get-sku-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUListingById(Model model, @RequestParam String id, HttpServletRequest request, HttpSession session) {
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
			res = restClient.getForObject(env.getMasterUrl() + "getSKUListingById?id="+id +"&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});
			
			for(ProductDetailsModel m : product) {
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
	@GetMapping(value = { "manage-product-get-purchase-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUPurchaseListing(Model model, @RequestParam String id, HttpServletRequest request, HttpSession session) {
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
			res = restClient.getForObject(env.getMasterUrl() + "getSKUPurchaseListingById?id="+id +"&orgName=" + orgName + "&orgDiv=" + orgDivision,
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
			
			for(ProductDetailsModel m : product) {
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
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-product-get-product-details" })
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
			res = restClient.getForObject(env.getMasterUrl() + "getProductDetailsById?id=" + tCountry+"&orgName=" + orgName + "&orgDiv=" + orgDivision,
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
		
		logger.info("Method : getProductDetails ends"+res);
		return res;
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-product-get-sku-details" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUDetails(Model model, @RequestBody DropDownModel tCountry,
			BindingResult result) {
		logger.info("Method : getSKUDetails starts");
		
		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();
		
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getSKUDetailsById?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
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
	@PostMapping(value = { "manage-product-get-purchase-details-edit" })
	public @ResponseBody JsonResponse<ProductDetailsModel> getSKUPurchaseDetails(Model model, @RequestBody ProductDetailsModel tCountry,
			BindingResult result,HttpSession session) {
		logger.info("Method : getSKUPurchaseDetails starts");
		
		JsonResponse<ProductDetailsModel> res = new JsonResponse<ProductDetailsModel>();
		String orgName = "";
		String orgDivision = "";
            try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getSKUPurchaseDetails?id=" + tCountry.getProductId() + "&skuid=" + tCountry.getSku() + "&vendorId=" + tCountry.getVendorId()+"&orgName=" + orgName + "&orgDiv=" + orgDivision,
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
		
		logger.info("Method : getSKUPurchaseDetails ends");
		return res;
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-product-get-variant-dtls" })
	public @ResponseBody JsonResponse<DropDownModel> getVariantDetails(Model model, @RequestBody DropDownModel tCountry,
			BindingResult result) {
		logger.info("Method : getVariantDetails starts");
		
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		
		try {
			res = restClient.getForObject(env.getMasterUrl() + "getVariantDetails?id=" + tCountry.getKey() + "&skuid=" + tCountry.getName(),
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
	
	@RequestMapping(value = "manage-product-report-excel", headers = "content-type=application/*", method={RequestMethod.POST})
	public ModelAndView downloadExcelReport(HttpServletResponse servResponse, HttpSession session,
			@RequestBody List<ProductMasterModel> listData) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("product", listData);
			servResponse.setContentType("application/ms-excel");
			servResponse.setHeader("Content-disposition", "attachment; filename=ProductExcelReport.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView(new ExcelProductReport(), map);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-product-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModal(HttpSession session) {
		logger.info("Method : getProductCategoryDataListModal starts");
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restClient.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-product-delete")
	public @ResponseBody JsonResponse<Object> deletesku(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deletesku starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deletesku?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
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
	@GetMapping("manage-product-purchase-delete")
	public @ResponseBody JsonResponse<Object> deletepurchase(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : deletesku starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		// logger.info(env.getEmployeeUrl() + "deletework?id="+ id);
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deletepurchase?id=" + id, JsonResponse.class);
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
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-product-delete")
	public @ResponseBody JsonResponse<Object> deleteProductMaster(Model model, @RequestParam String id,
			@RequestParam String simpleid, HttpSession session) {
		logger.info("Method : deleteProductMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String createdBy = "";
		String orgName = "";
		String orgDivision = "";

		try {
			createdBy = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getMasterUrl() + "deleteProductMaster?id=" + id + "&createdBy=" + createdBy + "&simpleid=" + simpleid +"&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : deleteProductMaster ends");
		return resp;
	}

	@PostMapping("/manage-product-upload-file")
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
	
	@PostMapping("manage-product-delete-file")
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
