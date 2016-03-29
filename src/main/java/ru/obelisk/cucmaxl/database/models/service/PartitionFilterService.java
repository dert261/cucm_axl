package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.PartitionFilter;

public interface PartitionFilterService {

	public PartitionFilter add(PartitionFilter partitionFilters);
	public PartitionFilter edit(PartitionFilter partitionFilters);
	void delete(int id);
	
	public PartitionFilter findById(int id);
	public List<PartitionFilter> findAll();
    
	public DataTablesOutput<PartitionFilter> findAll(DataTablesInput input);
}