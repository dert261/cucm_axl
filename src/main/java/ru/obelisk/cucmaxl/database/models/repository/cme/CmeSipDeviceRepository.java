package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;

public interface CmeSipDeviceRepository extends DataTablesRepository<CmeSipDevice, Integer> {

	@Query("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice"
			+ " LEFT JOIN FETCH cmeSipDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeSipDevice.router"
			+ " WHERE cmeSipDevice.id = :id"
	)
	CmeSipDevice findByID(@Param("id") String id);
	
	@Query("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice"
			+ " LEFT JOIN FETCH cmeSipDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeSipDevice.router"
			+ " WHERE cmeSipDevice.router = :router AND cmeSipDevice.name = :name"
	)
	CmeSipDevice findByNameAndRouter(@Param("name") String name, @Param("router") CmeRouter router);
	
	
	@Query("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice"
			+ " LEFT JOIN FETCH cmeSipDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeSipDevice.router"
			+ " WHERE cmeSipDevice.router = :router"
			+ " AND cmeSipDevice.customSipDevice.enable = true"
			+ " AND cmeSipDevice.exported = false"
	)
	List<CmeSipDevice> findAllForExportByRouterWithRelations(@Param("router") CmeRouter router);
	
	
	@Query("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice"
			+ " LEFT JOIN FETCH cmeSipDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeSipDevice.router"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeSipDevice> findAll();
}
