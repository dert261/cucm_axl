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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.cucm.utils.CucmRepo;

@Entity
@Table(name = "cucm_device", catalog="adsync", schema="public")
@ToString(exclude={"linesTemp", "lines", "userId", "cucmAxlPort"})
public class CucmDevice implements Serializable, CucmRepo {
	private static final long serialVersionUID = -8143008404515034945L;
	
	@Setter
	@Getter
	@Id
	@SequenceGenerator(sequenceName = "cucm_device_id_seq", name = "CucmDeviceIdSequence", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmDeviceIdSequence")
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Getter
	@Setter
	@Column(name = "pkid")
	private String pkid;
	
	@Getter
	@Setter
	@Column(name = "name")
	private String name;
	
	@Getter
	@Setter
	@Column(name = "description")
	private String description;
	
	@Getter
	@Setter
	@Column(name = "protocol")
	private String protocol;
	
	@Getter
	@Setter	
	@Column(name = "model")
	private String model;
		
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userId;
	
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cucm_axl_port_id")
	private CucmAxlPort cucmAxlPort;
	
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<CucmDeviceLine> lines = new HashSet<CucmDeviceLine>(0);
	
	@Transient
	@Getter
	@Setter
	private Set<CucmDeviceLine> linesTemp = new HashSet<CucmDeviceLine>(0);
	
	@Transient
	@Getter
	@Setter
	private boolean changed = false;

	public boolean isNew(){
		return this.id==null;
	}

	public boolean myEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CucmDevice other = (CucmDevice) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pkid == null) {
			if (other.pkid != null)
				return false;
		} else if (!pkid.equals(other.pkid))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		/*if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (other.userId!=null || !userId.getId().equals(other.userId.getId()))
			return false;*/
		return true;
	}
}
