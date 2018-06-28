package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.sg4.SurfaceGeochemistry;

@RepositoryRestResource(collectionResourceRel = "surfaceGeochemistry", path = "surfaceGeochemistry")
public interface SurfaceGeochemistryRepository extends PagingAndSortingRepository<SurfaceGeochemistry, Long> {

	@RestResource(path="get")
	@Query("SELECT s FROM SurfaceGeochemistry s WHERE s.loaderId = :sessionId")
	List<SurfaceGeochemistry> findByLoaderId(@Param("sessionId") long sessionId);
}
