package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CucmAxlPortService {

	public CucmAxlPort add(CucmAxlPort cucmAxlPort);
	public CucmAxlPort edit(CucmAxlPort cucmAxlPort);
	void delete(int id);
	
	public CucmAxlPort getCucmAxlPortById(int id);
	public List<CucmAxlPort> getAllCucmAxlPorts();
    
	public List<Select2Result> findCucmAxlPortByTerm(String term);
	public List<CucmAxlPort> findCucmAxlPortWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}