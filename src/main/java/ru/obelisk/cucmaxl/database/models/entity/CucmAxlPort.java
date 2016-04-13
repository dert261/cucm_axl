package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

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
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import ru.obelisk.cucmaxl.database.models.entity.enums.ResyncStatus;
import ru.obelisk.cucmaxl.database.models.entity.enums.ResyncUnit;
import ru.obelisk.cucmaxl.database.models.entity.utils.BaseEntity;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;


@Entity
@Table(name = "cucm_axl_port", catalog="adsync", schema="public")
public class CucmAxlPort implements Serializable, BaseEntity {
	
	private static final long serialVersionUID = 5227717725577564434L;

	@Id
	@JsonView(value={View.Collector.class, View.RouterExportDetail.class, View.EndUser.class, View.CucmAxlPort.class})
	@SequenceGenerator(sequenceName = "cucm_axl_port_id_seq", name = "CucmAxlPortIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmAxlPortIdSequence")
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(value={View.CucmAxlPort.class})
	@Transient
	private int numberLocalized;
	
	@JsonView(value={View.Collector.class, View.RouterExportDetail.class, View.EndUser.class, View.CucmAxlPort.class})
	@Column(name = "name")
	@NotNull 
    @NotEmpty
	private String name=null;
	
	@JsonView(value={View.Collector.class, View.CucmAxlPort.class})
	@Column(name = "description")
	private String description=null;
	
	@JsonView(value={View.CucmAxlPort.class})
	@Column(name = "axl_url")
	@NotNull 
    @NotEmpty
	private String axlUrl=null;
	
	@JsonView(value={View.CucmAxlPort.class})
	@Column(name = "axl_user")
	@NotNull 
    @NotEmpty
	private String axlUser=null;
	
	@Column(name = "axl_password")
	@NotNull 
    @NotEmpty
	private String axlPassword=null;
	
	@JsonView(value={View.EndUser.class, View.CucmAxlPort.class})
    @Column(name = "fqdn_name", nullable = false)
    @NotNull 
    @NotEmpty
    private String fqdnName=null;
	
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="partition_filter_id")
    private PartitionFilter partitionFilter = null;
    
	@JsonView(value={View.CucmAxlPort.class})
    @Column(name = "ldap_sync_flag")
    private boolean ldapSyncFlag = false;

	@Transient
	@NotNull 
    @NotEmpty
	private String axlPasswordConfirm=null;
	
	@JsonView(value={View.CucmAxlPort.class})
    @Column(name = "resync_flag")
    private boolean resyncFlag = true;
	
	//@JsonView(value={View.LdapDirSyncParameters.class})
    @Column(name = "resync_interval")
    private int resyncInterval = 1;
	
	//@JsonView(value={View.LdapDirSyncParameters.class})
    @Column(name = "resync_unit")
	@Enumerated(EnumType.STRING)
    private ResyncUnit resyncUnit = ResyncUnit.HOUR;
	
    @JsonView(value={View.CucmAxlPort.class})
    @Column(name = "resync_status")
	@Enumerated(EnumType.STRING)
    private ResyncStatus resyncStatus = ResyncStatus.NONACTIVE;

	@PostLoad
	private void postLoad(){
		this.axlPasswordConfirm = this.axlPassword;
	}
		
	public String getAxlPasswordConfirm() {
		return axlPasswordConfirm;
	}

	public void setAxlPasswordConfirm(String axlPasswordConfirm) {
		this.axlPasswordConfirm = axlPasswordConfirm;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAxlUrl() {
		return axlUrl;
	}

	public void setAxlUrl(String axlUrl) {
		this.axlUrl = axlUrl;
	}

	public String getAxlUser() {
		return axlUser;
	}

	public void setAxlUser(String axlUser) {
		this.axlUser = axlUser;
	}

	public String getAxlPassword() {
		return axlPassword;
	}

	public void setAxlPassword(String axlPassword) {
		this.axlPassword = axlPassword;
	}
	
	public int getNumberLocalized() {
		return numberLocalized;
	}

	public void setNumberLocalized(int numberLocalized) {
		this.numberLocalized = numberLocalized;
	}

	public String getFqdnName() {
		return fqdnName;
	}

	public void setFqdnName(String fqdnName) {
		this.fqdnName = fqdnName;
	}
	
	public boolean isNew(){
		return this.id==null;
	}
	
	public boolean isLdapSyncFlag() {
		return ldapSyncFlag;
	}

	public void setLdapSyncFlag(boolean ldapSyncFlag) {
		this.ldapSyncFlag = ldapSyncFlag;
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

	@Override
	public String toString() {
		return "CucmAxlPort [id=" + id + ", name=" + name + ", description=" + description + ", axlUrl=" + axlUrl
				+ ", axlUser=" + axlUser + ", fqdnName=" + fqdnName + ", ldapSyncFlag=" + ldapSyncFlag + ", resyncFlag="
				+ resyncFlag + ", resyncInterval=" + resyncInterval + ", resyncUnit=" + resyncUnit + "]";
	}
}
