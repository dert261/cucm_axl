package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeDeviceService {

	public CmeDevice add(CmeDevice cmeDevice);
	public CmeDevice edit(CmeDevice cmeDevice);
	void delete(int id);
	
	public CmeDevice findById(int id);
	
	public CmeDevice findByIdWithRelations(int id);
	
	public List<CmeDevice> findAllWithRelations();
	public List<CmeDevice> findAllByRouterWithRelations(CmeRouter router);
	public List<CmeDevice> findAllForExportByRouterWithRelations(CmeRouter router);
	
	public DataTablesOutput<CmeDevice> findAll(DataTablesInput input);
	public DataTablesOutput<CmeDevice> findAllByRouter(DataTablesInput input, CmeRouter router);
	
	public CmeDevice findByNameAndRouter(String name, CmeRouter router);
	public List<CmeDevice> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeDevice> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}