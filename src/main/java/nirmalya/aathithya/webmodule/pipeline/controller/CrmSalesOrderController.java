package nirmalya.aathithya.webmodule.pipeline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.common.pagination.DataTableRequest;
import nirmalya.aathithya.webmodule.common.pagination.DataTableResponse;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmProductModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmQuoteModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmSalesOrderModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmVendorsModel;
import nirmalya.aathithya.webmodule.pipeline.model.PipelineCampaignModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmSalesOrderController {
	Logger logger = LoggerFactory.getLogger(CrmSalesOrderController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	

	@GetMapping(value = { "/view-crm-sales-order" })
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
		return "pipeline/crm-sales-order";
	}

	
	//view-crm-sales-order-add
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-sales-order-add")
	public @ResponseBody JsonResponse<Object> addCRMSalesOrder(HttpSession session,
			@RequestBody List<CrmSalesOrderModel> crmSalesOrderModel) {
		logger.info("Method : addCRMSalesOrder starts");
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
			resp = restClient.postForObject(env.getPipeline() + "addCRMSalesOrder", crmSalesOrderModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			  List<CrmSalesOrderModel> salesorder = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<CrmSalesOrderModel>>() {
			  });
			 
		/*	for (CrmQuoteModel m : crmQuoteModel) {
				if (m.getQutValidDate() != null && m.getQutValidDate() != "") {
					m.setQutValidDate(DateFormatter.dateFormat(m.getQutValidDate(), dateFormat));
				}
			}*/
			  
			logger.info("salesOrder------------"+salesorder);  
			  
			resp.setBody(salesorder);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCRMSalesOrder ends");

		return resp;
	}
	
	/*
	 * view-crm-sales-order-through-ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-sales-order-through-ajax")
	public @ResponseBody List<CrmSalesOrderModel> viewCRMSalesOrder(HttpSession session) {

		logger.info("Method :viewCRMSalesOrder starts");

		JsonResponse<List<CrmSalesOrderModel>> resp = new JsonResponse<List<CrmSalesOrderModel>>();
			
		try {

			resp = restClient.getForObject(env.getPipeline() + "getAllCRMSalesOrder", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmSalesOrderModel> crmSalesOrderModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmSalesOrderModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (CrmSalesOrderModel a : crmSalesOrderModel) {
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

		resp.setBody(crmSalesOrderModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewCRMSalesOrder ends");
		//logger.info("RESPONSE" + resp);
		return resp.getBody();
	}
	
	//view-crm-sales-order-edit-new
	
	 /*
	* for editing using employee id
	*
	*
	*/
	@GetMapping(value = { "view-crm-sales-order-edit-new" })
	public @ResponseBody List<CrmSalesOrderModel> viewCRMSalesOrderEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewCRMSalesOrderEdit starts");
		 logger.info(id);
		List<CrmSalesOrderModel> productList = new ArrayList<CrmSalesOrderModel>();

		if (id != null && id != "") {
			try {
				CrmSalesOrderModel[] crmSalesOrderModel = restClient.getForObject(
						env.getPipeline() + "viewCRMSalesOrderEdit?id=" + id, CrmSalesOrderModel[].class);

				productList = Arrays.asList(crmSalesOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (CrmSalesOrderModel m : crmSalesOrderModel) {

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
		logger.info("Method : viewCRMSalesOrderEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}



    /*
	 * Delete view-crm-sales-order-delete
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-crm-sales-order-delete")
	public @ResponseBody JsonResponse<Object> deleteCRMSalesOrder(
			@RequestBody CrmSalesOrderModel crmSalesOrderModel, HttpSession session) {
		logger.info("Method : deleteCRMSalesOrder starts");
		logger.info("crmQuoteModel web--------------------"+crmSalesOrderModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			crmSalesOrderModel.setQutCreatedBy(userId);
		} catch (Exception e) {

		}
		try {

			resp = restClient.postForObject(env.getPipeline() + "deleteCRMSalesOrder",
					crmSalesOrderModel, JsonResponse.class);
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
		logger.info("Method : deleteCRMSalesOrder Ends");
		return resp;
	}

	
	//view-crm-sales-order-detail
	@GetMapping("/view-crm-sales-order-detail")
	public String viewCrmSODetails(Model model,@RequestParam String id,HttpSession session){
		logger.info("Method : viewCrmSODetails start");
		logger.info("id url -----------------"+id);
		model.addAttribute("SOidval",id);
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
		
		logger.info("Method : viewCrmSODetails end");		
		return "pipeline/crm-sales-order-detail";
	}
	
	
/////////////////////////
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-sales-order-view-detail")
	public @ResponseBody JsonResponse<List<CrmSalesOrderModel>> viewSOInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewSOInfo starts" + id);

		JsonResponse<List<CrmSalesOrderModel>> jsonResponse = new JsonResponse<List<CrmSalesOrderModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-SOInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewSOInfo ends");
		return jsonResponse;
	}
	
	//view-crm-sales-order-view-detail-mail
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-sales-order-view-detail-mail")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewSOMailInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewSOMailInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-SOMailInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewSOMailInfo ends");
		return jsonResponse;
	}
	
	/// Added By Pankaj For Crm Purchase Order
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-contact-sales-order-through-ajax")
	public @ResponseBody JsonResponse viewCrmSalesOrderDetails(HttpSession session,@RequestParam String id) {

		logger.info("Method : viewCrmSalesOrderDetails starts");
		JsonResponse jsonResponse = new JsonResponse();
		
		try {
			
			jsonResponse = restClient.getForObject(env.getPipeline() + "viewCrmSalesOrderDetails?id="+id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}


		logger.info("Method : viewCrmSalesOrderDetails ends");
		return jsonResponse;
	}
}
