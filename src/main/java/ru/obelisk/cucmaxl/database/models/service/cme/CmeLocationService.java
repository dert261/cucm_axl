package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeLocation;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeLocationService {

	public CmeLocation add(CmeLocation cmeLocation);
	public CmeLocation edit(CmeLocation cmeLocation);
	void delete(int id);
	
	public CmeLocation findById(int id);
	public List<CmeLocation> findAll();
	public List<CmeLocation> findAllWithoutRelations();
	    
	public List<Select2Result> findByTerm(String term);
	public List<CmeLocation> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}