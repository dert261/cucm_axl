package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.obelisk.cucmaxl.database.models.entity.ScheduleJob;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Integer> {
	@Query("SELECT scheduleJob FROM ScheduleJob scheduleJob"
			+ " LEFT JOIN FETCH scheduleJob.scheduleCron"
			+ " LEFT JOIN FETCH scheduleJob.ldapDirSyncParameters"
			+ " LEFT JOIN FETCH scheduleJob.cucmAxlPorts")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<ScheduleJob> findAll();
}
