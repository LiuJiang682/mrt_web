package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.SessionHeader;

@RepositoryRestResource(collectionResourceRel = "sessionHeader", path = "sessionHeader")
public interface SessionHeaderRepository extends PagingAndSortingRepository<SessionHeader, Long> {

	@RestResource
	List<SessionHeader> findAll();
}
