package nirmalya.aathithya.webmodule.maintenance.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.maintenance.model.VendorRegistrationModal;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "maintenance/")
public class ServiceListingWebController {
	Logger logger = LoggerFactory.getLogger(ServiceListingWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;


	@GetMapping("/service-listing")
	public String viewManageCus(Model model, HttpSession session) {
		logger.info("Method : viewManageCus start");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorCategory",
					DropDownModel[].class);
			List<DropDownModel> CategoryList = Arrays.asList(Collection);

			model.addAttribute("CategoryList", CategoryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getSalutationLists",
					DropDownModel[].class);
			List<DropDownModel> SalutationLists = Arrays.asList(Collection);

			model.addAttribute("SalutationLists", SalutationLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Collection = restTemplate.getForObject(env.getPurchaseUrl() + "getPaymentTermsLists",
					DropDownModel[].class);
			List<DropDownModel> PaymentTermList = Arrays.asList(Collection);

			model.addAttribute("PaymentTermList", PaymentTermList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] serviceList = restTemplate.getForObject(
					env.getTicketUrl() + "get-vendor-service-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> serveList = Arrays.asList(serviceList);
			System.out.println("VALUE OF SERVICE :::: "+serveList);
			model.addAttribute("serviceList", serveList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewManageCus end");
		return "maintenance/service-listing";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("service-listing-view")
	public @ResponseBody Object viewVendorRegistration(HttpSession session) {
		logger.info("Method :viewServiceList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userRole = "";
		String userName = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMaintenance() + "rest-service-listing-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision+ "&userRole=" + userRole+ "&userName=" + userName+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewServiceList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("service-listing-edit")
	public @ResponseBody Object editVendorDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :editVendorDetails starts");
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

			resp = restTemplate.getForObject(env.getMaintenance() + "rest-vendor-registration-edit?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editVendorDetails ends");
		return resp;
	}
	
	
	@GetMapping(value = { "service-listing-getgstno" })
	public @ResponseBody JsonResponse<Object> getGstData(Model model, @RequestParam("id") String id,
			HttpSession session) {

		logger.info("Method : getGstData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("GSTT" + id);
		try {
			String url = " ";
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
	
	
	@GetMapping("service-listing-bank-list")
	public @ResponseBody Object getbankAutoSearchList(@RequestParam String searchValue, HttpSession session) {

		logger.info("Method :getbankAutoSearchList starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getPurchaseUrl() + "getbankAutoSearchList?id=" + searchValue + "&org="
					+ org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("service-listing-active")
	public @ResponseBody JsonResponse<Object> activeVendorDetails(@RequestParam String id,String operation, HttpSession session) {
		logger.info("Method : activeVendorDetails function starts" + id);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMaintenance() + "rest-vendor-registration-active?id=" + id + "&org=" + orgName+ "&operation=" + operation
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : activeVendorDetails function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "service-listing-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getstateCusList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/service-listing-add")
	public @ResponseBody JsonResponse<Object> addServiceDetails(@RequestBody VendorRegistrationModal vendorRegistrationModal,HttpSession session) {
		logger.info("Method : addServiceDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
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
			vendorRegistrationModal.setCreatedBy(userId);
			vendorRegistrationModal.setOrgName(orgName);
			vendorRegistrationModal.setOrgDivision(orgDivision);

			resp = restTemplate.postForObject(env.getMaintenance() + "/rest-service-listing-add", vendorRegistrationModal, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addServiceDetails ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("service-listing-service-show")
	public @ResponseBody Object editServiceDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :editServiceDetails starts");
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

			resp = restTemplate.getForObject(env.getMaintenance() + "rest-service-listing-service-show?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editServiceDetails ends");
		return resp;
	}
}
