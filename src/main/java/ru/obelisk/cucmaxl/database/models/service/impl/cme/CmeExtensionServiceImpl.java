package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtension;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeExtensionRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeExtensionService;
import ru.obelisk.cucmaxl.database.models.service.utils.cme.CmeExtensionServiceUtils;
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
public class CmeExtensionServiceImpl implements CmeExtensionService {
 
    @Autowired
    private CmeExtensionRepository cmeExtensionRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeExtension add(CmeExtension cmeExtension) {
    	return cmeExtensionRepository.save(cmeExtension);
         
    }
 
    @Override
    @Transactional
    public CmeExtension edit(CmeExtension cmeExtension) {
    	return cmeExtensionRepository.save(cmeExtension);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeExtensionRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeExtension> findAll() {
        return cmeExtensionRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeExtension findById(int id) {
		return cmeExtensionRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
    public CmeExtension findByNumberAndRouter(String number, CmeRouter router){
    	return cmeExtensionRepository.findByNumberAndRouter(number, router);
    }
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cmeExtension.id, cmeExtension.name) FROM CmeExtension cmeExtension" 
                		+ " WHERE "
                        + " cmeExtension.name LIKE :term", Select2Result.class)
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
	public List<CmeExtension> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeExtension FROM CmeExtension cmeExtension");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CmeExtensionServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("cmeExtension." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<CmeExtension> query = entityManager.createQuery(queryBuilder.toString(), CmeExtension.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CmeExtension> idGenerate(List<CmeExtension> cmeExtensions, int start){
		
		for(int i=0;i<cmeExtensions.size();i++){
			cmeExtensions.get(i).setNumberLocalized(start+i+1);
		}
		return cmeExtensions;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeExtension FROM CmeExtension cmeExtension");
		queryBuilder.append(CmeExtensionServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(cmeExtension) FROM CmeExtension cmeExtension");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}