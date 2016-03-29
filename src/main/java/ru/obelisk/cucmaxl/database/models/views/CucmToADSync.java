package ru.obelisk.cucmaxl.database.models.views;

import javax.xml.bind.annotation.XmlElement;

public class CucmToADSync {
	
	private String fqdnName;
	private String userId;
	private String phones;
		
	@XmlElement(name="fqdn")
	public String getFqdnName() {
		return fqdnName;
	}
	public void setFqdnName(String fqdnName) {
		this.fqdnName = fqdnName;
	}
	
	@XmlElement(name = "samaccountname")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@XmlElement(name = "phones")
	public String getPhones() {
		return phones;
	}
	
	public void setPhones(String phones) {
		this.phones = phones;
	}
	
	@Override
	public String toString() {
		return "CucmToADSync [fqdnName=" + fqdnName + ", userId=" + userId + ", phones=" + phones + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fqdnName == null) ? 0 : fqdnName.hashCode());
		result = prime * result + ((phones == null) ? 0 : phones.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CucmToADSync other = (CucmToADSync) obj;
		if (fqdnName == null) {
			if (other.fqdnName != null)
				return false;
		} else if (!fqdnName.equals(other.fqdnName))
			return false;
		if (phones == null) {
			if (other.phones != null)
				return false;
		} else if (!phones.equals(other.phones))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
	
	
}
