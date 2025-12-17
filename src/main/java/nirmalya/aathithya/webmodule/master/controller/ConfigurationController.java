package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "" })
public class ConfigurationController {

	Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "/configuration" })
	public String returnConfigurationPage(Model model, HttpSession session) {
		logger.info("Method : returnConfigurationPage starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { DropDownModel[] departmentType =
		 * restTemplate.getForObject(env.getMasterUrl() +
		 * "getDepartmentTypeForShiftType?organization=" + organization +
		 * "&orgDivision=" + orgDivision, DropDownModel[].class);
		 * 
		 * List<DropDownModel> department = Arrays.asList(departmentType);
		 * model.addAttribute("department", department);
		 * 
		 * } catch (RestClientException e) { e.printStackTrace(); }
		 */
		
		try {
			DropDownModel[] category = restTemplate.getForObject(env.getMasterUrl() + "getcategorylist?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			
			List<DropDownModel> categoryList = Arrays.asList(category);
			model.addAttribute("categoryList", categoryList);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] pcategory = restTemplate.getForObject(env.getMasterUrl() + "getprojectcategorylist?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			
			List<DropDownModel> pcategoryList = Arrays.asList(pcategory);
			model.addAttribute("pcategoryList", pcategoryList);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		/*
		 * try { DropDownModel[] source = restTemplate.getForObject(env.getPipeline() +
		 * "/getCountry", DropDownModel[].class);
		 * 
		 * List<DropDownModel> sourceList = Arrays.asList(source);
		 * model.addAttribute("countryList", sourceList); } catch (RestClientException
		 * e) { e.printStackTrace(); }
		 */
		
		try {
			DropDownModel[] locationType = restTemplate.getForObject(env.getMasterUrl() + "getLocationTypeList", DropDownModel[].class);
			List<DropDownModel> locationTypeList = Arrays.asList(locationType);
			
			model.addAttribute("locationTypeList", locationTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : returnConfigurationPage ends");
		return "new-master/configuration";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/configuration-get-data")
	public @ResponseBody JsonResponse<Object> getConfigData(HttpSession session) {
		logger.info("Method : getConfigData starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getConfigData?org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getConfigData ends");
		return resp;
	}
	
	/************************ transaction document numbering *********************/
	
	@GetMapping(value = { "/transaction-document-numbering" })
	public String returnTransactionDocumentNumberingPage(Model model, HttpSession session) {
		logger.info("Method : returnTransactionDocumentNumberingPage starts");
		
		
		logger.info("Method : returnTransactionDocumentNumberingPage ends");
		return "new-master/transaction-document-numbering";
	}	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/transaction-document-numbering-get-data")
	public @ResponseBody JsonResponse<Object> getTransactionDocumentNumberingData(HttpSession session) {
		logger.info("Method : getTransactionDocumentNumberingData starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getTransactionDocumentNumberingData?org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getTransactionDocumentNumberingData ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("transaction-document-numbering-modify-data")
	public @ResponseBody JsonResponse<Object> modifyTransactionDocumentNumberingData(HttpSession session,
			@RequestBody Map<String, Object> prefixData) {
		logger.info("Method : modifyTransactionDocumentNumberingData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-modifyTransactionDocumentNumberingData?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, prefixData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : modifyTransactionDocumentNumberingData ends");
		return resp;
	}
}
