package nirmalya.aathithya.webmodule.asset.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class AssetMechanicalReportController {

	Logger logger = LoggerFactory.getLogger(AssetMechanicalReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	// Meta Detector
	@GetMapping("/metal-detector")
	public String getMetalDetector(Model model, HttpSession session) {
		logger.info("Method : getMetalDetector starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0022";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getMetalDetector ends");
		return "asset/metal-detector";

	}

	// view Metal Detector

	@SuppressWarnings("unchecked")

	@GetMapping("metal-detector-view")
	public @ResponseBody Object viewMetalDetector(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewMetalDetector starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0022";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewMetalDetector?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewMetalDetector ends");
		return resp;
	}

	// Double Track Machine

	@GetMapping("/doublei-track-packMachine")
	public String getDoubleTrack(Model model, HttpSession session) {
		logger.info("Method : getDoubleTrack starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0024";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getDoubleTrack ends");
		return "asset/doublei-track-packMachine";

	}

	// view Double Track

	@SuppressWarnings("unchecked")

	@GetMapping("doublei-track-packMachine-view")
	public @ResponseBody Object viewDoubleTrack(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewDoubleTrack starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0024";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewDoubleTrack?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewDoubleTrack ends");
		return resp;
	}

	// Gear Oil Equipment

	@GetMapping("/gear-oil-equipments")
	public String getGearOil(Model model, HttpSession session) {
		logger.info("Method : getGearOil starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0032";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getGearOil ends");
		return "asset/gear-oil-equipments";

	}

	// view Gear oil

	@SuppressWarnings("unchecked")

	@GetMapping("gear-oil-equipments-view")
	public @ResponseBody Object viewGearOil(@RequestParam String fromDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method :viewGearOil starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0032";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewGearOil?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate + "&policyId="
					+ policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewGearOil ends");
		return resp;
	}

	// Checklist For Area
	@GetMapping("/cheklist-process-area")
	public String getChecklist(Model model, HttpSession session) {
		logger.info("Method : getChecklist starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0038";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			logger.info("assetList"+assetList);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getChecklist ends");
		return "asset/cheklist-process-area";

	}

	// view Checklist

	@SuppressWarnings("unchecked")

	@GetMapping("cheklist-process-area-view")
	public @ResponseBody Object viewgetChecklist(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewgetChecklist starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0038";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewgetChecklist?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewgetChecklist ends");
		return resp;
	}

	// Bag For CheckList
	@GetMapping("/bag-filter-checkList")
	public String getBagFilterCheckList(Model model, HttpSession session) {
		logger.info("Method : getBagFilterCheckList starts");

		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0040";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getBagFilterCheckList ends");
		return "asset/bag-filterCheckList";
	}

	// view Bag Filter Checklist

	@SuppressWarnings("unchecked")

	@GetMapping("bag-filter-checkList-view")
	public @ResponseBody Object viewBagFiletrCheckList(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewBagFiletrCheckList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0040";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewBagFiletrCheckList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewBagFiletrCheckList ends");
		return resp;
	}

	// BIB MACHINE/////////////////////////////////

	@GetMapping("/preventive-bib-machine")
	public String getBibMachine(Model model, HttpSession session) {
		logger.info("Method : getBibMachine starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0020";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getBibMachine ends");
		return "asset/bib-machine";

	}

	// view Bib Machine

	@SuppressWarnings("unchecked")

	@GetMapping("preventive-bib-machine-view")
	public @ResponseBody Object viewBibMachine(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewBibMachine starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0020";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewBibMachine?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewBibMachine ends");
		return resp;
	}

	// SHREDDING MACHINE ///////////////////////////////

	@GetMapping("/shredding-machine")
	public String getshredding(Model model, HttpSession session) {
		logger.info("Method : getshredding starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0033";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		logger.info("Method : getshredding ends");
		return "asset/shredding-machine";

	}

	// view Shredding machine

	@SuppressWarnings("unchecked")

	@GetMapping("shredding-machine-view")
	public @ResponseBody Object viewgetshredding(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewgetshredding starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0033";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewgetshredding?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewgetshredding ends");
		return resp;
	}

	/////// STARPAC PERFORATION////////////////

	@GetMapping("/starpac-perforationCheckList")
	public String getStrpacBlade(Model model, HttpSession session) {
		logger.info("Method : getStrpacBlade starts");
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0042";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getStrpacBlade ends");
		return "asset/strpac-blade-checklist";

	}

	// view Starpac Blade
	@SuppressWarnings("unchecked")

	@GetMapping("starpac-perforationCheckList-view")
	public @ResponseBody Object viewStrpacBlade(@RequestParam String mon, HttpSession session) {
		logger.info("Method :viewStrpacBlade starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0042";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewStrpacBlade?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&mon=" + mon + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewStrpacBlade ends" + resp);
		return resp;
	}

	// view Nut Boult

	@SuppressWarnings("unchecked")

	@GetMapping("nut-boults-equipment-view")
	public @ResponseBody Object viewNutBults(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewNutBults starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0029";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewNutBults?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewNutBults ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("multi-track-packMachine-view")
	public @ResponseBody Object viewMultiTrack(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :viewMultiTrack starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0021";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewMultiTrack?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewMultiTrack ends");
		return resp;
	}

	@GetMapping("/paint-workCheckList")
	public String getworkCheckList(Model model, HttpSession session) {
		logger.info("Method : workCheckList starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : workCheckList ends");
		return "asset/paint-worklist";
	}

	@SuppressWarnings("unchecked")

	@GetMapping("paint-workCheckList-view")
	public @ResponseBody Object paintworkCheckList(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String assetName, HttpSession session) {
		logger.info("Method :paintworkCheckList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "PPID/2024-25/0003";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-paintworkCheckList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&assetName=" + assetName + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :paintworkCheckList ends" + resp);
		return resp;
	}

	@GetMapping("/multi-track-packMachine")
	public String getMultiTrack(Model model, HttpSession session) {
		logger.info("Method : getMultiTrack starts");

		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0021";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getMultiTrack ends");
		return "asset/multi-track-packing";

	}

	@GetMapping("/nut-boults-equipment")
	public String getNutBults(Model model, HttpSession session) {
		logger.info("Method : getNutBults starts");

		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0029";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		
		String organization = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method : getNutBults ends");
		return "asset/nut-boults-equipment";

	}

	@GetMapping("/epoxy-workCheckList")
	public String getEpoxyworkCheckList(Model model, HttpSession session) {
		logger.info("Method : getEpoxyworkCheckList starts");

		logger.info("Method : getEpoxyworkCheckList ends");
		return "asset/epoxy-workCheckList";
	}

	// Epoxy Work Check List View
	@SuppressWarnings("unchecked")
	@GetMapping("epoxy-workCheckList-view")
	public @ResponseBody Object epoxyWorkCheckListView(@RequestParam String fromDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method :epoxyWorkCheckListView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "PPID/2024-25/0004";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-epoxyWorkCheckListView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :epoxyWorkCheckListView ends" + resp);
		return resp;
	}

	@GetMapping("/starpac-cutterChangeRecord")
	public String getStrpacCutterChangeRecord(Model model, HttpSession session) {
		logger.info("Method : getStrpacCutterChangeRecord starts");

		logger.info("Method : getStrpacCutterChangeRecord ends");
		return "asset/strpac-cutter-changeRecord";

	}

	// View Starpac Center Cutter Change Record
	@SuppressWarnings("unchecked")
	@GetMapping("starpac-cutterChangeRecord-view")
	public @ResponseBody Object viewStrpacCutterRecord(@RequestParam String mon, HttpSession session) {
		logger.info("Method :viewStrpacCutterRecord starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0041";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewStrpacCutterRecord?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&mon=" + mon + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewStrpacCutterRecord ends" + resp);
		return resp;
	}
	
	@GetMapping("/wet-cleaning-checklist")
	public String getWetCleanCheckList(Model model, HttpSession session) {
		logger.info("Method : getWetCleanCheckList starts");

		logger.info("Method : getWetCleanCheckList ends");
		return "asset/wet-cleanCheckList";

	}

	// View Wet Cleaning Check List

		@SuppressWarnings("unchecked")
		@GetMapping("wet-cleaning-checklist-view")
		public @ResponseBody Object viewWetCleaningCheckList(@RequestParam String date,HttpSession session) {
			logger.info("Method :viewWetCleaningCheckList starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String userId = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewWetCleaningCheckList?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&userId=" + userId +"&date="+date, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewWetCleaningCheckList ends"+resp);
			return resp;
		}
}
