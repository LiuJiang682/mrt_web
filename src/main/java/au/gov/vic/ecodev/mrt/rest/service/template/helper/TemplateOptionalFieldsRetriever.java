package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateOptionalFieldsRetriever {

	private static final Logger LOGGER = Logger.getLogger(TemplateOptionalFieldsRetriever.class);
	
	private static final String FIELD_VALUE = "FIELD_VALUE";
	private static final String TEMPLATE_HEADER = "TEMPLATE_HEADER";
	private static final String ROW_NUMBER = "ROW_NUMBER";
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateOptionalFieldsRetriever(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateOptionalFieldsRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void extractOptionalFields(final Map<String, List<Map<String, Object>>> resultMap, 
			final Map<String, Object> templateHeadersMap, final int mandatoryColumnCount,
			final long batchId, final String template) {
		Map<String, Boolean> headers = extractHeaderList(templateHeadersMap, template);
		int headerLen = headers.size();
		doHeaderDataExtraction(resultMap, mandatoryColumnCount, batchId, template, headers);
		List<Map<String, Object>> optionalFields = 
				new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
				.getList(template, batchId, headerLen);
		
		optionalFields.stream()
			.forEach(optionalField -> {
				LOGGER.info(optionalField);
				String rowNumber = (String) optionalField.get(ROW_NUMBER);
				if (!Strings.ZERO.equalsIgnoreCase(rowNumber)) {
					int row = Integer.parseInt(rowNumber);
					if (headerLen < row) {
						row -= headerLen;
						rowNumber = String.valueOf(row);
					}
					String header = (String) optionalField.get(TEMPLATE_HEADER);
					String value = (String) optionalField.get(FIELD_VALUE);
					String fileName = (String) optionalField.get(Strings.FILE_NAME);
					String key = new StringBuilder(template)
							.append(Strings.UNDER_LINE)
							.append(fileName)
							.append(Strings.UNDER_LINE_DATA_KEY)
							.append(rowNumber)
							.toString();
					List<Map<String, Object>> dataRecord = resultMap.get(key);
					if (null == dataRecord) {
						dataRecord = new ArrayList<>();
						Map<String, Object> dataMap = new HashMap<>();
						dataMap.put(header, value);
						dataRecord.add(dataMap);
					} else {
						Map<String, Object> dataMap = dataRecord.get(Numeral.ZERO);
						dataMap.put(header, value);
					}
					resultMap.put(key, dataRecord);
				}
			});
		
	}

	protected final void doHeaderDataExtraction(
			Map<String, List<Map<String, Object>>> resultMap, 
			final int mandatoryColumnCount,
			final long batchId, final String template,
			Map<String, Boolean> headerMap) {
		int headerLen = headerMap.size();
		List<Map<String, Object>> optionalFields = 
				new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
					.getHeaderData(template, batchId, headerLen);
		optionalFields.stream()
			.forEach(headerField -> {
				String header = (String) headerField.get(TEMPLATE_HEADER);
				String fileName = (String) headerField.get(Strings.FILE_NAME);
				String value = (String) headerField.get(FIELD_VALUE);
				BigDecimal columnNumber = (BigDecimal) headerField.get("COLUMN_NUMBER");
				int columnNo = columnNumber.intValue();
				columnNo += mandatoryColumnCount;
				if (headerMap.get(header)) {
					String key = new StringBuilder(template)
							.append(Strings.UNDER_LINE)
							.append(fileName)
							.append(Strings.UNDER_LINE)
							.append(header)
							.toString();
					List<Map<String, Object>> dataRecord = resultMap.get(key);
					if (null == dataRecord) {
						dataRecord = new ArrayList<>();
						Map<String, Object> dataMap = new HashMap<>();
						String headerTitle = getHeaderTitle(resultMap, template, fileName, String.valueOf(columnNo));
						dataMap.put(headerTitle, value);
						dataRecord.add(dataMap);
					} else {
						Map<String, Object> dataMap = dataRecord.get(Numeral.ZERO);
						String headerTitle = getHeaderTitle(resultMap, template, fileName, String.valueOf(columnNo));
						dataMap.put(headerTitle, value);
					}
					resultMap.put(key, dataRecord);
				}
			});
		
	}

	private String getHeaderTitle(Map<String, List<Map<String, Object>>> resultMap, final String template,
			String fileName, String columnNumber) {
		String headerKey = new StringBuilder(template)
				.append(Strings.UNDER_LINE)
				.append(fileName)
				.append(Strings.HEADERS_SUFFIX)
				.toString();
		List<Map<String, Object>> headerList = resultMap.get(headerKey);
		Map<String, Object> headers = headerList.get(Numeral.ZERO);
		String headerTitle = (String) headers.get(columnNumber);
		return headerTitle;
	}

	protected final Map<String, Boolean> extractHeaderList(final Map<String, Object> templateHeadersMap,
			final String templateName) {
		Object object = templateHeadersMap.get(templateName);
		String headerString = (String) object;
		String[] headers = headerString.split(Strings.COMMA);
		Map<String, Boolean> headerConfigMap = new HashMap<>();
		for (String header : headers) {
			String[] headerConfig = header.split(Strings.HYPEN);
			headerConfigMap.put(headerConfig[Numeral.ZERO], 
					Boolean.valueOf(headerConfig[Numeral.ONE]));
		}
		return headerConfigMap;
	}

}
