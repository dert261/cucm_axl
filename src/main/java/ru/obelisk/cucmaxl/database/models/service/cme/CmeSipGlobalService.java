package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipGlobal;

public interface CmeSipGlobalService {

	public CmeSipGlobal add(CmeSipGlobal cmeSipGlobal);
	public CmeSipGlobal edit(CmeSipGlobal cmeSipGlobal);
	void delete(int id);
	
	public CmeSipGlobal findById(int id);
	public List<CmeSipGlobal> findAll();
}