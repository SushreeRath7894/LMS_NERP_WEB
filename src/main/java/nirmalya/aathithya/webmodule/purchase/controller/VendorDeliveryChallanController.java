package nirmalya.aathithya.webmodule.purchase.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.ActivitylogModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorDeliveryChallanModel;

/*
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "purchase/")
public class VendorDeliveryChallanController {
	Logger logger = LoggerFactory.getLogger(VendorDeliveryChallanController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("vendor-delivery-challan")
	public String generateDeliveryChallan(Model model, HttpSession session) {

		logger.info("Method : generatePo starts");

		/**
		 * get DropDown value for Requisition Type
		 *
		 */
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getPurchaseUrl() + "getCarrierList",
					DropDownModel[].class);
			List<DropDownModel> CarrierLists = Arrays.asList(dropDownModel);
			model.addAttribute("CarrierLists", CarrierLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : generatePo ends");
		return "purchase/vendor-deliveryChallan";

	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("vendor-delivery-challan-add")
	public @ResponseBody JsonResponse<Object> addvendorDeliveryChallan(HttpSession session,
			@RequestBody List<VendorDeliveryChallanModel> vendorDeliveryChallanModel) {
		logger.info("Method : addvendorDeliveryChallan starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<VendorDeliveryChallanModel> documentList = new ArrayList<VendorDeliveryChallanModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		for (VendorDeliveryChallanModel a : vendorDeliveryChallanModel) {
			if (a.getEbillDate() != null && a.getEbillDate() != "") {
				a.setEbillDate(DateFormatter.inputDateFormat(a.getEbillDate(), dateFormat));
			}
		}

		for (VendorDeliveryChallanModel m : vendorDeliveryChallanModel) {
			m.setQutCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		
		for (InventoryVendorDocumentModel b : vendorDeliveryChallanModel.get(0).getDocumentList()) {

			if (b.getImageNameEdit() != null && b.getImageNameEdit() != "") {
				b.setFileName(b.getImageNameEdit());
			} else {
				if (b.getFileName() != null && b.getFileName() != "") {
					String[] extension = b.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : b.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension[lastindex]);
							b.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}
		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addvendorDeliveryChallan",
					vendorDeliveryChallanModel, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<VendorDeliveryChallanModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<VendorDeliveryChallanModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addvendorDeliveryChallan ends");

		return resp;
	}
	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}
	/*
	 * view
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("vendor-delivery-challan-view-ajax")
	public @ResponseBody List<VendorDeliveryChallanModel> viewvendordeliveryChallan(HttpSession session) {

		logger.info("Method :viewvendordeliveryChallan starts");
		JsonResponse<List<VendorDeliveryChallanModel>> resp = new JsonResponse<List<VendorDeliveryChallanModel>>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-viewvendordeliveryChallan?org=" + orgName
					+ "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<VendorDeliveryChallanModel> VendorDeliveryChallanModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<VendorDeliveryChallanModel>>() {
				});

		resp.setBody(VendorDeliveryChallanModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewvendordeliveryChallan ends");

		return resp.getBody();
	}

	/*
	 * edit Delivery Challan
	 */

	@GetMapping(value = { "vendor-delivery-challan-edit-new" })
	public @ResponseBody List<VendorDeliveryChallanModel> viewDeliveryChallanEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewDeliveryChallanEdit starts");

		List<VendorDeliveryChallanModel> productList = new ArrayList<VendorDeliveryChallanModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

	//	List<VendorDeliveryChallanModel> productList = new ArrayList<VendorDeliveryChallanModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {

			try {
				VendorDeliveryChallanModel[] vendorDeliveryChallanModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewDeliveryChallanEdit?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
						VendorDeliveryChallanModel[].class);

				productList = Arrays.asList(vendorDeliveryChallanModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (VendorDeliveryChallanModel m : vendorDeliveryChallanModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

				}
				
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewDeliveryChallanEdit ends"+productList);

		return productList;
	}

	/*
	 * delete
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("vendor-delivery-challan-delete")
	public @ResponseBody JsonResponse<Object> deleteDeliveryChallan(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDeliveryChallan function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "deleteDeliveryChallan?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDeliveryChallan function Ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-deliverychallan-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveDeliverychallan(HttpSession session,
			@RequestParam String approveStatus, String vendorDeliveryChallan) {

		logger.info("Method : approveDeliverychallan starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
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
		logger.info("orgName" + orgName);
		logger.info("orgDivision" + orgDivision);
		try {
			response = restTemplate.getForObject(env.getPurchaseUrl() + "approveDeliverychallan?approveStatus="
					+ approveStatus + "&vendorDeliveryChallan=" + vendorDeliveryChallan + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision +  "&userId=" + userId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		logger.info("response=====" + response);
		logger.info("Method : approveDeliverychallan ends");
		return response;
	}
	@PostMapping("/view-deliverychallan-upload-fileproduct")
	public @ResponseBody JsonResponse<Object> uploadProductFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadProductFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("productPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadProductFile controller ' ends");
		return response;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}


}
