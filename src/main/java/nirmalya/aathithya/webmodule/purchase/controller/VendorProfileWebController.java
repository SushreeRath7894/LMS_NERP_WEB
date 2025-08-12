package nirmalya.aathithya.webmodule.purchase.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceReturnModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class VendorProfileWebController {
	Logger logger = LoggerFactory.getLogger(VendorPurchaseController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;


	@GetMapping("/vendor-profile")
	public String viewManageCus(Model model, HttpSession session) {
		logger.info("Method : viewManageCus start");

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

		logger.info("Met    hod : viewManageCus end");
		return "purchase/view-vendor-profile-details";
	}

	//vendor-profile-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "vendor-profile-stateList" })
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
	@PostMapping("/vendor-profile-add")
	public @ResponseBody JsonResponse<Object> addVendor(@RequestBody VendorNewModel vendorNewModel,
			HttpSession session) {

		logger.info("Method : addVendor starts");

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

			vendorNewModel.setCreatedBy(userId);
			vendorNewModel.setOrgName(orgName);
			vendorNewModel.setOrgDivision(orgDivision);

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "/addVendor", vendorNewModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addVendor ends" + resp);

		return resp;
	}

	//vendor-profile-throughAjax

	/**
	 *vendor-profile-throughAjax
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-profile-throughAjax")
	public @ResponseBody List<VendorNewModel> restViewVendorProfileDtls(HttpSession session) {

		logger.info("Method : viewVendorPurchase starts");
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
	
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<List<VendorNewModel>> resp = new JsonResponse<List<VendorNewModel>>();
		try {
			resp = restTemplate.getForObject(
					env.getPurchaseUrl() + "/restViewVendorProfileDtls?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<VendorNewModel> vendorNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<VendorNewModel>>() {
				});

		

		resp.setBody(vendorNewModel);

		logger.info("Method : restViewVendorProfileDtls ends");
		return resp.getBody();
	}

	//
	//vendor-profile-edit

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-profile-edit")
	public @ResponseBody JsonResponse<List<VendorNewModel>> editVendorInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editVendorInfo starts" + id);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		JsonResponse<List<VendorNewModel>> jsonResponse = new JsonResponse<List<VendorNewModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "/editVendorPurchaseInfo?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";

		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<VendorNewModel> vendorNewModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<VendorNewModel>>() {
				});

		for (VendorNewModel i : vendorNewModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);

			}

		}

		jsonResponse.setBody(vendorNewModel);

		logger.info("Method : editVendorInfo ends");
		return jsonResponse;
	}

	//vendor-profile-delete-id

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-profile-delete-id")
	public @ResponseBody JsonResponse<Object> deleteVendorDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteVendorDetails function starts" + id);
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
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteVendorDetails?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteVendorDetails function Ends");

		return res;
	}

	@GetMapping(value = { "vendor-profile-getgstno" })
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

//  Autosearch
	@GetMapping("vendor-profile-bank-list")
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


}
