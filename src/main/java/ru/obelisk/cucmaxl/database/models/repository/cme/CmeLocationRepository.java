package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeLocation;

public interface CmeLocationRepository extends DataTablesRepository<CmeLocation, Integer> {

	@Query("SELECT cmeLocation FROM CmeLocation cmeLocation LEFT JOIN FETCH cmeLocation.routers WHERE cmeLocation.id = :id")
	CmeLocation findByID(@Param("id") String id);
	
	@Query("SELECT cmeLocation FROM CmeLocation cmeLocation LEFT JOIN FETCH cmeLocation.routers")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeLocation> findAll();
	
	@Query("SELECT cmeLocation FROM CmeLocation cmeLocation")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeLocation> findAllWithoutRelations();
}
