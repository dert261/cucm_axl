package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.ScheduleJob;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface ScheduleJobService {

	public ScheduleJob add(ScheduleJob scheduleJob);
	public ScheduleJob edit(ScheduleJob scheduleJob);
	void delete(int id);
	
	public ScheduleJob getScheduleJobById(int id);
	public List<ScheduleJob> getAllScheduleJobs();
    
	public List<Select2Result> findScheduleJobByTerm(String term);
	public List<ScheduleJob> findScheduleJobWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}