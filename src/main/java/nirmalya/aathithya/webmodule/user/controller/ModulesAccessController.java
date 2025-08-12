package nirmalya.aathithya.webmodule.user.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.LocationRoomModel;
import nirmalya.aathithya.webmodule.user.model.ModulesAccessModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class ModulesAccessController {

	Logger logger = LoggerFactory.getLogger(ModulesAccessController.class);
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "/manage-modules" })
	public String manageModules(Model model, HttpSession session) {
		logger.info("Method : manageModules starts");
		
		try {
			ModulesAccessModel[] module = restClient.getForObject(env.getUserUrl() + "getModuleListForAccess", ModulesAccessModel[].class);
			List<ModulesAccessModel> moduleList = Arrays.asList(module);
			
			List<ModulesAccessModel> compList = new ArrayList<ModulesAccessModel>();
			
			for(ModulesAccessModel m : moduleList) {
				try {
					ModulesAccessModel[] dropdown = restClient.getForObject(env.getUserUrl() + "getComponentListForAccess?id=" + m.getKey(), ModulesAccessModel[].class);
					compList = Arrays.asList(dropdown);
					m.setDataList(compList);
					List<ModulesAccessModel> subCompList = new ArrayList<ModulesAccessModel>();
					for(ModulesAccessModel k : compList) {
						try {
							ModulesAccessModel[] dd = restClient.getForObject(env.getUserUrl() + "getSubComponentListForAccess?id=" + m.getKey() + "&comp=" + k.getKey(), ModulesAccessModel[].class);
							subCompList = Arrays.asList(dd);
							k.setDataList(subCompList);
							
						} catch (RestClientException e) {
							e.printStackTrace();
						}
					}
					
				} catch (RestClientException e) {
					e.printStackTrace();
				}
			}
			model.addAttribute("moduleList", moduleList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : manageModules starts");
		return "user/manageModules";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-modules-update-activity")
	public @ResponseBody JsonResponse<Object> updateActivity(@RequestBody DropDownModel data, HttpSession session) {
		logger.info("Method : updateActivity starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getUserUrl() + "updateActivity?id="+data.getKey() + "&status=" + data.getName() + "&modifiedBy=" + userId ,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : updateActivity starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-modules-update-function")
	public @ResponseBody JsonResponse<Object> updateFunction(@RequestBody DropDownModel data, HttpSession session) {
		logger.info("Method : updateFunction starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getUserUrl() + "updateFunction?id="+data.getKey() + "&status=" + data.getName() + "&modifiedBy=" + userId ,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : updateFunction starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-modules-update-module")
	public @ResponseBody JsonResponse<Object> updateModule(@RequestBody DropDownModel data, HttpSession session) {
		logger.info("Method : updateModule starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getUserUrl() + "updateModule?id="+data.getKey() + "&status=" + data.getName() + "&modifiedBy=" + userId ,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : updateModule starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-modules-update-data")
	public @ResponseBody JsonResponse<ModulesAccessModel> updateData(@RequestBody ModulesAccessModel data, HttpSession session) {
		logger.info("Method : updateModule starts");
		
		JsonResponse<ModulesAccessModel> resp = new JsonResponse<ModulesAccessModel>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		try {
			resp = restClient.postForObject(env.getUserUrl() + "updateData" , data ,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : updateData starts");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-modules-avl-func-lists" })
	public @ResponseBody JsonResponse<List<ModulesAccessModel>> getAvailableFunctionLists(Model model) {
		logger.info("Method : getAvailableFunctionLists starts");
		
		JsonResponse<List<ModulesAccessModel>> res = new JsonResponse<List<ModulesAccessModel>>();
		
		try {
			res = restClient.getForObject(env.getUserUrl() + "getAvailableFunctionLists",
					JsonResponse.class);
			
//			ObjectMapper mapper = new ObjectMapper();
//			
//			List<ModulesAccessModel> dataList = mapper.convertValue(res.getBody(),
//					new TypeReference<List<ModulesAccessModel>>() {
//			});
//			
//			for(ModulesAccessModel m : dataList) {
//				
//				try {
//					DropDownModel[] avlFunc = restClient.getForObject(env.getUserUrl() + "getAvlFuncByActivityId}id="+m.getCreatedBy(), DropDownModel[].class);
//					List<DropDownModel> avlFuncList = Arrays.asList(avlFunc);
//					m.setFuncList(avlFuncList);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null && res.getMessage() != "") {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getAvailableFunctionLists ends");
		return res;
		
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manage-modules-edit-view-slider" })
	public @ResponseBody JsonResponse<ModulesAccessModel> editViewSliderDetails(Model model, @RequestBody DropDownModel id, HttpSession session) {
		logger.info("Method : getAvailableFunctionLists starts");
		
		JsonResponse<ModulesAccessModel> res = new JsonResponse<ModulesAccessModel>();
		
		try {
			res = restClient.getForObject(env.getUserUrl() + "editViewSliderDetails?id="+id.getKey() + "&name=" + id.getName(),
					JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (res.getMessage() != null && res.getMessage() != "") {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		
		logger.info("Method : editViewSliderDetails ends");
		return res;
		
	}
}
