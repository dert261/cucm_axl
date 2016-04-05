package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import ru.obelisk.datatables.mapping.DataTablesInput;
import ru.obelisk.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.repository.UserRepository;
import ru.obelisk.cucmaxl.database.models.service.UserService;
import ru.obelisk.cucmaxl.database.models.service.utils.UserServiceUtils;
import ru.obelisk.cucmaxl.web.ui.datatables.ColumnDef;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;
import ru.obelisk.cucmaxl.web.ui.select2.Select2Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
 
@Service
@Repository
@Transactional
public class UserServiceImpl implements UserService {
 
    @Autowired
    private UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
 
    @Override
    @Transactional
    public User addUser(User user) {
    	user.setPass(passwordEncoder.encode(user.getPass()));
    	user.setRoles(user.getRoles());
        //User savedUser = userRepository.saveAndFlush(user);
    	User savedUser = userRepository.save(user);
        return savedUser;
    }
    
    @Override
    @Transactional
    public User add(User user) {
    	user.setPass(passwordEncoder.encode(user.getPass()));
    	return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public User edit(User user) {
    	return userRepository.save(user);
    }
 
    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(id);
    }
 
    @Override
    @Transactional(readOnly=true)
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }
 
    @Override
    @Transactional
    public User editUser(User formUser) {
    	User user = getUserById(formUser.getId());
		user.setAdGuid(formUser.getAdGuid());
		user.setAdLocation(formUser.getAdLocation());
		user.setCompany(formUser.getCompany());
		user.setDepartment(formUser.getDepartment());
		user.setEmail(formUser.getEmail());
		user.setFname(formUser.getFname());
		user.setMname(formUser.getMname());
		user.setLname(formUser.getLname());
		user.setName(formUser.getLname()+" "+formUser.getFname()+" "+formUser.getMname());
		
		user.setIpAddress(formUser.getIpAddress());
		user.setLocalUserFlag(formUser.getLocalUserFlag());
		user.setLogin(formUser.getLogin());
		user.setMobile(formUser.getMobile());
				
		String oldPass = user.getPass();
		String newPass = formUser.getPass();
		if(!oldPass.equals(newPass))
			user.setPass(passwordEncoder.encode(formUser.getPass()));
				
		user.setRoles(formUser.getRoles());
		user.setStatus(formUser.getStatus());
		user.setStreetAddress(formUser.getStreetAddress());
		user.setTitle(formUser.getTitle());
				
		return userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly=true)
    public User getUserByUsernameAndLdapDir(String username, LdapDirSyncParameters ldapDir){
    	return userRepository.findUserByUsernameAndLdapDir(username, ldapDir);
    }
    
    @Override
    @Transactional(readOnly=true)
	public List<User> getUsersByLdapDir(LdapDirSyncParameters ldapDir){
		return userRepository.findUsersByLdapDir(ldapDir);
	}
    
 
    @Override
    @Transactional(readOnly=true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
	public User getUserById(int id) {
		return userRepository.findOne(id);
	}
    
    @Override
    @Transactional(readOnly=true)
    public User getUserByLdapObjectGuid(String objectGUID){
		return userRepository.findByLdapObjectGUID(objectGUID);
	}
		
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<User> findAllWithSpecs(DataTablesInput input){
    	Specification<User> additionalSpecification = new Specification<User>() {
        	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        		
        		Class<?> clazz = query.getResultType();
   		     	if (clazz.equals(Long.class) || clazz.equals(long.class))
   		     		return null;
   			
   		     	root.fetch("phoneBook", JoinType.LEFT);
   		     	return builder.conjunction();
   		    }
		}; 
		return userRepository.findAll(input, additionalSpecification);
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<User> findAll(DataTablesInput input){
    	return userRepository.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findUserByTerm(String term) {
		
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(u.id, CONCAT(u.name,' (',u.login,')')) FROM User u" 
                		+ " WHERE "
                        + " LOWER(u.name) LIKE :term"
                        + " OR LOWER(u.login) LIKE :term", Select2Result.class)
        .setParameter("term", "%" + term.toLowerCase() + "%")
        .setHint("org.hibernate.cacheable", true)
        .getResultList();
        return reultList;
	}
    
    
    @Override
    @Transactional(readOnly=true)
	public List<Select2Result> findCucmUserByTerm(String term) {
		List<Select2Result> reultList = entityManager.createQuery(
                "SELECT NEW ru.obelisk.cucmaxl.web.ui.select2.Select2Result(u.id, CONCAT(u.name,' (',u.login,')')) FROM User u"
						+" LEFT JOIN u.ldapDirSyncParameters ldapParams"
						+ " WHERE "
                		+ " u.status = 'ACTIVE'"
						+ " AND ldapParams.fqdnName IN ('bin.bank','corp.icba.biz')"
                        + " AND (LOWER(u.name) LIKE :term"
                        + " OR LOWER(u.login) LIKE :term)", Select2Result.class)
        .setParameter("term", "%"+term.toLowerCase()+"%")
        .setHint("org.hibernate.cacheable", true)
        .getResultList();
        return reultList;
	}
	
	/**
	* <p>
	* Query used to populate the DataTables that display the list of persons.
	*
	* @param criterias
	* The DataTables criterias used to filter the persons.
	* (maxResult, filtering, paging, ...)
	* @return a filtered list of persons.
	*/
    @Override
    @Transactional(readOnly=true)
	public List<User> findUserWithDatatablesCriterias(DatatablesCriterias criterias) {
		//StringBuilder queryBuilder = new StringBuilder("SELECT u FROM User u JOIN FETCH u.roles roles LEFT JOIN FETCH u.phoneBook");
    	StringBuilder queryBuilder = new StringBuilder("SELECT u FROM User u");
		
		/**
		* Step 1: global and individual column filtering
		*/
		queryBuilder.append(UserServiceUtils.getFilterQuery(criterias));
				
		/**
		* Step 2: sorting
		*/
		if (criterias.hasOneSortedColumn()) {
			List<String> orderParams = new ArrayList<String>();
			for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
				String columnName = null;
				if(columnDef.getName().endsWith("Localized")){
					columnName=columnDef.getName().replaceAll("Localized", "");
				} else {
					columnName=columnDef.getName();
				}
				orderParams.add("u." + columnName + " " + columnDef.getSortDirection());
			}
			if(!orderParams.isEmpty()){
				queryBuilder.append(" ORDER BY ");
				Iterator<String> itr2 = orderParams.iterator();
				while (itr2.hasNext()) {
					queryBuilder.append(itr2.next());
					if (itr2.hasNext()) {
						queryBuilder.append(" , ");
					}
				}
			}
		}
		
		System.out.println("SQL: "+queryBuilder.toString());
		
		TypedQuery<User> query = entityManager.createQuery(queryBuilder.toString(), User.class);
		
		/**
		* Step 3: paging
		*/
		query.setFirstResult(criterias.getStart());
		if(criterias.getLength()>0)
			query.setMaxResults(criterias.getLength());
		query.setHint("org.hibernate.cacheable", true);
		return idGenerate(query.getResultList(),criterias.getStart());
	}
	
	private List<User> idGenerate(List<User> users, int start){
		
		for(int i=0;i<users.size();i++){
			users.get(i).setNumberLocalized(start+i+1);
		}
		return users;
	}
	/**
	* <p>
	* Query used to return the number of filtered persons.
	*
	* @param criterias
	* The DataTables criterias used to filter the persons.
	* (maxResult, filtering, paging, ...)
	* @return the number of filtered persons.
	*/
	@Override
    @Transactional(readOnly=true)
	public Long getFilteredCount(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT u FROM User u");
		queryBuilder.append(UserServiceUtils.getFilterQuery(criterias));
		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setHint("org.hibernate.cacheable", true);
		return Long.parseLong(String.valueOf(query.getResultList().size()));
	}
	/**
	* @return the total count of persons.
	*/
	@Override
    @Transactional(readOnly=true)
	public Long getTotalCount() {
		Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u");
		query.setHint("org.hibernate.cacheable", true);
		return (Long) query.getSingleResult();
	}

	@Override
    @Transactional(readOnly=true)
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> findAllForADSyncByLdapSource(LdapDirSyncParameters ldapDir) {
		return userRepository.findAllForADSyncByLdapSource(ldapDir);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findAllForADSyncByFqdn(String fqdn) {
		return userRepository.findAllForADSyncByFqdn(fqdn);
	}
	
}