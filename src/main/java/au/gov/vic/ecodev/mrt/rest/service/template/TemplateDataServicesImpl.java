package au.gov.vic.ecodev.mrt.rest.service.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateClassesListHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesHelper;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.TemplateDisplayPropertiesPopulator;

@Service
public class TemplateDataServicesImpl implements TemplateDataServices {

	private static final Logger LOGGER = Logger.getLogger(TemplateDataServicesImpl.class);
	
	private static final Pattern DATA_RECORD_HEADER_PATTERN = Pattern.compile("^D\\d{1,}$");
	
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
		Map<String, List<Map<String, Object>>> resultMap = this.retrieveDisplayData(batchId);
		return groupRecordsByTemplate(resultMap);
	}

	protected final Map<String, List<Map<String, Object>>> groupRecordsByTemplate(
			Map<String, List<Map<String, Object>>> resultMap) {
		Map<String, List<Map<String, Object>>> groupedMap = new HashMap<>();
		List<String> templates = new ArrayList<>();
		Map<String, Map<String, Map<String, Object>>> cache = new HashMap<>();
		LOGGER.info(resultMap);
		resultMap.forEach((k, v) -> {
			LOGGER.info("k: " + k);
			String[] templateAndRows = k.split(Strings.UNDER_LINE);
			String template = templateAndRows[Numeral.ZERO];
			if (Numeral.ONE == templateAndRows.length) {
				groupedMap.put(k, v);
			} else {
				String rowLabel = templateAndRows[Numeral.ONE];
				Map<String, Map<String, Object>> rows;
				if (templates.contains(template)) {
					rows = cache.get(template);
				} else {
					rows = new HashMap<>();
					templates.add(template);
					cache.put(template, rows);
				}
				LOGGER.info(v);
				rows.put(rowLabel, v.get(Numeral.ZERO));
			}
		});
		
		cache.forEach((k, v) -> {
			LOGGER.info(k + " " + v);
			List<Map<String, Object>> dataList = new ArrayList<>();
			Map<String, Object> headers = new HashMap<>();
			TreeMap<String, Map<String, Object>> dataMap = new TreeMap<>();
			TreeMap<String, Map<String, Object>> headersMap = new TreeMap<>();
			v.forEach((vk, vv) -> {
				if (Strings.HEADERS.equalsIgnoreCase(vk)) {
					headers.putAll(vv);
				} else if (DATA_RECORD_HEADER_PATTERN.matcher(vk).matches()) {
					dataMap.put(vk, vv);
				} else {
					headersMap.put(vk, vv);
				}
			});
			LOGGER.info(dataMap);
			dataList.add(headers);
			dataList.addAll(headersMap.values());
			dataList.addAll(dataMap.values());
			groupedMap.put(k, dataList);
		});
		LOGGER.info(groupedMap);
		return groupedMap;
	}
	
	protected final Map<String, List<Map<String, Object>>> retrieveDisplayData(String batchId) throws Exception {
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
		String[] templateArray = templates.split(Strings.COMMA);
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
