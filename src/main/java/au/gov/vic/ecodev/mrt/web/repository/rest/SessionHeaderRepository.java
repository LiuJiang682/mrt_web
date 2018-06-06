package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.SessionHeader;

@RepositoryRestResource(collectionResourceRel = "sessionHeader", path = "sessionHeader")
public interface SessionHeaderRepository extends PagingAndSortingRepository<SessionHeader, Long> {

//	@RestResource(path="display")
//	@Query(value = "SELECT * FROM SESSION_HEADER s WHERE s.APPROVED = 0 AND s.REJECTED = 0 ORDER BY s.CREATED DESC", nativeQuery=true)
//	List<SessionHeader> findByNotApprovedAndNotRejected();
	
	@RestResource(path="display")
//	@Query(value = "SELECT * FROM SESSION_HEADER s WHERE s.APPROVED = 0 AND s.REJECTED = 0 ORDER BY s.CREATED DESC", nativeQuery=true)
	@Query("SELECT s FROM SessionHeader s WHERE s.approved = 0 AND s.rejected = 0 ORDER BY s.created DESC")
	Page<SessionHeader> findByPageNo(Pageable pa);
}
