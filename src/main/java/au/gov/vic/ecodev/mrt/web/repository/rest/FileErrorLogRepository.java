package au.gov.vic.ecodev.mrt.web.repository.rest;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import au.gov.vic.ecodev.mrt.model.FileLog;

@RepositoryRestResource(collectionResourceRel = "fileLogs", path = "fileLogs")
public interface FileErrorLogRepository extends PagingAndSortingRepository<FileLog, Long> {

	@RestResource(path="get")
	@Query("SELECT f FROM FileLog f WHERE f.batchId = :loaderId ORDER BY f.createdTime DESC")
	List<FileLog> findByLoaderId(@Param("loaderId") long loaderId);
}
