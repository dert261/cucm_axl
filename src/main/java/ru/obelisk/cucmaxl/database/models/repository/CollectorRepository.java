package ru.obelisk.cucmaxl.database.models.repository;

import ru.obelisk.datatables.repository.DataTablesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.Collector;

public interface CollectorRepository extends DataTablesRepository<Collector, Integer> {
	
	@Query("SELECT collector FROM Collector collector"
			+ " LEFT JOIN FETCH collector.sourceAxlPort"
			+ " LEFT JOIN FETCH collector.collectorFtpConfig"
			+ " WHERE collector.id = :id"
	)
	Collector findById(@Param("id") Integer id);
	
	@Query("SELECT collector FROM Collector collector"
			+ " LEFT JOIN FETCH collector.sourceAxlPort"
			+ " LEFT JOIN FETCH collector.collectorFtpConfig"
	)
	List<Collector> findAllWithRelations();
	
}
