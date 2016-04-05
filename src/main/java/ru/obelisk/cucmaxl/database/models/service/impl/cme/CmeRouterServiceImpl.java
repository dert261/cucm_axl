package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeRouterRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeRouterService;
import ru.obelisk.cucmaxl.database.models.service.utils.cme.CmeRouterServiceUtils;
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
public class CmeRouterServiceImpl implements CmeRouterService {
 
    @Autowired
    private CmeRouterRepository cmeRouterRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeRouter add(CmeRouter cmeRouter) {
    	//return cmeRouterRepository.saveAndFlush(cmeRouter);
    	return cmeRouterRepository.save(cmeRouter);
         
    }
 
    @Override
    @Transactional
    public CmeRouter edit(CmeRouter cmeRouter) {
    	//return cmeRouterRepository.saveAndFlush(cmeRouter);
    	return cmeRouterRepository.save(cmeRouter);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeRouterRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeRouter> findAll() {
        return cmeRouterRepository.findAll();
    }
    
    public DataTablesOutput<CmeRouter> findAll(DataTablesInput input){
    	return cmeRouterRepository.findAll(input);
    }

    @Override
    @Transactional(readOnly=true)
	public CmeRouter findById(int id) {
		return cmeRouterRepository.findOne(id);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cmeRouter.id, cmeRouter.name) FROM CmeRouter cmeRouter" 
                		+ " WHERE "
                        + " LOWER(cmeRouter.name) LIKE :term", Select2Result.class)
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
	public List<CmeRouter> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeRouter FROM CmeRouter cmeRouter");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CmeRouterServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("cmeRouter." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<CmeRouter> query = entityManager.createQuery(queryBuilder.toString(), CmeRouter.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CmeRouter> idGenerate(List<CmeRouter> cmeRouters, int start){
		
		for(int i=0;i<cmeRouters.size();i++){
			cmeRouters.get(i).setNumberLocalized(start+i+1);
		}
		return cmeRouters;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeRouter FROM CmeRouter cmeRouter");
		queryBuilder.append(CmeRouterServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(cmeRouter) FROM CmeRouter cmeRouter");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}