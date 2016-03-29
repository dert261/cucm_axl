package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeGlobal;

public interface CmeGlobalService {

	public CmeGlobal add(CmeGlobal cmeGlobal);
	public CmeGlobal edit(CmeGlobal cmeGlobal);
	void delete(int id);
	
	public CmeGlobal findById(int id);
	public List<CmeGlobal> findAll();
}