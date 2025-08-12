package nirmalya.aathithya.webmodule.procurment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class RequisitionController {

	Logger logger = LoggerFactory.getLogger(RequisitionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("view-requisition")
	public String generateInventoryStockReport(Model model, HttpSession session) {

		logger.info("Method : generateInventoryStockReport starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-requisition-type",
					DropDownModel[].class);
			List<DropDownModel> requisitionTypeList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionTypeList", requisitionTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getInventoryUrl() + "get-requisition-priority", DropDownModel[].class);
			List<DropDownModel> requisitionPrioList = Arrays.asList(dropDownModel);
			model.addAttribute("requisitionPrioList", requisitionPrioList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			InventoryRequisitionModel[] inventoryStockModel = restTemplate.getForObject(
					env.getInventoryUrl() + "get-requisition-item-list", InventoryRequisitionModel[].class);
			List<InventoryRequisitionModel> productList = Arrays.asList(inventoryStockModel);
			model.addAttribute("productList", productList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-cost-center",
					DropDownModel[].class);
			List<DropDownModel> costCenterList = Arrays.asList(dropDownModel);
			model.addAttribute("costCenterList", costCenterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-location",
					DropDownModel[].class);
			List<DropDownModel> locationList = Arrays.asList(dropDownModel);
			model.addAttribute("locationList", locationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/**
		 * get DropDown value for Requisition Type
		 *
		 */

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getInventoryUrl() + "get-uom",
					DropDownModel[].class);
			List<DropDownModel> uomList = Arrays.asList(dropDownModel);
			model.addAttribute("uomList", uomList);
		} catch (RestClientException e) {
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
			DropDownModel[] brand = restTemplate.getForObject(env.getMasterUrl()+"getBrandListForProduct?org=" + organization + "&orgDiv=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			model.addAttribute("brandList", brandList);
		} catch(Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : generateInventoryStockReport ends");
		return "purchase/view-requisition";

	}

	/*
	 * for copy
	 * 
	 * 
	 */
	@GetMapping(value = { "view-requisition-item-trough-ajax" })
	public @ResponseBody List<InventoryRequisitionModel> viewRequsitionEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewRequsitionEdit starts");
		List<InventoryRequisitionModel> productList = new ArrayList<InventoryRequisitionModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id != null && id != "") {
			try {
				InventoryRequisitionModel[] inventoryStockModel = restTemplate.getForObject(env.getInventoryUrl()
						+ "get-requisition-edit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						InventoryRequisitionModel[].class);
				productList = Arrays.asList(inventoryStockModel);
				productList.forEach(s -> s.setId(s.getSku()));
				int count = 0;
				for (InventoryRequisitionModel m : inventoryStockModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");
					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
					}
					if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
						m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
					}
					if (m.getActiveDate() != null && m.getActiveDate() != "") {
						m.setActiveDate(DateFormatter.dateFormat(m.getActiveDate(), dateFormat));
					}
					if (m.getCompleteDate() != null && m.getCompleteDate() != "") {
						m.setCompleteDate(DateFormatter.dateFormat(m.getCompleteDate(), dateFormat));
					}
					if (m.getOnHoldDate() != null && m.getOnHoldDate() != "") {
						m.setOnHoldDate(DateFormatter.dateFormat(m.getOnHoldDate(), dateFormat));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewRequsitionEdit ends");
		return productList;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-requisition-activity-log" })
	public @ResponseBody List<ActivitylogModel> getActivityLog(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewStockThroughAjax starts");
		List<ActivitylogModel> activityLogList = new ArrayList<ActivitylogModel>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {

			ActivitylogModel[] activityLog = restTemplate.getForObject(
					env.getInventoryUrl() + "get-activity-log?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					ActivitylogModel[].class);
			activityLogList = Arrays.asList(activityLog);

			for (ActivitylogModel m : activityLog) {
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getOperationOn() != null && m.getOperationOn() != "") {
					m.setOperationOn(DateFormatter.dateFormat(m.getOperationOn(), dateFormat));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewStockThroughAjax ends");
		return activityLogList;
	}

	/*
	 * post Mapping for add ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-requisition-save-th-ajax")
	public @ResponseBody JsonResponse<Object> saveItemRequisition(
			@RequestBody List<InventoryRequisitionModel> inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : saveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (InventoryRequisitionModel m : inventoryItemRequisitionModel) {
			m.setCreatedBy(userId);
			m.setReceiveDate(DateFormatter.inputDateFormat(m.getReceiveDate(), dateFormat));
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDivision);
		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-add-requisition",
					inventoryItemRequisitionModel, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			List<InventoryRequisitionModel> inventoryStockModel = mapper.convertValue(res.getBody(),
					new TypeReference<List<InventoryRequisitionModel>>() {
					});
			for (InventoryRequisitionModel m : inventoryStockModel) {
				if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
					m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
				}
				if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
					m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
				}
				if (m.getActiveDate() != null && m.getActiveDate() != "") {
					m.setActiveDate(DateFormatter.dateFormat(m.getActiveDate(), dateFormat));
				}
				if (m.getCompleteDate() != null && m.getCompleteDate() != "") {
					m.setCompleteDate(DateFormatter.dateFormat(m.getCompleteDate(), dateFormat));
				}
				if (m.getOnHoldDate() != null && m.getOnHoldDate() != "") {
					m.setOnHoldDate(DateFormatter.dateFormat(m.getOnHoldDate(), dateFormat));
				}
			}
			res.setBody(inventoryStockModel);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : saveItemRequisition function Ends");
		return res;
	}

	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-requisition-get-product-list" })
	public @ResponseBody JsonResponse<InventoryRequisitionModel> getItemAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemAutoSearchList starts");

		JsonResponse<InventoryRequisitionModel> res = new JsonResponse<InventoryRequisitionModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductListByAutoSearch?id=" + searchValue,
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

		logger.info("Method : getItemAutoSearchList ends");
		return res;
	}

	/*
	 * view throughAjax
	 * 
	 * 
	 */
	@GetMapping(value = { "view-requisition-trough-ajax" })
	public @ResponseBody List<InventoryRequisitionModel> viewRequisitionThroughAjax(HttpSession session) {
		logger.info("Method : viewRequisitionThroughAjax starts");
		List<InventoryRequisitionModel> productList = new ArrayList<InventoryRequisitionModel>();
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
		try {

			InventoryRequisitionModel[] inventoryStockModel = restTemplate.getForObject(env.getInventoryUrl()
					+ "get-requisition-view-list?id=" + userId + "&org=" + orgName + "&orgDiv=" + orgDivision,
					InventoryRequisitionModel[].class);
			productList = Arrays.asList(inventoryStockModel);

			for (InventoryRequisitionModel m : inventoryStockModel) {
				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
					m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
				}
				if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
					m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
				}
				if (m.getActiveDate() != null && m.getActiveDate() != "") {
					m.setActiveDate(DateFormatter.dateFormat(m.getActiveDate(), dateFormat));
				}
				if (m.getCompleteDate() != null && m.getCompleteDate() != "") {
					m.setCompleteDate(DateFormatter.dateFormat(m.getCompleteDate(), dateFormat));
				}
				if (m.getOnHoldDate() != null && m.getOnHoldDate() != "") {
					m.setOnHoldDate(DateFormatter.dateFormat(m.getOnHoldDate(), dateFormat));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewRequisitionThroughAjax ends");
		return productList;
	}

	/*
	 * post Mapping for delete ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-requisition-delete-th-ajax")
	public @ResponseBody JsonResponse<Object> deleteItemRequisition(
			@RequestBody InventoryRequisitionModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : deleteItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			inventoryItemRequisitionModel.setOrganizationName(orgName);
			inventoryItemRequisitionModel.setOrganizationDivision(orgDivision);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-delete-requisition",
					inventoryItemRequisitionModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteItemRequisition function Ends");
		return res;
	}

	/*
	 * post Mapping for approve ItemRequisition
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-requisition-approve-th-ajax")
	public @ResponseBody JsonResponse<Object> approveItemRequisition(
			@RequestBody InventoryRequisitionModel inventoryItemRequisitionModel, HttpSession session) {
		logger.info("Method : approveItemRequisition function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			inventoryItemRequisitionModel.setCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			res = restTemplate.postForObject(env.getInventoryUrl() + "rest-approve-requisition",
					inventoryItemRequisitionModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveItemRequisition function Ends");
		return res;
	}

	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-requisition-get-product-by-req-sku" })
	public @ResponseBody JsonResponse<InventoryRequisitionModel> getProductByReqList(@RequestParam String id,
			@RequestParam String prodId) {
		logger.info("Method : getProductByReqList starts");

		JsonResponse<InventoryRequisitionModel> res = new JsonResponse<InventoryRequisitionModel>();

		try {
			res = restTemplate.getForObject(
					env.getInventoryUrl() + "getProductByReqList?id=" + id + "&prodId=" + prodId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getProductByReqList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-requisition-product-category-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getAllProductCategoryList(HttpSession session) {
		logger.info("Method : getAllProductCategoryList starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getAllProductCategoryList", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllProductCategoryList starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-requisition-product-category-get-category-list-by-id")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListById(@RequestBody String id,
			HttpSession session) {
		logger.info("Method : getProductCategoryListById starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getProductCategoryListById?id=" + id,
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

	/*
	 * drop down for supervisor by job title
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-requisition-get-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProduct(HttpSession session ,Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getProduct starts");
		
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductByCat?id=" + index+ "&orgName=" + orgName+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getProduct  ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-requisition-get-product-cat-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModal(
			@RequestBody String yearDtls, HttpSession session) {
		logger.info("Method : getProductCategoryDataListModal starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : getProductCategoryDataListModal starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-requisition-product-save")
	public @ResponseBody JsonResponse<Object> saveOneProductMaster(@RequestBody ProductMasterModel product,
			HttpSession session) {
		logger.info("Method : saveOneProductMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);

		try {
			resp = restTemplate.postForObject(env.getInventoryUrl() + "saveOneProductMaster", product,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		DropDownModel dd = mapper.convertValue(resp.getBody(), new TypeReference<DropDownModel>() {
		});
		product.setProductId(dd.getKey());
		product.setMode(dd.getName());

		resp.setBody(product);
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveOneProductMaster starts");
		return resp;
	}

	@GetMapping(value = { "view-requisition-get-RFQdata" })
	public @ResponseBody List<InventoryRequisitionModel> getRFQdata(@RequestParam String id, String sku, HttpSession session) {
		logger.info("Method : getRFQdata starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<InventoryRequisitionModel> inventoryRequisitionModel = new ArrayList<InventoryRequisitionModel>();
		if (id != null && id != "") {
			logger.info("IDD" + id);
			try {
				InventoryRequisitionModel[] inventoryRequisition = restTemplate.getForObject(env.getInventoryUrl()
						+ "getRFQdata?id=" + id + "&sku=" + sku + "&org=" + orgName + "&orgDiv=" + orgDivision,
						InventoryRequisitionModel[].class);

				inventoryRequisitionModel = Arrays.asList(inventoryRequisition);

				inventoryRequisitionModel.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (InventoryRequisitionModel m : inventoryRequisition) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
					}
				}
		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getRFQdata ends");
		logger.info("edit@@@@@@@@" + inventoryRequisitionModel);
		return inventoryRequisitionModel;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-requisition-add-department")
	public @ResponseBody JsonResponse<Object> adddepartmentDetails(@RequestBody ProductMasterModel productMasterModel,
			HttpSession session) {
		logger.info("Method : adddepartmentDetails starts");
		
		

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
		
		productMasterModel.setCreatedBy(userId);
		productMasterModel.setOrganizationName(orgName);
		productMasterModel.setOrganizationDivision(orgDivision);
		try {

			resp = restTemplate.postForObject(env.getInventoryUrl() + "adddepartmentDetails", productMasterModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :adddepartmentDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-requisition-get-deptList" })
	public @ResponseBody JsonResponse<Object> getDeptList(@RequestParam String id,HttpSession session) {
		logger.info("Method : getDeptList starts"+ id);
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
		logger.info("Method : orgName starts"+ orgName);
		logger.info("Method : orgDivision starts"+ orgDivision);
		try {		
		
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getDeptList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
			//res = restTemplate.getForObject(env.getPurchaseUrl() + "getBrandList?orgName=" + orgName + "&orgDivision=" + orgDivision,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getDeptList ends");
		return resp;
	}	
}
