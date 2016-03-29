package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.repository.CucmAxlPortRepository;
import ru.obelisk.cucmaxl.database.models.service.CucmAxlPortService;
import ru.obelisk.cucmaxl.database.models.service.utils.CucmAxlPortServiceUtils;
import ru.obelisk.cucmaxl.web.ui.datatables.ColumnDef;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
 
@Service
@Repository
@Transactional
public class CucmAxlPortServiceImpl implements CucmAxlPortService {
 
    @Autowired
    private CucmAxlPortRepository cucmAxlPortRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CucmAxlPort add(CucmAxlPort cucmAxlPort) {
    	return cucmAxlPortRepository.saveAndFlush(cucmAxlPort);
         
    }
 
    @Override
    @Transactional
    public CucmAxlPort edit(CucmAxlPort cucmAxlPort) {
    	return cucmAxlPortRepository.saveAndFlush(cucmAxlPort);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cucmAxlPortRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CucmAxlPort> getAllCucmAxlPorts() {
        return cucmAxlPortRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CucmAxlPort getCucmAxlPortById(int id) {
		return cucmAxlPortRepository.findById(id);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findCucmAxlPortByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cucmAxlPort.id, cucmAxlPort.name) FROM CucmAxlPort cucmAxlPort" 
                		+ " WHERE "
                        + " cucmAxlPort.name LIKE :term", Select2Result.class)
        .setParameter("term", "%" + term.toLowerCase() + "%")
        .setHint("org.hibernate.cacheable", true)
        .getResultList();
        return reultList;
	}
	
	/**
	* <p>
	* Query used to populate the DataTables that display the list of persons.
	*
	* @param criterias
	* The DataTables criterias used to filter the persons.
	* (maxResult, filtering, paging, ...)
	* @return a filtered list of persons.
	*/
    @Override
    @Transactional(readOnly=true)
	public List<CucmAxlPort> findCucmAxlPortWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cucmAxlPort FROM CucmAxlPort cucmAxlPort");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CucmAxlPortServiceUtils.getFilterQuery(criterias));
				
		/**
		* Step 2: sorting
		*/
		if (criterias.hasOneSortedColumn()) {
			List<String> orderParams = new ArrayList<String>();
			for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
				String columnName = null;
				if(columnDef.getName().endsWith("Localized")){
					columnName=columnDef.getName().replaceAll("Localized", "");
				} else {
					columnName=columnDef.getName();
				}
				orderParams.add("cucmAxlPort." + columnName + " " + columnDef.getSortDirection());
			}
			if(!orderParams.isEmpty()){
				queryBuilder.append(" ORDER BY ");
				Iterator<String> itr2 = orderParams.iterator();
				while (itr2.hasNext()) {
					queryBuilder.append(itr2.next());
					if (itr2.hasNext()) {
						queryBuilder.append(" , ");
					}
				}
			}
		}
		
		TypedQuery<CucmAxlPort> query = entityManager.createQuery(queryBuilder.toString(), CucmAxlPort.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CucmAxlPort> idGenerate(List<CucmAxlPort> cucmAxlPorts, int start){
		
		for(int i=0;i<cucmAxlPorts.size();i++){
			cucmAxlPorts.get(i).setNumberLocalized(start+i+1);
		}
		return cucmAxlPorts;
	}
	/**
	* <p>
	* Query used to return the number of filtered persons.
	*
	* @param criterias
	* The DataTables criterias used to filter the persons.
	* (maxResult, filtering, paging, ...)
	* @return the number of filtered persons.
	*/
	@Override
    @Transactional(readOnly=true)
	public Long getFilteredCount(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cucmAxlPort FROM CucmAxlPort cucmAxlPort");
		queryBuilder.append(CucmAxlPortServiceUtils.getFilterQuery(criterias));
		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setHint("org.hibernate.cacheable", true);
		return Long.parseLong(String.valueOf(query.getResultList().size()));
	}
	/**
	* @return the total count of persons.
	*/
	@Override
    @Transactional(readOnly=true)
	public Long getTotalCount() {
		Query query = entityManager.createQuery("SELECT COUNT(cucmAxlPort) FROM CucmAxlPort cucmAxlPort");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}