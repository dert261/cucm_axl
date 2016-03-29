package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface LdapDirSyncParametersService {

	public LdapDirSyncParameters add(LdapDirSyncParameters ldapDirSyncParameters);
	public LdapDirSyncParameters edit(LdapDirSyncParameters ldapDirSyncParameters);
	void delete(int id);
	
	public LdapDirSyncParameters getLdapDirSyncParametersById(int id);
	public List<LdapDirSyncParameters> getAllLdapDirSyncParameters();
	public List<LdapDirSyncParameters> getAllLdapDirSyncParametersWithoutFriendship();
    
	public List<Select2Result> findLdapDirSyncParametersByTerm(String term);
	public List<LdapDirSyncParameters> findLdapDirSyncParametersWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
	LdapDirSyncParameters findById(int id);
}