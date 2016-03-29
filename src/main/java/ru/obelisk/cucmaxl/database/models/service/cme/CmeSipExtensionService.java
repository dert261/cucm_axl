package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeSipExtensionService {

	public CmeSipExtension add(CmeSipExtension cmeSipExtension);
	public CmeSipExtension edit(CmeSipExtension cmeSipExtension);
	void delete(int id);
	
	public CmeSipExtension findById(int id);
	
	public CmeSipExtension findByNumberAndRouter(String number, CmeRouter router);
	
	public List<CmeSipExtension> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeSipExtension> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}