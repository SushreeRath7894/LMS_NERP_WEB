package nirmalya.aathithya.webmodule.purchase.controller;

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
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.productionplan.model.RmPmRequisitionModel;


@Controller
@RequestMapping(value = "purchase/")
public class InventoryRequisitionController {
	
	Logger logger = LoggerFactory.getLogger(InventoryRequisitionController.class);

	@Autowired
	//RestTemplate restClient;
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("inventory-requisition")
	public String inevntoryRequisition(Model model, HttpSession session) {
		logger.info("Method : inevntoryRequisition start");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String type="purchase";
		try {
			DropDownModel[] item = restTemplate.getForObject(env.getMasterUrl() + "getAllItemList?org=" + orgName
							+ "&orgDiv=" + orgDivision+ "&type=" + type, DropDownModel[].class);
			List<DropDownModel> itemlist = Arrays.asList(item);
			model.addAttribute("itemlist", itemlist);
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
		
		logger.info("Method : inevntoryRequisition ends");
		return "purchase/inventory-requisition";
	}
	 

//getItemdDtailsBySku
	@SuppressWarnings("unchecked")
	@GetMapping("inventory-requisition-getItemdDtailsBySku")
	public @ResponseBody Object getItemdDtailsBySku(@RequestParam String id, HttpSession session) {
		logger.info("Method :getItemdDtailsBySku starts");
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
			System.out.println(env.getMasterUrl()  + "getItemdDtailsBySku?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision);
			resp = restTemplate.getForObject(env.getMasterUrl()  + "getItemdDtailsBySku?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getItemdDtailsBySku ends");
		return resp;
	}
	// view
		@SuppressWarnings("rawtypes")
		@GetMapping("inventory-requisition-view")
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
					resp = restTemplate.getForObject(
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

		@PostMapping(value = { "inventory-requisition-add" })
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
			try {
				resp = restTemplate.postForObject(env.getProduction() + "rest-addRmPmRequisition", av, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : addRmPmRequisition function Ends");
			return resp;
		}
		
		
		// Edit.
		
		@SuppressWarnings("unchecked")
		@GetMapping("inventory-requisition-edit")
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
		@GetMapping("inventory-requisition-delete")
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
		@GetMapping("inventory-requisition-approve")
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
		@GetMapping("inventory-requisition-getRawMaterialList")
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
				resp = restTemplate.getForObject(
					env.getProduction() + "rest-getRawMaterialList?planId=" + planId + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getRawMaterialList ends  ");
			return resp;
	}
			
			
			

}
