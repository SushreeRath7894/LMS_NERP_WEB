package nirmalya.aathithya.webmodule.pipeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private EnvironmentVaribles env;

    //  Get All Admin Notifications Service - Addded By Pankaj Kumar	
    public JsonResponse<Object> getAdminNotification(String userId, String org, String orgDiv) {
        JsonResponse<Object> resp = new JsonResponse<>();

        try {
            resp = restTemplate.getForObject(
                    env.getPipeline() + "admin-notification?org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
                    JsonResponse.class);
        } catch (Exception e) {
            resp.setMessage(e.getMessage());
            resp.setCode("Failed");
            e.printStackTrace();
        }

        return resp;
    }
    
    // Admin Notification Escalation Service - Addded By Pankaj Kumar	
    public JsonResponse<Object> escalateNotificationService(String ids) {
        JsonResponse<Object> resp = new JsonResponse<>();

        try {
            resp = restTemplate.getForObject(
                    env.getPipeline() + "admin-notification-escalate?ids=" + ids ,
                    JsonResponse.class);
        } catch (Exception e) {
            resp.setMessage(e.getMessage());
            resp.setCode("Failed");
            e.printStackTrace();
        }

        return resp;
    }
    
    // Get All Notifications WIthout Logins
    public JsonResponse<Object> getAllAdminNotificationData(){
    	JsonResponse<Object> resp = new JsonResponse<>();
    	
    	try {
    		
    		resp = restTemplate.getForObject(env.getPipeline() + "getAllAdminNotifications", JsonResponse.class);
    		
    	} catch (Exception e) {
    		resp.setMessage(e.getMessage());
            resp.setCode("Failed");
			e.printStackTrace();
		}
    	return resp;
    }
    
    // Lead Activity Reminder 
    public JsonResponse<Object> getLeadReminderData(){
    	JsonResponse<Object> resp = new JsonResponse<>();
    	
    	try {
    		
    		resp = restTemplate.getForObject(env.getPipeline() + "getLeadReminderData", JsonResponse.class);
    		
    	} catch (Exception e) {
    		resp.setMessage(e.getMessage());
            resp.setCode("Failed");
			e.printStackTrace();
		}
    	return resp;
    }
}
