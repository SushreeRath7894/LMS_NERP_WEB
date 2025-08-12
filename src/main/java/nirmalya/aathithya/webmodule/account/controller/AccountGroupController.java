package nirmalya.aathithya.webmodule.account.controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.DataSetAccountTree;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.OrganizationalMasterModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountGroupController {
	Logger logger = LoggerFactory.getLogger(AccountGroupController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * View Group
	 */



	@SuppressWarnings("unchecked")
	@GetMapping("manage-account-group")
	public String viewAccountGroup(Model model, HttpSession session) {

		logger.info("Method : viewAccountGroup starts");
		JsonResponse<List<DataSetAccountTree>> respTblact = new JsonResponse<List<DataSetAccountTree>>();
		List<DataSetAccountTree> treeData = new ArrayList<DataSetAccountTree>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			respTblact = restClient.getForObject(
					env.getAccountUrl() + "getAccountTreeDetails?Action=" + "getAccountTreeDetails" + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String messageForTblact = respTblact.getMessage();

		if (messageForTblact != null || messageForTblact != "") {
			model.addAttribute("message", messageForTblact);
		}

		ObjectMapper mapper4 = new ObjectMapper();

		treeData = mapper4.convertValue(respTblact.getBody(), new TypeReference<List<DataSetAccountTree>>() {
		});
		model.addAttribute("treeData1", treeData);

		logger.info("Method : viewAccountGroup  end" + treeData);
		return "account/manage-account-group";
	}

//view-account-group-add-parent

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-group-add-parent", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<Object> addAccountParent(@ModelAttribute DataSetAccountTree dataForParent,
			Model model, HttpSession session) {
		JsonResponse<Object> response = new JsonResponse<Object>();
		logger.info("Method : addParent function starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		dataForParent.setOrgName(orgName);
		dataForParent.setOrgDivision(orgDivision);
		
		try {
			response = restClient.postForObject(env.getAccountUrl() + "restAddParent", dataForParent,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = response.getMessage();

		if (message != null && message != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("response data" + response);
		logger.info("Method : addAccountParent function Ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-group-add-child", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<Object> addAccountChild(@ModelAttribute DataSetAccountTree dataForChild,
			Model model, HttpSession session) {
		JsonResponse<Object> response = new JsonResponse<Object>();
		logger.info("Method : addAccountChild function starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		dataForChild.setOrgName(orgName);
		dataForChild.setOrgDivision(orgDivision);
		
		try {
			response = restClient.postForObject(env.getAccountUrl() + "restAddChild", dataForChild, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = response.getMessage();

		if (message != null && message != "") {

		} else {
			response.setMessage("Success");
		}

		System.out.println("response data" + response);
		logger.info("Method : addAccountChild function Ends");
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-group-modifyName")
	public @ResponseBody JsonResponse<Object> modifyHeaderName(@RequestParam String id, String nameGroup,
			 HttpSession session) {
		logger.info("Method : modifyHeaderName function starts=="+nameGroup);

		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		
		try {
			res = restClient.getForObject(env.getAccountUrl() + "modifyHeaderName?id="+id + "&nameGroup=" + nameGroup + "&orgName=" + orgName + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : modifyHeaderName function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-group-modifyNameParent")
	public @ResponseBody JsonResponse<Object> modifyHeaderNameParent(@RequestParam String id, String nameGroup, String natureGrp,
			 HttpSession session) {
		logger.info("Method : modifyHeaderNameParent function starts=="+nameGroup);

		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		
		try {
			res = restClient.getForObject(env.getAccountUrl() + "modifyHeaderNameParent?id="+ id + "&nameGroup=" + nameGroup + "&natureGrp=" + natureGrp + "&orgName=" + orgName + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : modifyHeaderNameParent function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
}
