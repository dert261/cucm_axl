package ru.obelisk.cucmaxl.database.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.obelisk.cucmaxl.database.models.entity.LdapCustomFilter;

public interface LdapCustomFilterRepository extends JpaRepository<LdapCustomFilter, Integer>{

}
