package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.CallDetailRecord;
import ru.obelisk.cucmaxl.database.models.repository.CallDetailRecordRepository;
import ru.obelisk.cucmaxl.database.models.service.CallDetailRecordService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

 
@Service
@Repository
@Transactional
public class CallDetailRecordServiceImpl implements CallDetailRecordService {
 
    @Autowired
    private CallDetailRecordRepository cdrRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CallDetailRecord add(CallDetailRecord cdr) {
    	return cdrRepository.save(cdr);
         
    }
 
    @Override
    @Transactional
    public CallDetailRecord edit(CallDetailRecord cdr) {
    	return cdrRepository.save(cdr);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cdrRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CallDetailRecord> findAll() {
        return (List<CallDetailRecord>) cdrRepository.findAll();
    }
    
    public DataTablesOutput<CallDetailRecord> findAll(DataTablesInput input){
    	return cdrRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<CallDetailRecord> findAllWithRelations(DataTablesInput input){
    	Specification<CallDetailRecord> additionalSpecification = new Specification<CallDetailRecord>() {
        	public Predicate toPredicate(Root<CallDetailRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   			   	return builder.conjunction();
            }
		}; 
        return cdrRepository.findAll(input, additionalSpecification);
    }

    @Override
    @Transactional(readOnly=true)
	public CallDetailRecord findById(int id) {
		return cdrRepository.findOne(id);
	}
}