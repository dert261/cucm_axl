package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeExtensionService {

	public CmeExtension add(CmeExtension cmeExtension);
	public CmeExtension edit(CmeExtension cmeExtension);
	void delete(int id);
	
	public CmeExtension findById(int id);
	
	public CmeExtension findByNumberAndRouter(String number, CmeRouter router);
	
	
	public List<CmeExtension> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeExtension> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}