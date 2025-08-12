package nirmalya.aathithya.webmodule.edms.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import java.util.stream.Collectors;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "edms")
public class EdmsWorkspaceNewController {
	Logger logger = LoggerFactory.getLogger(EdmsWorkspaceNewController.class);

	RestTemplate restClient;
	EnvironmentVaribles env;

	@Autowired
	public EdmsWorkspaceNewController(EnvironmentVaribles EnvironmentVaribles, RestTemplate RestTemplate) {
		this.env = EnvironmentVaribles;
		this.restClient = RestTemplate;
	}
	
	
	
	@GetMapping(value = { "workspace-new" })
	public String workspaceControl(HttpSession session, Model model) {
		logger.info("Method : documentControl starts");
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
	
		logger.info("Method : workspaceControl ends");
		return "edms/workSpaceNew";
	}
	
	
	//Get Folder Details
	@SuppressWarnings("unchecked")
	@GetMapping("workspace-new-folderDetails")
	public @ResponseBody JsonResponse<Object> getFolderDetails(HttpSession session) {
		logger.info("Method :getFolderDetails starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-getFolderDetails?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getFolderDetails ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "workspace-new-save")
	public @ResponseBody JsonResponse<Object> addNewWorkSpace(@RequestBody Map<String, Object> itm) {
		
		logger.info("Method : addNewWorkSpace function starts"+itm);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String workSpaceId = (String) itm.get("workspaceId");
		
		String nFolder = itm.get("newFolderName").toString();
		String parentId = itm.get("parentId").toString();
		
		//String parentFolderPath = env.getFileUploadDms() + (String) itm.get("parentFolderName").toString();
        String childFolderName = nFolder; // Assuming nFolder is just the name of the child folder
      
       // List<String> currentPathDetails = (List<String>) itm.get("currentPathDetails");
        String currentPathDetails = (String) itm.get("currentPathDetails");
        String folderPath = (String) itm.get("parentFolderPath");
        String folder="";
        String parentFolderPath="";
        String parentFolder="";
		if (currentPathDetails == null || currentPathDetails.isEmpty()) {
		    folder=nFolder;
		    parentFolderPath=env.getFileUploadDms()+String.join("/", "");
		} else {
			System.out.println("folderPath"+folderPath);
		     parentFolderPath = env.getFileUploadDms()+String.join("/", folderPath);
		      parentFolder = String.join("/", currentPathDetails);
		     System.out.println("parentFolder"+parentFolder);
		}
        
        //System.out.println("Result"+parentFolderPath);
       
        EdmsWorkspaceController creator = new EdmsWorkspaceController();
        creator.createFolders(parentFolderPath, childFolderName);

		// Rest api call
		try {
			resp = restClient.postForObject(env.getEdms() + "rest-addWorkspaceNew?workSpaceId=" + workSpaceId +"&parentFolder="+parentFolder+"&parentId="+parentId, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addNewWorkSpace function starts"+resp);
		return resp;
	}
	
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("workspace-new-parentFolder")
	public @ResponseBody JsonResponse<Object> getParentFolder(HttpSession session,@RequestParam String path) {
		logger.info("Method :getFolderDetails starts");
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

			resp = restClient.getForObject(env.getEdms() + "rest-getParentFolder?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision +"&path="+path, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getParentFolder ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("workspace-new-getAccesDetails")
	public @ResponseBody Object getAccessDetails(HttpSession session, @RequestParam String id) {

		logger.info("Method :getAccessDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getEdms() + "rest-getAccesDetails?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getAccessDetails ends" + resp);
		return resp;
	}
}
