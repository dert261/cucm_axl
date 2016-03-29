package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeFastDial;

public interface CmeFastDialRepository extends DataTablesRepository<CmeFastDial, Integer> {

	@Query("SELECT cmeFastDial FROM CmeFastDial cmeFastDial WHERE cmeFastDial.id = :id")
	CmeFastDial findByID(@Param("id") Integer id);
	
	@Query("SELECT cmeFastDial FROM CmeFastDial cmeFastDial")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeFastDial> findAll();
}
