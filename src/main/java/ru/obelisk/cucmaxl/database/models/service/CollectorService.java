package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.Collector;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

public interface CollectorService extends BaseDataTablesService<Collector> {
	public DataTablesOutput<Collector> findAllWithRelations(DataTablesInput input);
	public List<Collector> findAllWithRelations();
}