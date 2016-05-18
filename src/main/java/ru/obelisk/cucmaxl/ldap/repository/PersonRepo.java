package ru.obelisk.cucmaxl.ldap.repository;

import java.util.List;

import ru.obelisk.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.ldap.entity.Person;

public interface PersonRepo {

	public List<Person> getAllPersons();
	public Person findPersonByLogin(String login);
	public Person findPersonByTelephoneNumber(String number);
	public Person findPersonByEmployeeId(String employeeId);
	public List<Person> getSyncPersonsFromLdap(final LdapDirSyncParameters ldapDirSyncParameters);
}
