package nirmalya.aathithya.webmodule.master.controller;

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
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ReimbursementPaymentModel;
import nirmalya.aathithya.webmodule.master.model.AccountantSectionWebModel;

@Controller
@RequestMapping(value = "master")
public class AccountantSectionWebController {

	Logger logger = LoggerFactory.getLogger(AccountantSectionWebController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "accountant-section" })

	public String accountantSection(Model model, HttpSession session) {
		logger.info("Method : accountSection starts");
		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] bankNames = restTemplate.getForObject(env.getEmployeeUrl() + "getBankNamesPay",
					DropDownModel[].class);
			List<DropDownModel> bankNameLists = Arrays.asList(bankNames);
			model.addAttribute("bankNameLists", bankNameLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] paymentMode = restTemplate.getForObject(env.getEmployeeUrl() + "getPaymentMode",
					DropDownModel[].class);
			List<DropDownModel> PayModeLists = Arrays.asList(paymentMode);

			model.addAttribute("PayModeLists", PayModeLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : accountSection ends");
		return "master/accountantSection";
	}

	/*
	 * Add Payment
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("accountant-section-add-paymentDetails")
	public @ResponseBody JsonResponse<Object> addAccountantSection(
			@RequestBody ReimbursementPaymentModel reimbursementModel, HttpSession session) {
		logger.info("Method : addAccountantSectionPaymeentdeatils starts");
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

		reimbursementModel.setCreatedBy(userId);
		reimbursementModel.setOrganization(organization);
		reimbursementModel.setOrgDivision(orgDivision);
		logger.info("ADDDDATA" + reimbursementModel);
 
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getMasterUrl() + "add-accountantSection-payment", reimbursementModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("ADDDDDDD" + resp);
		logger.info("Method : addAccountantSectionPaymeentdeatils ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("accountant-section-view")
	public @ResponseBody List<AccountantSectionWebModel> viewAccountantSection(HttpSession session,
			@RequestParam String userid) {

		logger.info("Method : viewAccountantSection ");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<AccountantSectionWebModel>> resp = new JsonResponse<List<AccountantSectionWebModel>>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewAccountantSection?userid=" + userid,
					JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AccountantSectionWebModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountantSectionWebModel>>() {
				});
		for (AccountantSectionWebModel a : Model) {
			if (a.getPaymentStatus().equals("1")) {
				a.setPaymentStatus("Paid");
			}else {
				a.setPaymentStatus("Pending");
			}
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewAccountantSection  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);
		return Model;

	}

}
