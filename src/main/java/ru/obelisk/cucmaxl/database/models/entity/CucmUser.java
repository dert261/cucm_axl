package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.cisco.axl.api._10.LUser;

@Entity
@Table(name = "cucm_user", catalog="adsync", schema="public")
public class CucmUser implements Serializable {
	private static final long serialVersionUID = -7219265456818335570L;

	@Id
	@SequenceGenerator(sequenceName = "cucm_user_id_seq", name = "CucmUserIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmUserIdSequence")
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;

	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "account_type")
	private String accountType;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "title")
	private String title;

	@Column(name = "directory_uri")
	private String directoryUri;

	@Column(name = "telephone_number")
	private String telephoneNumber;

	@Column(name = "home_number")
	private String homeNumber;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "pager_number")
	private String pagerNumber;

	@Column(name = "mailid")
	private String mailid;

	@Column(name = "manager")
	private String manager;

	@Column(name = "department")
	private String department;

	@Column(name = "user_locale")
	private String userLocale;

	@Column(name = "associated_pc")
	private String associatedPc;

	@Column(name = "status")
	private String status;

	@Column(name = "prim_extension_pattern")
	private String primExtensionPattern;

	@Column(name = "prim_extension_route_partition_name")
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
