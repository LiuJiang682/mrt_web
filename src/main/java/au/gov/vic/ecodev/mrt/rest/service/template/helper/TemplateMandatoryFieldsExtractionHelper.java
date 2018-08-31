package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateMandatoryFieldsExtractionHelper {
	
	private static final String COLUMN_ROW_NUMBER = "ROW_NUMBER";
	
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateMandatoryFieldsExtractionHelper(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateMandatoryFieldsExtractionHelper -- jdbcTemplate param cannot null be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void extractMandatoryFields(Map<String, List<Map<String, Object>>> resultMap,
			Map<String, Object> templateFieldMap, long batchId, String cls) {
		Object object = templateFieldMap.get(cls);
		if (object instanceof List) {
			@SuppressWarnings({ "unchecked" })
			List<LinkedHashMap<String, String>> templateClassesList = (List<LinkedHashMap<String, String>>) object;
			templateClassesList.stream().forEach(classAndFields -> {
				doSingleTemplateDataExtraction(resultMap, batchId, cls, classAndFields);
			});
		}
	}

	protected final void doSingleTemplateDataExtraction(Map<String, List<Map<String, Object>>> resultMap, long batchId,
			String cls, LinkedHashMap<String, String> classAndFields) {
		classAndFields.entrySet().stream().forEach(element -> {
			doSingleElementValueExtraction(resultMap, batchId, cls, element);
		});
	}

	protected final void doSingleElementValueExtraction(Map<String, List<Map<String, Object>>> resultMap, long batchId,
			String cls, Entry<String, String> element) {
		String fieldString = element.getValue();
		TemplateDisplayPropertiesJdbcTemplateHelper templateDisplayPropertiesJdbcTemplateHelper = 
				new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate); 
		List<Map<String, Object>> results = templateDisplayPropertiesJdbcTemplateHelper
				.getList(element.getKey(), fieldString, batchId);
		results.stream().forEach(result -> {
			doValuePopulation(resultMap, cls, result);
		});
	}

	protected final void doValuePopulation(Map<String, List<Map<String, Object>>> resultMap, String cls,
			Map<String, Object> result) {
		String fileName = (String) result.get(Strings.FILE_NAME);
		String rowNumber = (String) result.get(COLUMN_ROW_NUMBER);
		String currentKey = new StringBuilder(cls)
				.append(Strings.UNDER_LINE)
				.append(fileName)
				.append(Strings.UNDER_LINE_DATA_KEY)
				.append(rowNumber)
				.toString();
		List<Map<String, Object>> dataRecord = resultMap.get(currentKey);
		if (null == dataRecord) {
			dataRecord = new ArrayList<>();
			dataRecord.add(result);
		} else {
			Map<String, Object> datas = dataRecord.get(Numeral.ZERO);
			result.forEach((key, value) -> {
				datas.put(key, value);
			});
		}
		resultMap.put(currentKey, dataRecord);
	}
}
