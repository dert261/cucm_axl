package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.entity.cme.CmeCustomDevice;
import ru.obelisk.cucmaxl.database.models.entity.enums.UserStatus;
import ru.obelisk.cucmaxl.database.models.entity.enums.UserType;
import ru.obelisk.cucmaxl.database.models.views.View;
//import ru.obelisk.cucmaxl.web.validator.Email;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;
import ru.obelisk.cucmaxl.web.validator.NotNullField;

@Entity
@Table(name = "users", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class User implements Serializable {
 	private static final long serialVersionUID = 2731242147275363481L;

 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(value={View.User.class, View.CmeDevice.class, View.CmeSipDevice.class})
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
    @Transient
    @JsonView(value={View.User.class})
    private int numberLocalized;
       
    @JsonView(value={View.User.class, View.CmeDevice.class, View.CmeSipDevice.class, View.Job.class})
    @Column(name = "login", length = 50, nullable = false)
    @NotNull 
    @NotEmpty
    private String login=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "pass", length = 60)
    @NotEmpty
    private String pass=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status=UserStatus.NONACTIVE;
    @Transient
    @JsonView(value={View.User.class})
    private String statusLocalized=null;
    
    //@JsonView(value={View.User.class})
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="users2user_roles", catalog="adsync", schema="public",
    	joinColumns=@JoinColumn(name="user_id"),
    	inverseJoinColumns=@JoinColumn(name="role_id")
    )
    @NotNullField 
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserRole> roles;
    @JsonView(value={View.User.class})
    @Transient
    private String roleLocalized=null;
    
    @JsonView(value={View.User.class, View.CmeDevice.class, View.CmeSipDevice.class, View.Job.class})
    @Column(name = "name", length = 150)
    private String name=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "email", length = 50)
    //@Email
    private String email=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "last_login")
    private Date lastLogin=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "signin_date")
    private Date signinDate=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "ip_address", length = 16)
    private String ipAddress=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "local_user")
    @Enumerated(EnumType.STRING)
    private UserType localUserFlag=UserType.LOCAL;
    @JsonView(value={View.User.class})
    @Transient
    private String localUserFlagLocalized=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "fname", length = 100)
    @NotNull
    @NotEmpty
    private String fname;
    
    @JsonView(value={View.User.class})
    @Column(name = "mname", length = 100)
    private String mname=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "lname", length = 100)
    @NotNull
    @NotEmpty
    private String lname;
        
    @JsonView(value={View.User.class})
    @Column(name = "ad_guid", length = 100)
    private String adGuid=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "mobile", length = 100)
    private String mobile=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "company", length = 500)
    private String company=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "department", length = 500)
    private String department=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "title", length = 500)
    private String title=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "ad_location", length = 500)
    private String adLocation=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "street_address", length = 500)
    private String streetAddress=null;
    
    @JsonView(value={View.User.class})
    @Column(name = "block_date")
    private Date blockDate=null;
       
    @JsonView(value={View.User.class})
    @Column(name = "employee_id")
    private String employeeId;
    
    @JsonView(value={View.User.class})
    @Column(name = "telephone_number")
    private String telephoneNumber;
    
    @JsonView(value={View.User.class})
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.DETACH)
	@JoinColumn(name = "ldap_dir_sync_parameters_id")
	private LdapDirSyncParameters ldapDirSyncParameters;
    
    //@JsonView(value={View.User.class})
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId", cascade=CascadeType.MERGE)
	private Set<CucmDevice> devices = new HashSet<CucmDevice>(0);
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.MERGE)
	private Set<CmeCustomDevice> sccpDevices = new HashSet<CmeCustomDevice>(0);
    
    @JsonView(value={View.User.class})
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "phonebook_id")
	private EndUserPhoneBook phoneBook = new EndUserPhoneBook();
    
    public User(){
    	//roles.add(new UserRole("USER",this)); 
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getNumberLocalized() {
		return numberLocalized;
	}

	public void setNumberLocalized(int numberLocalized) {
		this.numberLocalized = numberLocalized;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getSigninDate() {
		return signinDate;
	}

	public void setSigninDate(Date signinDate) {
		this.signinDate = signinDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public UserType getLocalUserFlag() {
		return localUserFlag;
	}

	public void setLocalUserFlag(UserType localUserFlag) {
		this.localUserFlag = localUserFlag;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAdGuid() {
		return adGuid;
	}

	public void setAdGuid(String adGuid) {
		this.adGuid = adGuid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdLocation() {
		return adLocation;
	}

	public void setAdLocation(String adLocation) {
		this.adLocation = adLocation;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Date getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}
	
	public boolean isNew() {
		return (this.id == null);
	}

	public String getStatusLocalized() {
		return statusLocalized;
	}

	public void setStatusLocalized(String statusLocalized) {
		this.statusLocalized = statusLocalized;
	}

	public String getRoleLocalized() {
		return roleLocalized;
	}

	public void setRoleLocalized(String roleLocalized) {
		this.roleLocalized = roleLocalized;
	}

	public String getLocalUserFlagLocalized() {
		return localUserFlagLocalized;
	}

	public void setLocalUserFlagLocalized(String localUserFlagLocalized) {
		this.localUserFlagLocalized = localUserFlagLocalized;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public boolean isLdapUser(){
		return localUserFlag==UserType.LDAP;
	}
	
	public boolean isBlocked(){
		return status==UserStatus.NONACTIVE;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public LdapDirSyncParameters getLdapDirSyncParameters() {
		return ldapDirSyncParameters;
	}

	public void setLdapDirSyncParameters(LdapDirSyncParameters ldapDirSyncParameters) {
		this.ldapDirSyncParameters = ldapDirSyncParameters;
	}
	
	public Set<CucmDevice> getDevices() {
		return devices;
	}

	public void setDevices(Set<CucmDevice> devices) {
		this.devices = devices;
	}
	
	public EndUserPhoneBook getPhoneBook() {
		return phoneBook;
	}

	public void setPhoneBook(EndUserPhoneBook phoneBook) {
		this.phoneBook = phoneBook;
	}
	
	public Set<CmeCustomDevice> getSccpDevices() {
		return sccpDevices;
	}

	public void setSccpDevices(Set<CmeCustomDevice> sccpDevices) {
		this.sccpDevices = sccpDevices;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", numberLocalized=" + numberLocalized
				+ ", login=" + login + ", pass=" + pass + ", status=" + status
				+ ", statusLocalized=" + statusLocalized + ", roles=" + roles
				+ ", roleLocalized=" + roleLocalized + ", name=" + name
				+ ", email=" + email + ", lastLogin=" + lastLogin
				+ ", signinDate=" + signinDate + ", ipAddress=" + ipAddress
				+ ", localUserFlag=" + localUserFlag
				+ ", localUserFlagLocalized=" + localUserFlagLocalized
				+ ", fname=" + fname + ", mname=" + mname + ", lname=" + lname
				+ ", adGuid=" + adGuid + ", mobile=" + mobile + ", company="
				+ company + ", department=" + department + ", title=" + title
				+ ", adLocation=" + adLocation + ", streetAddress="
				+ streetAddress + ", blockDate=" + blockDate + "]";
	}
}    
 