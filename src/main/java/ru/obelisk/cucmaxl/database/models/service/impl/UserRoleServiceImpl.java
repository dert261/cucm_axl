package ru.obelisk.cucmaxl.database.models.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.UserRole;
import ru.obelisk.cucmaxl.database.models.repository.UserRoleRepository;
import ru.obelisk.cucmaxl.database.models.service.UserRoleService;

@Service
@Repository
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
    private UserRoleRepository userRoleRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional(readOnly=true)
	public List<UserRole> getAllUserRoles() {
		return userRoleRepository.findAll();
	}
}
