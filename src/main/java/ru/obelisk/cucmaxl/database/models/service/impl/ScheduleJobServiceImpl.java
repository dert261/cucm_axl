package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.ScheduleJob;
import ru.obelisk.cucmaxl.database.models.repository.ScheduleJobRepository;
import ru.obelisk.cucmaxl.database.models.service.ScheduleJobService;
import ru.obelisk.cucmaxl.database.models.service.utils.ScheduleJobServiceUtils;
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
public class ScheduleJobServiceImpl implements ScheduleJobService {
 
    @Autowired
    private ScheduleJobRepository scheduleJobRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public ScheduleJob add(ScheduleJob scheduleJob) {
    	return scheduleJobRepository.saveAndFlush(scheduleJob);
         
    }
 
    @Override
    @Transactional
    public ScheduleJob edit(ScheduleJob scheduleJob) {
    	return scheduleJobRepository.saveAndFlush(scheduleJob);
    }
    
    @Override
    @Transactional
    public void delete(int id) {
        scheduleJobRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public List<ScheduleJob> getAllScheduleJobs() {
        return scheduleJobRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public ScheduleJob getScheduleJobById(int id) {
		return scheduleJobRepository.findOne(id);
	}
	
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findScheduleJobByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(scheduleJob.id, scheduleJob.name) FROM ScheduleJob scheduleJob" 
                		+ " WHERE "
                        + " LOWER(scheduleJob.name) LIKE :term", Select2Result.class)
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
	public List<ScheduleJob> findScheduleJobWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT scheduleJob FROM ScheduleJob scheduleJob");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(ScheduleJobServiceUtils.getFilterQuery(criterias));
				
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
				orderParams.add("scheduleJob." + columnName + " " + columnDef.getSortDirection());
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
		
		TypedQuery<ScheduleJob> query = entityManager.createQuery(queryBuilder.toString(), ScheduleJob.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<ScheduleJob> idGenerate(List<ScheduleJob> scheduleJobs, int start){
		
		for(int i=0;i<scheduleJobs.size();i++){
			scheduleJobs.get(i).setNumberLocalized(start+i+1);
		}
		return scheduleJobs;
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
		StringBuilder queryBuilder = new StringBuilder("SELECT scheduleJob FROM ScheduleJob scheduleJob");
		queryBuilder.append(ScheduleJobServiceUtils.getFilterQuery(criterias));
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
		Query query = entityManager.createQuery("SELECT COUNT(scheduleJob) FROM ScheduleJob scheduleJob");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}
}