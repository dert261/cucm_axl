package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;

public interface CucmAxlPortRepository extends JpaRepository<CucmAxlPort, Integer> {
	
	@Query("SELECT cucmAxlPort FROM CucmAxlPort cucmAxlPort "
			+ "LEFT JOIN FETCH cucmAxlPort.partitionFilter "
			+ "WHERE cucmAxlPort.id=:id")
	CucmAxlPort findById(@Param("id") Integer id);
	
	@Query("SELECT cucmAxlPort FROM CucmAxlPort cucmAxlPort")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CucmAxlPort> findAll();
}
