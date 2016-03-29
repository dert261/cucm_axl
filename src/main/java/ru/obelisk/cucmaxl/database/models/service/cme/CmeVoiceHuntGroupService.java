package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeVoiceHuntGroup;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeVoiceHuntGroupService {

	public CmeVoiceHuntGroup add(CmeVoiceHuntGroup cmeVoiceHuntGroup);
	public CmeVoiceHuntGroup edit(CmeVoiceHuntGroup cmeVoiceHuntGroup);
	void delete(int id);
	
	public CmeVoiceHuntGroup findById(int id);
	
	public DataTablesOutput<CmeVoiceHuntGroup> findAll(DataTablesInput input);
	public DataTablesOutput<CmeVoiceHuntGroup> findAllByRouter(DataTablesInput input, CmeRouter router);
	
	public CmeVoiceHuntGroup findByPilotNumberAndRouter(String pilotNumber, CmeRouter router);
	
	public List<CmeVoiceHuntGroup> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeVoiceHuntGroup> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}