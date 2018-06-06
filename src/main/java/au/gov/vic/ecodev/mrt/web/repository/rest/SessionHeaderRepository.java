package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.SessionHeader;

@RepositoryRestResource(collectionResourceRel = "sessionHeader", path = "sessionHeader")
public interface SessionHeaderRepository extends PagingAndSortingRepository<SessionHeader, Long> {

	@RestResource
	@Query(value = "SELECT * FROM SESSION_HEADER s WHERE s.APPROVED = 0 AND s.REJECTED = 0 ORDER BY s.CREATED desc", nativeQuery=true)
	List<SessionHeader> findByNotApprovedAndNotRejected();
}
