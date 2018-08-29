package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateOptionalFieldsRetriever {

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
			final Map<String, Boolean> templateHeadersMap, final int mandatoryColumnCount,
			final long batchId, final String template) {

		int headerLen = templateHeadersMap.size();
		List<Map<String, Object>> optionalFields = 
				new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
					.getData(template, batchId);
		
		optionalFields.stream()
			.forEach(optionalField -> {
				BigDecimal rowNumber = (BigDecimal) optionalField.get(ROW_NUMBER);
				int row  = rowNumber.intValue();
				if (Numeral.ZERO != row) {
					if (row <= headerLen) {
						handleHeader(optionalField, mandatoryColumnCount, templateHeadersMap,
								template, resultMap);
					} else {
						handleData(optionalField, headerLen, template, resultMap);
					}
				}
			});
	}

	protected final String getHeaderTitle(final Map<String, List<Map<String, Object>>> resultMap,
			final String template, final String fileName, final String columnNumber) {
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

	protected final void handleData(final Map<String, Object> optionalField, int headerLen, 
			final String template, final Map<String, List<Map<String, Object>>> resultMap) {
		BigDecimal rowNumber = (BigDecimal) optionalField.get(ROW_NUMBER);
		int row  = rowNumber.intValue();
		if (headerLen < row) {
			row -= headerLen;
		}
		String header = (String) optionalField.get(TEMPLATE_HEADER);
		String value = (String) optionalField.get(FIELD_VALUE);
		String fileName = (String) optionalField.get(Strings.FILE_NAME);
		String key = new StringBuilder(template).append(Strings.UNDER_LINE).append(fileName)
				.append(Strings.UNDER_LINE_DATA_KEY).append(row).toString();
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

	protected final void handleHeader(final Map<String, Object> headerField, 
			final int mandatoryColumnCount,
			final Map<String, Boolean> templateHeadersMap, 
			final String template, 
			final Map<String, List<Map<String, Object>>> resultMap) {
		String header = (String) headerField.get(TEMPLATE_HEADER);
		String fileName = (String) headerField.get(Strings.FILE_NAME);
		String value = (String) headerField.get(FIELD_VALUE);
		BigDecimal columnNumber = (BigDecimal) headerField.get("COLUMN_NUMBER");
		int columnNo = columnNumber.intValue();
		columnNo += mandatoryColumnCount;
		if (templateHeadersMap.get(header)) {
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
				String headerTitle = getHeaderTitle(resultMap, template, fileName, 
						String.valueOf(columnNo));
				dataMap.put(headerTitle, value);
				dataRecord.add(dataMap);
			} else {
				Map<String, Object> dataMap = dataRecord.get(Numeral.ZERO);
				String headerTitle = getHeaderTitle(resultMap, template, fileName, 
						String.valueOf(columnNo));
				dataMap.put(headerTitle, value);
			}
			resultMap.put(key, dataRecord);
		}
	}

}
