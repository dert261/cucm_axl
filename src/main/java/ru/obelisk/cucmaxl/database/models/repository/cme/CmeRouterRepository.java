package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;

public interface CmeRouterRepository extends DataTablesRepository<CmeRouter, Integer> {

	@Query("SELECT cmeRouter FROM CmeRouter cmeRouter"
			+ " LEFT JOIN FETCH cmeRouter.location"
			+ " LEFT JOIN FETCH cmeRouter.cmeGlobal"
			+ " LEFT JOIN FETCH cmeRouter.cmeSipGlobal"
			+ " LEFT JOIN FETCH cmeRouter.sccpDevices"
			+ " LEFT JOIN FETCH cmeRouter.sipDevices"
			+ " WHERE cmeRouter.id = :id"
	)
	CmeRouter findByID(@Param("id") String id);
	
	@Query("SELECT cmeRouter FROM CmeRouter cmeRouter"
			+ " LEFT JOIN FETCH cmeRouter.location"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeRouter> findAll();
}
