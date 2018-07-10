package au.gov.vic.ecodev.mrt.rest.service.template.helper.headers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PropertyHeaderMappingHelperTest {

	@Autowired
	private PropertyHeaderMappingHelper testInstance;
	
	@Test
	public void shouldReturnHoleIdAsSiteIdForSl4() {
		//Given
		String template = "sl4";
		String dataHeader = "site_id";
		//When
		String displayHeader = testInstance.lookUp(template, dataHeader);
		//Then
		assertThat(displayHeader, is(notNullValue()));
		assertThat(displayHeader, is(equalTo("Hole_id")));
	}
	
	@Test
	public void shouldReturnHoleIdAsSiteIdForSl4CaseInsentive() {
		//Given
		String template = "Sl4";
		String dataHeader = "Site_id";
		//When
		String displayHeader = testInstance.lookUp(template, dataHeader);
		//Then
		assertThat(displayHeader, is(notNullValue()));
		assertThat(displayHeader, is(equalTo("Hole_id")));
	}
	
	@Test
	public void shouldReturn2TemplateWith2TemplateString() throws Exception {
		//Given
//		String propertyString = "{sl4-site_id:Hole_id,easting:Easting_MGA+Easting_AMG,northing:Northing_MGA+Northing_AMG;vgphydro-loader_id:session id}";
		String propertyString = "{\"sl4\":{\"site_id\":\"Hole_id\", \"easting\":\"Easting_MGA+Easting_AMG\", \"northing\":\"Northing_MGA+Northing_AMG\"}, \"vgpHydro\":{\"loader_id\":\"session id\"}}";
		//When
		Map<String, Map<String, Object>> map = testInstance.init(propertyString);
		//Then
		assertThat(map, is(notNullValue()));
		Map<String, Object> sl4Map = map.get("SL4");
		assertThat(sl4Map, is(notNullValue()));
		assertThat(sl4Map.keySet().size(), is(equalTo(3)));
		assertThat(sl4Map.get("SITE_ID"), is(equalTo("Hole_id")));
		assertThat(sl4Map.get("EASTING"), is(equalTo("Easting_MGA+Easting_AMG")));
		assertThat(sl4Map.get("NORTHING"), is(equalTo("Northing_MGA+Northing_AMG")));
		Map<String, Object> vgpHydroMap = map.get("VGPHYDRO");
		assertThat(vgpHydroMap, is(notNullValue()));
		assertThat(vgpHydroMap.keySet().size(), is(equalTo(1)));
		assertThat(vgpHydroMap.get("LOADER_ID"), is(equalTo("session id")));
	}
	
	@Test
	public void shouldAutowiredConfig() {
		assertThat(testInstance, is(notNullValue()));
	}
}
