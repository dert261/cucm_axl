package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;
import ru.obelisk.cucmaxl.database.models.repository.CucmLineRepository;
import ru.obelisk.cucmaxl.database.models.service.CucmLineService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
public class CucmLineServiceImpl implements CucmLineService {
 
    @Autowired
    private CucmLineRepository lineRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.LineService#addLine(ru.obelisk.cucmaxl.database.models.entity.Line)
	 */
    @Override
	
    public CucmLine addLine(CucmLine line) {
    	CucmLine savedLine = lineRepository.saveAndFlush(line);
        return savedLine;
    }
    
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.LineService#editLine(ru.obelisk.cucmaxl.database.models.entity.Line)
	 */
    @Override
	
    public CucmLine editLine(CucmLine formLine) {
    	return lineRepository.saveAndFlush(formLine);
    }
 
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.LineService#deleteLine(int)
	 */
    @Override
	
    public void deleteLine(int id) {
    	lineRepository.delete(id);
    }
    
    public void deleteLine(CucmLine line) {
    	lineRepository.delete(line);
    }

	@Override
	public List<CucmLine> getAllLine() {
		return lineRepository.findAll();
	}
	
	@Override
	public List<CucmLine> findByPkCucmAxlPort(CucmAxlPort axlPort) {
		return lineRepository.findByPkCucmAxlPort(axlPort);
	}

	@Override
	public CucmLine findByPkID(String pkid) {
		return lineRepository.findByPkID(pkid);
	}
	
	@Override
	public CucmLine findByID(int id) {
		return lineRepository.findOne(id);
	}
}