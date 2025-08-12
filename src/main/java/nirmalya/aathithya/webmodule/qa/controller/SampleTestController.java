package nirmalya.aathithya.webmodule.qa.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.qa.model.SampleTastModel;

@Controller

@RequestMapping(value = { "production/" })
public class SampleTestController {
	
	Logger logger = LoggerFactory.getLogger(SampleTestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "sample-test" })

	public String qaRequest(Model model, HttpSession session) {
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/sample-test";
	}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("sample-test-detls")
		public @ResponseBody Object qaRequestDtls(HttpSession session) {

			logger.info("Method :qaRequestDtls starts");
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
				resp = restTemplate.getForObject(
						env.getProduction() + "rest-qaTestDtls?&orgName=" +orgName+"&orgDivision=" +orgDivision ,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("edit>>>-----" + resp);
			logger.info("Method :qaRequestDtls ends");

			return resp;
		}
		
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("sample-test-child")
		public @ResponseBody Object qaRequestDtls(@RequestParam String rid,@RequestParam String id, HttpSession session) {
			
			logger.info("Method :sample-child-detls starts",rid,id);
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
				resp = restTemplate.getForObject(
						env.getProduction() + "rest-qaChildDtls?rid=" + rid +"&id=" +id +"&orgName=" +orgName+"&orgDivision=" +orgDivision ,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("edit>>>-----" + resp);
			logger.info("Method :sample-child-detls ends");

			return resp;
		}
		
		
		
		@SuppressWarnings("unchecked")
		@PostMapping("sample-test-Result")
		public @ResponseBody JsonResponse<Object> testResultSubmit(@RequestBody SampleTastModel data, HttpSession session) {
			
			logger.info("Method :testResultSubmit starts");
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
			
			data.setCreatedBy(userId);
			data.setOrgName(orgName);
			data.setOrgDiv(orgDivision);
			System.out.println("data>>>-----"+data);
			try {
				resp = restTemplate.postForObject(
						env.getProduction() + "rest-testResultSubmit" , data ,JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("edit>>>-----" + resp);
			logger.info("Method :testResultSubmit ends");

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("sample-test-approve")
		public @ResponseBody Object testApproveSet(@RequestParam String id, String sku, HttpSession session) {
			
			logger.info("Method :testApproveSet starts");
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
				resp = restTemplate.getForObject(
						env.getProduction() + "rest-testApprove?id=" + id + "&sku=" + sku,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("edit>>>-----" + resp);
			logger.info("Method :testApproveSet ends");

			return resp;
		}

		

}

