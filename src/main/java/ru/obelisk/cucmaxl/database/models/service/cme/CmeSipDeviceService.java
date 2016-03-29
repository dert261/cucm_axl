package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeSipDeviceService {

	public CmeSipDevice add(CmeSipDevice cmeSipDevice);
	public CmeSipDevice edit(CmeSipDevice cmeSipDevice);
	void delete(int id);
	
	public CmeSipDevice findById(int id);
	
	public DataTablesOutput<CmeSipDevice> findAll(DataTablesInput input);
	public DataTablesOutput<CmeSipDevice> findAllByRouter(DataTablesInput input, CmeRouter router);
	
	public List<CmeSipDevice> findAllForExportByRouterWithRelations(CmeRouter router);
	
	public CmeSipDevice findByNameAndRouter(String name, CmeRouter router);
	
	public List<CmeSipDevice> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeSipDevice> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}