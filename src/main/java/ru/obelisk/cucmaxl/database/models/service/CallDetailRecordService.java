package ru.obelisk.cucmaxl.database.models.service;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.CallDetailRecord;

public interface CallDetailRecordService extends BaseService<CallDetailRecord> {
	public DataTablesOutput<CallDetailRecord> findAll(DataTablesInput input);
	public DataTablesOutput<CallDetailRecord> findAllWithRelations(DataTablesInput input);
}