package nirmalya.aathithya.webmodule.ticket.controller;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
	
	@Controller
	@RequestMapping(value = { "ticket" })
	public class WorkPermitWebController {
	    Logger logger = LoggerFactory.getLogger(WorkPermitWebController.class);
	    @Autowired
	    RestTemplate restTemplate;
	    @Autowired
	    EnvironmentVaribles env;
	    //My Achievement fetch
	    @GetMapping(value = "workpermit")
	    public String workpermit(HttpSession session, Model model) {

	        logger.info("Method: workpermit starts");
	        String userId = "";
			String organization = "";
			String orgDivision = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			model.addAttribute("userId", userId);
			model.addAttribute("organization", organization);
			model.addAttribute("orgDivision", orgDivision);
	        logger.info("Method: workpermit ends");
	        
	    	try {
				DropDownModel[] emp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + organization
						+ "&orgDiv=" + orgDivision + "&userId=" + userId, DropDownModel[].class);
				List<DropDownModel> empLists = Arrays.asList(emp);

				model.addAttribute("empLists", empLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

	        return "ticket/workpermit";
	    }

	    //add my workpermit
	    @SuppressWarnings("unchecked")
	    @PostMapping("/workpermit-add")
	    public @ResponseBody JsonResponse<Object> workpermitAdd(@RequestBody Map<String, Object> workpermit, Model model, HttpSession session) {
	        logger.info("Method : workpermitAdd Starts");
	        System.out.println(workpermit);
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
	        try {

	            resp = restTemplate.postForObject(env.getTicketUrl() + "saveWorkPermit", workpermit,
	                    JsonResponse.class);
	            System.out.println(resp);
	            resp.setCode("Success");
	            resp.setMessage("workpermit added successfully");

	        } catch (RestClientException e) {
	            resp.setCode("Failed");
	            resp.setMessage("Error During workpermit add");
	            e.printStackTrace();
	        }
	        logger.info("Method : workpermit add End");
	        return resp;
	    }
	    
	    @SuppressWarnings("unchecked")
	    @GetMapping("workpermit-view")
	    public @ResponseBody Object fetchWorkPermit(HttpSession session) {
	        logger.info("Method :fetchWorkPermit starts");
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
	        try {
	            resp = restTemplate
	                    .getForObject(env.getTicketUrl() + "rest-fetch-workpermit?org=" + organization+ "&orgDiv="
	                            + orgDivision,JsonResponse.class);
	            System.out.println("final view response"+resp.getBody());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        logger.info("Method :fetchWorkPermit ends");
	        return resp.getBody();
	    }
	    
	    @SuppressWarnings("unchecked")
	    @GetMapping("workpermit-edit")
	    public @ResponseBody Object editWorkPermit(HttpSession session,@RequestParam String id) {
	        logger.info("Method :editWorkPermit starts");
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
	        try {
	            resp = restTemplate
	                    .getForObject(env.getTicketUrl() + "edit-work-permit?org=" + organization +"&orgDiv=" + orgDivision+ "&id="
	                            + id,JsonResponse.class);

	            resp.setCode("Success");
	            resp.setMessage("WorkPermit fetch successfully");
	            System.out.println(resp);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        logger.info("Method :editWorkPermit ends");
	        return resp;
	    }
	    
	    @SuppressWarnings("unchecked")
	    @GetMapping("workpermit-delete")
	    public @ResponseBody JsonResponse<Object> deleteleWorkPermit(@RequestParam String id,
	                                                                    Model model, HttpSession session) {
	        logger.info("Method : deleteleWorkPermit function starts");
	        JsonResponse<Object> res = new JsonResponse<Object>();
	        String organization="";
	        String orgDivision="";

	        try {
	            organization = (String) session.getAttribute("ORGANIZATION");
	            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        } catch (Exception e) {

	        }
	        try {
	            res = restTemplate.getForObject(env.getTicketUrl() + "rest-delete-workpermit?id=" + id+"&org="+organization+"&div="+orgDivision, JsonResponse.class);
	        } catch (RestClientException e) {
	            e.printStackTrace();
	        }

	        if(res.getCode().equals("success")) {
	            res.setMessage("Success");
	        } else {
	            res.setCode(res.getMessage());
	            res.setMessage("Unsuccess");
	        }
	        logger.info("Method : deleteleMyAchievement function Ends");

	        logger.info("deleteleWorkPermit"+res);
	        return res;
	    }
//
	    @SuppressWarnings("unchecked")
	    @GetMapping("workpermit-approve")
	    public @ResponseBody JsonResponse<Object> approveWorkPermit(@RequestParam String id,
	                                                                    Model model, HttpSession session) {
	        logger.info("Method : approveWorkPermit function starts");
	        JsonResponse<Object> res = new JsonResponse<Object>();
	        String organization="";
	        String orgDivision="";
	        String userId="";

	        try {
	            organization = (String) session.getAttribute("ORGANIZATION");
	            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	            userId = (String) session.getAttribute("USER_ID");
	        } catch (Exception e) {

	        }
	        try {
	            res = restTemplate.getForObject(env.getTicketUrl() + "rest-approve-workpermit?id=" + id+"&org="+organization+"&div="+orgDivision+"&userId="+userId, JsonResponse.class);
	        } catch (RestClientException e) {
	            e.printStackTrace();
	        }

	        if(res.getCode().equals("success")) {
	            res.setMessage("Success");
	        } else {
	            res.setCode(res.getMessage());
	            res.setMessage("Unsuccess");
	        }
	        logger.info("Method : approveWorkPermit function Ends");

	        logger.info("approveWorkPermit"+res);
	        return res;
	    }
}
