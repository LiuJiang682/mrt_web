package au.gov.vic.ecodev.mrt.rest.service.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateClassesListHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesPopulator;

@Service
public class TemplateDataServicesImpl implements TemplateDataServices {

	private static final Logger LOGGER = Logger.getLogger(TemplateDataServicesImpl.class);
	
	@Autowired
	private SessionHeaderDao sessionHeaderDao;
	@Autowired
	private TemplateDisplayPropertiesHelper templateDisplayPropertiesHelper;
	@Autowired
	private TemplateClassesListHelper templateClassesListHelper;
	@Autowired
	private TemplateDisplayPropertiesPopulator templateDisplayPropertiesPopulator;
			
	@Override
	public Map<String, List<Map<String, Object>>> getAllTemplateData(String batchId) throws Exception {
		LOGGER.info("TemplateDataServicesImpl.getAllTemplateData -- batchId: " + batchId);
		SessionHeader sessionHeader = (SessionHeader) sessionHeaderDao
				.get(Long.parseLong(batchId));
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		if (null != sessionHeader) {
			String templates = sessionHeader.getTemplate().toUpperCase();
			List<String> templateList = extractUniqueTemplate(templates);
			for(String template : templateList) {
				Map<String, Object> templateFieldMap = templateDisplayPropertiesHelper
						.getTemplateDisplayProperties(template);
				List<String> classesList = templateClassesListHelper
						.getTemplateClassesList(template);
				templateDisplayPropertiesPopulator
					.doPopulation(resultMap, classesList, templateFieldMap, Long.parseLong(batchId));
			}
		}
		return resultMap;
	}

	protected final List<String> extractUniqueTemplate(String templates) {
		if (StringUtils.isEmpty(templates)) {
			throw new IllegalArgumentException("TemplateDataServicesImpl.extractUniqueTemplate -- templates parameter cannot be null!");
		}
		String[] templateArray = templates.split(Constants.Strings.COMMA);
		String[] distinctTemplates = Arrays.stream(templateArray)
				.distinct()
				.toArray(String[]::new);
		return Arrays.asList(distinctTemplates);
	}

	@Override
	public List<String> getSingleTemplateData(String templateName, String batchId) {
		LOGGER.info("TemplateDataServicesImpl.getSingleTemplateData -- templateName: " 
				+ templateName + ", batchId: " + batchId);
		return new ArrayList<>();
	}

}
