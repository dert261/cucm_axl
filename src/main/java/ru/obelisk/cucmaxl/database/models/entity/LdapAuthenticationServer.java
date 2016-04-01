package ru.obelisk.cucmaxl.database.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "ldap_authentication_servers", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LdapAuthenticationServer {
	
	public interface LdapAuthServersValid{}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
    public LdapAuthenticationParameters getLdapAuthParams() {
		return ldapAuthParams;
	}

	@Column(name = "host", length = 200, nullable = false)
    @NotNull
    @NotEmpty
    private String host = null;
    
    @Column(name = "port", length = 5, nullable = false)
    @Min(value = 0, message = "field.validation.error.min")
    @Max(value = 65535, message = "field.validation.error.max")
    private String port = "389";
    
    @Column(name = "use_ssl")
    private boolean useSSL = false;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private LdapAuthenticationParameters ldapAuthParams;
    
    public LdapAuthenticationServer (){
    	
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isUseSSL() {
		return useSSL;
	}

	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}

	@Override
	public String toString() {
		return "LdapAuthenticationServers [id=" + id + ", host=" + host
				+ ", port=" + port + ", useSSL=" + useSSL + "]";
	}
}
