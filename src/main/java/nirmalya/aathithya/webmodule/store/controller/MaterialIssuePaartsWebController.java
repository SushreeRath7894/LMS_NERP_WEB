package nirmalya.aathithya.webmodule.store.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.store.model.MaterialIssueDetailsWebModel;
import nirmalya.aathithya.webmodule.store.model.StoreMaterialDetailsWebModel;

@Controller
@RequestMapping(value = { "store" })
public class MaterialIssuePaartsWebController {
	Logger logger = LoggerFactory.getLogger(MaterialIssuePaartsWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/material-issue" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : MaterialIssuePaartsWebController starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {

			DropDownModel[] EmployeeList = restTemplate.getForObject(env.getMasterUrl()+
					"employeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> employeeList = Arrays.asList(EmployeeList);
			model.addAttribute("EmployeeList", employeeList);
		} catch (RestClientException e) {
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

		logger.info("Method : MaterialIssuePaartsWebController ends");
		return "store/material-issue";
	}

	// auto search preced name
	@SuppressWarnings("unchecked")
	@GetMapping("material-issue-autosearch-refNo")
	public @ResponseBody JsonResponse<DropDownModel> getIndentNameAutoSearch(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getIndentNameAutoSearch starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getMasterUrl() + "rest-getIndentNameAutoSearch?id=" + searchValue,
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
		logger.info("Method : getIndentNameAutoSearch ends");
		System.out.println("AUTOSEARCHHH" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("material-issue-view-items")
	public @ResponseBody List<MaterialIssueDetailsWebModel> getItemDetails(@RequestParam String id
			,@RequestParam String projectId,HttpSession session) {

		logger.info("Method : getItemDetails starts");

		JsonResponse<List<MaterialIssueDetailsWebModel>> resp = new JsonResponse<List<MaterialIssueDetailsWebModel>>();
		List<MaterialIssueDetailsWebModel> productList = new ArrayList<MaterialIssueDetailsWebModel>();
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-view-items?id=" + id+"&projectId="+projectId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<MaterialIssueDetailsWebModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<MaterialIssueDetailsWebModel>>() {
				});

		Model.forEach(s -> s.setSlNo(s.getSlNo()));
		int count = 0;
		for (MaterialIssueDetailsWebModel m : Model) {
			count++;
			m.setSlNo(count);
		}

		resp.setBody(Model);

		logger.info("Method : getItemDetails ends");
		return resp.getBody();
	}

	// childEdit-edit

	@SuppressWarnings("unchecked")
	@GetMapping("material-issue-edit-child")
	public @ResponseBody JsonResponse<List<MaterialIssueDetailsWebModel>> childEdit(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : childEdit starts" + id);

		JsonResponse<List<MaterialIssueDetailsWebModel>> jsonResponse = new JsonResponse<List<MaterialIssueDetailsWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getMasterUrl() + "rest-edit-child?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<MaterialIssueDetailsWebModel> MaterialIssueDetailsWebModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<MaterialIssueDetailsWebModel>>() {
				});

		System.out.println("###" + MaterialIssueDetailsWebModel);
		jsonResponse.setBody(MaterialIssueDetailsWebModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : childEdit ends");
		return jsonResponse;
	}

	/*@SuppressWarnings("unchecked")
	@PostMapping("material-issue-add")
	public @ResponseBody JsonResponse<Object> addIssue(@RequestBody MaterialIssueDetailsWebModel budget,HttpSession session) {

		logger.info("Method : addIssue starts" + budget);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web addIssue ======================" + budget);

		try {

			resp = restClient.postForObject(env.getMasterUrl() + "rest-issue-add", budget, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(resp.getMessage());
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addIssue ends");

		return resp;
	}*/
	
	@SuppressWarnings("unchecked")
	@PostMapping("material-issue-add")
	public @ResponseBody JsonResponse<Object> addIssue (
			@RequestBody List<MaterialIssueDetailsWebModel> addIssue, Model model, HttpSession session) {
		System.out.println("Enter controller");

		logger.info("Method : addIssue starts" + addIssue);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (MaterialIssueDetailsWebModel m : addIssue) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("========" + addIssue);
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "rest-issue-add", addIssue,
					JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("RESP----------------" + resp);

		logger.info("Method : addIssue ends");

		return resp;
	}


