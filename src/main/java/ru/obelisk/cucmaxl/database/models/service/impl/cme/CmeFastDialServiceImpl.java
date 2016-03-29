package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeFastDial;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeFastDialRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeFastDialService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
@Repository
@Transactional
public class CmeFastDialServiceImpl implements CmeFastDialService {
 
    @Autowired
    private CmeFastDialRepository cmeFastDialRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeFastDial add(CmeFastDial cmeFastDial) {
    	return cmeFastDialRepository.save(cmeFastDial);
         
    }
 
    @Override
    @Transactional
    public CmeFastDial edit(CmeFastDial cmeFastDial) {
    	return cmeFastDialRepository.save(cmeFastDial);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeFastDialRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeFastDial> findAll() {
        return cmeFastDialRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeFastDial findById(int id) {
		return cmeFastDialRepository.findByID(id);
	}
}