package nirmalya.aathithya.webmodule.edms.controller;

import java.io.File;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.edms.model.WorkSpaceModel;

@Controller
@RequestMapping(value = "edms")
public class EdmsWorkspaceController {

	Logger logger = LoggerFactory.getLogger(EdmsWorkspaceController.class);

	RestTemplate restClient;

	EnvironmentVaribles env; 
	
	public EdmsWorkspaceController() {
		super();
	}
	
	@Autowired
	public EdmsWorkspaceController(EnvironmentVaribles EnvironmentVaribles,RestTemplate RestTemplate) {
		this.env = EnvironmentVaribles;
		this.restClient=RestTemplate;
	}

	@GetMapping(value = { "work-space" })
	public String workspaceDetails(Model model, HttpSession session) {
		logger.info("Method : workspaceDetails starts");
		String userId="";
		String userName="";
		String orgName="";
		String orgDivision="";
		userId = (String) session.getAttribute("USER_ID");
		userName = (String) session.getAttribute("USER_NAME");
		orgName = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("orgName", orgName);
		model.addAttribute("orgDivision", orgDivision);

		try {
			DropDownModel[] grade = restClient.getForObject(env.getEdms() + "rest-fileaccess-type",
					DropDownModel[].class);
			List<DropDownModel> accessType = Arrays.asList(grade);
			model.addAttribute("accessType", accessType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] grade = restClient.getForObject(env.getEdms() + "rest-fileoperation-type",
					DropDownModel[].class);
			List<DropDownModel> operationType = Arrays.asList(grade);
			model.addAttribute("operationType", operationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] parent = restClient.getForObject(env.getEdms() + "rest-parentFolder",
					DropDownModel[].class);
			List<DropDownModel> parentFolder = Arrays.asList(parent);
			model.addAttribute("parentFolder", parentFolder);
			System.out.println("parentFolder===>>>" + parentFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] emplist = restClient.getForObject(env.getEdms() + "rest-getUserList?orgName="+orgName+"&orgDivision="+orgDivision+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplists", emplists);
			System.out.println("emplists===>>>" + emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		

		logger.info("Method : workspaceDetails ends");
		return "edms/workspace";
	}

//	@SuppressWarnings({ "unchecked" })
//	@PostMapping("work-space-add")
//	public @ResponseBody JsonResponse<Object> saveWorkSpaceModel(@RequestBody WorkSpaceModel eventModel,
//			HttpSession session) {
//
//		logger.info("Method : saveWorkSpaceModel function starts");
//		JsonResponse<Object> resp = new JsonResponse<Object>();
//
//		String userId;
//		String organization="";
//		String orgDivision="";
//		userId = (String) session.getAttribute("USER_ID");
//		try {
//			userId = (String) session.getAttribute("USER_ID");
//			organization = (String) session.getAttribute("ORGANIZATION"); 
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		eventModel.setCreatedBy(userId);
//		eventModel.setOrganizationName(organization);
//		eventModel.setOrganizationDivision(orgDivision);
//
//		eventModel.setCreatedBy(userId);
//		String nFolder = eventModel.getParentFolderName().concat("\\")
//				.concat(eventModel.getNewFolderName());
//		String pFolder = eventModel.getParentFolderName();
//		String path1=env.getFileUploadDms() + nFolder;
//		eventModel.setFolderPath(path1);
//		
//		
//		String parentFolderPath = env.getFileUploadDms() + eventModel.getParentFolderName();
//        String childFolderName = nFolder; // Assuming nFolder is just the name of the child folder
//
//        EdmsWorkspaceController creator = new EdmsWorkspaceController();
//        creator.createFolders(parentFolderPath, childFolderName);
//		
//
//		try {
//			resp = restClient.postForObject(env.getEdms() + "save-workspacemodel", eventModel, JsonResponse.class);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//
//		String message = resp.getMessage();
//		if (message != null && message != "") {
//			resp.setMessage("Unsuccess");
//		} else {
//			resp.setMessage("Success");
//		}
//		logger.info("Method : saveWorkSpaceModel function Ends");
//		return resp;
//	}
	
	/**
	 * 
	 * @param parentFolderPath
	 * @param childFolderName
	 */
	public void createFolders(String parentFolderPath, String childFolderName) {
        // Create File object for the parent folder
        File fileParent = new File(parentFolderPath);
        logger.info("fileParent", fileParent);

        // Check if the parent folder exists; if not, create it
        if (!fileParent.exists()) {
            if (fileParent.mkdirs()) {
                logger.info("Parent folder created: {}", fileParent.getAbsolutePath());
            } else {
                logger.error("Failed to create parent folder: {}", fileParent.getAbsolutePath());
            }
        } else {
            logger.info("Parent folder already exists: {}", fileParent.getAbsolutePath());
        }

        // Create File object for the child folder inside the parent folder
        File fileChild = new File(fileParent, childFolderName);
        logger.info("fileParent", fileParent);
        logger.info("childFolderName", childFolderName);

        // Check if the child folder exists within the parent folder; if not, create it
        if (!fileChild.exists()) {
            if (fileChild.mkdirs()) {
                logger.info("Child folder created: {}", fileChild.getAbsolutePath());
            } else {
            	logger.error("Failed to create child folder: {}", fileChild.getAbsolutePath());
            }
        } else {
        	logger.info("Child folder already exists: {}", fileChild.getAbsolutePath());
        }
    }
	
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-view")
	public @ResponseBody Object workSpaceView(HttpSession session) {

		logger.info("Method :workSpaceView starts");
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

		try {

			resp = restClient.getForObject(env.getEdms() + "rest-workSpace?userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workSpaceView ends");

		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-edit")
	public @ResponseBody Object workSpaceEdit(HttpSession session,@RequestParam String id) {

		logger.info("Method :workSpaceEdit starts");
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

		try {

			resp = restClient.getForObject(env.getEdms() + "rest-workSpace-edit?workSpaceId="+id+"&userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workSpaceEdit ends");

		return resp;
	}
	 public static void deleteDirectory(File file) {
	        if (file.isDirectory()) {
	            File[] entries = file.listFiles();
	            if (entries != null) {
	                for (File entry : entries) {
	                    deleteDirectory(entry);
	                }
	            }
	        }

	        // Delete the directory or file
	        if (file.delete()) {
	            System.out.println("Deleted: " + file.getAbsolutePath());
	        } else {
	            System.out.println("Failed to delete: " + file.getAbsolutePath());
	        }
	    }
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-delete")
	public @ResponseBody JsonResponse<Object> deleteWorkSpace(@RequestParam String id,@RequestParam String parentFolder,@RequestParam String newFolder , HttpSession session) {

		logger.info("Method : deleteWorkSpace starts"+id);

		JsonResponse<Object> response = new JsonResponse<Object>();
		
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
		
		String deleteFolder=env.getFileUploadDms()+parentFolder+"\\"+newFolder;
		System.out.println("folder"+deleteFolder);
		 File folder = new File(deleteFolder);
		 deleteDirectory(folder);
	    
		try {
			response = restClient.getForObject(env.getEdms() + "rest-deleteworksapce?id=" +id+"&userId="+userId +"&orgName="+orgName +"&orgDivision="+orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : deleteWorkSpace ends");
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "work-space-add-new")
	public @ResponseBody JsonResponse<Object> addNewWorkSpace(@RequestBody Map<String, Object> itm) {
		// Process the received JSON data here (e.g., save it to the database)
		// For simplicity, I'll just print the received data here.
		logger.info("Method : addNewWorkSpace function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String workSpaceId = (String) itm.get("workspaceId");
		
		String nFolder = itm.get("newFolderName").toString();
		
		String parentFolderPath = env.getFileUploadDms() + (String) itm.get("parentFolderName").toString();
        String childFolderName = nFolder; // Assuming nFolder is just the name of the child folder

        logger.info("parentFolderPath"+parentFolderPath);
        logger.info("childFolderName"+childFolderName);
        EdmsWorkspaceController creator = new EdmsWorkspaceController();
        creator.createFolders(parentFolderPath, childFolderName);
		// Convert the received JSON data to a JSON string

		// Rest api call
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-getNew-workspace-add?workSpaceId=" + workSpaceId, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addNewWorkSpace function starts");
		return resp;
	}

	
	// auto search user name
	@SuppressWarnings("unchecked")
	@GetMapping("work-space-userAutoSearch")
	public @ResponseBody JsonResponse<Object> getUserAutoSearch(Model model, @RequestParam String searchValue,
			@RequestParam String type,HttpServletRequest request, HttpSession session) {
		logger.info("Method : getUserAutoSearch starts  " + searchValue);
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getEdms() + "rest-userAutoSearch?id=" + searchValue
					+"&type="+type,JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getUserAutoSearch ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "work-space-autosearchUser" })
	public @ResponseBody JsonResponse<DropDownModel> getAutosearchUser(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : getAutosearchUser starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {

			String userId = "";
			String orgName = "";
			String orgDiv = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
			res = restClient.getForObject(
					env.getEdms() + "rest-getAutosearchUser?id=" + searchValue + "&userId=" + userId + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAutosearchUser ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "work-space-autosearchGroup" })
	public @ResponseBody JsonResponse<DropDownModel> getAutosearchGroup(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : getAutosearchGroup starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {

			String userId = "";

			try {
				userId = (String) session.getAttribute("USER_ID");

			} catch (Exception e) {
				e.printStackTrace();
			}
			res = restClient.getForObject(
					env.getEdms() + "rest-getAutosearchGroup?id=" + searchValue + "&userId=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAutosearchGroup ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}
}
