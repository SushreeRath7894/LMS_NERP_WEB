package nirmalya.aathithya.webmodule.sales.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class CustomerNewController {

	Logger logger = LoggerFactory.getLogger(CustomerNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	CustomerNewController customerNewController;

	@SuppressWarnings("unchecked")
	@PostMapping("/view-customer-add")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
	        HttpSession session) {

	    logger.info("Method : addAccountCustomer starts");

	    String organization = "";
	    String orgDivision = "";

	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    customerNewModel.setOrganization(organization);
	    customerNewModel.setOrgDivision(orgDivision);

	    JsonResponse<Object> resp = new JsonResponse<>();
	    logger.info("web AccountModel ======================" + customerNewModel);

	    try {
	        String userId = "";
	        try {
	            userId = (String) session.getAttribute("USER_ID");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        logger.info("created by id-------------------------------" + userId);
	        customerNewModel.setCreatedBy(userId);

	        // Set headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<CustomerNewModel> request = new HttpEntity<>(customerNewModel, headers);

	        ResponseEntity<JsonResponse> response = restTemplate.exchange(
	                env.getSalesUrl() + "/addAccountCustomer",
	                HttpMethod.POST,
	                request,
	                JsonResponse.class
	        );

	        resp = response.getBody();
	        logger.info("Response status: " + response.getStatusCode());

	    } catch (HttpClientErrorException e) {
	        // Handle HTTP 409 or any 4xx error
	        if (e.getStatusCode() == HttpStatus.CONFLICT) {
	            try {
	                ObjectMapper mapper = new ObjectMapper();
	                resp = mapper.readValue(e.getResponseBodyAsString(), JsonResponse.class);
	            } catch (IOException ioEx) {
	                ioEx.printStackTrace();
	                resp.setMessage("Conflict occurred, but could not parse message.");
	            }
	        } else {
	            e.printStackTrace();
	            resp.setMessage("Client error occurred: " + e.getStatusCode());
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        resp.setMessage("Unexpected server error.");
	    }

	    if (resp.getMessage() == null || resp.getMessage().isEmpty()) {
	        resp.setMessage("Success");
	    }

	    logger.info("Method : addAccountCustomer ends : " + resp);
	    return resp;
	}


	/**
	 * View Customer Account
	 * 
	 */

	@GetMapping("/view-customer")
	public String viewManageCus(Model model, HttpSession session) {
		logger.info("Method : viewManageCus start");

		/*
		 * try { DropDownModel[] bank = restTemplate.getForObject(env.getAccountUrl() +
		 * "/getBankList", DropDownModel[].class);
		 * 
		 * List<DropDownModel> bankList = Arrays.asList(bank);
		 * model.addAttribute("bankList", bankList); } catch (RestClientException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Met    hod : viewManageCus end");
		return "sales/view-customer";
	}

	/**
	 * Delete Customer Account Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-delete-id")
	public @ResponseBody JsonResponse<Object> deleteCustomerDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteCustomerDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "deleteCusAccDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteCustomerDetails function Ends");

		logger.info("Response" + res);
		return res;
	}

	/**
	 * View Customer Account Ajax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-throughAjax")
	public @ResponseBody List<CustomerNewModel> viewCustomerAccount(HttpSession session) {

		logger.info("Method : viewCustomerAccount starts");

		JsonResponse<List<CustomerNewModel>> resp = new JsonResponse<List<CustomerNewModel>>();

		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "/restViewCustDtls", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CustomerNewModel> customerNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CustomerNewModel>>() {
				});

		for (CustomerNewModel i : customerNewModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}
		}
		resp.setBody(customerNewModel);

		logger.info("Method : viewCustomerAccount ends");
		return resp.getBody();
	}

	/**
	 * Edit Customer Account Record
	 */

	// view-customer-edit

	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-edit")
	public @ResponseBody JsonResponse<List<CustomerNewModel>> editCustomerInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editCustomerInfo starts" + id);

		JsonResponse<List<CustomerNewModel>> jsonResponse = new JsonResponse<List<CustomerNewModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "/editAccCusInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";

		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CustomerNewModel> customerNewModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CustomerNewModel>>() {
				});

		for (CustomerNewModel i : customerNewModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------" + date);
			}

		}

		logger.info("###" + customerNewModel);
		jsonResponse.setBody(customerNewModel);

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editCustomerInfo ends");
		return jsonResponse;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-customer-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}

	/* GST Integration */
	@GetMapping(value = { "view-customer-getgstno" })
	public @ResponseBody JsonResponse<Object> getGstData(Model model, @RequestParam("id") String id,
			HttpSession session) {

		logger.info("Method : getGstData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = " ";
			// url
			// ="https://commonapi.mastersindia.co/commonapis/searchgstin?gstin=21EINPS9908C1Z0";
			url = "https://commonapi.mastersindia.co/commonapis/searchgstin?gstin=" + id;

			HttpHeaders requestHeaders = new HttpHeaders();
			// requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
			requestHeaders.add("Authorization", "Bearer 0ab31ef7392227173c6e8d34195e86d5eb0da1e9");
			requestHeaders.add("client_id", "JarZChUcsytSBbnkpt");

			HttpEntity<String> httpEntity = new HttpEntity<String>(requestHeaders);
			ResponseEntity<String> st = null;
			String err_code = null;

			try {
				st = null;
				st = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			} catch (Exception e) {
				err_code = e.getMessage();
			}

			JSONObject obres = null;
			try {
				obres = new JSONObject(st.getBody());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.setBody(st.getBody());

		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : getGstData ends" + resp);
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-customer-save-shipping-address")
	public @ResponseBody JsonResponse<Object> saveAddressDetails(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : saveAddressDetails starts");

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

		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(orgName);
		customerNewModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "saveAddressDetails", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAddressDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-shippingdetails")
	public @ResponseBody Object viewShippingDetails(HttpSession session, @RequestParam String customerIdd) {
		logger.info("Method :viewShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingDetails?customerIdd=" + customerIdd
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShippingDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-customer-edit-address")
	public @ResponseBody Object editShippingDetails(HttpSession session, @RequestParam String addressId) {
		logger.info("Method :editShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingDetails?addressId=" + addressId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editShippingDetails ends");
		return resp;
	}

	@GetMapping("view-customer-address-delete")
	public @ResponseBody Object deleteaddressdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteaddressdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-deleteaddressdata?deleteId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-customer-delete-child-data")
	public @ResponseBody JsonResponse<Object> updateShippingAddress(@RequestBody List<Map<String, Object>> dataset,
			HttpSession session) {
		logger.info("Method : updateShippingAddress starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = (String) session.getAttribute("ORGANIZATION");
		if (organization == null)
			organization = "";

		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		if (orgDivision == null)
			orgDivision = "";

		String createdById = (String) session.getAttribute("USER_ID");
		if (createdById == null)
			createdById = "";

		try {
			logger.info("Received dataset: " + dataset);

			String url = env.getSalesUrl() + "rest-update-shipping-address";
			logger.info("URL for Meeting: " + url);

			resp = restTemplate.postForObject(url, dataset, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in updateShippingAddress: ", e);
			resp.setMessage("Error processing request");
			resp.setCode("Error");
		}

		logger.info("Method : updateShippingAddress ends");
		return resp;
	}

}
