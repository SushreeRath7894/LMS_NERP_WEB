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
import nirmalya.aathithya.webmodule.pipeline.model.CrmInvoiceModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmInvoiceController {
	Logger logger = LoggerFactory.getLogger(CrmInvoiceController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	

	@GetMapping(value = { "/view-crm-invoice" })
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
		return "pipeline/crm-invoice";
	}
	
	//view-crm-invoice-add
	
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-invoice-add")
	public @ResponseBody JsonResponse<Object> addCRMInvoice(HttpSession session,
			@RequestBody List<CrmInvoiceModel> crmInvoiceModel) {
		logger.info("Method : addCRMInvoice starts");
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
			resp = restClient.postForObject(env.getPipeline() + "addCRMInvoice", crmInvoiceModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			  List<CrmInvoiceModel> purchaseorder = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<CrmInvoiceModel>>() {
			  });
			 
		/*	for (CrmQuoteModel m : crmQuoteModel) {
				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				}
			}*/
			  
			logger.info("addCRMInvoice------------"+purchaseorder);  
			  
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

		logger.info("Method : addCRMInvoice ends");

		return resp;
	}
	
	
	

	/*
	 * view-crm-invoice-through-ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-invoice-through-ajax")
	public @ResponseBody List<CrmInvoiceModel> viewCRMInvoice(HttpSession session) {

		logger.info("Method :viewCRMInvoice starts");

		JsonResponse<List<CrmInvoiceModel>> resp = new JsonResponse<List<CrmInvoiceModel>>();
			
		try {

			resp = restClient.getForObject(env.getPipeline() + "viewCRMInvoice", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmInvoiceModel> crmInvoiceModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmInvoiceModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (CrmInvoiceModel a : crmInvoiceModel) {
			if (a.getQutValidDate() != null && a.getQutValidDate() != "") {
				a.setQutValidDate(DateFormatter.dateFormat(a.getQutValidDate(), dateFormat));
			}

			
			  if (a.getQutUpdatedOn() != null && a.getQutUpdatedOn() != "") {
			  a.setQutUpdatedOn(DateFormatter.dateFormat(a.getQutUpdatedOn(), dateFormat));
			  }
			  
			  if (a.getsOrderDate() != null && a.getsOrderDate() != "") {
				  a.setsOrderDate(DateFormatter.dateFormat(a.getsOrderDate(), dateFormat));
				  }
			  
			  
			//logger.info(quotationNewModel); 

		}

		resp.setBody(crmInvoiceModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewCRMInvoice ends");
		//logger.info("RESPONSE" + resp);
		return resp.getBody();
	}

	
	 /*
	* for editing view-crm-quote-invoice-new
	*
	*
	*/
	@GetMapping(value = { "view-crm-quote-invoice-new" })
	public @ResponseBody List<CrmInvoiceModel> viewCRMInvoiceEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewCRMInvoiceEdit starts");
		 logger.info(id);
		List<CrmInvoiceModel> productList = new ArrayList<CrmInvoiceModel>();

		if (id != null && id != "") {
			try {
				CrmInvoiceModel[] crmInvoiceModel = restClient.getForObject(
						env.getPipeline() + "viewCRMInvoiceEdit?id=" + id, CrmInvoiceModel[].class);

				productList = Arrays.asList(crmInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (CrmInvoiceModel m : crmInvoiceModel) {

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
		logger.info("Method : viewCRMInvoiceEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}
	
	

	/*
	 * Delete view-crm-invoice-delete
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-crm-invoice-delete")
	public @ResponseBody JsonResponse<Object> deleteCRMInvoice(
			@RequestBody CrmInvoiceModel crmInvoiceModel, HttpSession session) {
		logger.info("Method : deleteCRMInvoice starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			crmInvoiceModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restClient.postForObject(env.getPipeline() + "deleteCRMInvoice",
					crmInvoiceModel, JsonResponse.class);
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
		logger.info("Method : deleteCRMInvoice Ends");
		return resp;
	}
	
	//view-crm-invoice-detail
	//view-crm-purchase-order-detail
			@GetMapping("/view-crm-invoice-detail")
			public String viewCrmInvoiceDetails(Model model,@RequestParam String id,HttpSession session){
				logger.info("Method : viewCrmInvoiceDetails start");
				logger.info("id url -----------------"+id);
				model.addAttribute("InvoiceIdval",id);
				logger.info("Method : viewCrmInvoiceDetails end");
				
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
				
				logger.info("Method : viewCrmInvoiceDetails end");		
				return "pipeline/crm-invoice-detail";
			}
	
	
	//view-crm-invoice-view-detail
			

			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-invoice-view-detail")
			public @ResponseBody JsonResponse<List<CrmInvoiceModel>> viewInvoiceInfo(Model model, @RequestParam String id,
					HttpSession session) {

				logger.info("Method : viewInvoiceInfo starts" + id);

				JsonResponse<List<CrmInvoiceModel>> jsonResponse = new JsonResponse<List<CrmInvoiceModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-InvoiceInfo?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				logger.info("REsp" + jsonResponse);
				logger.info("Method : viewInvoiceInfo ends");
				return jsonResponse;
			}
			
			
			//view-crm-invoice-view-detail-mail
			
			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-invoice-view-detail-mail")
			public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewInvoiceMailInfo(Model model, @RequestParam String id,
					HttpSession session) {

				logger.info("Method : viewInvoiceMailInfo starts" + id);

				JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-InvoiceMailInfo?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}
			

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				logger.info("REsp" + jsonResponse);
				logger.info("Method : viewInvoiceMailInfo ends");
				return jsonResponse;
			}
			
			
			/// Crm Invoices view in contact
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@GetMapping("view-crm-contact-invoices-through-ajax")
			public @ResponseBody JsonResponse viewCrmInvoices(HttpSession session,@RequestParam String id) {

				logger.info("Method : viewCrmInvoices starts");
				JsonResponse jsonResponse = new JsonResponse();
				
				try {
					
					jsonResponse = restClient.getForObject(env.getPipeline() + "viewCrmInvoices?id="+id, JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}


				logger.info("Method : viewCrmInvoices ends");
				return jsonResponse;
			}
	
	
}
