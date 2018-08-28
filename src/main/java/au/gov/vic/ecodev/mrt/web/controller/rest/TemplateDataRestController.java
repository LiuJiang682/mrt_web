package au.gov.vic.ecodev.mrt.web.controller.rest;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.gov.vic.ecodev.mrt.rest.service.template.TemplateDataServices;


@RestController
public class TemplateDataRestController {

	private static final Logger LOGGER = Logger.getLogger(TemplateDataRestController.class);
	
	@Autowired
	private TemplateDataServices templateDataServices;
	
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping("/template/{batchId}")
	public Map<String, Map<String, List<Map<String, Object>>>> getAllTemplateData(@PathVariable long batchId) throws Exception {
		LOGGER.info("TemplateDataRestController.getAllTemplateData -- batchId: " + batchId);
		return templateDataServices.getAllTemplateData(batchId);
	}
	
	@RequestMapping("/template/{templateName}/{batchId}")
	public List<String> getSingleTemplateData(@PathVariable String templateName, 
			@PathVariable String batchId) {
		LOGGER.info("TemplateDataRestController.getSingleTemplateData -- templateName: " 
			+ templateName + ", batchId: " + batchId);
		return templateDataServices.getSingleTemplateData(templateName, batchId);
	}
}
