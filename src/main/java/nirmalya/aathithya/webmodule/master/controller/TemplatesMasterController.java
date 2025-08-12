package nirmalya.aathithya.webmodule.master.controller;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.TemplatesMasterModel;

/**
 *
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "master/" })
public class TemplatesMasterController {

	Logger logger = LoggerFactory.getLogger(TemplatesMasterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "view-templates" })
	public String emailConfiguration(Model model, HttpSession session) {
		logger.info("Method : emailConfiguration starts");

		try {
			TemplatesMasterModel[] temp = restClient.getForObject(env.getMasterUrl() + "getTemplatesList",
					TemplatesMasterModel[].class);
			List<TemplatesMasterModel> tempList = Arrays.asList(temp);

			if (tempList != null) {
				for (TemplatesMasterModel m : tempList) {
					if (m.getFileName() != null && m.getFileName() != "") {
						String[] extension = m.getFileName().split("\\.");
						if (extension.length == 2) {
							if (extension[1].equals("html")) {
								String docPath = "<i class=\"fa fa-file\" title=\"" + m.getFileName() + "\"></i>";
								m.setAction(docPath);
							}
						} else {
							m.setAction("N/A");
						}
					} else {
						m.setAction("N/A");
					}
					m.setAction("<a href=\"/document/module/" + m.getFileName() + "\" target=\"_blank\">"
							+ m.getAction() + "</a>");
				}
			}

			model.addAttribute("tempList", tempList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : emailConfiguration ends");
		return "master/viewTemplates";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "view-templates-save-th-ajax")
	public @ResponseBody JsonResponse<Object> saveReportDocument(@RequestBody TemplatesMasterModel tempDtls,
			HttpSession session, Model model) {
		logger.info("Method : saveReportDocument function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		TemplatesMasterModel docList = new TemplatesMasterModel();

		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		tempDtls.setCreatedBy(userId);
		if (tempDtls.getFileName() != null && tempDtls.getFileName() != "") {
			String delimiters = "\\.";
			String[] x = tempDtls.getFileName().split(delimiters);

			if (x[1].contentEquals("html") || x[1].contentEquals("htm")) {
				for (String s1 : tempDtls.getHtmlFile()) {
					try {
						byte[] bytes = Base64.getDecoder().decode(s1);
						String htmlName = saveAllHtml(bytes, tempDtls.getTempId());
						tempDtls.setFileName(htmlName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			res = restClient.postForObject(env.getMasterUrl() + "saveReportDocument", tempDtls, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		docList = mapper.convertValue(res.getBody(), new TypeReference<TemplatesMasterModel>() {
		});
		if (docList != null) {
			if (docList.getFileName() != null && docList.getFileName() != "") {
				String[] extension = docList.getFileName().split("\\.");
				if (extension.length == 2) {
					if (extension[1].equals("html")) {
						String docPath = "<i class=\"fa fa-file\" title=\"" + docList.getFileName() + "\"></i>";
						docList.setAction(docPath);
					}
				} else {
					docList.setAction("N/A");
				}
			} else {
				docList.setAction("N/A");
			}
			docList.setAction("<a href=\"/document/module/" + docList.getFileName() + "\" target=\"_blank\">"
					+ docList.getAction() + "</a>");
		}
		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		res.setBody(docList);

		logger.info("Method : saveReportDocument function Ends");
		return res;
	}

	public String saveAllHtml(byte[] imageBytes, String name) {
		logger.info("Method : saveAllHtml starts");

		String htmlFileName = null;

		try {
			if (imageBytes != null) {
//				long nowTime = new Date().getTime();
				name = name.replaceAll(" ", "");
				htmlFileName = name + ".html";
			}

			Path path = Paths.get(env.getFileUploadMaster() + htmlFileName);
			try {
				Files.deleteIfExists(Paths.get(env.getFileUploadMaster() + htmlFileName));
			} catch (NoSuchFileException e) {
				logger.info("No such file/directory exists");
			} catch (DirectoryNotEmptyException e) {
				logger.info("Directory is not empty.");
			} catch (IOException e) {
				logger.info("Invalid permissions.");
			}
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllHtml ends");
		return htmlFileName;
	}
}
