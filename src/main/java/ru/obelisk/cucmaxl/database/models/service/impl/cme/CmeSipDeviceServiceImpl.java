package ru.obelisk.cucmaxl.database.models.service.impl.cme;

import org.springframework.beans.factory.annotation.Autowired;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeSipExtMapStatus;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeSipDeviceRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeSipDeviceService;
import ru.obelisk.cucmaxl.database.models.service.utils.cme.CmeSipDeviceServiceUtils;
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
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
 
@Service
@Repository
@Transactional
public class CmeSipDeviceServiceImpl implements CmeSipDeviceService {
 
    @Autowired
    private CmeSipDeviceRepository cmeSipDeviceRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeSipDevice add(CmeSipDevice cmeSipDevice) {
    	return cmeSipDeviceRepository.save(cmeSipDevice);
         
    }
 
    @Override
    @Transactional
    public CmeSipDevice edit(CmeSipDevice cmeSipDevice) {
    	return cmeSipDeviceRepository.save(cmeSipDevice);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeSipDeviceRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeSipDevice> findAll() {
        return cmeSipDeviceRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<CmeSipDevice> findAllForExportByRouterWithRelations(CmeRouter router){
        return cmeSipDeviceRepository.findAllForExportByRouterWithRelations(router);
    }
    
    @Override
    @Transactional(readOnly=true)
    @Lock(LockModeType.NONE)
	public DataTablesOutput<CmeSipDevice> findAll(DataTablesInput input){
    	return cmeSipDeviceRepository.findAll(input);
    }
    
   
	
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<CmeSipDevice> findAllByRouter(DataTablesInput input, final CmeRouter router){
    	Specification<CmeSipDevice> additionalSpecification = new Specification<CmeSipDevice>() {
        	public Predicate toPredicate(Root<CmeSipDevice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   		     		
   			
   		     	Fetch<CmeSipDevice, CmeSipExtMapStatus> cmeSipExtMap = root.fetch("lines", JoinType.LEFT);
   		     	cmeSipExtMap.fetch("extension", JoinType.LEFT).fetch("customSipExtension", JoinType.LEFT);
   		     	root.fetch("customSipDevice", JoinType.LEFT).fetch("user", JoinType.LEFT);
   		     	
   		        		     	
   		     	return builder.equal(root.get("router"), router);
            }
		}; 
        return cmeSipDeviceRepository.findAll(input, additionalSpecification);
    }
    
    

    @Override
    @Transactional(readOnly=true)
	public CmeSipDevice findById(int id) {
		return cmeSipDeviceRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
	public CmeSipDevice findByNameAndRouter(String name, CmeRouter router) {
		return cmeSipDeviceRepository.findByNameAndRouter(name, router);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cmeSipDevice.id, cmeSipDevice.name) FROM CmeSipDevice cmeSipDevice" 
                		+ " WHERE "
                        + " cmeSipDevice.name LIKE :term", Select2Result.class)
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
	public List<CmeSipDevice> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CmeSipDeviceServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("cmeSipDevice." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<CmeSipDevice> query = entityManager.createQuery(queryBuilder.toString(), CmeSipDevice.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CmeSipDevice> idGenerate(List<CmeSipDevice> cmeSipDevices, int start){
		
		for(int i=0;i<cmeSipDevices.size();i++){
			cmeSipDevices.get(i).setNumberLocalized(start+i+1);
		}
		return cmeSipDevices;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeSipDevice FROM CmeSipDevice cmeSipDevice");
		queryBuilder.append(CmeSipDeviceServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(cmeSipDevice) FROM CmeSipDevice cmeSipDevice");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}