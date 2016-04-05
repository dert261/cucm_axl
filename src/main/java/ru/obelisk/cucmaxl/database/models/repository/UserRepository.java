package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.obelisk.datatables.repository.DataTablesRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.database.models.entity.User;

public interface UserRepository extends DataTablesRepository<User, Integer> {


	@Query("SELECT b FROM User b LEFT JOIN FETCH b.roles "
			+ "LEFT JOIN FETCH b.ldapDirSyncParameters ldapDir "
			+ "LEFT JOIN FETCH ldapDir.ldapDirSyncServers ldapDirServers "
			+ "WHERE b.login = :login")
	User findByUsername(@Param("login") String login);
	
	@Query("SELECT b FROM User b WHERE b.adGuid = :objectGUID")
	User findByLdapObjectGUID(@Param("objectGUID") String objectGUID);
	
	@Query("SELECT b FROM User b LEFT JOIN FETCH b.roles WHERE b.name = :fname")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findByName(@Param("fname") String fname);
	
	@Query("SELECT b FROM User b LEFT JOIN FETCH b.roles WHERE b.id = :id")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findOne(@Param("id") int id);
	
	@Query("SELECT b FROM User b")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<User> findAll();
	
	@Query("SELECT user FROM User user LEFT JOIN FETCH user.ldapDirSyncParameters WHERE user.login = :userId AND user.ldapDirSyncParameters = :ldapDir")
	User findUserByUsernameAndLdapDir(@Param("userId") String userId, @Param("ldapDir") LdapDirSyncParameters ldapDir);
	
	@Query("SELECT user FROM User user LEFT JOIN FETCH user.ldapDirSyncParameters WHERE user.ldapDirSyncParameters = :ldapDir")
	List<User> findUsersByLdapDir(@Param("ldapDir") LdapDirSyncParameters ldapDir);
	
	@Query("SELECT user FROM User user"
			+ " LEFT JOIN FETCH user.phoneBook phoneBook"
			+ " LEFT JOIN FETCH user.ldapDirSyncParameters ldapDir"
			+ " LEFT JOIN FETCH user.devices dev"
			+ " LEFT JOIN FETCH dev.lines devLine"
			+ " LEFT JOIN FETCH devLine.line line"
		+ " WHERE"
			+ " (phoneBook.uploadPhone = true OR phoneBook.uploadPhone IS NULL)"
			+ " AND (devLine.lineIndex = '1' OR devLine.lineIndex IS NULL)"
			+ " AND user.ldapDirSyncParameters = :ldapParams"
			//+ " AND ldapDir = :ldapParams"
		+" ORDER BY user.id, devLine.lineIndex"
	)
	List<User> findAllForADSyncByLdapSource(@Param("ldapParams") LdapDirSyncParameters ldapParams);
	
	@Query("SELECT user FROM User user"
			+ " LEFT JOIN FETCH user.phoneBook phoneBook"
			+ " LEFT JOIN FETCH user.ldapDirSyncParameters ldapDir"
			+ " LEFT JOIN FETCH user.devices dev"
			+ " LEFT JOIN FETCH dev.lines devLine"
			+ " LEFT JOIN FETCH devLine.line line"
		+ " WHERE"
			+ " (phoneBook.uploadPhone = true OR phoneBook.uploadPhone IS NULL)"
			+ " AND (devLine.lineIndex = '1' OR devLine.lineIndex IS NULL)"
			+ " AND ldapDir.fqdnName = :fqdn"
			+" ORDER BY user.id, devLine.lineIndex"
	)
	List<User> findAllForADSyncByFqdn(@Param("fqdn") String fqdn);
	
}
