package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;

public class TemplateHeaderRetriever {

	private static final String HEADERS_SUFFIX = "_Headers";
	private static final String FIELD_VALUE = "FIELD_VALUE";
	
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateHeaderRetriever(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateHeaderRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void extractTemplateHeaders(Map<String, List<Map<String, Object>>> resultMap, 
			long batchId, String template) {
		List<Map<String, Object>> headers = new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
				.getHeaders(template, batchId);
		headers.stream()
			.forEach(header -> {
				// System.out.println(header);
				String fileName = (String) header.get(Strings.FILE_NAME);
				String headersString = (String) header.get(FIELD_VALUE);
				String[] headersArray = headersString.split(Strings.COMMA);
				Map<String, Object> newHeaders = new HashMap<>(headersArray.length);
				for (int i = Numeral.ZERO; i < headersArray.length; i++) {
					int index = i;
					String key = String.valueOf(++index);
					newHeaders.put(key, headersArray[i]);
				}
				List<Map<String, Object>> list = new ArrayList<>(Numeral.ONE);
				list.add(newHeaders);
				String resultKey = new StringBuilder(template)
						.append(Strings.UNDER_LINE)
						.append(fileName)
						.append(HEADERS_SUFFIX)
						.toString();
				resultMap.put(resultKey, list);
			});
	}
}
