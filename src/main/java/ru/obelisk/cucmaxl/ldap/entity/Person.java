package ru.obelisk.cucmaxl.ldap.entity;

public class Person {
	private String cn;
	private String displayName;
	private String givenName;
	private String name;
	private String sn;
	private String sAMAccountName;
	private String sAMAccountType;
	private String employeeId;
	
	private String company;
	private String department;
	private String mail;
	private String objectGUID;
	private String title;
	private String l;
	private String streetAddress;
	private String telephoneNumber;
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	public String getsAMAccountType() {
		return sAMAccountType;
	}
	public void setsAMAccountType(String sAMAccountType) {
		this.sAMAccountType = sAMAccountType;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getObjectGUID() {
		return objectGUID;
	}
	public void setObjectGUID(String objectGUID) {
		this.objectGUID = objectGUID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	@Override
	public String toString() {
		return "Person [cn=" + cn + ", displayName=" + displayName + ", givenName=" + givenName + ", name=" + name
				+ ", sn=" + sn + ", sAMAccountName=" + sAMAccountName + ", sAMAccountType=" + sAMAccountType
				+ ", employeeId=" + employeeId + ", company=" + company + ", department=" + department + ", mail="
				+ mail + ", objectGUID=" + objectGUID + ", title=" + title + ", l=" + l + ", streetAddress="
				+ streetAddress + ", telephoneNumber=" + telephoneNumber + "]";
	}
}
