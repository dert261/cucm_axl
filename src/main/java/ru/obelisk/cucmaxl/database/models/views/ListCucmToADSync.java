package ru.obelisk.cucmaxl.database.models.views;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users-phones-list" )  
public class ListCucmToADSync {
	private Set<CucmToADSync> user = new HashSet<CucmToADSync>(0);

	public Set<CucmToADSync> getUser() {
		return user;
	}

	public void setUser(Set<CucmToADSync> user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		ListCucmToADSync other = (ListCucmToADSync) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
}
