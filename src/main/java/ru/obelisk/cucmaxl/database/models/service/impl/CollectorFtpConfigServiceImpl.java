package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.CollectorFtpConfig;
import ru.obelisk.cucmaxl.database.models.repository.CollectorFtpConfigRepository;
import ru.obelisk.cucmaxl.database.models.service.CollectorFtpConfigService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
@Repository
@Transactional
public class CollectorFtpConfigServiceImpl implements CollectorFtpConfigService {
 
    @Autowired
    private CollectorFtpConfigRepository ftpConfigRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CollectorFtpConfig add(CollectorFtpConfig ftpConfig) {
    	return ftpConfigRepository.save(ftpConfig);
    }
 
    @Override
    @Transactional
    public CollectorFtpConfig edit(CollectorFtpConfig ftpConfig) {
    	return ftpConfigRepository.save(ftpConfig);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        ftpConfigRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CollectorFtpConfig> findAll() {
        return (List<CollectorFtpConfig>) ftpConfigRepository.findAll();
    }
    
    public DataTablesOutput<CollectorFtpConfig> findAll(DataTablesInput input){
    	return ftpConfigRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public CollectorFtpConfig findById(int id) {
		return ftpConfigRepository.findOne(id);
	}
}