	// view-issue
	@SuppressWarnings("unchecked")
	@GetMapping("material-issue-view")
	public @ResponseBody Object viewIssue(HttpSession session) {
		logger.info("Method : viewIssue starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-issue-view?org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("viewIssue>>>>>>-------"+resp);
		logger.info("Method :viewIssue ends");
		return resp;
	}
	// view-budget-edit

	
	@SuppressWarnings("unchecked")
	@GetMapping("material-issue-edit")
	public @ResponseBody Object issueEdit(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : issueEdit starts" + id);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "rest-issueEdit?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		


		System.out.println("REsp" + resp);

		logger.info("Method : editDealerOrder ends");
		return resp;
	}
	
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("material-issue-edit") public @ResponseBody
	 * JsonResponse<List<MaterialIssueDetailsWebModel>> issueEdit(Model model,
	 * 
	 * @RequestParam String id, HttpSession session) {
	 * 
	 * logger.info("Method : issueEdit starts" + id);
	 * 
	 * JsonResponse<List<MaterialIssueDetailsWebModel>> jsonResponse = new
	 * JsonResponse<List<MaterialIssueDetailsWebModel>>(); try { jsonResponse =
	 * restClient.getForObject(env.getMasterUrl() + "rest-issueEdit?id=" + id,
	 * JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); List<MaterialIssueDetailsWebModel>
	 * MaterialIssueDetailsWebModel = mapper.convertValue(jsonResponse.getBody(),
	 * new TypeReference<List<MaterialIssueDetailsWebModel>>() { });
	 * 
	 * System.out.println("###" + MaterialIssueDetailsWebModel);
	 * jsonResponse.setBody(MaterialIssueDetailsWebModel);
	 * 
	 * System.out.println("REsp" + jsonResponse);
	 * logger.info("Method : estimateBudgetEdit ends"); return jsonResponse; }
	 */
	/*
	 * vendor autoSearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "manage-issue-get-vendor-list" })
	public @ResponseBody JsonResponse<PurchaseOrderModel> getVendorAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getVendorAutoSearchList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<PurchaseOrderModel> res = new JsonResponse<PurchaseOrderModel>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getVendorAutoSearch?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getVendorAutoSearchList ends");
		return res;
	}
	
	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "material-issue-item-get-customer-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchNewListForMI(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getItemQuotationAutoSearchNewListForMI starts");
		logger.info("QuotationNewModel" + searchValue);
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restClient.getForObject(
					env.getMasterUrl() + "getItemQuotationAutoSearchNewListForMI?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("RESPONSE@@" + res);
		logger.info("Method : getItemQuotationAutoSearchNewListForMI ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("material-issue-item-get-product-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryList(@RequestBody String yearDtls,
			HttpSession session) {
		logger.info("Method : getProductCategoryListModal starts");

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

		logger.info("Method : getProductCategoryListModal starts");
		return resp;
	}
	

	// grt product by cat

	@SuppressWarnings("unchecked")

	@PostMapping(value = { "material-issue-item-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProductsByCatInvoice(Model model,
			@RequestBody String index,@RequestParam String projectId, BindingResult result,HttpSession session) {
		logger.info("Method : getProductsByCatInvoice starts");

		String indexValue = index.substring(0, index.length() - 1);

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProductsNByCat?id=" + indexValue + "&org=" + organization + "&orgDiv=" + orgDivision,
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
		logger.info("DATA" + res);
		logger.info("Method : getProductsByCatInvoice ends");
		return res;
	}
	
	
	// Delete
		@SuppressWarnings("unchecked")
		@GetMapping("material-issue-delete")
		public @ResponseBody Object deleteIssue(@RequestParam String id ,HttpSession session) {
			logger.info("Method : deleteIssue starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			

			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
            
			try {
				resp = restClient.getForObject(env.getMasterUrl() + "rest-deleteIssue?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id, JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :deleteIssue ends");
			return resp;
		}
		
		// Approve
		@SuppressWarnings("unchecked")
		@GetMapping("material-issue-approve")
		public @ResponseBody Object approveIssue(@RequestParam String id ,HttpSession session) {
			logger.info("Method : approveIssue starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
      
			try {
				resp = restClient.getForObject(env.getMasterUrl() + "rest-approveIssue?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id + "&userId=" + userId, JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :approveIssue ends");
			return resp;
		}

		// Add Material Return Quantity.
		
		@SuppressWarnings("unchecked")
		@GetMapping("material-issue-add-return")
		public @ResponseBody Object addReturn(@RequestParam String slipNo, String returnQuant, String returnRemark, String sku ,HttpSession session) {
			logger.info("Method : viewIssue starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			

			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}

			try {
				resp = restClient.getForObject(env.getMasterUrl() + "rest-add-return?org=" + orgName + "&orgDiv=" + orgDivision 
						+ "&slipNo=" + slipNo + "&returnQuant=" + returnQuant + "&returnRemark=" + returnRemark+ "&sku=" + sku, JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("viewIssue>>>>>>-------"+resp);
			logger.info("Method :viewIssue ends");
			return resp;
		}
		
		// Stock Quantity
		@SuppressWarnings("unchecked")
		@GetMapping("material-issue-stockQuantity")
		public @ResponseBody Object viewStockQuantity(@RequestParam String sku,HttpSession session) {
			logger.info("Method : viewStockQuantity starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			

			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}

			try {
				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStockQuantity?org=" + orgName + "&orgDiv=" + orgDivision + "&sku=" + sku, JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("viewStockQuantity>>>>>>-------"+resp);
			logger.info("Method :viewIssue ends");
			return resp;
		}
		
		
		// Excel Download

		
		@SuppressWarnings("unchecked")
		@GetMapping("material-issue-download-excel")
		public @ResponseBody Object downloadExcel(Model model,
				HttpSession session) {
			logger.info("Method : downloadExcel Starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			try {
				resp = restClient.getForObject(env.getMasterUrl() + "rest-downloadExcel?org=" + orgName + "&orgDiv=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			


			System.out.println("REsp" + resp);

			logger.info("Method : downloadExcel ends");
			return resp;
		}

}
