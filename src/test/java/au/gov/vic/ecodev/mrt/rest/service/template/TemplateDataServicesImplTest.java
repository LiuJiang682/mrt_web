package au.gov.vic.ecodev.mrt.rest.service.template;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.StarterProfiles;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(StarterProfiles.TEST)
public class TemplateDataServicesImplTest {

	@Autowired
	private TemplateDataServicesImpl testInstance;
	
//	@Ignore
	@Test
	public void shouldReturnAllTemplateData() throws Exception {
		//Given
		long batchId = 100l;
		//When
		Map<String, Map<String, List<Map<String, Object>>>> results = testInstance.getAllTemplateData(batchId);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.isEmpty(), is(false));
	}
	
	@Test
	public void shouldGroupDataByTemplate() throws Exception {
		//Given
		Map<String, List<Map<String, Object>>> results = testInstance.retrieveDisplayData(100l);
		//When
		Map<String, Map<String, List<Map<String, Object>>>> groupedMap = testInstance.groupRecordsByTemplate(results);
		//Then
		assertThat(groupedMap, is(notNullValue()));
	}
	
	@Test
	public void shouldReturn3ElementsWhen3ElementStringProvided() {
		//Given
		String key = "SL4_myTest.txt_D1";
		//When
		String[] templateAndRow = testInstance.getTemplateAndRows(key);
		//Then
		assertThat(templateAndRow, is(notNullValue()));
		assertThat(templateAndRow.length, is(equalTo(3)));
		assertThat(templateAndRow[0], is(equalTo("SL4")));
		assertThat(templateAndRow[1], is(equalTo("myTest.txt")));
		assertThat(templateAndRow[2], is(equalTo("D1")));
	}
	
	@Test
	public void shouldReturn3ElementsWhenMultiplyElementStringProvided() {
		//Given
		String key = "SL4_EL5478_201702_0c_Collar.txt_D1";
		//When
		String[] templateAndRow = testInstance.getTemplateAndRows(key);
		//Then
		assertThat(templateAndRow, is(notNullValue()));
		assertThat(templateAndRow.length, is(equalTo(3)));
		assertThat(templateAndRow[0], is(equalTo("SL4")));
		assertThat(templateAndRow[1], is(equalTo("EL5478_201702_0c_Collar.txt")));
		assertThat(templateAndRow[2], is(equalTo("D1")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
}
