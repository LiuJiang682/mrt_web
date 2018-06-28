package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.sl4.Site;

@RepositoryRestResource(collectionResourceRel = "site", path = "site")
public interface SiteRepository extends PagingAndSortingRepository<Site, Long> {

	@RestResource(path="get")
	@Query("SELECT s FROM Site s WHERE s.loaderId = :sessionId")
	List<Site> findByLoaderId(@Param("sessionId") long sessionId);
}
