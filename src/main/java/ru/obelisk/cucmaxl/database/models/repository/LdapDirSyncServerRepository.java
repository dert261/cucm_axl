package ru.obelisk.cucmaxl.database.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncServer;

public interface LdapDirSyncServerRepository extends JpaRepository<LdapDirSyncServer, Integer>{

}
