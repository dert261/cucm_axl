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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.cucm.utils.CucmRepo;

@Entity
@Table(name = "cucm_device", catalog="adsync", schema="public")
@EqualsAndHashCode(exclude={"id","linesTemp","lines"})
@ToString(exclude={"linesTemp", "lines", "userId", "cucmAxlPort"})
public class CucmDevice implements Serializable, CucmRepo {
	private static final long serialVersionUID = -8143008404515034945L;
	
	@Setter
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	private User userId;
	
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
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
}
