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

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeDevice;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeExtMapStatus;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.repository.cme.CmeDeviceRepository;
import ru.obelisk.cucmaxl.database.models.service.cme.CmeDeviceService;
import ru.obelisk.cucmaxl.database.models.service.utils.cme.CmeDeviceServiceUtils;
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
public class CmeDeviceServiceImpl implements CmeDeviceService {
 
    @Autowired
    private CmeDeviceRepository cmeDeviceRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public CmeDevice add(CmeDevice cmeDevice) {
    	return cmeDeviceRepository.save(cmeDevice);
         
    }
 
    @Override
    @Transactional
    public CmeDevice edit(CmeDevice cmeDevice) {
    	return cmeDeviceRepository.save(cmeDevice);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        cmeDeviceRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<CmeDevice> findAll() {
        return cmeDeviceRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public CmeDevice findById(int id) {
		return cmeDeviceRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
	public CmeDevice findByIdWithRelations(int id) {
		return cmeDeviceRepository.findByID(id);
	}
    
    @Override
    @Transactional(readOnly=true)
	public List<CmeDevice> findAllWithRelations() {
		return cmeDeviceRepository.findAllWithRelations();
	}
    
    @Override
    @Transactional(readOnly=true)
	public List<CmeDevice> findAllByRouterWithRelations(CmeRouter router) {
		return cmeDeviceRepository.findAllByRouterWithRelations(router);
	}
    
    @Override
    @Transactional(readOnly=true)
	public List<CmeDevice> findAllForExportByRouterWithRelations(CmeRouter router) {
		return cmeDeviceRepository.findAllForExportByRouterWithRelations(router);
	}
        
    @Override
    @Transactional(readOnly=true)
	public CmeDevice findByNameAndRouter(String name, CmeRouter router) {
		return cmeDeviceRepository.findByNameAndRouter(name, router);
	}
	
    @Override
    @Transactional(readOnly=true)
    @Lock(LockModeType.NONE)
	public DataTablesOutput<CmeDevice> findAll(DataTablesInput input){
    	return cmeDeviceRepository.findAll(input);
    }
	
    @Override
    @Transactional(readOnly=true)
	public DataTablesOutput<CmeDevice> findAllByRouter(DataTablesInput input, final CmeRouter router){
    	Specification<CmeDevice> additionalSpecification = new Specification<CmeDevice>() {
        	public Predicate toPredicate(Root<CmeDevice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   			
   		     	Fetch<CmeDevice, CmeExtMapStatus> cmeExtMap = root.fetch("lines", JoinType.LEFT);
		     	cmeExtMap.fetch("extension", JoinType.LEFT).fetch("customExtension", JoinType.LEFT);
		     	root.fetch("speedDials", JoinType.LEFT);
   		     	root.fetch("blfSpeedDials", JoinType.LEFT);
   		     	root.fetch("fastDials", JoinType.LEFT);
   		     	root.fetch("addonModules", JoinType.LEFT);
   		     	root.fetch("customDevice", JoinType.LEFT).fetch("user", JoinType.LEFT);
   		     	
   		     	return builder.equal(root.get("router"), router);
            }
		}; 
        return cmeDeviceRepository.findAll(input, additionalSpecification);
    }
    
    
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(cmeDevice.id, cmeDevice.name) FROM CmeDevice cmeDevice" 
                		+ " WHERE "
                        + " LOWER(cmeDevice.name) LIKE :term", Select2Result.class)
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
	public List<CmeDevice> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeDevice FROM CmeDevice cmeDevice");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(CmeDeviceServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("cmeDevice." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<CmeDevice> query = entityManager.createQuery(queryBuilder.toString(), CmeDevice.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<CmeDevice> idGenerate(List<CmeDevice> cmeDevices, int start){
		
		for(int i=0;i<cmeDevices.size();i++){
			cmeDevices.get(i).setNumberLocalized(start+i+1);
		}
		return cmeDevices;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT cmeDevice FROM CmeDevice cmeDevice");
		queryBuilder.append(CmeDeviceServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(cmeDevice) FROM CmeDevice cmeDevice");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}