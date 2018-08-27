package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

public class TemplateDataRetrieverHelperTest {

	private TemplateDataRetrieverHelper testInstance;
	private JdbcTemplate mockJdbcTemplate;
	
	@Test
	public void shouldReturnDataRetrieverClassMap() {
		//Given
		givenTestInstance();
		when(mockJdbcTemplate.queryForList(eq("SELECT TEMPLATE, TEMPLATE_RETRIEVER FROM TEMPLATE_DISPLAY_PROPERTIES")))
			.thenReturn(givenList());
		//When
		Map<String, String> map = testInstance.getDataRetrieverClassMap();
		//Then
		assertThat(map, is(notNullValue()));
		assertThat(map.size(), is(equalTo(1)));
		map.entrySet().forEach(entry -> {
			assertThat(entry.getKey(), is(equalTo("MRT-SL4")));
			assertThat(entry.getValue(), is(equalTo("au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever")));
		});
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldRaiseExceptionWhenDataRetrieverClassMapIsInvalid() {
		//Given
		givenTestInstance();
		Map<String, Object> classMap = new HashMap<>();
		classMap.put("TEMPLATE", "MRT");
		classMap.put("TEMPLATE_RETRIEVER", "{");
		when(mockJdbcTemplate.queryForList(eq("SELECT TEMPLATE, TEMPLATE_RETRIEVER FROM TEMPLATE_DISPLAY_PROPERTIES")))
			.thenReturn(Arrays.asList(classMap));
		//When
		testInstance.getDataRetrieverClassMap();
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		mockJdbcTemplate = null;
		//When
		new TemplateDataRetrieverHelper(mockJdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		testInstance = new TemplateDataRetrieverHelper(mockJdbcTemplate);
	}
	
	private List<Map<String, Object>> givenList() {
		Map<String, Object> map = new HashMap<>();
		map.put("TEMPLATE", "MRT");
		map.put("TEMPLATE_RETRIEVER", "{\"SL4\":\"au.gov.vic.ecodev.mrt.rest.service.template.retriever.mrt.MrtTemplateDataRetriever\"}");
		return Arrays.asList(map);
	}
}
