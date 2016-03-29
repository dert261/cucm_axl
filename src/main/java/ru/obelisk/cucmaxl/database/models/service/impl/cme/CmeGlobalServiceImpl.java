package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeGlobal;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeGlobalRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeGlobalService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
@Repository
@Transactional
public class CmeGlobalServiceImpl implements CmeGlobalService {
 
    @Autowired
    private CmeGlobalRepository cmeGlobalRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeGlobal add(CmeGlobal cmeGlobal) {
    	return cmeGlobalRepository.save(cmeGlobal);
         
    }
 
    @Override
    @Transactional
    public CmeGlobal edit(CmeGlobal cmeGlobal) {
    	return cmeGlobalRepository.save(cmeGlobal);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeGlobalRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeGlobal> findAll() {
        return cmeGlobalRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeGlobal findById(int id) {
		return cmeGlobalRepository.findByID(id);
	}
}