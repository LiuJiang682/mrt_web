package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

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
	
	@RestResource(path="approve")
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE SessionHeader s set s.approved = 1 WHERE s.sessionId in :sessionId ")
	int approveSession(@Param("sessionId") List<Long> sessionId);
	
	@RestResource(path="reject")
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE SessionHeader s set s.rejected = 1 WHERE s.sessionId in :sessionId ")
	int rejectSession(@Param("sessionId") List<Long> sessionId);
}
