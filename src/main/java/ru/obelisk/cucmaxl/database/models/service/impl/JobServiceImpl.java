package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.Job;
import ru.obelisk.cucmaxl.database.models.repository.JobRepository;
import ru.obelisk.cucmaxl.database.models.service.JobService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

 
@Service
@Repository
@Transactional
public class JobServiceImpl implements JobService {
 
    @Autowired
    private JobRepository jobsRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Job add(Job jobs) {
    	return jobsRepository.save(jobs);
         
    }
 
    @Override
    @Transactional
    public Job edit(Job jobs) {
    	return jobsRepository.save(jobs);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        jobsRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<Job> findAll() {
        return (List<Job>) jobsRepository.findAll();
    }
    
    public DataTablesOutput<Job> findAll(DataTablesInput input){
    	return jobsRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<Job> findAllWithRelations(DataTablesInput input){
    	Specification<Job> additionalSpecification = new Specification<Job>() {
        	public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   			   		     	
		     	root.fetch("user", JoinType.LEFT);
   		     	root.fetch("results", JoinType.LEFT);
   		     	return builder.conjunction();
            }
		}; 
        return jobsRepository.findAll(input, additionalSpecification);
    }

    @Override
    @Transactional(readOnly=true)
	public Job findById(int id) {
		return jobsRepository.findByID(id);
	}
}