package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

public interface UserService {

	public User addUser(User user);
	public User editUser(User user);
	void deleteUser(int id);
	
	public User add(User user);
	public User edit(User user);
	
	public User getUserByName(String name);
	public User getUserByUsername(String username);
	public User getUserById(int id);
	public User getUserByLdapObjectGuid(String objectGUID);
	public List<User> getAllUsers();
	public User getUserByUsernameAndLdapDir(String username, LdapDirSyncParameters ldapDir);
	public List<User> getUsersByLdapDir(LdapDirSyncParameters ldapDir);
	
	public List<User> findAllForADSyncByLdapSource(LdapDirSyncParameters ldapDir);
	public List<User> findAllForADSyncByFqdn(String fqdn);
    
	public List<Select2Result> findUserByTerm(String term);
	public List<Select2Result> findCucmUserByTerm(String term);
	
	public List<User> findUserWithDatatablesCriterias(DatatablesCriterias criterias);
	public Long getFilteredCount(DatatablesCriterias criterias);
	public Long getTotalCount();
	
	
	public DataTablesOutput<User> findAllWithSpecs(DataTablesInput input);
	public DataTablesOutput<User> findAll(DataTablesInput input);
	
	
}