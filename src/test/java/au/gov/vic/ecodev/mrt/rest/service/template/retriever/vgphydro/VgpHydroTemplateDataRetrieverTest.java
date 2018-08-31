package au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.common.Constants.Strings;
import au.gov.vic.ecodev.mrt.test.fixture.TestFixture;

public class VgpHydroTemplateDataRetrieverTest {

	private VgpHydroTemplateDataRetriever testInstance;
	private JdbcTemplate mockJdbcTemplate;
	
	@Test
	public void shouldPopulateResultMapWhenTemplateIsSamp() throws Exception {
		//Given
		givenTestInstance();
		String templateName = "SAMP";
		testInstance.setTemplateName(templateName);
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		testInstance.setResultMap(resultMap);
		testInstance.setTemplateFieldMap(TestFixture.getVgpHydroTemplateFieldMap());
		testInstance.setJdbcTemplate(mockJdbcTemplate);
		Map<String, Object> map = new HashMap<>();
		map.put(Strings.FILE_NAME, "myTest.txt");
		map.put("ROW_NUMBER", "1");
		List<Map<String, Object>> result = Arrays.asList(map);
		when(mockJdbcTemplate.queryForList(Matchers.anyString(), Matchers.any(Object[].class)))
			.thenReturn(result);
		//When
		testInstance.doDataRetrieve();
		//Then
		assertThat(resultMap.isEmpty(), is(false));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}

	private void givenTestInstance() {
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		testInstance = new VgpHydroTemplateDataRetriever();
	}
}
