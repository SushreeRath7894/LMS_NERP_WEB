package nirmalya.aathithya.webmodule.productionplan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.productionplan.model.PackingLogBookWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.PackingLotManufactureWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.Packing2AFSSMachineWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.PackingInitialCheckWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.PackingALCWebModel;
import nirmalya.aathithya.webmodule.productionplan.model.PackingFCWLWebModel;

@Controller
@RequestMapping(value = "production/")
public class PackingLogBookWebController {

	Logger logger = LoggerFactory.getLogger(PackingLogBookWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("packing-logbook")
	public String packingLogbook(Model model, HttpSession session) {
		logger.info("Method : packingLogbook starts");
		
		String userId = "";
		String userName = "";
		String userRole = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("orgName", org);
		model.addAttribute("orgDivision", orgDiv);
		 
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] line = restClient.getForObject(
					env.getMasterUrl() + "getLineLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> lineLists = Arrays.asList(line);

			model.addAttribute("lineLists", lineLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : packingLogbook ends");
		return "production_plan/packing-logbook";
	}
	// main
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add")
	public @ResponseBody JsonResponse<Object> addPackingLogbook(HttpSession session,
			@RequestBody PackingLogBookWebModel offDay) {
		logger.info("Method : addPackingLogbook starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "addPackingLogbook", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addPackingLogbook ends"+resp);
		return resp;
	}
	
	
	@GetMapping("packing-logbook-view")
	public @ResponseBody Object viewPackingLogbook(Model model, HttpSession session, @RequestParam String pageno) {

			logger.info("Method :viewPackingLogbook starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "viewPackingLogbook?org=" + org + "&orgDiv=" + orgDiv + "&pageno=" + pageno,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewPackingLogbook ends  ");
		return resp;
	}
	
	@GetMapping("packing-logbook-view-edit")
	public @ResponseBody Object viewEditPackingLogbook(@RequestParam String id, HttpSession session) {

			logger.info("Method :viewEditPackingLogbook starts");
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
			System.out.println("org-----"+org+"orgDiv--------"+orgDiv);
			try {
				resp = restClient.getForObject(
					env.getProduction() + "viewEditPackingLogbook?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewEditPackingLogbook ends  ");
		return resp;
	}

	@GetMapping("packing-logbook-edit")
	public @ResponseBody Object editPackingLogbook(@RequestParam String id, HttpSession session) {

			logger.info("Method :editPackingLogbook starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "viewEditPackingLogbook?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editPackingLogbook ends  ");
		return resp;
	}
	
	@GetMapping("packing-logbook-delete")
	public @ResponseBody Object deletePackingLogbook(@RequestParam String id, HttpSession session) {

		logger.info("Method :deletePackingLogbook starts");
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
			resp = restClient.getForObject(env.getProduction() + "deletePackingLogbook?id=" + id +  "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :deletePackingLogbook ends"+resp);
		return resp;
	}

// lot management
	@GetMapping("packing-logbook-view-lotmanagement")
	public @ResponseBody Object viewlotmanagement(@RequestParam String id, HttpSession session) {

			logger.info("Method :viewlotmanagement starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "viewlotmanagement?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewlotmanagement ends  ");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-lotManufacture")
	public @ResponseBody JsonResponse<Object> addLotManufacture(HttpSession session,
			@RequestBody PackingLotManufactureWebModel offDay) {
		logger.info("Method : addLotManufacture starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "addLotManufacture", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addLotManufacture ends"+resp);
		return resp;
	}
	
// 2A FSS Machine Setting
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-save2AFSSMachine")
	public @ResponseBody JsonResponse<Object> add2AFSSMachine(HttpSession session,
			@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : add2AFSSMachine starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "add2AFSSMachine", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : add2AFSSMachine ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-get2AFSSMachine")
	public @ResponseBody Object view2AFSSMachine(@RequestParam String id, HttpSession session) {

			logger.info("Method :view2AFSSMachine starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "view2AFSSMachine?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :view2AFSSMachine ends  ");
		return resp;
	}
	
// 2 B FSS MACHINE SETTING
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-save2BFSSMachine")
	public @ResponseBody JsonResponse<Object> save2BFSSMachine(HttpSession session,
			@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : save2BFSSMachine starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "save2BFSSMachine", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : add2AFSSMachine ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-get2BFSSMachine")
	public @ResponseBody Object view2BFSSMachine(@RequestParam String id, HttpSession session) {

			logger.info("Method :get2BFSSMachine starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "view2BFSSMachine?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :view2BFSSMachine ends  ");
		return resp;
	}
	
//5.Inspection Of Non-Blending Bulk Powder & Batch Coding Record
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-save5INBBPBCR")
	public @ResponseBody JsonResponse<Object> save5INBBPBCR(HttpSession session,
			@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : save5INBBPBCR starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "save5INBBPBCR", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : save5INBBPBCR ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-get5INBBPBCR")
	public @ResponseBody Object view25INBBPBCR(@RequestParam String id, HttpSession session) {
				logger.info("Method :view5INBBPBCR starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "view5INBBPBCR?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :view5INBBPBCR ends  ");
		return resp;
	}
	
//6. RH/TEMP CHECKS-SACHET / POUCH LINE
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-saveRHTemp")
	public @ResponseBody JsonResponse<Object> saveRHTemp(HttpSession session,
	@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : saveRHTemp starts"+offDay);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		offDay.setCreatedBy(userId);
		offDay.setOrganization(organization);
		offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "saveRHTemp", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : saveRHTemp ends"+resp);
		return resp;
	}
	
	@GetMapping("packing-logbook-view-getRHTemp")
	public @ResponseBody Object getRHTemp(@RequestParam String id, HttpSession session) {
		logger.info("Method :getRHTemp starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "viewRHTemp?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getRHTemp ends  ");
		return resp;
	}
// 9. Challenge Test - Sachet / Pouch Line
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-saveChalTest")
	public @ResponseBody JsonResponse<Object> saveChalTest(HttpSession session,
	@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : saveChalTest starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		offDay.setCreatedBy(userId);
		offDay.setOrganization(organization);
		offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "saveChalTest", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : saveChalTest ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-getChalTest")
	public @ResponseBody Object getChalTest(@RequestParam String id, HttpSession session) {
		logger.info("Method :getChalTest starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "getChalTest?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getChalTest ends  ");
		return resp;
	}

// 14 GTP
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-saveGTP")
	public @ResponseBody JsonResponse<Object> saveGTP(HttpSession session,
	@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : saveGTP starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		offDay.setCreatedBy(userId);
		offDay.setOrganization(organization);
		offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "saveGTP", offDay,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : saveGTP ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-getGTP")
	public @ResponseBody Object getGTP(@RequestParam String id, HttpSession session) {
		logger.info("Method :getGTP starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "getGTP?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getGTP ends  ");
		return resp;
	}	
	// 14 CTQ
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-saveCTQ")
	public @ResponseBody JsonResponse<Object> saveCTQ(HttpSession session,
		@RequestBody Packing2AFSSMachineWebModel offDay) {
		logger.info("Method : saveCTQ starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		offDay.setCreatedBy(userId);
		offDay.setOrganization(organization);
		offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "saveCTQ", offDay,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : saveCTQ ends"+resp);
		return resp;
	}

	@GetMapping("packing-logbook-view-getCTQ")
	public @ResponseBody Object getCTQ(@RequestParam String id, HttpSession session) {
		logger.info("Method :getCTQ starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "getCTQ?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getCTQ ends  ");
		return resp;
	}
// Initial Check
	@SuppressWarnings("unchecked")
	@PostMapping("packing-logbook-add-saveInitialCheck")
	public @ResponseBody JsonResponse<Object> saveInitialCheck(HttpSession session,
		@RequestBody PackingInitialCheckWebModel offDay) {
		logger.info("Method : saveInitialCheck starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		offDay.setCreatedBy(userId);
		offDay.setOrganization(organization);
		offDay.setOrgDivision(orgDivision);
		try {
			resp = restClient.postForObject(env.getProduction() + "saveInitialCheck", offDay,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
			logger.info("Method : saveInitialCheck ends"+resp);
			return resp;
	}

	@GetMapping("packing-logbook-view-getInitialCheck")
	public @ResponseBody Object getInitialCheck(@RequestParam String id, HttpSession session) {
		logger.info("Method :getInitialCheck starts");
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
			resp = restClient.getForObject(
				env.getProduction() + "getInitialCheck?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
				JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getInitialCheck ends  ");
		return resp;
	}
	// ALC
		@SuppressWarnings("unchecked")
		@PostMapping("packing-logbook-add-saveALC")
		public @ResponseBody JsonResponse<Object> saveALC(HttpSession session,
			@RequestBody PackingALCWebModel offDay) {
			logger.info("Method : saveALC starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
			try {
				resp = restClient.postForObject(env.getProduction() + "saveALC", offDay,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
				logger.info("Method : saveALC ends"+resp);
				return resp;
		}

		@GetMapping("packing-logbook-view-getALC")
		public @ResponseBody Object getALC(@RequestParam String id, HttpSession session) {
			logger.info("Method :getALC starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "getALC?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getALC ends  ");
			return resp;
		}
// FCWL
		@SuppressWarnings("unchecked")
		@PostMapping("packing-logbook-add-saveFCWL")
		public @ResponseBody JsonResponse<Object> saveFCWL(HttpSession session,
			@RequestBody PackingFCWLWebModel offDay) {
			logger.info("Method : saveFCWL starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			offDay.setCreatedBy(userId);
			offDay.setOrganization(organization);
			offDay.setOrgDivision(orgDivision);
			try {
				resp = restClient.postForObject(env.getProduction() + "saveFCWL", offDay,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Success");
			} else {
				resp.setMessage("Unsuccess");
			}
				logger.info("Method : saveFCWL ends"+resp);
				return resp;
		}

		@GetMapping("packing-logbook-view-getFCWL")
		public @ResponseBody Object getFCWL(@RequestParam String id, HttpSession session) {
			logger.info("Method :getFCWL starts");
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
				resp = restClient.getForObject(
					env.getProduction() + "getFCWL?id=" + id +"&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :getFCWL ends  ");
			return resp;
		}
		
// PDF
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "/packing-logbook-download" })
		public void LogBookPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("packId") String encodedParam1, @RequestParam("type") String encodedParam2,
			@RequestParam("organization") String encodedParam3, @RequestParam("orgDivision") String encodedParam4) {
				logger.info("Method : LogBookPdf starts");

				byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
				String packId = (new String(encodeByte1));

				byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
				String type = (new String(encodeByte2));
				
				byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
				String org = (new String(encodeByte3));

				byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
				String orgDiv = (new String(encodeByte4));

				
				JsonResponse<Object> resp = new JsonResponse<Object>();
				try {
					resp = restClient.getForObject(
						env.getProduction() + "logManufacturePdf?id=" + packId +"&org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> data = new HashMap<String, Object>();
				try {
					Map<String, Object> dataa = mapper.readValue(resp.getBody().toString(), new TypeReference<Map<String, Object>>() {});
					
				    	data.put("date", dataa.get("date"));
				    	data.put("shift", dataa.get("shift"));
				    	data.put("productName", dataa.get("productName"));
				    	data.put("skuId", dataa.get("skuId"));
				    	data.put("lineNo", dataa.get("lineNo"));
				    	data.put("batchNo", dataa.get("batchNo"));
			            data.put("lineNo", data.get("lineNo"));
				    	data.put("lotman", dataa.get("lotManage"));
				    	if (type.equals("01")) {
				    		extractAndStoreJsonData(dataa,data,"lotManage");
				    	} else if (type.equals("02A")) {
					        extractAndStoreJsonData(dataa,data,"AFSSMachine");
				    	} else if (type.equals("02B")) {
					        extractAndStoreJsonData(dataa,data,"BFSSMachine");
				    	} else if (type.equals("03A") || type.equals("03B")) {
					        extractAndStoreJsonData(dataa,data,"ALC");
				    	} else if (type.equals("04")) {
					        extractAndStoreJsonData(dataa,data,"initialCheck");
				    	} else if (type.equals("05")) {
					        extractAndStoreJsonData(dataa,data,"INBBPBCR");
				    	} else if (type.equals("06")) {
					        extractAndStoreJsonData(dataa,data,"RHTemp");
				    	} else if (type.equals("09")) {
					        extractAndStoreJsonData(dataa,data,"chalTest");
				    	} else if (type.equals("13")) {
					        extractAndStoreJsonData(dataa,data,"CTQ");
				    	} else if (type.equals("14")) {
					        extractAndStoreJsonData(dataa,data,"GTP");
				    	} else if (type.equals("15")) {
					        extractAndStoreJsonData(dataa,data,"FCWL");
				    	} else {
				    	}
				} catch (IOException e2) {
				    e2.printStackTrace();
				}
				/*
				 * String logo = (String) session.getAttribute("ORGANIZATION_LOGO"); URL getUrl
				 * = null; try { getUrl = new URL(logo); } catch (MalformedURLException e2) {
				 * e2.printStackTrace(); } String encodedLogoUrl =
				 * DownloadDocumentUrl.downloadDocumentUrl(getUrl); data.put("logo",
				 * "data:image/png;base64," + encodedLogoUrl);
				 */
				data.put("orgName",orgDiv);
				response.setContentType("application/pdf");
			//	response.setHeader("Content-disposition", "inline; filename=lotManufacturingRecordPouchLine.pdf");
				File file = null;
				byte[] fileData = null;
				try {
				    if (type.equals("01")) {
				    	response.setHeader("Content-disposition", "inline; filename=01LotManufacturingRecordPouchLine.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/01lotManufacturingRecordPouchLine.html", data);
				    } else if (type.equals("02A")) {
				    	response.setHeader("Content-disposition", "inline; filename=02AffsMachineSettingDetails.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/02AffsMachineSettingDetails.html", data);
				    } else if (type.equals("02B")) {
				    	response.setHeader("Content-disposition", "inline; filename=02BffsMachineSettingDetails.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/02BffsMachineSettingDetails.html", data);
				    } else if (type.equals("03A")) {
				    	response.setHeader("Content-disposition", "inline; filename=03AareaLineClearanceChecklistSachetPouchLine.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/03AareaLineClearanceChecklistSachetPouchLine.html", data);
				    } else if (type.equals("03B")) {
				    	response.setHeader("Content-disposition", "inline; filename=03BareaLineClearanceChecklistSachetPouchLine.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/03BareaLineClearanceChecklistSachetPouchLine.html", data);
				    } else if (type.equals("04")) {
				    	response.setHeader("Content-disposition", "inline; filename=04InitialChecks.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/04initialChecks.html", data);
				    } else if (type.equals("05")) {
				    	response.setHeader("Content-disposition", "inline; filename=05InspectionOfNonBlendingBulkPowderBatchCodingRecord.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/05inspectionOfNonBlendingBulkPowderBatchCodingRecord.html", data);
				    } else if (type.equals("06")) {
				    	response.setHeader("Content-disposition", "inline; filename=06RHTempChecksSachetPouchLine.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/06rHTempChecksSachetPouchLine.html", data);
				    } else if (type.equals("09")) {
				    	response.setHeader("Content-disposition", "inline; filename=09ChallengeTestSachetPouchLine.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/09challengeTestSachetPouchLine.html", data);
				    } else if (type.equals("13")) {
				    	response.setHeader("Content-disposition", "inline; filename=13Ctq&NonCtqList.pdf");
				        file = pdfGeneratorUtil.createPdf("production_plan/13ctqNonCtqList.html", data);
				    } else if (type.equals("14")) {
				    	response.setHeader("Content-disposition", "inline; filename=14GluingTankParametersBIBLine.pdf");
				    	file = pdfGeneratorUtil.createPdf("production_plan/14gluingTankParametersBIBLine.html", data);
				    }else if(type.equals("15")){
				    	response.setHeader("Content-disposition", "inline; filename=15FilledCartonWeighmentLimit.pdf");
						file = pdfGeneratorUtil.createPdf("production_plan/15filledCartonWeighmentLimit.html", data);
					}else if(type.equals("16")){
				    	response.setHeader("Content-disposition", "inline; filename=16SealingJawAlignmentRecordJawImpression.pdf");
						file = pdfGeneratorUtil.createPdf("production_plan/16sealingJawAlignmentRecordJawImpression.html", data);
					}else {
//				    	response.setHeader("Content-disposition", "inline; filename=lotManufacturingRecordPouchLine.pdf");
//						file = pdfGeneratorUtil.createPdf("production_plan/", data);
						
					}
					InputStream in = new FileInputStream(file);
					fileData = IOUtils.toByteArray(in);
					response.setContentLength(fileData.length);
					response.getOutputStream().write(fileData);
					response.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				logger.info("Method : LogBookPdf ends");
			}
        @SuppressWarnings("unchecked")
		public void extractAndStoreJsonData(Map<String, Object> dataa, Map<String, Object> data, String key) throws JsonParseException, JsonMappingException, IOException {
		    // Check if the key exists in the JSONObject
		    if (dataa.containsKey(key)) {
		        ObjectMapper mapper = new ObjectMapper();
		        // Convert the JSON object to a map
				Map<String, Object> allData = (Map<String, Object>) dataa.get(key);
				 if (allData != null) {
					// Check and process "jsonData"
				        if (allData.containsKey("jsonData")) {
				        	String jsonData = (String) allData.get("jsonData");
				            List<Map<String, Object>> jsonDataList = mapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>() {});
							allData.put("jsonList", jsonDataList);
				        }else {
				        	allData.put("jsonList", "");
				        }
				        if (allData.containsKey("jsonData1")) {
				        	String jsonData = (String) allData.get("jsonData1");
				            List<Map<String, Object>> jsonDataList = mapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>() {});
							allData.put("jsonList1", jsonDataList);
				        }else {
				        	allData.put("jsonList1", "");
				        }
				        if (allData.containsKey("jsonData2")) {
				        	String jsonData = (String) allData.get("jsonData2");
				            List<Map<String, Object>> jsonDataList = mapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>() {});
							allData.put("jsonList2", jsonDataList);
				        }else {
				        	allData.put("jsonList2", "");
				        }
				        if (allData.containsKey("jsonData3")) {
				        	String jsonData = (String) allData.get("jsonData3");
				            List<Map<String, Object>> jsonDataList = mapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>() {});
							allData.put("jsonList3", jsonDataList);
				        }else {
				        	allData.put("jsonList3", "");
				        }
				        data.put(key, allData);
				 }
		    }
		}
 // search
        @GetMapping("packing-logbook-view-search")
    	public @ResponseBody Object viewPackingLogbookSearch(Model model, HttpSession session, @RequestParam String searchValue,String pageno) {

    			logger.info("Method :viewPackingLogbookSearch starts");
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
    				resp = restClient.getForObject(
    					env.getProduction() + "viewPackingLogbookSearch?org=" + org + "&orgDiv=" + orgDiv + "&searchValue=" + searchValue + "&pageno=" + pageno,
    					JsonResponse.class);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			logger.info("Method :viewPackingLogbookSearch ends  ");
    		return resp;
    	}
     // MAchine List.

    	@SuppressWarnings("unchecked")
    	@GetMapping("packing-logbook-machineList")
    	public @ResponseBody Object machineList(HttpSession session, @RequestParam String id) {
    		logger.info("Method :machineList starts");
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

    			resp = restTemplate.getForObject(env.getProduction() + "rest-lmrLog-machineList?id=" + id + "&orgName="
    					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		logger.info("Method :machineList ends");
    		return resp;
    	}
	
}