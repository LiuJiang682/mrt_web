package au.gov.vic.ecodev.mrt.config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateWebPropertiesConfigTest {

	@Autowired
	private TemplateWebPropertiesConfig testInstance;
	
	@Test
	public void shouldAutowiredConfig() {
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnArrayOfUrls() {
		//Given
		//When
		String[] urls = testInstance.getSessionIdFilterUrls();
		//Then
		assertThat(urls, is(notNullValue()));
		assertThat(urls.length, is(equalTo(4)));
	}
	
	@Test
	public void shouldReturnSl4TemplateHeaderMapString() {
		//Given
		//When
		String templateHeaderMappingString = testInstance.getTemplateHeaderMappingString();
		//Then
		assertThat(templateHeaderMappingString, is(notNullValue()));
//		assertThat(templateHeaderMappingString, is(equalTo("sl4-site_id:Hole_id")));
	}
}
