package au.gov.vic.ecodev.mrt.test.fixture;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestFixture {

	private static final String JSON = "{\"SL4\":[{\"LOC_SITE\":\"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE\"},{\"DH_BOREHOLE\":\"DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?\"}],\"DS4\":[{\"DH_DOWNHOLE\":\"SURVEYED_DEPTH,AZIMUTH_MAG,DIP\"}],\"DL4\":[{\"DH_LITHOLOGY\":\"HOLE_ID,DEPTH_FROM\"}],\"DG4\":[{\"DH_GEOCHEMISTRY\":\"HOLE_ID,SAMPLE_ID,SAMPLE_FROM,SAMPLE_TO,DRILL_CODE\"}],\"SG4\":[{\"DH_SURFACE_GEOCHEMISTRY\":\"SAMPLE_ID,EASTING,NORTHING,SAMPLE_TYPE\"}]}";
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTemplateFieldMap() throws Exception {
		return new ObjectMapper().readValue(JSON, HashMap.class);
	}

}
