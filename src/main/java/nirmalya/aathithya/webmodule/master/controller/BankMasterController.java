/*
 * package nirmalya.aathithya.webmodule.master.controller;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import javax.servlet.http.HttpSession;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.client.RestClientException; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.fasterxml.jackson.core.type.TypeReference; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * 
 * import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles; import
 * nirmalya.aathithya.webmodule.common.utils.JsonResponse;
 * 
 * import nirmalya.aathithya.webmodule.master.model.MasterBankModel;
 * 
 *//**
	 * @author NirmalyaLabs
	 *
	 *//*
		 * @Controller
		 * 
		 * @RequestMapping(value = { "master/" }) public class BankMasterController {
		 * 
		 * Logger logger = LoggerFactory.getLogger(BankMasterController.class);
		 * 
		 * @Autowired RestTemplate restClient;
		 * 
		 * @Autowired EnvironmentVaribles env;
		 * 
		 * @Autowired BankMasterController bankMasterController;
		 * 
		 * @GetMapping(value = { "email-configuration-bank" }) public String
		 * bankConfiguration(Model model, HttpSession session) {
		 * logger.info("Method :bankConfiguration starts");
		 * 
		 * logger.info("Method : bankConfiguration ends"); return
		 * "master/emailConfiguration"; }
		 * 
		 * @SuppressWarnings("unchecked")
		 * 
		 * @PostMapping("email-configuration-bank-get-total") public @ResponseBody
		 * JsonResponse<List<MasterBankModel>> getTotalBankList(HttpSession session) {
		 * logger.info("Method : getTotalBankList starts");
		 * 
		 * JsonResponse<List<MasterBankModel>> resp = new
		 * JsonResponse<List<MasterBankModel>>();
		 * 
		 * try { resp = restClient.getForObject(env.getMasterUrl() + "getTotalBankList",
		 * JsonResponse.class); } catch(Exception e) { e.printStackTrace(); }
		 * 
		 * if (resp.getMessage() != null && resp.getMessage() != "") {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("success"); }
		 * 
		 * logger.info("Method : getTotalBankList starts"); return resp; } }
		 */