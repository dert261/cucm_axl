package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.JobResult;

public interface JobResultService {

	public JobResult add(JobResult jobResults);
	public JobResult edit(JobResult jobResults);
	void delete(int id);
	
	public JobResult findById(int id);
	public List<JobResult> findAll();
    
	public DataTablesOutput<JobResult> findAll(DataTablesInput input);
}