package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.common.Constants.Numeral;
import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.rest.service.template.helper.utils.StringToListIndexHeaderConvertor;

public class TemplateHeaderExtractor {

	private final Map<String, Object> templateFieldMap;
	private final String templateName;
	
	public TemplateHeaderExtractor(final Map<String, Object> templateFieldMap, 
			final String templateName) {
		if (null == templateFieldMap) {
			throw new IllegalArgumentException("TemplateHeaderExtractor:templateFieldMap parameter cannot be null!");
		}
		this.templateFieldMap = templateFieldMap;
		if (StringUtils.isEmpty(templateName)) {
			throw new IllegalArgumentException("TemplateHeaderExtractor:templateName parameter cannot be null or empty!");
		}
		this.templateName = templateName;
	}

	public void doHeadersExtraction(Map<String, List<Map<String, Object>>> resultMap, 
			Map<String, String> templateTableMap) {
		Object object = templateFieldMap.get(templateName);
		if (null != object) {
			if (object instanceof List) {
				@SuppressWarnings("unchecked")
				List<LinkedHashMap<String, String>> templateClassesList = (List<LinkedHashMap<String, String>>) object;
				LinkedHashMap<String, String> templateClass = templateClassesList.get(Numeral.ZERO);
				Entry<String, String> entry = templateClass.entrySet().iterator().next();
				String templateTable = entry.getKey();
				templateTableMap.put(templateName, templateTable);
				String fieldString = entry.getValue();
				List<Map<String, Object>> list = new StringToListIndexHeaderConvertor(fieldString)
						.getIndexedHeaderList();
				String resultKey = new StringBuilder(templateName)
						.append(Strings.HEADERS_SUFFIX)
						.toString();
				resultMap.put(resultKey, list);
			}
			
		}
	}

}
