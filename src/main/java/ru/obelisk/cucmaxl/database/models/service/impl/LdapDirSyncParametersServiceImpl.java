package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.database.models.repository.LdapDirSyncParametersRepository;
import ru.obelisk.cucmaxl.database.models.service.LdapDirSyncParametersService;
import ru.obelisk.cucmaxl.database.models.service.utils.LdapDirSyncParametersServiceUtils;
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
public class LdapDirSyncParametersServiceImpl implements LdapDirSyncParametersService {
 
    @Autowired
    private LdapDirSyncParametersRepository ldapDirSyncParametersRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public LdapDirSyncParameters add(LdapDirSyncParameters ldapDirSyncParameters) {
    	return ldapDirSyncParametersRepository.saveAndFlush(ldapDirSyncParameters);
         
    }
 
    @Override
    @Transactional
    public LdapDirSyncParameters edit(LdapDirSyncParameters ldapDirSyncParameters) {
    	return ldapDirSyncParametersRepository.saveAndFlush(ldapDirSyncParameters);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        ldapDirSyncParametersRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<LdapDirSyncParameters> getAllLdapDirSyncParameters() {
        return ldapDirSyncParametersRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<LdapDirSyncParameters> getAllLdapDirSyncParametersWithoutFriendship() {
        return ldapDirSyncParametersRepository.findAllWithoutFriendship();
    }

    @Override
    @Transactional(readOnly=true)
	public LdapDirSyncParameters getLdapDirSyncParametersById(int id) {
		return ldapDirSyncParametersRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
	public LdapDirSyncParameters findById(int id) {
		return ldapDirSyncParametersRepository.findById(id);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findLdapDirSyncParametersByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(ldapDirSyncParameters.id, ldapDirSyncParameters.configName) FROM LdapDirSyncParameters ldapDirSyncParameters" 
                		+ " WHERE "
                        + " ldapDirSyncParameters.configName LIKE :term", Select2Result.class)
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
	public List<LdapDirSyncParameters> findLdapDirSyncParametersWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT ldapDirSyncParameters FROM LdapDirSyncParameters ldapDirSyncParameters");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(LdapDirSyncParametersServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("ldapDirSyncParameters." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<LdapDirSyncParameters> query = entityManager.createQuery(queryBuilder.toString(), LdapDirSyncParameters.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<LdapDirSyncParameters> idGenerate(List<LdapDirSyncParameters> ldapDirSyncParameterss, int start){
		
		for(int i=0;i<ldapDirSyncParameterss.size();i++){
			ldapDirSyncParameterss.get(i).setNumberLocalized(start+i+1);
		}
		return ldapDirSyncParameterss;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT ldapDirSyncParameters FROM LdapDirSyncParameters ldapDirSyncParameters");
		queryBuilder.append(LdapDirSyncParametersServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(ldapDirSyncParameters) FROM LdapDirSyncParameters ldapDirSyncParameters");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}