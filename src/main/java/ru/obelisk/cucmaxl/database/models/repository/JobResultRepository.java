package ru.obelisk.cucmaxl.database.models.repository;

import ru.obelisk.datatables.repository.DataTablesRepository;
import ru.obelisk.cucmaxl.database.models.entity.JobResult;

public interface JobResultRepository extends DataTablesRepository<JobResult, Integer> {

	/*@Query("SELECT jobResult FROM JobResult jobResult"			
			+ " WHERE jobResult.id = :id"
	)
	JobResult findByID(@Param("id") String id);
	
	@Query("SELECT job FROM Job job"
			+ " LEFT JOIN FETCH job.user"
	)
	List<Job> findAll();*/
}
