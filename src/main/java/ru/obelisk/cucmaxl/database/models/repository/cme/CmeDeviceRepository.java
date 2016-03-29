package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;


public interface CmeDeviceRepository extends DataTablesRepository<CmeDevice, Integer> {

	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.customDevice customDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH extension.customExtension customExtension"
			/*+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"*/
			+ " LEFT JOIN FETCH cmeDevice.router"
			+ " WHERE cmeDevice.id = :id"
			+ " ORDER BY extMap.lineId"
	)
	CmeDevice findByID(@Param("id") Integer id);
	
	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.customDevice customDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH extension.customExtension customExtension"
			/*+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"*/
			+ " LEFT JOIN FETCH cmeDevice.router"
			+ " ORDER BY extMap.lineId"
	)
	List<CmeDevice> findAllWithRelations();
	
	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.customDevice customDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH extension.customExtension customExtension"
			/*+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"*/
			+ " LEFT JOIN FETCH cmeDevice.router"
			+ " WHERE cmeDevice.router = :router"
			+ " ORDER BY extMap.lineId"
	)
	List<CmeDevice> findAllByRouterWithRelations(@Param("router") CmeRouter router);
	
	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.customDevice customDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH extension.customExtension customExtension"
			/*+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"*/
			+ " LEFT JOIN FETCH cmeDevice.router"
			+ " WHERE cmeDevice.router = :router"
			+ " AND cmeDevice.customDevice.enable = true"
			+ " AND cmeDevice.exported = false"
			+ " ORDER BY extMap.lineId"
	)
	List<CmeDevice> findAllForExportByRouterWithRelations(@Param("router") CmeRouter router);
	
	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"
			+ " LEFT JOIN FETCH cmeDevice.router"
			+ " WHERE cmeDevice.router = :router AND cmeDevice.name = :name"
	)
	CmeDevice findByNameAndRouter(@Param("name") String name, @Param("router") CmeRouter router);
	
	@Query("SELECT cmeDevice FROM CmeDevice cmeDevice"
			+ " LEFT JOIN FETCH cmeDevice.lines extMap"
			+ " LEFT JOIN FETCH extMap.extension extension"
			+ " LEFT JOIN FETCH cmeDevice.blfSpeedDials"
			+ " LEFT JOIN FETCH cmeDevice.fastDials"
			+ " LEFT JOIN FETCH cmeDevice.speedDials"
			+ " LEFT JOIN FETCH cmeDevice.addonModules"
			+ " LEFT JOIN FETCH cmeDevice.router"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeDevice> findAll();
	
	
}
