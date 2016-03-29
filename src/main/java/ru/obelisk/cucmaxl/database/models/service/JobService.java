package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.Job;

public interface JobService {

	public Job add(Job jobs);
	public Job edit(Job jobs);
	void delete(int id);
	
	public Job findById(int id);
	public List<Job> findAll();
    
	public DataTablesOutput<Job> findAll(DataTablesInput input);
	public DataTablesOutput<Job> findAllWithRelations(DataTablesInput input);
}