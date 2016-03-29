package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.LdapCustomFilter;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface LdapCustomFilterService {

	public LdapCustomFilter add(LdapCustomFilter ldapCustomFilter);
	public LdapCustomFilter edit(LdapCustomFilter ldapCustomFilter);
	void delete(int id);
	
	public LdapCustomFilter getLdapCustomFilterById(int id);
	public List<LdapCustomFilter> getAllLdapCustomFilters();
    
	public List<Select2Result> findLdapCustomFilterByTerm(String term);
	public List<LdapCustomFilter> findLdapCustomFilterWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
}