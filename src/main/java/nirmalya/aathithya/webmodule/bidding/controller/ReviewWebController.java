/*
 * package nirmalya.aathithya.webmodule.bidding.controller;
 * 
 * import javax.servlet.http.HttpSession;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles; import
 * nirmalya.aathithya.webmodule.projects.controller.CloseOutWebController;
 * 
 * @SuppressWarnings("unused")
 * 
 * @Controller
 * 
 * @RequestMapping(value = "bidding/") public class ReviewWebController {
 * 
 * Logger logger = LoggerFactory.getLogger(CloseOutWebController.class);
 * 
 * @Autowired RestTemplate restClient;
 * 
 * @Autowired EnvironmentVaribles env;
 * 
 * @GetMapping("/review-mstr") public String closeOut(Model model, HttpSession
 * session) {
 * 
 * logger.info("Method : review starts");
 * 
 * logger.info("Method : review ends");
 * 
 * return "bidding/review-mstr"; }
 * 
 * }
 */