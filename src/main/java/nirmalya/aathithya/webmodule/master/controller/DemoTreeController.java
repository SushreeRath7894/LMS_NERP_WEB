package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.DemoTreeModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class DemoTreeController {

	Logger logger = LoggerFactory.getLogger(DemoTreeController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "demo-tree" })
	public String demoCategory(Model model, HttpSession session) {
		logger.info("Method : demoCategory starts");

		logger.info("Method : demoCategory ends");
		return "master/demo-tree";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("demo-tree-save")
	public @ResponseBody JsonResponse<Object> saveDemoCategory(@RequestBody DemoTreeModel category, HttpSession session) {
		logger.info("Method : saveDemoCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		//logger.info("DemoTreeModel" + category);
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		category.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveDemoCategory", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		//logger.info(resp);
		logger.info("Method : saveDemoCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("demo-tree-get-total-list")
	public @ResponseBody JsonResponse<List<DemoTreeModel>> getAllDemoCategoryList(HttpSession session) {
		logger.info("Method : getAllDemoCategoryList starts");
		
		JsonResponse<List<DemoTreeModel>> resp = new JsonResponse<List<DemoTreeModel>>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "getAllDemoCategoryList",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllDemoCategoryList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("demo-tree-save-subdemo")
	public @ResponseBody JsonResponse<Object> saveDemoSubCategory(@RequestBody DemoTreeModel category, HttpSession session) {
		logger.info("Method : saveDemoSubCategory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		//logger.info("DemoTreeModel" + category);
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		category.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getMasterUrl() + "saveDemoSubCategory", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		//logger.info(resp);
		logger.info("Method : saveDemoSubCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("demo-tree-get-category-dtls-by-id")
	public @ResponseBody JsonResponse<DemoTreeModel> editDemoCategoryById(@RequestBody String id,HttpSession session) {
		logger.info("Method : editDemoCategoryById starts");
		
		JsonResponse<DemoTreeModel> resp = new JsonResponse<DemoTreeModel>();
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "editDemoCategoryById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : editDemoCategoryById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("demo-tree-delete")
	public @ResponseBody JsonResponse<Object> deleteDemoCategory(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		logger.info(id);
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getMasterUrl() + "deleteDemoCategory?id="+id+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : deleteDemoCategory starts");
		return resp;
	}
	
	
}
