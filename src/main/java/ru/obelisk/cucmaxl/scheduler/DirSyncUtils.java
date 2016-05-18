package ru.obelisk.cucmaxl.scheduler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.obelisk.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.entity.UserRole;
import ru.obelisk.database.models.entity.enums.ResyncStatus;
import ru.obelisk.database.models.entity.enums.UserStatus;
import ru.obelisk.database.models.entity.enums.UserType;
import ru.obelisk.database.models.service.LdapDirSyncParametersService;
import ru.obelisk.database.models.service.UserRoleService;
import ru.obelisk.database.models.service.UserService;
import ru.obelisk.module.utils.TimePeriod;
import ru.obelisk.cucmaxl.ldap.entity.Person;
import ru.obelisk.cucmaxl.ldap.repository.PersonRepo;

@Component
public class DirSyncUtils {
	private static Logger logger = LogManager.getLogger(DirSyncUtils.class);
	
	@Autowired private LdapDirSyncParametersService ldapDirSyncParamsService;
	@Autowired private PersonRepo ldapPersonRepo;
	@Autowired private UserService userService;
	@Autowired private UserRoleService userRoleService;
	
	
	public void refreshUsersFromLdap(int ldapId) {
		
			logger.info("Requesting resync Ldap Directory");
			
			LdapDirSyncParameters ldapDir = ldapDirSyncParamsService.findById(ldapId);
			
			
			if(ldapDir!=null){
				LocalDateTime startDate = LocalDateTime.now();
				ldapDir.setResyncStatus(ResyncStatus.ACTIVE);
				ldapDirSyncParamsService.edit(ldapDir);
				logger.info("Start resync Ldap Directory at {}: {}",startDate, ldapDir);
				
				
				try {
					List<Person> persons = ldapPersonRepo.getSyncPersonsFromLdap(ldapDir);
					if(persons!=null){
						
						List<User> users = userService.getUsersByLdapDir(ldapDir);
						Map<String, User> usersMap = new HashMap<String, User>();
						Iterator<User> userIterator = users.iterator();
						while(userIterator.hasNext()){
							User user = userIterator.next();
							usersMap.put(user.getLogin(), user);
						}
						
						Map<String, Person> personsMap = new HashMap<String, Person>();
						Iterator<Person> personIterator = persons.iterator(); 
						while(personIterator.hasNext()){
							Person person = personIterator.next();
							personsMap.put(person.getsAMAccountName(), person);
							User user = null;
							if(!usersMap.containsKey(person.getsAMAccountName())){
								user = createNewUser(person, ldapDir);
								if(user!=null)
									userService.add(user);
							} else {
								user = updateUser(usersMap.get(person.getsAMAccountName()), person);
								userService.edit(user);
							}
						}
						
						//------------------------------ Block Users ----------------//
						
						Iterator<User> userToBlockIterator = users.iterator();
						while(userToBlockIterator.hasNext()){
							User user = userToBlockIterator.next();
							if(!personsMap.containsKey(user.getLogin()) && user.getStatus()==UserStatus.ACTIVE){
								user.setBlockDate(new Date());
								user.setStatus(UserStatus.NONACTIVE);
								userService.edit(user);
							}
						}
						
					}
					
				} catch(Exception e) {
					logger.warn("Periodic sync end with errors: {}", e);
				}	
				
				LocalDateTime stopDate = LocalDateTime.now();
				logger.info("Stop resync Ldap Directory at {}: {}",stopDate, ldapDir);
				TimePeriod interval = TimePeriod.getDateTimeDuration(startDate, stopDate); 
				logger.info("Total work time is {}", interval);
			}
			ldapDir.setResyncStatus(ResyncStatus.NONACTIVE);
			ldapDirSyncParamsService.edit(ldapDir);
	
	}
		
	private User createNewUser(Person person, LdapDirSyncParameters ldapDir){
		User user = new User();
		user.setAdGuid(person.getObjectGUID());
		user.setAdLocation(person.getL());
		user.setCompany(person.getCompany());
		user.setDepartment(person.getDepartment());
		user.setEmail(person.getMail()!="" ? person.getMail().toLowerCase() : "");
		user.setEmployeeId(person.getEmployeeId());
		user.setLdapDirSyncParameters(ldapDir);
		user.setLocalUserFlag(UserType.LDAP);
		user.setLogin(person.getsAMAccountName());
		user.setName(person.getName());
		
		user.setPass("12345!BIN");
		
		Set<UserRole> userRoles = new HashSet<UserRole>();
		Iterator<UserRole> rolesIterator = userRoleService.getAllUserRoles().iterator();
		while(rolesIterator.hasNext()){
			UserRole role = rolesIterator.next();
			if(role.getRoleName().equals("USER"))
				userRoles.add(role);
		}
		user.setRoles(userRoles);
		
		user.setSigninDate(new Date());
		user.setStatus(UserStatus.ACTIVE);
		user.setBlockDate(null);
		user.setStreetAddress(person.getStreetAddress());
		user.setTelephoneNumber(person.getTelephoneNumber());
		user.setTitle(person.getTitle());
		
		String[] dispName = person.getDisplayName().split(" ");
		switch(dispName.length){
			case 0: break;
			case 1: user.setLname(dispName[0]); 
					break;
			case 2: user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					break;
			case 3: user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					user.setMname(dispName[2]);
					break;
			default:user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					user.setMname(dispName[2]);
					break;
		}
		if((user.getLname()==null || user.getLname().length()==0) || (user.getFname()==null || user.getFname().length()==0))
			user = null;
		return user;
	}
	
	private User updateUser(User user, Person person){
		user.setAdGuid(person.getObjectGUID());
		user.setAdLocation(person.getL());
		user.setCompany(person.getCompany());
		user.setDepartment(person.getDepartment());
		user.setEmail(person.getMail());
		user.setEmployeeId(person.getEmployeeId());
		user.setName(person.getName());
		user.setStatus(UserStatus.ACTIVE);
		user.setBlockDate(null);
		user.setStreetAddress(person.getStreetAddress());
		user.setTelephoneNumber(person.getTelephoneNumber());
		user.setTitle(person.getTitle());
		
		String[] dispName = person.getDisplayName().split(" ");
		switch(dispName.length){
			case 0: break;
			case 1: user.setLname(dispName[0]); 
					break;
			case 2: user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					break;
			case 3: user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					user.setMname(dispName[2]);
					break;
			default:user.setLname(dispName[0]); 
					user.setFname(dispName[1]);
					user.setMname(dispName[2]);
					break;
		}
		return user;
	}
}
