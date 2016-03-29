package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;

public interface CmeSipExtensionRepository extends DataTablesRepository<CmeSipExtension, Integer> {

	@Query("SELECT cmeSipExtension FROM CmeSipExtension cmeSipExtension"
			+ " LEFT JOIN FETCH cmeSipExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
			+ " WHERE cmeSipExtension.id = :id"
	)
	CmeSipExtension findByID(@Param("id") String id);
	
	@Query("SELECT cmeSipExtension FROM CmeSipExtension cmeSipExtension"
			+ " LEFT JOIN FETCH cmeSipExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
		+ " WHERE"
			+ " cmeSipExtension.number = :number"
			+ " AND device.router = :router"
	)
	CmeSipExtension findByNumberAndRouter(@Param("number") String number, @Param("router") CmeRouter router);
	
	@Query("SELECT cmeSipExtension FROM CmeSipExtension cmeSipExtension"
			+ " LEFT JOIN FETCH cmeSipExtension.devices extMap"
			+ " LEFT JOIN FETCH extMap.device device"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeSipExtension> findAll();
}
