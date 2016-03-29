package ru.obelisk.cucmaxl.database.models.service.impl.jobs;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.jobs.ChangeNumberDetail;
import ru.obelisk.cucmaxl.database.models.repository.jobs.ChangeNumberDetailRepository;
import ru.obelisk.cucmaxl.database.models.service.jobs.ChangeNumberDetailService;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


 
@Service
@Repository
@Transactional
public class ChangeNumberDetailServiceImpl implements ChangeNumberDetailService {
 
    @Autowired
    private ChangeNumberDetailRepository changeNumberDetailsRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public ChangeNumberDetail add(ChangeNumberDetail changeNumberDetails) {
    	return changeNumberDetailsRepository.save(changeNumberDetails);
         
    }
 
    @Override
    @Transactional
    public ChangeNumberDetail edit(ChangeNumberDetail changeNumberDetails) {
    	return changeNumberDetailsRepository.save(changeNumberDetails);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        changeNumberDetailsRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<ChangeNumberDetail> findAll() {
        return (List<ChangeNumberDetail>) changeNumberDetailsRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
	public ChangeNumberDetail findById(int id) {
		return changeNumberDetailsRepository.findOne(id);
	}
}