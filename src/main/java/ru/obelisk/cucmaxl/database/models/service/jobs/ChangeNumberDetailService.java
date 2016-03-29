package ru.obelisk.cucmaxl.database.models.service.jobs;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.jobs.ChangeNumberDetail;

public interface ChangeNumberDetailService {

	public ChangeNumberDetail add(ChangeNumberDetail changeNumberDetails);
	public ChangeNumberDetail edit(ChangeNumberDetail changeNumberDetails);
	void delete(int id);
	
	public ChangeNumberDetail findById(int id);
	public List<ChangeNumberDetail> findAll();
    
	
}