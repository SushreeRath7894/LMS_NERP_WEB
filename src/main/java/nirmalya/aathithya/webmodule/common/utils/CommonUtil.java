package nirmalya.aathithya.webmodule.common.utils;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CommonUtil {
	Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Autowired
	RestTemplate restTemplate;

	/**
	 * @param listName it will be used to set the list name which will be used in
	 *                 thymleaf
	 * @url rest api URL
	 * @model Java-5-specific interface that defines a holder for model attributes.
	 *        Primarily designed for adding attributes to the model.
	 * 
	 * @author Deepak, Nirmalyabs
	 */
	public void getDropshownList(String listName, String url, Model model) {
		logger.info("getDropshownList start");
		logger.info("list name :{},rest url :{},", listName, url);
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(url, DropDownModel[].class);
			List<DropDownModel> dropDownList = Arrays.asList(dropDownModel);
			model.addAttribute(listName, dropDownList);
		} catch (RestClientException e) {
			logger.info("Exception while getting dropdown list for {}, error {}", listName, e);
		}
		logger.info("getDropshownList end");
	}

	/**
	 * @param url     rest api URL
	 * @param apiName this will be used only for logger, so that we can find out the
	 *                name of api call
	 * 
	 * @author Deepak, Nirmalyabs
	 */
	@SuppressWarnings("unchecked")
	public JsonResponse<Object> getRestAPIcall(String url, String apiName) {
		JsonResponse<Object> response = new JsonResponse<>();
		logger.info("getRestAPIcall start, api call for : {}", apiName);
		logger.info("rest url :{}", url);
		try {
			response = restTemplate.getForObject(url, JsonResponse.class);
		} catch (RestClientException e) {
			logger.info("RestClientException while calling rest API for : {}, error : {}", apiName, e);
		} catch (Exception e) {
			logger.info("Exception while calling rest API for : {}, error : {}", apiName, e);
		}
		logger.info("getRestAPIcall end");
		return response;
	}

	/**
	 * @param url     rest api URL
	 * @param apiName this will be used only for logger, so that we can find out the
	 *                name of api call
	 * 
	 * @author Deepak, Nirmalyabs
	 */
	@SuppressWarnings("unchecked")
	public JsonResponse<Object> postRestAPIcall(String url, String apiName, Object object) {
		JsonResponse<Object> response = new JsonResponse<>();
		logger.info("postRestAPIcall start, api call for : {}", apiName);
		logger.info("rest url :{}", url);
		try {
			response = restTemplate.postForObject(url, object, JsonResponse.class);
		} catch (RestClientException e) {
			logger.info("Exception while calling rest API for : {}, error : {}", apiName, e);
		} catch (Exception e) {
			logger.info("Exception while calling rest API for : {}, error : {}", apiName, e);
		}
		logger.info("getRestAPIcall end");
		return response;
	}
}
