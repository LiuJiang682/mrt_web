package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateMandatoryHeaderFieldsExtractionHelper {

	private static final Object ROW_NUMBER = "ROW_NUMBER";
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateMandatoryHeaderFieldsExtractionHelper(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateMandatoryHeaderFieldsExtractionHelper:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void extractTemplateHeaderFields(Map<String, List<Map<String, Object>>> resultMap,
			 long batchId, String templateName) {
		TemplateMandatoryHeadersJdbcTemplateHelper templateMandatoryHeadersJdbcTemplateHelper =
				new TemplateMandatoryHeadersJdbcTemplateHelper(jdbcTemplate);
		List<Map<String, Object>> results = templateMandatoryHeadersJdbcTemplateHelper
				.getList(templateName, batchId);
		results.stream().forEach(result -> {
			doValuePopulation(resultMap, result, templateName);
		});
	}

	protected final void doValuePopulation(Map<String, List<Map<String, Object>>> resultMap, 
			Map<String, Object> result, final String templateName) {
		String fileName = (String) result.get(Strings.FILE_NAME);
		String rowNumber = (String) result.get(ROW_NUMBER);
		String columnHeader = (String) result.get("COLUMN_HEADER");
		String fieldValue = (String) result.get("FIELD_VALUE");
		String currentKey = new StringBuilder(templateName)
				.append(Strings.UNDER_LINE)
				.append(fileName)
				.append(Strings.UNDER_LINE)
				.append(rowNumber)
				.toString();
		List<Map<String, Object>> dataRecord = resultMap.get(currentKey);
		if (null == dataRecord) {
			dataRecord = new ArrayList<>();
			Map<String, Object> headersMap = new HashMap<>();
			headersMap.put(columnHeader, fieldValue);
			dataRecord.add(headersMap);
		} else {
			Map<String, Object> datas = dataRecord.get(Numeral.ZERO);
			datas.put(columnHeader, fieldValue);
		}
		resultMap.put(currentKey, dataRecord);
	}

}
