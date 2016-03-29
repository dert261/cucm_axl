package ru.obelisk.cucmaxl.database.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.obelisk.cucmaxl.database.models.entity.LdapAuthenticationServer;

public interface LdapAuthenticationServerRepository extends JpaRepository<LdapAuthenticationServer, Integer>{

}
