package ru.obelisk.cucmaxl.database.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "ldap_custom_filter", catalog="adsync")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LdapCustomFilter {
	
	public interface LdapAuthServersValid{}
	
	@JsonView(value={View.LdapCustomFilter.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
	@Transient
	@JsonView(value={View.LdapCustomFilter.class})
    private int numberLocalized;
	
	@JsonView(value={View.LdapCustomFilter.class})
    @Column(name = "name", length = 200, nullable = false)
    @NotNull
    @NotEmpty
    private String name = null;
	
	@JsonView(value={View.LdapCustomFilter.class})
	@Column(name = "filter", length = 500, nullable = false)
    @NotNull
    @NotEmpty
    private String filter = null;
	    
    public LdapCustomFilter (){
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
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
	
	@Override
	public String toString() {
		return "LdapCustomFilter [id=" + id + ", name=" + name + ", filter=" + filter + "]";
	}
}
