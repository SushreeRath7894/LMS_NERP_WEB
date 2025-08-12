package nirmalya.aathithya.webmodule.productionplan.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.productionplan.model.RmPmRequisitionModel;

@Controller
@RequestMapping(value = "production/")
public class RmPmRequisitionController {
	
	Logger logger = LoggerFactory.getLogger(RmPmRequisitionController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("rmpm-requisition")
	public String productionRmPmRequsition(Model model, HttpSession session) {

		logger.info("Method : productionRmPmRequsition start");

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
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getProduction()+
					"productionPlanningList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("productionPlanningList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : productionRmPmRequsition ends");
		return "production_plan/rmpm-requisition";

	}
	
	

	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "rmpm-requisition-item-get-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue, HttpSession session,BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		String org = "";
		String orgDiv = "";
		String type = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getProduction() + "rest-getRmPmListForRequisition?id=" + searchValue
					+ "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}
	
	// view
	
		@SuppressWarnings("rawtypes")
		@GetMapping("rmpm-requisition-view")
		public @ResponseBody Object viewRmPmRequisition(Model model, HttpSession session) {

				logger.info("Method :viewRmPmRequisition starts");
				
				JsonResponse resp = new JsonResponse();
				String org = "";
				String orgDiv = "";
				//String pageno = "1";

				try {
					org = (String) session.getAttribute("ORGANIZATION");
					orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					resp = restClient.getForObject(
						env.getProduction() + "rest-viewRmPmRequisition?org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method :viewRmPmRequisition ends  ");
			return resp;
		}
		
		// add.
		
		@SuppressWarnings({ "unchecked" })

		@PostMapping(value = { "rmpm-requisition-add" })
		public @ResponseBody JsonResponse<Object> addRmPmRequisition(@RequestBody RmPmRequisitionModel av, HttpSession session) {
			logger.info("Method : addRmPmRequisition function starts");
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
			av.setCreatedBy(userId);
			av.setOrganization(organization);
			av.setOrgDivision(orgDivision);
			
			System.out.println("qc= ==" + av);
			try {
				resp = restTemplate.postForObject(env.getProduction() + "rest-addRmPmRequisition", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : addRmPmRequisition function Ends");
			System.out.println("Final Save>>>------" + resp);
			return resp;
		}
		
		
		// Edit.
		
		@SuppressWarnings("unchecked")
		@GetMapping("rmpm-requisition-edit")
		public @ResponseBody Object editRmPmRequisition(@RequestParam String id, HttpSession session) {
			logger.info("Method :editRmPmRequisition starts");
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

				resp = restTemplate.getForObject(env.getProduction()  + "rest-editRmPmRequisition?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :editRmPmRequisition ends");
			return resp;
		}
		
		
		// Delete.
		@SuppressWarnings("unchecked")
		@GetMapping("rmpm-requisition-delete")
		public @ResponseBody Object deleterRmPmRequisition(@RequestParam String id, HttpSession session) {
			logger.info("Method :deleterRmPmRequisition starts");
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

				resp = restTemplate.getForObject(env.getProduction()  + "rest-deleteRmPmRequisition?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :deleterRmPmRequisition ends");
			return resp;
		}
		
		// Approve.
		@SuppressWarnings("unchecked")
		@GetMapping("rmpm-requisition-approve")
		public @ResponseBody Object approveRmPmRequsition(@RequestParam String id, HttpSession session) {
			logger.info("Method :approveRmPmRequsition starts");
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
			try {

				resp = restTemplate.getForObject(env.getProduction()  + "rest-approveRmPmRequsition?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :approveRmPmRequsition ends");
			return resp;
		}
		// view
		
		@SuppressWarnings("rawtypes")
		@GetMapping("rmpm-requisition-getRawMaterialList")
		public @ResponseBody Object getRawMaterialList(@RequestParam String planId, HttpSession session) {

			logger.info("Method :getRawMaterialList starts=="+planId);
			
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";
			//String pageno = "1";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restClient.getForObject(
					env.getProduction() + "rest-getRawMaterialList?planId=" + planId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getRawMaterialList ends  ");
			return resp;
	}
			

}
