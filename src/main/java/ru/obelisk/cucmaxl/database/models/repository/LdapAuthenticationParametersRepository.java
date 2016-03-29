package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import ru.obelisk.cucmaxl.database.models.entity.LdapAuthenticationParameters;

public interface LdapAuthenticationParametersRepository extends JpaRepository<LdapAuthenticationParameters, Integer> {
	@Query("SELECT params FROM LdapAuthenticationParameters params LEFT JOIN FETCH params.ldapServers")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<LdapAuthenticationParameters> findAll();
}
