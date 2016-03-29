package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.JobResult;
import ru.obelisk.cucmaxl.database.models.repository.JobResultRepository;
import ru.obelisk.cucmaxl.database.models.service.JobResultService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

 
@Service
@Repository
@Transactional
public class JobResultServiceImpl implements JobResultService {
 
    @Autowired
    private JobResultRepository jobResultsRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public JobResult add(JobResult jobResults) {
    	return jobResultsRepository.save(jobResults);
         
    }
 
    @Override
    @Transactional
    public JobResult edit(JobResult jobResults) {
    	return jobResultsRepository.save(jobResults);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        jobResultsRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<JobResult> findAll() {
        return (List<JobResult>) jobResultsRepository.findAll();
    }
    
    public DataTablesOutput<JobResult> findAll(DataTablesInput input){
    	return jobResultsRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public JobResult findById(int id) {
		return jobResultsRepository.findOne(id);
	}
}