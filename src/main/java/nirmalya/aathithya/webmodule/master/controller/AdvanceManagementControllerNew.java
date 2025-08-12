package nirmalya.aathithya.webmodule.master.controller;


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
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModelNew;


@Controller

@RequestMapping(value = { "master/" })
public class AdvanceManagementControllerNew {
	Logger logger = LoggerFactory.getLogger(AdvanceManagementControllerNew.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "advance-policy" })

	public String employeeAdvancee(Model model, HttpSession session) {
		logger.info("Method :Advancee starts");
 
		try {

			DropDownModel[] requisition = restTemplate.getForObject(env.getMasterUrl() + "get-requisition-list",
					DropDownModel[].class);
			List<DropDownModel> requisitionList = Arrays.asList(requisition);

			model.addAttribute("requisitionList", requisitionList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		logger.info("Method : Advancee ends");
		return "master/advancePolicy";
	}
	@SuppressWarnings("unchecked")

	@PostMapping("/advance-policy-add")
	public @ResponseBody JsonResponse<Object> addAdvManagement(@RequestBody AdvanceManagementModelNew vendorMasterModel,
			HttpSession session) {
		logger.info("Method : addAdvpolicy starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String dateFormat = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendorMasterModel.setOrganization(organization);
		vendorMasterModel.setOrgDivision(orgDivision);

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			vendorMasterModel.setDate(DateFormatter.inputDateFormat(vendorMasterModel.getDate(), dateFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}

		vendorMasterModel.setCreatedBy(userId);

		/*if (vendorMasterModel.getDate() != null && vendorMasterModel.getDate() != "") {
			vendorMasterModel.setDate(DateFormatter.dateFormat(vendorMasterModel.getDate(), dateFormat));
		}*/
	//	logger.info("addAdvManagement" + vendorMasterModel); 
		try {

			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addAdvManagement", vendorMasterModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

	//	String message = resp.getMessage();

		  
		  if (resp.getMessage() != "" && resp.getMessage() != null) {
		  resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		  resp.setMessage("Success"); }
		  
		  logger.info("Method : addAdvpolicy ends");
		//  logger.info("addAdvManagement" + resp); 
		  return resp;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping("advance-policy-view")
	public @ResponseBody List<AdvanceManagementModelNew> viewAdvManagement(HttpSession session) {

		logger.info("Method : viewAdvpolicy ");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<AdvanceManagementModelNew>> resp = new JsonResponse<List<AdvanceManagementModelNew>>();
		// logger.info("studentModellllllllllllllllllllllllll" + resp);
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-advManagement-view?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		// logger.info("studentmappppppppppppppppppper" + mapper);

		List<AdvanceManagementModelNew> reimbursementModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AdvanceManagementModelNew>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (AdvanceManagementModelNew a : reimbursementModel) {

			// logger.info("for rach lllllllllooooooopppppppppp" + studentModel);
			if (a.getDate() != null && a.getDate() != "") {
				a.setDate(DateFormatter.dateFormat(a.getDate(), dateFormat));
			}
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : Advpolicy  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);

		logger.info("Advpolicy " + reimbursementModel);
		return reimbursementModel;

	}
	
	
	
	
	// edit  
	  
			//edit responsible
				@SuppressWarnings("unchecked")
				@PostMapping("advance-policy-edit")
				public @ResponseBody JsonResponse<List<Object>> editPolicy(@RequestBody String reqId, HttpSession session) {
					logger.info("Method : editstarts");
					
					JsonResponse<List<Object>> resp = new JsonResponse<List<Object>>();
					
					try {
						resp = restTemplate.getForObject(env.getMasterUrl() + "rest-advManagement-edit?id="+reqId,
								JsonResponse.class);
						logger.info("IDDDDD"+reqId);
						
					} catch (RestClientException e) {
						e.printStackTrace();
					}
					
					String message = resp.getMessage();
					
					if (message != null && message != "") {
						
					} else {
						resp.setMessage("success");
					}
					logger.info("edit"+resp);
					logger.info("Method : edit starts");
					return resp;
				}
				
				

				//delete 
				@SuppressWarnings("unchecked")
				@PostMapping("advance-policy-delete")
				public @ResponseBody JsonResponse<Object> deletePolicy(@RequestParam String id,
						Model model, HttpSession session) {
					logger.info("Method : deletePolicy function starts");

					JsonResponse<Object> res = new JsonResponse<Object>();

					JsonResponse<Object> resp = new JsonResponse<Object>();

					try {
						res = restTemplate.getForObject(env.getMasterUrl() + "rest-advManagement-delete?id=" + id  , JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deletePolicy function Ends");
					
					logger.info("RESPPPPPPP"+res);
					return res;
				}
	
				
			
}
