package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.LdapCustomFilter;
import ru.obelisk.cucmaxl.database.models.repository.LdapCustomFilterRepository;
import ru.obelisk.cucmaxl.database.models.service.LdapCustomFilterService;
import ru.obelisk.cucmaxl.database.models.service.utils.LdapCustomFilterServiceUtils;
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
public class LdapCustomFilterServiceImpl implements LdapCustomFilterService {
 
    @Autowired
    private LdapCustomFilterRepository ldapCustomFilterRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public LdapCustomFilter add(LdapCustomFilter ldapCustomFilter) {
    	return ldapCustomFilterRepository.saveAndFlush(ldapCustomFilter);
         
    }
 
    @Override
    @Transactional
    public LdapCustomFilter edit(LdapCustomFilter ldapCustomFilter) {
    	return ldapCustomFilterRepository.saveAndFlush(ldapCustomFilter);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        ldapCustomFilterRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<LdapCustomFilter> getAllLdapCustomFilters() {
        return ldapCustomFilterRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public LdapCustomFilter getLdapCustomFilterById(int id) {
		return ldapCustomFilterRepository.findOne(id);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findLdapCustomFilterByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(ldapCustomFilter.id, ldapCustomFilter.name) FROM LdapCustomFilter ldapCustomFilter" 
                		+ " WHERE "
                        + " LOWER(ldapCustomFilter.name) LIKE :term", Select2Result.class)
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
	public List<LdapCustomFilter> findLdapCustomFilterWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT ldapCustomFilter FROM LdapCustomFilter ldapCustomFilter");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(LdapCustomFilterServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("ldapCustomFilter." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<LdapCustomFilter> query = entityManager.createQuery(queryBuilder.toString(), LdapCustomFilter.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<LdapCustomFilter> idGenerate(List<LdapCustomFilter> ldapCustomFilters, int start){
		
		for(int i=0;i<ldapCustomFilters.size();i++){
			ldapCustomFilters.get(i).setNumberLocalized(start+i+1);
		}
		return ldapCustomFilters;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT ldapCustomFilter FROM LdapCustomFilter ldapCustomFilter");
		queryBuilder.append(LdapCustomFilterServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(ldapCustomFilter) FROM LdapCustomFilter ldapCustomFilter");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}