package nirmalya.aathithya.webmodule.master.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.IncomeTaxMasterModel;

@Controller
@RequestMapping(value = "master")

public class IncomeTaxMasterController {
	Logger logger = LoggerFactory.getLogger(IncomeTaxMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-incometax")
	public String displayPayRollPage(Model model, HttpSession session) {
		logger.info("Method : Payroll starts");

		try {
			DropDownModel[] financialYrType = restTemplate
					.getForObject(env.getMasterUrl() + "getFinancialYrForIncometax", DropDownModel[].class);

			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
			model.addAttribute("financialYr", financialYr);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] catagoryId = restTemplate.getForObject(env.getMasterUrl() + "getcatagoryName",
					DropDownModel[].class);

			List<DropDownModel> catagoryList = Arrays.asList(catagoryId);
			model.addAttribute("catagoryList", catagoryList);
			logger.info("NAMEEEE" + catagoryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : Payroll ends");
		return "master/view-incometax";
	}
	/*
	 *
	 * add
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-incometax-slab-master-add" })
	public @ResponseBody JsonResponse<Object> addslabMaster(Model model, HttpSession session,
			@RequestBody IncomeTaxMasterModel data) {
		logger.info("Method : addSlab starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-slabmaster", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : addSlab ends");
		logger.info("adddd=======" + res);
		return res;

	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("view-incometax-slab-master-view")
	public @ResponseBody List<IncomeTaxMasterModel> viewSlabMaster(HttpSession session) {
		logger.info("Method : viewSlabMaster starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<IncomeTaxMasterModel>> resp = new JsonResponse<List<IncomeTaxMasterModel>>();
		List<IncomeTaxMasterModel> returnList = new ArrayList<IncomeTaxMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewSlabMaster?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("VIEWWWWWWWWWWWWWW" + returnList);
		logger.info("Method : viewSlabMaster ends");
		return returnList;
	}

////addd for income

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-incometax-master-add" })
	public @ResponseBody JsonResponse<Object> addIncomeMaster(Model model, HttpSession session,
			@RequestBody IncomeTaxMasterModel data) {
		logger.info("Method : addIncomeMaster starts");
		String userId = "";
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-incomemaster", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : addIncomeMaster ends");
		logger.info("adddd=======" + res);
		return res;

	}

//view

	@SuppressWarnings("unchecked")

	@GetMapping("view-incometax-master-view")
	public @ResponseBody List<IncomeTaxMasterModel> viewIncomeMaster(HttpSession session) {
		logger.info("Method : viewIncomeMaster starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewIncomeMaster starts"+organization+orgDivision);
		JsonResponse<List<IncomeTaxMasterModel>> resp = new JsonResponse<List<IncomeTaxMasterModel>>();
		List<IncomeTaxMasterModel> returnList = new ArrayList<IncomeTaxMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewIncomeMaster?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("VIEWWWWWWW INCOME" + returnList);
		logger.info("Method : viewIncomeMaster ends");
		return returnList;
	}

//// add	 

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-incometax-professional-master-add" })
	public @ResponseBody JsonResponse<Object> addProfessionalMaster(Model model, HttpSession session,
			@RequestBody IncomeTaxMasterModel data) {
		logger.info("Method : addProfessionalMaster starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-professional", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : addProfessionalMaster ends");
		logger.info("adddd=======" + res);
		return res;

	}

//view

	@SuppressWarnings("unchecked")

	@GetMapping("view-incometax-professional-master-view")
	public @ResponseBody List<IncomeTaxMasterModel> viewProfessionalMaster(HttpSession session) {
		logger.info("Method : viewProfessionalMaster starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<IncomeTaxMasterModel>> resp = new JsonResponse<List<IncomeTaxMasterModel>>();
		List<IncomeTaxMasterModel> returnList = new ArrayList<IncomeTaxMasterModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewProfessionalMaster?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("VIEWWWWWWW PROFESSIONAL" + returnList);
		logger.info("Method : viewProfessionalMaster ends");
		return returnList;
	}

////delete slab 
	@SuppressWarnings("unchecked")
	@GetMapping("view-incometax-slab-master-delete")
	public @ResponseBody JsonResponse<Object> deleteslabmaster(HttpSession session, @RequestParam String id) {

		logger.info("Method : slab-master starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method : slab-master ends");
		return resp;
	}

////delete slab 
	@SuppressWarnings("unchecked")
	@GetMapping("view-incometax-professional-master-delete")
	public @ResponseBody JsonResponse<Object> deleteProfessionalslabmaster(HttpSession session,
			@RequestParam String id) {

		logger.info("Method : deleteProfessionalslabmaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteProfessionalslabmasterDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method :deleteProfessionalslabmaster ends");
		return resp;
	}

////deleteIncometaxmaster
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-incometax-master-delete")
	public @ResponseBody JsonResponse<Object> deleteIncometaxmaster(HttpSession session, @RequestParam String id) {

		logger.info("Method : deleteIncometaxmaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-deleteIncometaxmasterDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		logger.info("resp" + resp);
		logger.info("Method :deleteIncometaxmaster ends");
		return resp;
	}

}
