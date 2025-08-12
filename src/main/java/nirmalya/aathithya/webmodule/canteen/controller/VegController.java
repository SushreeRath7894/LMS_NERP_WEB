package nirmalya.aathithya.webmodule.canteen.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;



@Controller
@RequestMapping(value = "canteen")
public class VegController {
	Logger logger = LoggerFactory.getLogger(VegController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/veg")
	public String viewIncentive(Model model, HttpSession session) {



	logger.info("Method : Manage-incentivesDetails starts");
	

	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getIncentive",
				DropDownModel[].class);

		List<DropDownModel> incentivedetails = Arrays.asList(orderStatus);
		System.out.println("incentivedetails" + incentivedetails);
		model.addAttribute("incentivedetails", incentivedetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//for club member drop down list
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getClubMembers",
				DropDownModel[].class);

		List<DropDownModel> clubMemberDetails = Arrays.asList(orderStatus);
		System.out.println("clubMemberDetails" + clubMemberDetails);
		model.addAttribute("clubMemberDetails", clubMemberDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		DropDownModel[] orderStatus = restClient.getForObject(env.getcanteenUrl() + "/getvariants",
				DropDownModel[].class);

		List<DropDownModel> variantDetails = Arrays.asList(orderStatus);
		System.out.println("variantDetails" + variantDetails);
		model.addAttribute("variantDetails", variantDetails);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 
	logger.info("Method : Manage-incentivesDetails ends");
	return "canteen/veg";

}

	
}
