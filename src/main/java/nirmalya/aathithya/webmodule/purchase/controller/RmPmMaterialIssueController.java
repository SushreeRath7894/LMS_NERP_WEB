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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.productionplan.controller.RmPmRequisitionController;
import nirmalya.aathithya.webmodule.purchase.model.RmPmMaterialIssueModel;


@Controller
@RequestMapping(value = "purchase/")
public class RmPmMaterialIssueController {
	
	Logger logger = LoggerFactory.getLogger(RmPmRequisitionController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("rmpm-materialIssue")
	public String RmPmMaterialIssue(Model model, HttpSession session) {

		logger.info("Method : RmPmMaterialIssue start");

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
		logger.info("Method : RmPmMaterialIssue ends");
		return "purchase/rmpm-materialIssue";

	}
	
	// add.
	
			@SuppressWarnings({ "unchecked" })

			@PostMapping(value = { "rmpm-materialIssue-add" })
			public @ResponseBody JsonResponse<Object> addRmPmMaterialIssue(@RequestBody RmPmMaterialIssueModel av, HttpSession session) {
				logger.info("Method : addRmPmMaterialIssue function starts");
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
					resp = restTemplate.postForObject(env.getPurchaseUrl() + "rest-addRmPmMaterialIssue", av, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				logger.info("Method : addRmPmMaterialIssue function Ends");
				System.out.println("Final Save>>>------" + resp);
				return resp;
			}
			
			
			// view
			
			@SuppressWarnings("rawtypes")
			@GetMapping("rmpm-materialIssue-view")
			public @ResponseBody Object viewRmPmMaterialIssue(Model model, HttpSession session) {

					logger.info("Method :viewRmPmMaterialIssue starts");
					
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
							env.getPurchaseUrl() + "rest-viewRmPmMaterialIssue?org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :viewRmPmMaterialIssue ends  ");
				return resp;
			}
			
			
			// Edit.
			
			@SuppressWarnings("unchecked")
			@GetMapping("rmpm-materialIssue-edit")
			public @ResponseBody Object editRmPmMaterialIssue(@RequestParam String id, HttpSession session) {
				logger.info("Method :editRmPmMaterialIssue starts");
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

					resp = restTemplate.getForObject(env.getPurchaseUrl()  + "rest-editRmPmMaterialIssue?id=" + id + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				logger.info("Method :editRmPmMaterialIssue ends");
				return resp;
			}
			
			// Delete.
			@SuppressWarnings("unchecked")
			@GetMapping("rmpm-materialIssue-delete")
			public @ResponseBody Object deleterRmPmmMaterialIssue(@RequestParam String id, HttpSession session) {
				logger.info("Method :deleterRmPmmMaterialIssue starts");
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

					resp = restTemplate.getForObject(env.getPurchaseUrl()  + "rest-deleterRmPmmMaterialIssue?id=" + id + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				logger.info("Method :deleterRmPmmMaterialIssue ends");
				return resp;
			}
			
			// Approve.
			@SuppressWarnings("unchecked")
			@GetMapping("rmpm-materialIssue-approve")
			public @ResponseBody Object approveRmPmMaterialIssue(@RequestParam String id, HttpSession session) {
				logger.info("Method :approveRmPmMaterialIssue starts");
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

					resp = restTemplate.getForObject(env.getPurchaseUrl()  + "rest-approveRmPmMaterialIssue?id=" + id + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				logger.info("Method :approveRmPmMaterialIssue ends");
				return resp;
			}
			
			@SuppressWarnings("unchecked")
			@PostMapping(value = { "rmpm-materialIssue-getRFQList" })
			public @ResponseBody JsonResponse<InventorySkuProductModel> getRFQList(Model model,
					@RequestBody String searchValue, HttpSession session,BindingResult result) {
				logger.info("Method : getRFQList starts");
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
							env.getPurchaseUrl() + "rest-getRFQList?id=" + searchValue
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
				logger.info("Method : getRFQList ends");
				return res;
			}

			@SuppressWarnings("unchecked")
			@GetMapping("rmpm-materialIssue-getItemDetails")
			public @ResponseBody Object getItemDetails(@RequestParam String id, HttpSession session) {
				logger.info("Method :getItemDetails starts");
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

					resp = restClient.getForObject(env.getPurchaseUrl()  + "rest-getItemDetails?id=" + id + "&orgName="
							+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				logger.info("Method :getItemDetails ends");
				return resp;
			}
}
