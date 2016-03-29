package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface CmeRouterService {

	public CmeRouter add(CmeRouter cmeRouter);
	public CmeRouter edit(CmeRouter cmeRouter);
	void delete(int id);
	
	public CmeRouter findById(int id);
	public List<CmeRouter> findAll();
    
	public List<Select2Result> findByTerm(String term);
	public List<CmeRouter> findWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
	
	public DataTablesOutput<CmeRouter> findAll(DataTablesInput input);
}