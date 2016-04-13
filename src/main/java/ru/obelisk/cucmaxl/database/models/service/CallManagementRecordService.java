package ru.obelisk.cucmaxl.database.models.service;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.CallManagementRecord;

public interface CallManagementRecordService extends BaseService<CallManagementRecord> {
	public DataTablesOutput<CallManagementRecord> findAll(DataTablesInput input);
	public DataTablesOutput<CallManagementRecord> findAllWithRelations(DataTablesInput input);
}