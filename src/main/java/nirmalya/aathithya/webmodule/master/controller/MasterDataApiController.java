package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.pipeline.controller.*;
/**
 * @author  Nirmalya labs (Ashish)
 * 
 * Api for all master data
 */

@Service
public class MasterDataApiController {
	
	Logger logger = LoggerFactory.getLogger(MasterDataApiController.class);

	public final RestTemplate restClient;
	public final EnvironmentVaribles env;
	CrmAdminController crmAdminController = new CrmAdminController();
	
	@Autowired
	public MasterDataApiController(RestTemplate restClient, EnvironmentVaribles env) {
		this.restClient = restClient;
		this.env = env;
	}
	
	String org = CrmAdminController.org;
	String orgDiv = CrmAdminController.orgDiv;
	
	public final  List<DropDownModel> getOwnerList(HttpSession session) {
		
		String role = (String) session.getAttribute("IS_SALES_MANAGER");
		String userId = (String) session.getAttribute("USER_ID");
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
//		if (role != null  && role != "") {
//			userId = (String) session.getAttribute("USER_ID");
//		} else {
//			userId = "";
//		}
		System.out.println("userId--bu itish----->>>>>>"+role);
		DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "getOwnerList?userId=" + userId+"&org="+org+"&orgDiv="+orgDiv,
				DropDownModel[].class);
		System.out.println("Owner Lists------->>>>>>"+owner);
		List<DropDownModel> ownerList = Arrays.asList(owner);
		System.out.println("Owner Lists------->>>>>>"+ownerList);
		return ownerList;
	}
	
	public final  List<DropDownModel> getCrmTaskStatus() {
		logger.info("Inside getCrmTaskStatus" + env.getMasterUrl() + "getCrmTaskStatus");
		DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "getCrmTaskStatus",
				DropDownModel[].class);

		List<DropDownModel> ownerList = Arrays.asList(owner);
		return ownerList;
	}
	
}
