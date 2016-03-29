package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.UploadFile;

public interface UploadFileService {

	public UploadFile add(UploadFile UploadFile);
	public UploadFile edit(UploadFile UploadFile);
	void delete(int id);
	
	public UploadFile findById(int id);
	public List<UploadFile> findAll();
    
	public DataTablesOutput<UploadFile> findAll(DataTablesInput input);
}