package ru.obelisk.cucmaxl.database.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "ldap_dir_sync_server", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LdapDirSyncServer {
	
	public interface LdapAuthServersValid{}
	
	@JsonView(value={View.LdapDirSyncParameters.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
	@JsonView(value={View.LdapDirSyncParameters.class})
    @Column(name = "host", length = 200, nullable = false)
    @NotNull
    @NotEmpty
    private String host = null;
    
	@JsonView(value={View.LdapDirSyncParameters.class})
    @Column(name = "port", length = 5, nullable = false)
    @Min(value = 0, message = "field.validation.error.min")
    @Max(value = 65535, message = "field.validation.error.max")
    private String port = "389";
    
	@JsonView(value={View.LdapDirSyncParameters.class})
    @Column(name = "use_ssl")
    private boolean useSSL = false;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ldap_dir_sync_parameters_id")
    private LdapDirSyncParameters ldapDirSyncParameters;
    
    public LdapDirSyncServer (){
    	
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
	
	public LdapDirSyncParameters getLdapDirSyncParameters() {
		return ldapDirSyncParameters;
	}

	public void setLdapDirSyncParameters(LdapDirSyncParameters ldapDirSyncParameters) {
		this.ldapDirSyncParameters = ldapDirSyncParameters;
	}

	@Override
	public String toString() {
		return "LdapAuthenticationServers [id=" + id + ", host=" + host
				+ ", port=" + port + ", useSSL=" + useSSL + "]";
	}
}
