package ru.obelisk.cucmaxl.database.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.entity.enums.PhoneBookSyncSource;
import ru.obelisk.cucmaxl.database.models.entity.enums.ResyncStatus;
import ru.obelisk.cucmaxl.database.models.entity.enums.ResyncUnit;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.FieldMatch;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;
import ru.obelisk.cucmaxl.web.validator.NotNullField;

@Entity
@Table(name = "ldap_dir_sync_parameters", catalog="adsync")
@FieldMatch(first = "password", second = "passwordConfirm", groups=LdapDirSyncParameters.LdapDirSyncParamsStepTwo.class, message = "field.validation.error.diffpassword")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LdapDirSyncParameters {
 
		public interface LdapDirSyncParamsStepOne{}
		public interface LdapDirSyncParamsStepTwo{}
		
		@JsonView(value={View.LdapDirSyncParameters.class, View.User.class})
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id", length = 11, nullable = false)
	    private Integer id;
	    
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Transient
	    private int numberLocalized;
	    		
		@JsonView(value={View.LdapDirSyncParameters.class, View.User.class})
	    @Column(name = "config_name", nullable = false)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String configName=null;
	    
		@JsonView(value={View.LdapDirSyncParameters.class, View.User.class})
		@Column(name = "fqdn_name", nullable = false)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String fqdnName=null;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "distinguished_name", nullable = false)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String distinguishedName=null;
	    
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "password", length = 100)
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String password=null;
	    
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Transient
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String passwordConfirm=null;
	    	  
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "search_base")
	    @NotEmpty(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @NotNullField(groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private String searchBase=null;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "phonebook_sync_source")
		@Enumerated(EnumType.STRING)
	    private PhoneBookSyncSource phoneBookSyncSource = PhoneBookSyncSource.CUCMPORT;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "resync_flag")
	    private boolean resyncFlag = true;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "resync_interval")
	    private int resyncInterval = 1;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "resync_unit")
		@Enumerated(EnumType.STRING)
	    private ResyncUnit resyncUnit = ResyncUnit.HOUR;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "resync_status")
		@Enumerated(EnumType.STRING)
	    private ResyncStatus resyncStatus = ResyncStatus.NONACTIVE;
		
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Column(name = "count_result_on_page")
	    @Min(value = 10, message = "ldapdirsync.field.countOnResultPage.validation.error.min", groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    @Max(value = 10000, message = "ldapdirsync.field.countOnResultPage.validation.error.max", groups=LdapDirSyncParameters.LdapDirSyncParamsStepOne.class)
	    private int countOnResultPage=1000;
	    
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @Valid
	    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	    @JoinColumn(name="ldap_dir_sync_parameters_id")
	    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	    private List<LdapDirSyncServer> ldapDirSyncServers = new ArrayList<LdapDirSyncServer>(0);
	    
		@JsonView(value={View.LdapDirSyncParameters.class})
	    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE)
	    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	    private LdapCustomFilter ldapCustomFilter;
		
		public LdapDirSyncParameters(){
	    	
	    }
	    
	    @PostLoad
		private void postLoad(){
			this.passwordConfirm = this.password;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getConfigName() {
			return configName;
		}

		public void setConfigName(String configName) {
			this.configName = configName;
		}

		public String getDistinguishedName() {
			return distinguishedName;
		}

		public void setDistinguishedName(String distinguishedName) {
			this.distinguishedName = distinguishedName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPasswordConfirm() {
			return passwordConfirm;
		}

		public void setPasswordConfirm(String passwordConfirm) {
			this.passwordConfirm = passwordConfirm;
		}

		public String getSearchBase() {
			return searchBase;
		}

		public void setSearchBase(String searchBase) {
			this.searchBase = searchBase;
		}

		public List<LdapDirSyncServer> getLdapDirSyncServers() {
			return ldapDirSyncServers;
		}

		public void setLdapDirSyncServers(List<LdapDirSyncServer> ldapDirSyncServers) {
			this.ldapDirSyncServers = ldapDirSyncServers;
		}

		public LdapCustomFilter getLdapCustomFilter() {
			return ldapCustomFilter;
		}

		public void setLdapCustomFilter(LdapCustomFilter ldapCustomFilter) {
			this.ldapCustomFilter = ldapCustomFilter;
		}
		
		public int getNumberLocalized() {
			return numberLocalized;
		}

		public void setNumberLocalized(int numberLocalized) {
			this.numberLocalized = numberLocalized;
		}

		public boolean isNew(){
			return this.id==null;
		}
				
		public String getFqdnName() {
			return fqdnName;
		}

		public void setFqdnName(String fqdnName) {
			this.fqdnName = fqdnName;
		}
		
		public int getCountOnResultPage() {
			return countOnResultPage;
		}

		public void setCountOnResultPage(int countOnResultPage) {
			this.countOnResultPage = countOnResultPage;
		}
						
		public ResyncStatus getResyncStatus() {
			return resyncStatus;
		}

		public void setResyncStatus(ResyncStatus resyncStatus) {
			this.resyncStatus = resyncStatus;
		}

		public boolean isResyncFlag() {
			return resyncFlag;
		}

		public void setResyncFlag(boolean resyncFlag) {
			this.resyncFlag = resyncFlag;
		}

		public int getResyncInterval() {
			return resyncInterval;
		}

		public void setResyncInterval(int resyncInterval) {
			this.resyncInterval = resyncInterval;
		}

		public ResyncUnit getResyncUnit() {
			return resyncUnit;
		}

		public void setResyncUnit(ResyncUnit resyncUnit) {
			this.resyncUnit = resyncUnit;
		}
		
		public PhoneBookSyncSource getPhoneBookSyncSource() {
			return phoneBookSyncSource;
		}

		public void setPhoneBookSyncSource(PhoneBookSyncSource phoneBookSyncSource) {
			this.phoneBookSyncSource = phoneBookSyncSource;
		}

		@Override
		public String toString() {
			return "LdapDirSyncParameters [id=" + id + ", numberLocalized=" + numberLocalized + ", configName="
					+ configName + ", fqdnName=" + fqdnName + ", distinguishedName=" + distinguishedName + ", password="
					+ password + ", passwordConfirm=" + passwordConfirm + ", searchBase=" + searchBase
					+ ", countOnResultPage=" + countOnResultPage + ", ldapCustomFilter=" + ldapCustomFilter + "]";
		}
}
