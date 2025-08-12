package nirmalya.aathithya.webmodule.patient.controller;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "patient")
public class PatientSelfServiceController {

    Logger logger = LoggerFactory.getLogger(PatientSelfServiceController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate restClient;

    @Autowired
    EnvironmentVaribles env;

    @GetMapping("/dashboard")
    public String patientDashboard() {
        logger.info("Start of method : patientDashboard");
        logger.info("End of method : patientDashboard");
        return "patient/patientSelfService/dashboard";
    }

    @GetMapping("/package")
    public String patientPackage() {
        logger.info("Start of method : patientPackage");
        logger.info("End of method : patientPackage");
        return "patient/patientSelfService/package";
    }

    @GetMapping("/my-care")
    public String patientMyCare() {
        logger.info("Start of method : patientMyCare");
        logger.info("End of method : patientMyCare");
        return "patient/patientSelfService/myCare";
    }

}