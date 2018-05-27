package au.gov.vic.ecodev.mart;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.AppConfig;
import au.gov.vic.ecodev.mrt.DatabaseConfig;
import au.gov.vic.ecodev.mrt.SecurityConfig;
import au.gov.vic.ecodev.mrt.StarterProfiles;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { AppConfig.class, DatabaseConfig.class, SecurityConfig.class })
@ActiveProfiles(StarterProfiles.TEST)
public abstract class AbstractUnitTest {

}
