package nirmalya.aathithya.webmodule.pipeline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmVendorsModel;

@Controller
@RequestMapping(value = "pipeline")
public class CrmVendorController {
	Logger logger = LoggerFactory.getLogger(CrmVendorController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-crm-vendors")
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
			DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
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
		
		logger.info("Method : viewCRMVendor end");
		return "pipeline/crm-vendors";
	}
	
	/*
	 * upload image
	 */
	@PostMapping("/view-crm-vendors-upload-file")
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

	
	
	//view-crm-vendors-add-vendor-details
	
	/*
	 * Post Mapping for adding new vendor
	 */
	@SuppressWarnings("unchecked")
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
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-vendors-throughAjax")
	public @ResponseBody List<CrmVendorsModel> viewCrmVendors(HttpSession session) {

		logger.info("Method : viewCrmVendors starts");

		JsonResponse<List<CrmVendorsModel>> resp = new JsonResponse<List<CrmVendorsModel>>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "/restViewVendorDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<CrmVendorsModel> vendorModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmVendorsModel>>() {
				});
		
		for (CrmVendorsModel i : vendorModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("Created date---------------"+date);
			}
		}
		
		resp.setBody(vendorModel);
		logger.info("resp.getBody()-----------"+resp.getBody());
		
		logger.info("Method : viewCrmVendors ends");
		return resp.getBody();
	}
	
	
	

	///view-crm-vendors-edit
			
			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-vendors-edit")
			public @ResponseBody JsonResponse<List<CrmVendorsModel>> editVendorInfo(Model model, @RequestParam String id,
					HttpSession session) {
		
				logger.info("Method : editVendorInfo starts" + id);
				
				JsonResponse<List<CrmVendorsModel>> jsonResponse = new JsonResponse<List<CrmVendorsModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getPipeline() + "editVendorInfo?id=" + id,
							JsonResponse.class);
		
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		
				ObjectMapper mapper = new ObjectMapper();
				String date = "";
				String drProfDoc = null;
				String dateFormat = (String) (session).getAttribute("DATEFORMAT");
				
				List<CrmVendorsModel> campaignModel = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<CrmVendorsModel>>() {
						});
				
				for (CrmVendorsModel i : campaignModel) {
					if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
						date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
						i.setCreatedDate(date);
						logger.info("start date---------------"+date);
					}
					
					if (i.getImageName() != null && i.getImageName() != "") {
						 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
						
						logger.info("Image" + fileEmployeeimg);
						i.setImageName(fileEmployeeimg); 
					}				
					
				}
				
				logger.info("###" + campaignModel);
				jsonResponse.setBody(campaignModel);
		
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		
				} else {
					jsonResponse.setMessage("Success");
				}
		
				logger.info("REsp" + jsonResponse);
				logger.info("Method : editVendorInfo ends");
				return jsonResponse;
			}
			
			
			//view-crm-vendors-delete-id
			

			@SuppressWarnings("unchecked")
			@GetMapping("view-crm-vendors-delete-id")
			public @ResponseBody JsonResponse<Object> deleteCrmVendorsDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deleteCrmVendorsDetails function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restClient.getForObject(env.getPipeline() + "delete-vendor-Details?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deleteCrmVendorsDetails function Ends");
				
				logger.info("Response"+res);
				return res;
			}
			
			
			@PostMapping("/view-crm-vendors-delete-file")
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
			}
			
			//view-crm-purchase-order-get-vendor-list
			

			@SuppressWarnings("unchecked")
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
			
			
}
