package ru.obelisk.cucmaxl.ldap.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.LdapOperationsCallback;
import org.springframework.ldap.core.support.SingleContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;
import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncParameters;
import ru.obelisk.cucmaxl.database.models.entity.LdapDirSyncServer;
import ru.obelisk.cucmaxl.ldap.entity.Person;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class PersonRepoImpl implements PersonRepo {
	
	private static Logger logger = LogManager.getLogger(PersonRepoImpl.class);
	
	@Autowired private LdapTemplate ldapTemplate;
	
	private class PersonAttributesMapper implements AttributesMapper<Person> {
		public Person mapFromAttributes(Attributes attrs) throws NamingException  {
			
			Person person = new Person();
	        
			person.setCn(attrs.get("cn")!=null ? attrs.get("cn").get().toString() : "");
			person.setDisplayName(attrs.get("displayName")!=null ? attrs.get("displayName").get().toString() : "");
		    person.setGivenName(attrs.get("givenName")!=null ? attrs.get("givenName").get().toString() : "");
		    person.setName(attrs.get("name")!=null ? attrs.get("name").get().toString() : "");
		    person.setSn(attrs.get("sn")!=null ? attrs.get("sn").get().toString() : "");
		    person.setsAMAccountName(attrs.get("sAMAccountName")!=null ? attrs.get("sAMAccountName").get().toString() : "");
		    person.setsAMAccountType(attrs.get("sAMAccountType")!=null ? attrs.get("sAMAccountType").get().toString() : "");
		    person.setEmployeeId(attrs.get("employeeID")!=null ? attrs.get("employeeID").get().toString() : "");
		    
		    
		    person.setCompany(attrs.get("company")!=null ? attrs.get("company").get().toString() : "");
		    person.setDepartment(attrs.get("department")!=null ? attrs.get("department").get().toString() : "");
		    person.setMail(attrs.get("mail")!=null ? attrs.get("mail").get().toString() : "");
		    
		    person.setObjectGUID(attrs.get("objectGUID")!=null ? convertToBindingString((byte[])attrs.get("objectGUID").get()) : "");
		    		    
		    person.setTitle(attrs.get("title")!=null ? attrs.get("title").get().toString() : "");
		    person.setL(attrs.get("l")!=null ? attrs.get("l").get().toString() : "");
		    person.setStreetAddress(attrs.get("streetAddress")!=null ? attrs.get("streetAddress").get().toString() : "");
		    person.setTelephoneNumber(attrs.get("telephoneNumber")!=null ? attrs.get("telephoneNumber").get().toString() : "");
		   
		    
		    
	        return person;
	    }
	}
	
	public static String convertToBindingString(byte[] objectGUID) {
	    StringBuilder displayStr = new StringBuilder();
	    displayStr.append("<GUID=");
	    displayStr.append(convertToDashedString(objectGUID));
	    displayStr.append(">");
	    return displayStr.toString();
	}
	
	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
	
	public static String convertToDashedString(byte[] objectGUID) {
	    StringBuilder displayStr = new StringBuilder();

	    displayStr.append(prefixZeros((int) objectGUID[3] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[2] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[1] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[0] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[5] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[4] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[7] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[6] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[8] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[9] & 0xFF));
	    displayStr.append("-");
	    displayStr.append(prefixZeros((int) objectGUID[10] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[11] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[12] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[13] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[14] & 0xFF));
	    displayStr.append(prefixZeros((int) objectGUID[15] & 0xFF));

	    return displayStr.toString();
	}

	private static String prefixZeros(int value) {
	    if (value <= 0xF) {
	        StringBuilder sb = new StringBuilder("0");
	        sb.append(Integer.toHexString(value));

	        return sb.toString();

	    } else {
	        return Integer.toHexString(value);
	    }
	}
    
	
	public List<Person> getAllPersons() {
		return ldapTemplate.search(query()
				.where("objectclass").is("person")
				.and("sAMAccountType").is("805306368"), new PersonAttributesMapper());
	}
	
	public Person findPersonByLogin(String login) {
		LdapQuery query = query()
				.where("objectclass").is("person")
				.and("sn").like("*")
				.and("sAMAccountType").is("805306368")
				.and("employeeID").like("*")
				.and("mail").like("*")
				.and("givenname").like("* *")
				.and("UserAccountControl:1.2.840.113556.1.4.803:").not().is("2")
				.and("department").not().is("!Technical")
				.and("sAMAccountName").is(login); 
		
		List<Person> persons = ldapTemplate.search(query, new PersonAttributesMapper());
				
		Person person = null;
        if(persons.size()==1){
        	person=persons.get(0);
        }
		return person;
	}
	
	public Person findPersonByEmployeeId(String employeeId) {
		LdapQuery query = query()
				.where("objectclass").is("person")
				.and("employeeID").is(employeeId); 
		
		List<Person> persons = ldapTemplate.search(query, new PersonAttributesMapper());
				
		Person person = null;
        if(persons.size()==1){
        	person=persons.get(0);
        }
		return person;
	}
	
	public Person findPersonByTelephoneNumber(String number) {
		LdapQuery query = query()
				.where("objectclass").is("person")
				.and("sn").like("*")
				.and("sAMAccountType").is("805306368")
				.and("employeeID").like("*")
				.and("mail").like("*")
				.and("givenname").like("* *")
				.and("UserAccountControl:1.2.840.113556.1.4.803:").not().is("2")
				.and("department").not().is("!Technical")
				.and("telephoneNumber").is(number);
		
		List<Person> persons = ldapTemplate.search(query, new PersonAttributesMapper());
		
		Person person = null;
        if(persons.size()==1){
        	person=persons.get(0);
        }
		return person;
	}
		
	public List<Person> getSyncPersonsFromLdap(final LdapDirSyncParameters ldapDirSyncParameters) {
		if(ldapDirSyncParameters==null) return null;
		LdapTemplate ldapTemplate = getLdapTemplate(ldapDirSyncParameters);
		
	    final PagedResultsDirContextProcessor processor = new PagedResultsDirContextProcessor(ldapDirSyncParameters.getCountOnResultPage());
	    final PersonAttributesMapper personMapper = new PersonAttributesMapper();
	    
	    final SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    		
		if(ldapTemplate!=null){
		
			logger.info("ldapSearchBase: {}", ldapDirSyncParameters.getSearchBase());
			logger.info("ldapFilter: {}", ldapDirSyncParameters.getLdapCustomFilter()!=null ? ldapDirSyncParameters.getLdapCustomFilter().getFilter() : "(objectclass=person)");
	    			    
		    return SingleContextSource.doWithSingleContext(
		            ldapTemplate.getContextSource(), new LdapOperationsCallback<List<Person>>() {
		        @Override
		        public List<Person> doWithLdapOperations(LdapOperations operations) {
		        	List<Person> result = new LinkedList<Person>();
		        	try{
		            
			            do {
			                List<Person> oneResult = operations.search(
			                	LdapUtils.emptyLdapName(), 
			        			ldapDirSyncParameters.getLdapCustomFilter()!=null ? ldapDirSyncParameters.getLdapCustomFilter().getFilter() : "(objectclass=person)",
			                	searchControls, 
			                	personMapper, 
			                	processor);
			                result.addAll(oneResult);
			            } while (processor.hasMore());
		        	} catch (Exception e){
		        		logger.warn(e);
		        	}
		            return result;
		        	
		        }
		    });
		}
		return null;    
	}
	
	public LdapContextSource getLdapContextSource(LdapDirSyncParameters ldapDirSyncParameters){
		LdapContextSource context = new LdapContextSource();
		context.setBase(ldapDirSyncParameters.getSearchBase());
		context.setUrls(buildLdapUrls(ldapDirSyncParameters.getLdapDirSyncServers()));
		context.setUserDn(ldapDirSyncParameters.getDistinguishedName());
		context.setPassword(ldapDirSyncParameters.getPassword());
		context.setReferral("follow");
		
		Map<String,Object> baseEnvironmentProperties = new HashMap<String,Object>();
		baseEnvironmentProperties.put("java.naming.ldap.attributes.binary", "objectGUID");
		context.setBaseEnvironmentProperties(baseEnvironmentProperties);
		context.afterPropertiesSet();
		return context;
	}
	
	private String[] buildLdapUrls(List<LdapDirSyncServer> ldapServers){
		List<String> serversPool = new ArrayList<String>();
		Iterator<LdapDirSyncServer> server = ldapServers.iterator();
		while(server.hasNext()){
			LdapDirSyncServer currentServer = server.next();
			StringBuilder ldapServer = new StringBuilder();
			ldapServer.append(currentServer.isUseSSL()==true ? "ldaps://" : "ldap://");  
			ldapServer.append(currentServer.getHost());
			ldapServer.append(":");
			ldapServer.append(currentServer.getPort());
			serversPool.add(ldapServer.toString());
		}
		String[] simpleArray = new String[serversPool.size()];
		return serversPool.toArray(simpleArray);
	}
	
	
	public LdapTemplate getLdapTemplate(LdapDirSyncParameters ldapDirSyncParameters){
		LdapTemplate ldapTemplate = new LdapTemplate(getLdapContextSource(ldapDirSyncParameters));
		ldapTemplate.setIgnoreNameNotFoundException(true);
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setIgnoreSizeLimitExceededException(true);
		
		try {
			ldapTemplate.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ldapTemplate;
	}
}
