package au.gov.vic.ecodev.mrt.rest.service.template.retriever.vgphydro;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.test.fixture.TestFixture;

public class VgpHydroTemplateDataRetrieverTest {

	private VgpHydroTemplateDataRetriever testInstance;
	
	@Test
	public void shouldPopulateResultMapWhenTemplateIsSamp() throws Exception {
		//Given
		givenTestInstance();
		String templateName = "SAMP";
		testInstance.setTemplateName(templateName);
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		testInstance.setResultMap(resultMap);
		testInstance.setTemplateFieldMap(TestFixture.getVgpHydroTemplateFieldMap());
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
		testInstance = new VgpHydroTemplateDataRetriever();
	}
}
