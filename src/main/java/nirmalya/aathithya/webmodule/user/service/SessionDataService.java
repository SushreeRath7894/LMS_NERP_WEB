package nirmalya.aathithya.webmodule.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nirmalya.aathithya.webmodule.user.model.Activity;
import nirmalya.aathithya.webmodule.user.model.Function;
import nirmalya.aathithya.webmodule.user.model.Module;

public class SessionDataService {
	
	static Logger logger = LoggerFactory.getLogger(SessionDataService.class);
	
	@SuppressWarnings("unchecked")
	public static List<String> getActionButtonByActivity(String url, HttpSession session) {
		logger.info("Method : index getActionButtonByActivity starts");
		
		List<String> action_list = new ArrayList<String>();
		
		List<Activity> activityDetails = new ArrayList<Activity>();
		
		try {

			activityDetails = (List<Activity>) session.getAttribute("ACTIVITY_LIST");
			if(activityDetails != null && activityDetails.size() > 0) {
				List<String> name = activityDetails.stream()
		                .filter(activity -> url.equals(activity.getActivity()))
		                .map(a -> a.getActionName())
		                .collect(Collectors.toList());
				if(name.size() > 0 && name.get(0) != null && !name.get(0).equals(null) && !name.get(0).equals("null")) {
					String action = name.get(0);
					action_list = Arrays.asList(action.split(","));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : index getActionButtonByActivity ends");
		return action_list;
	}
}
