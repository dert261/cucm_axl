package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.obelisk.cucmaxl.database.models.entity.ScheduleCron;

public interface ScheduleCronRepository extends JpaRepository<ScheduleCron, Integer> {
	@Query("SELECT scheduleCron FROM ScheduleCron scheduleCron LEFT JOIN FETCH scheduleCron.scheduleJob")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<ScheduleCron> findAll();
}
