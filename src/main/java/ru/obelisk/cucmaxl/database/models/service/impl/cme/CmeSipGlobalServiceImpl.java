package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipGlobal;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeSipGlobalRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeSipGlobalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
@Repository
@Transactional
public class CmeSipGlobalServiceImpl implements CmeSipGlobalService {
 
    @Autowired
    private CmeSipGlobalRepository cmeSipGlobalRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeSipGlobal add(CmeSipGlobal cmeSipGlobal) {
    	return cmeSipGlobalRepository.save(cmeSipGlobal);
         
    }
 
    @Override
    @Transactional
    public CmeSipGlobal edit(CmeSipGlobal cmeSipGlobal) {
    	return cmeSipGlobalRepository.save(cmeSipGlobal);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeSipGlobalRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeSipGlobal> findAll() {
        return cmeSipGlobalRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeSipGlobal findById(int id) {
		return cmeSipGlobalRepository.findByID(id);
	}
}