package nirmalya.aathithya.webmodule.employee.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import nirmalya.aathithya.webmodule.employee.model.GradeSalaryMasterModel;
import nirmalya.aathithya.webmodule.employee.model.GradeSalaryModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@RequestMapping(value = { "employee/" })
public class GradeSalaryNewMasterController {
	
	Logger logger = LoggerFactory.getLogger(GradeSalaryNewMasterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping(value = { "/view-grade-salary" })
	public String gradesalarynewDetails(Model model, HttpSession session) {
		logger.info("Method : gradesalarynewDetails starts");
		/*
		 * for viewing drop down list for Grade Name
		 */
		try {
			DropDownModel[] dropDownModel = restClient.getForObject(env.getEmployeeUrl() + "getGradeNameNewList",
					DropDownModel[].class);
			List<DropDownModel> gradeList = Arrays.asList(dropDownModel);
			model.addAttribute("gradeList", gradeList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * for viewing drop down list for Component list
		 */
		try {
			DropDownModel[] dropDownModel = restClient.getForObject(env.getEmployeeUrl() + "getComponenmewtList",
					DropDownModel[].class);
			List<DropDownModel> componentList = Arrays.asList(dropDownModel);
			model.addAttribute("componentList", componentList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] dropDownModel = restClient.getForObject(env.getEmployeeUrl() + "getCalnewtList",
					DropDownModel[].class);
			List<DropDownModel> calculationList = Arrays.asList(dropDownModel);
			model.addAttribute("calculationList", calculationList);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : gradesalarynewDetails ends");
		return "employee/view-grade-salary-new-master";
	}
	/**
	 * get Description by the onChange of Grade selected
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-grade-salary-getDesc-throughAjax" })
	public @ResponseBody JsonResponse<DropDownModel> getGradeDescription(Model model, @RequestBody String grade,
			BindingResult result) {
		logger.info("Method : getGradeDescription starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "getGradeDescription?id=" + grade,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getGradeDescription ends");
		return res;
	}
	/**
	 * get Component Type by the onChange of Salary Component selected
	 *
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-grade-salary-getComponentType-throughAjax" })
	public @ResponseBody JsonResponse<GradeSalaryModel> getgrdsalComponentType(Model model,
			@RequestBody String componentType, BindingResult result) {
		logger.info("Method : getgrdsalComponentType starts");

		JsonResponse<GradeSalaryModel> res = new JsonResponse<GradeSalaryModel>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "getgrdsalComponentType?id=" + componentType,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("cmt"+res);
		logger.info("Method : getgrdsalComponentType ends");
		return res;
	}
	
	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-grade-salary-add")
	public @ResponseBody JsonResponse<Object> addgrdSalarynew(HttpSession session,@RequestBody
			List<GradeSalaryModel> gradeSalaryModel) {
		logger.info("Method : addgrdSalarynew starts");
		logger.info(gradeSalaryModel.toString());
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			

		} catch (Exception e) {

		}
		for (GradeSalaryModel m : gradeSalaryModel) {
			m.setgCreatedBy(userId);
			
		}

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addgrdSalarynew", gradeSalaryModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			
			  List<GradeSalaryModel> quotation = mapper.convertValue(resp.getBody(), 
					  new TypeReference<List<GradeSalaryModel>>() {
			  });
			 
			
			resp.setBody(quotation);
			logger.info(gradeSalaryModel.toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Sradha" + resp);

		logger.info("Method : addgrdSalarynew ends");

		return resp;
	}
	
	/*
	 * view
	 */
	@SuppressWarnings("unchecked")

	@GetMapping("view-grade-salary-through-ajax")
	public @ResponseBody List<GradeSalaryModel> viewgrdsalnew(HttpSession session) {

		logger.info("Method :getAllgrdsal starts");
		JsonResponse<List<GradeSalaryModel>> resp = new JsonResponse<List<GradeSalaryModel>>();
			
		try {

			resp = restClient.getForObject(env.getEmployeeUrl() + "getAllgrdsal", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<GradeSalaryModel> gradeSalaryModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GradeSalaryModel>>() {
				});
		

		resp.setBody(gradeSalaryModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :getAllgrdsal ends");
		logger.info("RESPONSEview" + resp);
		return resp.getBody();
	}
	
	
	/*
	* for editing using employee id
	*
	*
	*/
	
	@GetMapping(value = { "view-grade-salary-edit-new" })
	public @ResponseBody List<GradeSalaryModel> viewgrdSalEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewgrdSalEdit starts");
		logger.info(id);
		List<GradeSalaryModel> productList = new ArrayList<GradeSalaryModel>();

		if (id != null && id != "") {
			try {
				GradeSalaryModel[] gradeSalaryModel = restClient.getForObject(
						env.getEmployeeUrl() + "viewgrdSalEdit?id=" + id, GradeSalaryModel[].class);

				productList = Arrays.asList(gradeSalaryModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (GradeSalaryModel m : gradeSalaryModel) {

					count++;
					m.setSlNo(count);

					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewgrdSalEdit ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}
	
/*
* * Delete
*/
	


@SuppressWarnings("unchecked")

@GetMapping("view-grade-salary-delete")
public @ResponseBody JsonResponse<GradeSalaryModel> deletegrdsal(Model model, @RequestParam String selectedRowsString,
		
		HttpSession session) {
	logger.info("Method : deletegrdsal starts");

	JsonResponse<GradeSalaryModel> resp = new JsonResponse<GradeSalaryModel>();
	logger.info(selectedRowsString);
	
	
	String userId = "";

	try {
	userId = (String) session.getAttribute("USER_ID");
	} catch (Exception e) {
	e.printStackTrace();
	}

	try {
	resp = restClient.getForObject(env.getEmployeeUrl() + "deletegrdsal?id="+selectedRowsString+"&createdBy="+userId,
	JsonResponse.class);

	} catch (RestClientException e) {
	e.printStackTrace();
	}
	if (resp.getMessage() != null && resp.getMessage() != "") {
		resp.setCode(resp.getMessage());
		resp.setMessage("Unsuccess");
	} else {
		resp.setMessage("success");
	}

	logger.info("Method :  deletegrdsal ends");

	logger.info("resp" +resp);
	return resp;
}
}
