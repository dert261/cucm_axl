package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeGlobal;

public interface CmeGlobalRepository extends DataTablesRepository<CmeGlobal, Integer> {

	@Query("SELECT cmeGlobal FROM CmeGlobal cmeGlobal LEFT JOIN FETCH cmeGlobal.urlServices WHERE cmeGlobal.id = :id")
	CmeGlobal findByID(@Param("id") Integer id);
	
	@Query("SELECT cmeGlobal FROM CmeGlobal cmeGlobal LEFT JOIN FETCH cmeGlobal.urlServices")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeGlobal> findAll();
}
