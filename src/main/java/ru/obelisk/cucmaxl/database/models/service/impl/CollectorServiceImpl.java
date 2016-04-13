package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.Collector;
import ru.obelisk.cucmaxl.database.models.repository.CollectorRepository;
import ru.obelisk.cucmaxl.database.models.service.CollectorService;
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
public class CollectorServiceImpl implements CollectorService {
 
    @Autowired
    private CollectorRepository collectorRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Collector add(Collector collector) {
    	return collectorRepository.save(collector);
    }
 
    @Override
    @Transactional
    public Collector edit(Collector collector) {
    	return collectorRepository.save(collector);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        collectorRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<Collector> findAll() {
        return (List<Collector>) collectorRepository.findAll();
    }
    
    public DataTablesOutput<Collector> findAll(DataTablesInput input){
    	return collectorRepository.findAll(input);
    }
    
    public List<Collector> findAllWithRelations(){
    	return collectorRepository.findAllWithRelations();
    }
    
    @Override
    @Transactional(readOnly=true)
	public Collector findById(int id) {
		return collectorRepository.findById(id);
	}
    
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<Collector> findAllWithRelations(DataTablesInput input){
    	Specification<Collector> additionalSpecification = new Specification<Collector>() {
        	public Predicate toPredicate(Root<Collector> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
		     		return null;
			   		     	
		     	root.fetch("sourceAxlPort", JoinType.LEFT);
		     	root.fetch("collectorFtpConfig", JoinType.LEFT);
		     	return builder.conjunction();
   			}
		}; 
        return collectorRepository.findAll(input, additionalSpecification);
    }
}