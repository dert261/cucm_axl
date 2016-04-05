package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeVoiceHuntGroup;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeVoiceHuntGroupRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeVoiceHuntGroupService;
import ru.obelisk.cucmaxl.database.models.service.utils.cme.CmeVoiceHuntGroupServiceUtils;
import ru.obelisk.cucmaxl.web.ui.datatables.ColumnDef;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
 
@Service
@Repository
@Transactional
public class CmeVoiceHuntGroupServiceImpl implements CmeVoiceHuntGroupService {
 
    @Autowired
    private CmeVoiceHuntGroupRepository cmeVoiceHuntGroupRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeVoiceHuntGroup add(CmeVoiceHuntGroup cmeVoiceHuntGroup) {
    	return cmeVoiceHuntGroupRepository.save(cmeVoiceHuntGroup);
         
    }
 
    @Override
    @Transactional
    public CmeVoiceHuntGroup edit(CmeVoiceHuntGroup cmeVoiceHuntGroup) {
    	return cmeVoiceHuntGroupRepository.save(cmeVoiceHuntGroup);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeVoiceHuntGroupRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeVoiceHuntGroup> findAll() {
        return cmeVoiceHuntGroupRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeVoiceHuntGroup findById(int id) {
		return cmeVoiceHuntGroupRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
    @Lock(LockModeType.NONE)
	public CmeVoiceHuntGroup findByPilotNumberAndRouter(String pilotNumber, CmeRouter router) {
		return cmeVoiceHuntGroupRepository.findByPilotNumberAndRouter(pilotNumber, router);
	}
    
    @Override
    @Transactional(readOnly=true)
    @Lock(LockModeType.NONE)
	public DataTablesOutput<CmeVoiceHuntGroup> findAll(DataTablesInput input){
    	return cmeVoiceHuntGroupRepository.findAll(input);
    }
	
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<CmeVoiceHuntGroup> findAllByRouter(DataTablesInput input, final CmeRouter router){
    	Specification<CmeVoiceHuntGroup> additionalSpecification = new Specification<CmeVoiceHuntGroup>() {
    		public Predicate toPredicate(Root<CmeVoiceHuntGroup> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    			Class<?> clazz = query.getResultType();
    		    if (clazz.equals(Long.class) || clazz.equals(long.class))
    		    	return null;
    			
    			root.fetch("numbers", JoinType.LEFT);
    			return builder.equal(root.get("router"), router);
            }
		}; 
        return cmeVoiceHuntGroupRepository.findAll(input, additionalSpecification);
    }
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cmeVoiceHuntGroup.id, cmeVoiceHuntGroup.name) FROM CmeVoiceHuntGroup cmeVoiceHuntGroup" 
                		+ " WHERE "
                        + " LOWER(cmeVoiceHuntGroup.name) LIKE :term", Select2Result.class)
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
	public List<CmeVoiceHuntGroup> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeVoiceHuntGroup FROM CmeVoiceHuntGroup cmeVoiceHuntGroup");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CmeVoiceHuntGroupServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("cmeVoiceHuntGroup." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<CmeVoiceHuntGroup> query = entityManager.createQuery(queryBuilder.toString(), CmeVoiceHuntGroup.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CmeVoiceHuntGroup> idGenerate(List<CmeVoiceHuntGroup> cmeVoiceHuntGroups, int start){
		
		for(int i=0;i<cmeVoiceHuntGroups.size();i++){
			cmeVoiceHuntGroups.get(i).setNumberLocalized(start+i+1);
		}
		return cmeVoiceHuntGroups;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeVoiceHuntGroup FROM CmeVoiceHuntGroup cmeVoiceHuntGroup");
		queryBuilder.append(CmeVoiceHuntGroupServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(cmeVoiceHuntGroup) FROM CmeVoiceHuntGroup cmeVoiceHuntGroup");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}