package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;

public interface CmeExtensionRepository extends DataTablesRepository<CmeExtension, Integer> {

	@Query("SELECT cmeExtension FROM CmeExtension cmeExtension"
			+ " LEFT JOIN FETCH cmeExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
			+ " LEFT JOIN FETCH cmeExtension.callForward"
			+ " WHERE cmeExtension.id = :id"
	)
	CmeExtension findByID(@Param("id") String id);
	
	@Query("SELECT cmeExtension FROM CmeExtension cmeExtension"
			+ " LEFT JOIN FETCH cmeExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
			+ " LEFT JOIN FETCH cmeExtension.callForward"
		+ " WHERE"
			+ " cmeExtension.number = :number"
			+ " AND device.router = :router"
	)
	CmeExtension findByNumberAndRouter(@Param("number") String number, @Param("router") CmeRouter router);
	
	@Query("SELECT cmeExtension FROM CmeExtension cmeExtension"
			+ " LEFT JOIN FETCH cmeExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
			+ " LEFT JOIN FETCH cmeExtension.callForward"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeExtension> findAll();
}
