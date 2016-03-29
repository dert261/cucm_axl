package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.UploadFile;
import ru.obelisk.cucmaxl.database.models.repository.UploadFileRepository;
import ru.obelisk.cucmaxl.database.models.service.UploadFileService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

 
@Service
@Repository
@Transactional
public class UploadFileServiceImpl implements UploadFileService {
 
    @Autowired
    private UploadFileRepository uploadFileRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public UploadFile add(UploadFile uploadFile) {
    	return uploadFileRepository.save(uploadFile);
         
    }
 
    @Override
    @Transactional
    public UploadFile edit(UploadFile uploadFile) {
    	return uploadFileRepository.save(uploadFile);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        uploadFileRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<UploadFile> findAll() {
        return (List<UploadFile>) uploadFileRepository.findAll();
    }
    
    public DataTablesOutput<UploadFile> findAll(DataTablesInput input){
    	return uploadFileRepository.findAll(input);
    }

    @Override
    @Transactional(readOnly=true)
	public UploadFile findById(int id) {
		return uploadFileRepository.findOne(id);
	}
}