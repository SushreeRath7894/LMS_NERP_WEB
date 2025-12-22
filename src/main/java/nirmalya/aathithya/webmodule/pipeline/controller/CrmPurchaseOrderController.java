package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmPurchaseOrderModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmPurchaseOrderController {
	Logger logger = LoggerFactory.getLogger(CrmPurchaseOrderController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	

	@GetMapping(value = { "/view-crm-purchase-order" })
	public String crmQuoteDetails(Model model, HttpSession session) {
		logger.info("Method : crmQuoteDetails starts");
		
		try {
			DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : crmQuoteDetails ends");
		return "pipeline/crm-purchase-order";
	}

	
	//view-crm-purchase-order-add
	
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-purchase-order-add")
	public @ResponseBody JsonResponse<Object> addCRMPurchaseOrder(HttpSession session,
			@RequestBody List<CrmPurchaseOrderModel> crmPurchaseOrderModel) {
		logger.info("Method : addCRMPurchaseOrder starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			//logger.info("CReatedBYYYYYY"+userId);
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {

		}
		/*for (CrmQuoteModel m : crmQuoteModel) {
			m.setQutCreatedBy(userId);
			
			m.setQutValidDate(DateFormatter.inputDateFormat(m.getQutValidDate(), dateFormat));
		}*/

		try {
			resp = restClient.postForObject(env.getPipeline() + "addCRMPurchaseOrder", crmPurchaseOrderModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			  List<CrmPurchaseOrderModel> purchaseorder = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<CrmPurchaseOrderModel>>() {
			  });
			 
		/*	for (CrmQuoteModel m : crmQuoteModel) {
				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				}
			}*/
			  
			logger.info("purchaseOrder------------"+purchaseorder);  
			  
			resp.setBody(purchaseorder);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCRMPurchaseOrder ends");

		return resp;
	}
	
	
	
	
	/*
	 * view-crm-purchase-order-through-ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-purchase-order-through-ajax")
	public @ResponseBody List<CrmPurchaseOrderModel> viewCRMPurchaseOrder(HttpSession session) {

		logger.info("Method :viewCRMPurchaseOrder starts");

		JsonResponse<List<CrmPurchaseOrderModel>> resp = new JsonResponse<List<CrmPurchaseOrderModel>>();
			
		try {

			resp = restClient.getForObject(env.getPipeline() + "viewCRMPurchaseOrder", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmPurchaseOrderModel> crmPurchaseOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmPurchaseOrderModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (CrmPurchaseOrderModel a : crmPurchaseOrderModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}

			
			  if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
			  a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			  }
			  
			  if (a.getPoDate() != null && a.getPoDate() != "") {
				  a.setPoDate(DateFormatter.dateFormat(a.getPoDate(), dateFormat));
				  }
			  if (a.getsOrderDate() != null && a.getsOrderDate() != "") {
				  a.setsOrderDate(DateFormatter.dateFormat(a.getsOrderDate(), dateFormat));
				  }
			  
			  
			//logger.info(quotationNewModel); 

		}

		resp.setBody(crmPurchaseOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewCRMPurchaseOrder ends");
		//logger.info("RESPONSE" + resp);
		return resp.getBody();
	}
	
	
	
	/*
	 * Delete view-crm-purchase-order-delete
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-crm-purchase-order-delete")
	public @ResponseBody JsonResponse<Object> deleteCRMPurchaseOrder(
			@RequestBody CrmPurchaseOrderModel crmPurchaseOrderModel, HttpSession session) {
		logger.info("Method : deleteCRMPurchaseOrder starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			crmPurchaseOrderModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restClient.postForObject(env.getPipeline() + "deleteCRMPurchaseOrder",
					crmPurchaseOrderModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");

		} else {
			resp.setMessage("Success");
		}
		logger.info("delete@" + resp);
		logger.info("Method : deleteCRMPurchaseOrder Ends");
		return resp;
	}
	
	
	 /*
		* for editing view-crm-purchase-order-edit-new
		*
		*
		*/
		@GetMapping(value = { "view-crm-purchase-order-edit-new" })
		public @ResponseBody List<CrmPurchaseOrderModel> viewCRMProductOrderEdit(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : viewCRMProductOrderEdit starts");
			 logger.info(id);
			List<CrmPurchaseOrderModel> productList = new ArrayList<CrmPurchaseOrderModel>();

			if (id != null && id != "") {
				try {
					CrmPurchaseOrderModel[] crmPurchaseOrderModel = restClient.getForObject(
							env.getPipeline() + "viewCRMProductOrderEdit?id=" + id, CrmPurchaseOrderModel[].class);

					productList = Arrays.asList(crmPurchaseOrderModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (CrmPurchaseOrderModel m : crmPurchaseOrderModel) {

						count++;
						m.setSlNo(count);

						String dateFormat = (String) session.getAttribute("DATEFORMAT");

						if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
							m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));

						}
					}

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("Method : viewCRMProductOrderEdit ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}
		
		//view-crm-purchase-order-detail
		@GetMapping("/view-crm-purchase-order-detail")
		public String viewCrmPODetails(Model model,@RequestParam String id,HttpSession session){
			logger.info("Method : viewCrmPODetails start");
			logger.info("id url -----------------"+id);
			model.addAttribute("purchaseOrderIdval",id);
			logger.info("Method : viewCrmSODetails end");
			
			try {
				DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList",
						DropDownModel[].class);

				List<DropDownModel> ownerList = Arrays.asList(owner);
				logger.info("ownerList" + ownerList);
				model.addAttribute("ownerList", ownerList);
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				DropDownModel[] campaign = restClient.getForObject(env.getPipeline() + "/getDealCampaignList",
						DropDownModel[].class);

				List<DropDownModel> campaignList = Arrays.asList(campaign);
				model.addAttribute("campaignList", campaignList);
			} catch (RestClientException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//leadList
			
			try {
				DropDownModel[] lead = restClient.getForObject(env.getPipeline() + "/getLeadNameList",
						DropDownModel[].class);

				List<DropDownModel> leadList = Arrays.asList(lead);
				logger.info("leadList" + leadList);
				model.addAttribute("leadList", leadList);
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info("Method : viewCrmPODetails end");		
			return "pipeline/crm-purchase-order-detail";
		}
		
		//view-crm-purchase-order-view-detail
		

		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-purchase-order-view-detail")
		public @ResponseBody JsonResponse<List<CrmPurchaseOrderModel>> viewPOInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewPOInfo starts" + id);

			JsonResponse<List<CrmPurchaseOrderModel>> jsonResponse = new JsonResponse<List<CrmPurchaseOrderModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-POInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewPOInfo ends");
			return jsonResponse;
		}

		
		//view-crm-purchase-order-view-detail-mail
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-purchase-order-view-detail-mail")
		public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewPOMailInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewPOMailInfo starts" + id);

			JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-POMailInfo?id=" + id,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
		

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewPOMailInfo ends");
			return jsonResponse;
		}
		
		
		/// Added By Pankaj For Crm Purchase Order
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@GetMapping("view-crm-contact-purchase-through-ajax")
		public @ResponseBody JsonResponse viewCrmPoDetails(HttpSession session,@RequestParam String id) {

			logger.info("Method : viewCrmPoDetails starts");
			JsonResponse jsonResponse = new JsonResponse();
			
			try {
				
				jsonResponse = restClient.getForObject(env.getPipeline() + "viewCrmPurchaseDetails?id="+id, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method : viewCrmPoDetails ends");
			return jsonResponse;
		}
}
