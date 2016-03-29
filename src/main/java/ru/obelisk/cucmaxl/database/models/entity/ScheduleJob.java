package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;
import ru.obelisk.cucmaxl.web.validator.NotNullField;

@Entity
@Table(name = "schedule_job", catalog="adsync")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleJob implements Serializable {
	private static final long serialVersionUID = -8885259600985788247L;

	@Id
	@JsonView(value={View.ScheduleJob.class, View.LdapDirSyncParameters.class})
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(value={View.ScheduleJob.class, View.LdapDirSyncParameters.class})
    @Transient
    private int numberLocalized;
	
	@JsonView(value={View.ScheduleJob.class, View.LdapDirSyncParameters.class})
	@Column(name = "name")
	@NotNullField
    @NotEmpty
	private String name;
	
	@JsonView(value={View.ScheduleJob.class, View.LdapDirSyncParameters.class})
	@Column(name = "description")
	private String description;
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "startDate")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@NotNullField
    private DateTime startDate = null;
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "stopDate")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime stopDate = null;
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "repeatFlag")
	private boolean repeatFlag = false;
	
	@Valid
	@JsonView(value={View.ScheduleJob.class})
	@OneToOne(fetch=FetchType.LAZY, optional=true)
	private ScheduleCron scheduleCron;
	
	@JsonView(value={View.ScheduleJob.class})
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	//@JoinColumn(name="scheduleJob_id")
	private Set<LdapDirSyncParameters> ldapDirSyncParameters = new HashSet<LdapDirSyncParameters>(0);
	
	@JsonView(value={View.ScheduleJob.class})
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	//@JoinColumn(name="scheduleJob_id")
	private Set<CucmAxlPort> cucmAxlPorts = new HashSet<CucmAxlPort>(0);
	
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
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public DateTime getStopDate() {
		return stopDate;
	}
	public void setStopDate(DateTime stopDate) {
		this.stopDate = stopDate;
	}
	public boolean isRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(boolean repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public ScheduleCron getScheduleCron() {
		return scheduleCron;
	}
	public void setScheduleCron(ScheduleCron scheduleCron) {
		this.scheduleCron = scheduleCron;
	}
		
	public Set<LdapDirSyncParameters> getLdapDirSyncParameters() {
		return ldapDirSyncParameters;
	}
	public void setLdapDirSyncParameters(Set<LdapDirSyncParameters> ldapDirSyncParameters) {
		this.ldapDirSyncParameters = ldapDirSyncParameters;
	}
	public Set<CucmAxlPort> getCucmAxlPorts() {
		return cucmAxlPorts;
	}
	public void setCucmAxlPorts(Set<CucmAxlPort> cucmAxlPorts) {
		this.cucmAxlPorts = cucmAxlPorts;
	}
	
	public boolean isNew(){
		return this.id==null;
	}
	@Override
	public String toString() {
		return "ScheduleJob [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ ", stopDate=" + stopDate + ", repeatFlag=" + repeatFlag + ", scheduleCron=" + scheduleCron + "]";
	}
}
