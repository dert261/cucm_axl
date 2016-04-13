package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.CallManagementRecord;
import ru.obelisk.cucmaxl.database.models.repository.CallManagementRecordRepository;
import ru.obelisk.cucmaxl.database.models.service.CallManagementRecordService;
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
public class CallManagementRecordServiceImpl implements CallManagementRecordService {
 
    @Autowired
    private CallManagementRecordRepository cmrRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CallManagementRecord add(CallManagementRecord cmr) {
    	return cmrRepository.save(cmr);
         
    }
 
    @Override
    @Transactional
    public CallManagementRecord edit(CallManagementRecord cmr) {
    	return cmrRepository.save(cmr);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmrRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CallManagementRecord> findAll() {
        return (List<CallManagementRecord>) cmrRepository.findAll();
    }
    
    public DataTablesOutput<CallManagementRecord> findAll(DataTablesInput input){
    	return cmrRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<CallManagementRecord> findAllWithRelations(DataTablesInput input){
    	Specification<CallManagementRecord> additionalSpecification = new Specification<CallManagementRecord>() {
        	public Predicate toPredicate(Root<CallManagementRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   			   	return builder.conjunction();
            }
		}; 
        return cmrRepository.findAll(input, additionalSpecification);
    }

    @Override
    @Transactional(readOnly=true)
	public CallManagementRecord findById(int id) {
		return cmrRepository.findOne(id);
	}
}