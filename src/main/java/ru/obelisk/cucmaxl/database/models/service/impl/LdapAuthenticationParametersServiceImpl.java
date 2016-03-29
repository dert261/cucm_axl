package ru.obelisk.cucmaxl.database.models.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.LdapAuthenticationParameters;
import ru.obelisk.cucmaxl.database.models.repository.LdapAuthenticationParametersRepository;
import ru.obelisk.cucmaxl.database.models.service.LdapAuthenticationParametersService;

@Service
@Repository
@Transactional
public class LdapAuthenticationParametersServiceImpl implements LdapAuthenticationParametersService {

	@Autowired
    private LdapAuthenticationParametersRepository ldapAuthParamRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    	
	@Override
	@Transactional(readOnly=true)
	public LdapAuthenticationParameters getParameters() {
		List<LdapAuthenticationParameters> paramsList = ldapAuthParamRepository.findAll();
		if(paramsList==null || paramsList.isEmpty()) return new LdapAuthenticationParameters(); 
		else return paramsList.get(0);
	}

	@Override
	@Transactional
	public LdapAuthenticationParameters editParameters(
			LdapAuthenticationParameters formParameters) {
		if(formParameters.isNew()){
			return ldapAuthParamRepository.saveAndFlush(formParameters);
		}
		LdapAuthenticationParameters params = ldapAuthParamRepository.findOne(formParameters.getId());
		params.setActive(formParameters.isActive());
		params.setDistinguishedName(formParameters.getDistinguishedName());
		params.setPassword(formParameters.getPassword());
		params.setSearchBase(formParameters.getSearchBase());
		params.setLdapServers(formParameters.getLdapServers());
		return ldapAuthParamRepository.saveAndFlush(params);
	}

}
