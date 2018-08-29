package au.gov.vic.ecodev.mrt.rest.service.template.helper.headers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PropertyHeaderMappingHelper implements HeaderMappingHelper {
	
	private final String propertyString;
	private final Map<String, Map<String, Object>> templateHeaderMap;
	
	public PropertyHeaderMappingHelper(final String propertyString) throws Exception {
		this.propertyString = propertyString;
		this.templateHeaderMap = init(propertyString);
	}
	
	@Override
	public String lookUp(final String template, final String dataHeader) {
		Map<String, Object> templateMap = templateHeaderMap.get(template.toUpperCase());
		if (null != templateMap) {
			return (String) templateMap.get(dataHeader.toUpperCase());
		}
		return null;
	}

	public Map<String, Map<String, Object>> init(final String propertyString) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, Object>> myMap =
				mapper.readValue(propertyString, new TypeReference<Map<String, Map<String, Object>>>(){});
		Map<String, Map<String, Object>> dataMap = new HashMap<>();
		
		Iterator<String> keys = myMap.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			Map<String, Object> map = myMap.get(key);
			Iterator<String> subKeys = map.keySet().iterator();
			Map<String, Object> templateValueMap = new HashMap<>();
			while(subKeys.hasNext()) {
				String subKey = subKeys.next();
				Object value = map.get(subKey);
				templateValueMap.put(subKey.toUpperCase(), value);
			}
			dataMap.put(key.toUpperCase(), templateValueMap);
		}
		return dataMap;
	}

	public String getPropertyString() {
		return propertyString;
	}

}
