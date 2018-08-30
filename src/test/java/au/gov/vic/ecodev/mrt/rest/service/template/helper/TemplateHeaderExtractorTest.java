package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.test.fixture.TestFixture;

public class TemplateHeaderExtractorTest {

	private TemplateHeaderExtractor testInstance;
	private Map<String, Object> map;
	private String templateName;

	@Test
	public void shouldReturnSampTemplateHeaders() throws Exception {
		//Given
		givenTestInstance("SAMP");
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		Map<String, String> templateTableMap = new HashMap<>();
		//When
		testInstance.doHeadersExtraction(resultMap, templateTableMap);
		//Then
		assertThat(resultMap.isEmpty(), is(false));
		assertThat(resultMap.size(), is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnInstance() throws Exception {
		//Given
		givenTestInstance("SAMP");
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMapIsNull() {
		//Given
		//When
		new TemplateHeaderExtractor(map, templateName);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateNameIsNull() {
		//Given
		map = new HashMap<>();
		//When
		new TemplateHeaderExtractor(map, templateName);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(String templateName) throws Exception {
		map = TestFixture.getVgpHydroTemplateFieldMap();
		testInstance = new TemplateHeaderExtractor(map, templateName);
	}
}
