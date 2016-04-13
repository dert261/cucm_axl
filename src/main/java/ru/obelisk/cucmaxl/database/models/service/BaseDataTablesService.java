package ru.obelisk.cucmaxl.database.models.service;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

public interface BaseDataTablesService<T> extends BaseService<T>{
	public DataTablesOutput<T> findAll(DataTablesInput input);
}
