package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.obelisk.cucmaxl.database.models.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	@Query("SELECT role FROM UserRole role")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<UserRole> findAll();
}
