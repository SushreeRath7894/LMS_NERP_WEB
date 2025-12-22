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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmQuoteModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmQuoteController {
	Logger logger = LoggerFactory.getLogger(CrmQuoteController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	

	@GetMapping(value = { "/view-crm-quote" })
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
		return "pipeline/crm-quotation";
	}	
	
	
	///view-crm-quote-item-list
	
	/*
	 * Item autosearch
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-crm-quote-item-list" })
	public @ResponseBody JsonResponse<CrmQuoteModel> getItemQuoteAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemQuoteAutoSearchList starts");
			logger.info("QuotationNewModel"+searchValue);
		JsonResponse<CrmQuoteModel> res = new JsonResponse<CrmQuoteModel>();

		try {
			res = restClient.getForObject(env.getPipeline()+ "getItemQuoteAutoSearchList?id=" + searchValue,
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
		logger.info("RESPONSE@@" + res);
		logger.info("Method : getItemQuoteAutoSearchList ends");
		return res;
	}
	
	
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-quote-through-ajax")
	public @ResponseBody List<CrmQuoteModel> viewCRMQuotation(HttpSession session) {

		logger.info("Method :viewCRMQuotation starts");

		JsonResponse<List<CrmQuoteModel>> resp = new JsonResponse<List<CrmQuoteModel>>();
			
		try {

			resp = restClient.getForObject(env.getPipeline() + "getAllCRMQuotation", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmQuoteModel> crmQuoteModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmQuoteModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (CrmQuoteModel a : crmQuoteModel) {
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

		resp.setBody(crmQuoteModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewCRMQuotation ends");
		//logger.info("RESPONSE" + resp);
		return resp.getBody();
	}
	
	
	

/*
	 * Delete view-crm-quote-delete
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-crm-quote-delete")
	public @ResponseBody JsonResponse<Object> deleteCRMItemQuotation(
			@RequestBody CrmQuoteModel crmQuoteModel, HttpSession session) {
		logger.info("Method : deleteCRMItemQuotation starts");
		logger.info("crmQuoteModel"+crmQuoteModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			crmQuoteModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restClient.postForObject(env.getPipeline() + "deleteCRMItemQuotation",
					crmQuoteModel, JsonResponse.class);
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
		logger.info("Method : deleteCRMItemQuotation Ends");
		return resp;
	}

	
	//view-crm-quote-edit-new


	 /*
		* for editing using employee id
		*
		*
		*/
		@GetMapping(value = { "view-crm-quote-edit-new" })
		public @ResponseBody List<CrmQuoteModel> viewCRMQuotationEdit(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : viewCRMQuotationEdit starts");
			 logger.info(id);
			List<CrmQuoteModel> productList = new ArrayList<CrmQuoteModel>();

			if (id != null && id != "") {
				try {
					CrmQuoteModel[] crmQuoteModel = restClient.getForObject(
							env.getPipeline() + "viewCRMQuotationEdit?id=" + id, CrmQuoteModel[].class);

					productList = Arrays.asList(crmQuoteModel);

					productList.forEach(s -> s.setSlNo(s.getSlNo()));

					int count = 0;

					for (CrmQuoteModel m : crmQuoteModel) {

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
			logger.info("Method : viewCRMQuotationEdit ends");
			logger.info("edit@@@@@@@@" + productList);
			return productList;
		}

		




/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-quote-add")
	public @ResponseBody JsonResponse<Object> addCRMQuotation(HttpSession session,
			@RequestBody List<CrmQuoteModel> crmQuoteModel) {
		logger.info("Method : addCRMQuotation starts");
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
			resp = restClient.postForObject(env.getPipeline() + "addCRMQuotation", crmQuoteModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			  List<CrmQuoteModel> quotation = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<CrmQuoteModel>>() {
			  });
			 
		/*	for (CrmQuoteModel m : crmQuoteModel) {
				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				}
			}*/
			  
			logger.info("quotation------------"+quotation);  
			  
			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCRMQuotation ends");

		return resp;
	}

	
	
	
	@GetMapping("/view-crm-quote-detail")
	public String viewCrmQuoteDetails(Model model,@RequestParam String id,HttpSession session){
		logger.info("Method : viewCrmQuoteDetails start");
		logger.info("id url -----------------"+id);
		model.addAttribute("Quoteidval",id);
		logger.info("Method : viewCrmLeadsDetails end");
		
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
		
		logger.info("Method : viewCrmQuoteDetails end");		
		return "pipeline/crm-quote-detail";
	}
	

	//view-crm-quote-view-detail
	

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-quote-view-detail")
	public @ResponseBody JsonResponse<List<CrmQuoteModel>> viewQuoteInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewQuoteInfo starts" + id);

		JsonResponse<List<CrmQuoteModel>> jsonResponse = new JsonResponse<List<CrmQuoteModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-QuoteInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewQuoteInfo ends");
		return jsonResponse;
	}
	
	
	
	//view-crm-quote-detail-mail
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-quote-detail-mail")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewQuoteMailInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewQuoteMailInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-QuoteMailInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewQuoteMailInfo ends");
		return jsonResponse;
	}
	
	//view-crm-quote-get-deal-list
	

		/*
		 * deal Auto search
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-quote-get-deal-list" })
		public @ResponseBody JsonResponse<SalesOrderNewModel> getDealNameAutoList(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getDealNameAutoList starts");
			JsonResponse<SalesOrderNewModel> res = new JsonResponse<SalesOrderNewModel>();

			try {
				res = restClient.getForObject(env.getPipeline()+ "getDealNameAutoList?id=" + searchValue,
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
		
			logger.info("Method : getDealNameAutoList ends");
			return res;
		}
		
		//view-crm-quote-get-quote-list
		
		/*
		 * quote Auto search
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-quote-get-quote-list" })
		public @ResponseBody JsonResponse<SalesOrderNewModel> getQuoteNameAutoList(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getQuoteNameAutoList starts");
			JsonResponse<SalesOrderNewModel> res = new JsonResponse<SalesOrderNewModel>();

			try {
				res = restClient.getForObject(env.getPipeline()+ "getQuoteNameAutoList?id=" + searchValue,
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
		
			logger.info("Method : getQuoteNameAutoList ends");
			return res;
		}
		
		//////  For View Quotations in Contact
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@GetMapping("view-crm-contact-quotes-through-ajax")
		public @ResponseBody JsonResponse<ArrayList> viewQuotesDetails(HttpSession session,@RequestParam String id) {

			logger.info("Method : viewQuotes starts");
			JsonResponse jsonResponse = new JsonResponse();
			
			try {
				
				jsonResponse = restClient.getForObject(env.getPipeline() + "viewQuotes?id="+id, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}


			logger.info("Method : viewQuotes ends");
			return jsonResponse;
		}
		
	
}
