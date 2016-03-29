package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.Job;

public interface JobRepository extends DataTablesRepository<Job, Integer> {

	@Query("SELECT job FROM Job job"
			+ " LEFT JOIN FETCH job.results"
			+ " LEFT JOIN FETCH job.user"
			+ " LEFT JOIN FETCH job.uploadFile"
			+ " LEFT JOIN FETCH job.changeNumberDetail chNumDetail"
			+ " LEFT JOIN FETCH chNumDetail.datasource"
			+ " LEFT JOIN FETCH chNumDetail.destination"
			+ " WHERE job.id = :id"
	)
	Job findByID(@Param("id") int id);
	
	@Query("SELECT job FROM Job job"
			+ " LEFT JOIN FETCH job.user"
	)
	List<Job> findAll();
}
