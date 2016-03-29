package ru.obelisk.cucmaxl.database.models.service;

import ru.obelisk.cucmaxl.database.models.entity.LdapAuthenticationParameters;

public interface LdapAuthenticationParametersService {

		LdapAuthenticationParameters getParameters();
		LdapAuthenticationParameters editParameters(LdapAuthenticationParameters parameters);
}
