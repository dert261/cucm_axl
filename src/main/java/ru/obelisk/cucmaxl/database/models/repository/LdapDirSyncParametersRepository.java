package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;

public interface LdapDirSyncParametersRepository extends JpaRepository<LdapDirSyncParameters, Integer> {
	@Query("SELECT ldapDirSyncParameters FROM LdapDirSyncParameters ldapDirSyncParameters"
			+ " LEFT JOIN FETCH ldapDirSyncParameters.ldapDirSyncServers"
			+ " LEFT JOIN FETCH ldapDirSyncParameters.ldapCustomFilter")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<LdapDirSyncParameters> findAll();
	
	@Query("SELECT ldapDirSyncParameters FROM LdapDirSyncParameters ldapDirSyncParameters")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<LdapDirSyncParameters> findAllWithoutFriendship();
		
	@Query("SELECT ldapDirSyncParameters FROM LdapDirSyncParameters ldapDirSyncParameters"
			+ " LEFT JOIN FETCH ldapDirSyncParameters.ldapDirSyncServers"
			+ " LEFT JOIN FETCH ldapDirSyncParameters.ldapCustomFilter"
			+ " WHERE ldapDirSyncParameters.id = :id")
	//@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	LdapDirSyncParameters findById(@Param("id") Integer id);
}
