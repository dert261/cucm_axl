package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipGlobal;

public interface CmeSipGlobalRepository extends DataTablesRepository<CmeSipGlobal, Integer> {

	@Query("SELECT cmeSipGlobal FROM CmeSipGlobal cmeSipGlobal WHERE cmeSipGlobal.id = :id")
	CmeSipGlobal findByID(@Param("id") Integer id);
	
	@Query("SELECT cmeSipGlobal FROM CmeSipGlobal cmeSipGlobal")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeSipGlobal> findAll();
}
