package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.cisco.axl.api._10.LUser;

@Entity
@Table(name = "cucm_user", catalog="adsync")
public class CucmUser implements Serializable {
	private static final long serialVersionUID = -7219265456818335570L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;

	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "accountType")
	private String accountType;

	@Column(name = "userId")
	private String userId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "middleName")
	private String middleName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "title")
	private String title;

	@Column(name = "directoryUri")
	private String directoryUri;

	@Column(name = "telephoneNumber")
	private String telephoneNumber;

	@Column(name = "homeNumber")
	private String homeNumber;

	@Column(name = "mobileNumber")
	private String mobileNumber;

	@Column(name = "pagerNumber")
	private String pagerNumber;

	@Column(name = "mailid")
	private String mailid;

	@Column(name = "manager")
	private String manager;

	@Column(name = "department")
	private String department;

	@Column(name = "userLocale")
	private String userLocale;

	@Column(name = "associatedPc")
	private String associatedPc;

	@Column(name = "status")
	private String status;

	@Column(name = "primExtensionPattern")
	private String primExtensionPattern;

	@Column(name = "primExtensionRoutePartitionName")
	private String primExtensionRoutePartitionName;
	
	@Column(name = "create_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createTime;
	
	@Column(name = "update_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updateTime;
	
	@Column(name = "block_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime blockTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirectoryUri() {
		return directoryUri;
	}

	public void setDirectoryUri(String directoryUri) {
		this.directoryUri = directoryUri;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPagerNumber() {
		return pagerNumber;
	}

	public void setPagerNumber(String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public String getAssociatedPc() {
		return associatedPc;
	}

	public void setAssociatedPc(String associatedPc) {
		this.associatedPc = associatedPc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrimExtensionPattern() {
		return primExtensionPattern;
	}

	public void setPrimExtensionPattern(String primExtensionPattern) {
		this.primExtensionPattern = primExtensionPattern;
	}

	public String getPrimExtensionRoutePartitionName() {
		return primExtensionRoutePartitionName;
	}

	public void setPrimExtensionRoutePartitionName(String primExtensionRoutePartitionName) {
		this.primExtensionRoutePartitionName = primExtensionRoutePartitionName;
	}
	
	public CucmUser(LUser luser){
		if(luser==null) return;
		this.uuid = luser.getUuid();
		this.accountType = "";
		this.userId = luser.getUserid();
		this.firstName = luser.getFirstName();
		this.middleName = luser.getMiddleName();
		this.lastName = luser.getLastName();
		this.title = luser.getTitle();
		this.directoryUri = luser.getDirectoryUri();
		this.telephoneNumber = luser.getTelephoneNumber();
		this.homeNumber = luser.getHomeNumber();
		this.mobileNumber = luser.getMobileNumber();
		this.pagerNumber = luser.getPagerNumber();
		this.mailid = luser.getMailid();
		this.manager = luser.getManager();
		this.department = luser.getDepartment();
		this.userLocale = luser.getUserLocale();
		this.associatedPc = luser.getAssociatedPc();
		this.status = luser.getStatus();
		this.primExtensionPattern = luser.getPrimaryExtension().getPattern();
		this.primExtensionRoutePartitionName = luser.getPrimaryExtension().getRoutePartitionName();
	}

	@Override
	public String toString() {
		return "CucmUser [id=" + id + ", uuid=" + uuid + ", accountType=" + accountType + ", userId=" + userId
				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", title="
				+ title + ", directoryUri=" + directoryUri + ", telephoneNumber=" + telephoneNumber + ", homeNumber="
				+ homeNumber + ", mobileNumber=" + mobileNumber + ", pagerNumber=" + pagerNumber + ", mailid=" + mailid
				+ ", manager=" + manager + ", department=" + department + ", userLocale=" + userLocale
				+ ", associatedPc=" + associatedPc + ", status=" + status + ", primExtensionPattern="
				+ primExtensionPattern + ", primExtensionRoutePartitionName=" + primExtensionRoutePartitionName + "]";
	}
}
