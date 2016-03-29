package ru.obelisk.cucmaxl.database.models.repository.cme;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeVoiceHuntGroup;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;

public interface CmeVoiceHuntGroupRepository extends DataTablesRepository<CmeVoiceHuntGroup, Integer> {

	@Query("SELECT cmeVoiceHuntGroup FROM CmeVoiceHuntGroup cmeVoiceHuntGroup"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.numbers"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.router"
			+ " WHERE cmeVoiceHuntGroup.id = :id"
	)
	CmeVoiceHuntGroup findByID(@Param("id") String id);
	
	@Query("SELECT cmeVoiceHuntGroup FROM CmeVoiceHuntGroup cmeVoiceHuntGroup"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.numbers"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.router"
			+ " WHERE cmeVoiceHuntGroup.router = :router AND cmeVoiceHuntGroup.pilotNumber = :pilotNumber"
	)
	CmeVoiceHuntGroup findByPilotNumberAndRouter(@Param("pilotNumber") String pilotNumber, @Param("router") CmeRouter router);
	
	@Query("SELECT cmeVoiceHuntGroup FROM CmeVoiceHuntGroup cmeVoiceHuntGroup"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.numbers"
			+ " LEFT JOIN FETCH cmeVoiceHuntGroup.router"
	)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<CmeVoiceHuntGroup> findAll();
}
