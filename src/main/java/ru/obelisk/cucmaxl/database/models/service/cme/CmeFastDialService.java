package ru.obelisk.cucmaxl.database.models.service.cme;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeFastDial;

public interface CmeFastDialService {

	public CmeFastDial add(CmeFastDial cmeFastDial);
	public CmeFastDial edit(CmeFastDial cmeFastDial);
	void delete(int id);
	
	public CmeFastDial findById(int id);
	public List<CmeFastDial> findAll();
}