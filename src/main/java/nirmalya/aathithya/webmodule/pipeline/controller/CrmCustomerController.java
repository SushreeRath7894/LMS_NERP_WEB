package nirmalya.aathithya.webmodule.pipeline.controller;

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
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmCustomerController {
	Logger logger = LoggerFactory.getLogger(CrmCustomerController.class);
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-crm-customers")
	public String viewCRMVendor(Model model, HttpSession session) {
		logger.info("Method : viewCRMVendor start");
		CrmDealModel CrmTaskModel = new CrmDealModel();
		CrmDealModel form = (CrmDealModel) session.getAttribute("sCrmTaskModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("CrmTaskModel", form);
			session.setAttribute("sCrmTaskModel", null);

		} else {
			model.addAttribute("CrmTaskModel", CrmTaskModel);
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("customerList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] owner = restTemplate.getForObject(env.getPipeline() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getDocumentList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("documentTypeList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getLeadList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("leadList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getindustrylist",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("industryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] rating = restTemplate.getForObject(env.getPipeline() + "/getRatingList",
					DropDownModel[].class);

			List<DropDownModel> ratingList = Arrays.asList(rating);
			model.addAttribute("ratingList", ratingList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : viewCRMVendor end");
		return "pipeline/crm-customers";
	}
	
	/*
	 * upload image
	 */
	/*@PostMapping("/view-crm-vendors-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadimage controller ' ends" + response);
		return response;
	}

	*/
	
	//view-crm-vendors-add-vendor-details
	
	/*
	 * Post Mapping for adding new vendor
	 */
/*	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-vendors-add-vendor-details")
	public @ResponseBody JsonResponse<Object> addVendorCRM(@RequestBody CrmVendorsModel crmVendorModel, Model model,
			HttpSession session) {

		logger.info("Method : addVendorCRM starts" + crmVendorModel);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				// logger.info(imageName);

				crmVendorModel.setImageName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restClient.postForObject(env.getPipeline() + "addVendorCRM", crmVendorModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addVendorCRM ends" + resp);

		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		Path imagePath = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				// crm.setImagePath(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends" + imagePath);
		return imageName;
	}
	
	*/
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-customers-throughAjax")
	public @ResponseBody List<CrmCustomerModel> viewCrmCustomers(HttpSession session) {

		logger.info("Method : viewCrmCustomers starts");

		JsonResponse<List<CrmCustomerModel>> resp = new JsonResponse<List<CrmCustomerModel>>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "/restViewCustDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<CrmCustomerModel> crmCustomerModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmCustomerModel>>() {
				});
		
		for (CrmCustomerModel i : crmCustomerModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("Created date---------------"+date);
			}
		}
		
		resp.setBody(crmCustomerModel);
		logger.info("resp.getBody()-----------"+resp.getBody());
		
		logger.info("Method : viewCrmCustomers ends");
		return resp.getBody();
	}
	
	
	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-customers-add")
	public @ResponseBody JsonResponse<Object> addCustomer(@RequestBody CrmCustomerModel crmCustomerModel,
			HttpSession session) {

		logger.info("Method : addContact starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web crmCustomerModel ======================" + crmCustomerModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmCustomerModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "/addCustomer", crmCustomerModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}*/

		logger.info("Method : addCustomer ends");

		return resp;
	}
	
	

	///view-crm-customers-edit
			
			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-customers-edit")
			public @ResponseBody JsonResponse<List<CrmCustomerModel>> editCustomerInfo(Model model, @RequestParam String id,
					HttpSession session) {
		
				logger.info("Method : editCustomerInfo starts" + id);
				
				JsonResponse<List<CrmCustomerModel>> jsonResponse = new JsonResponse<List<CrmCustomerModel>>();
				try {
					jsonResponse = restTemplate.getForObject(env.getPipeline() + "editCustomerInfo?id=" + id,
							JsonResponse.class);
		
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
				ObjectMapper mapper = new ObjectMapper();
				String date = "";
				
			/*	String dob1 = "";
				String dob2 = "";
				String dob3 = "";
				
				String marriageDate1 = "";
				String marriageDate2 = "";
				String marriageDate3 = "";*/
				
				String drProfDoc = null;
				String dateFormat = (String) (session).getAttribute("DATEFORMAT");
				
				List<CrmCustomerModel> crmCustomerModel = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<CrmCustomerModel>>() {
						});
				
				for (CrmCustomerModel i : crmCustomerModel) {
					if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
						date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
						i.setCreatedDate(date);
						logger.info("start date---------------"+date);
					}
					
				/*	if (i.getDob1() != null && i.getDob1() != "") {
						dob1 = DateFormatter.dateFormat(i.getDob1(), dateFormat);
						i.setDob1(dob1);
					}
					
					if (i.getDob2() != null && i.getDob2() != "") {
						dob2 = DateFormatter.dateFormat(i.getDob2(), dateFormat);
						i.setDob2(dob2);
					}
					
					
					if (i.getDob3() != null && i.getDob3() != "") {
						dob3 = DateFormatter.dateFormat(i.getDob3(), dateFormat);
						i.setDob3(dob3);
					}
					
					
					if (i.getMarriageAnniversary1() != null && i.getMarriageAnniversary1() != "") {
						marriageDate1 = DateFormatter.dateFormat(i.getMarriageAnniversary1(), dateFormat);
						i.setMarriageAnniversary1(marriageDate1);
					}
					
					
					if (i.getMarriageAnniversary2() != null && i.getMarriageAnniversary2() != "") {
						marriageDate2 = DateFormatter.dateFormat(i.getMarriageAnniversary2(), dateFormat);
						i.setMarriageAnniversary2(marriageDate2);
					}
					
					
					if (i.getMarriageAnniversary3() != null && i.getMarriageAnniversary3() != "") {
						marriageDate3 = DateFormatter.dateFormat(i.getMarriageAnniversary3(), dateFormat);
						i.setMarriageAnniversary3(marriageDate3);
					}*/
					
			/*		if (i.getImageName() != null && i.getImageName() != "") {
						 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
						
						logger.info("Image" + fileEmployeeimg);
						i.setImageName(fileEmployeeimg); 
					}	*/			
					
				}
				
				logger.info("###" + crmCustomerModel);
				jsonResponse.setBody(crmCustomerModel);
		/*
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		
				} else {
					jsonResponse.setMessage("Success");
				}*/
		
				logger.info("REsp" + jsonResponse);
				logger.info("Method : editCustomerInfo ends");
				return jsonResponse;
			}
			
			
			//view-crm-customers-delete-id
			

			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-customers-delete-id")
			public @ResponseBody JsonResponse<Object> deleteCrmCustomersDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deleteCrmCustomersDetails function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restTemplate.getForObject(env.getPipeline() + "deleteCrmCustomersDetails?id="+id, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

			/*	String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}*/
				logger.info("Method : deleteCrmCustomersDetails function Ends");
				
				logger.info("Response"+res);
				return res;
			}
			
			
	/*		@PostMapping("/view-crm-vendors-delete-file")
			public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
				logger.info("Method : deleteFile employee uploadimage controller starts");

				JsonResponse<Object> response = new JsonResponse<Object>();

				try {
					session.removeAttribute("employeePFile");
				} catch (RestClientException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method : deleteFile employee uploadimage controller ends");
				return response;
			}*/
			
			//view-crm-purchase-order-get-vendor-list
			

		/*	@SuppressWarnings("unchecked")
			@PostMapping(value = { "view-crm-purchase-order-get-vendor-list" })
			public @ResponseBody JsonResponse<DropDownModel> getVendorAutoSearch(Model model,
					@RequestBody String searchVal, BindingResult result) {
				logger.info("Method : getVendorAutoSearch starts");
				JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

				try {
					res = restClient.getForObject(env.getPipeline() + "getVendorNameAutoSearch?id=" + searchVal,
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
				logger.info("Method : getVendorAutoSearch ends");
				logger.info("AUTOSEARCHHH" + res);
				return res;
			}
		*/	
			
}
