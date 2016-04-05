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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.cucm.utils.CucmRepo;

@Entity
@Table(name = "cucm_line", catalog="adsync", schema="public")
//@EqualsAndHashCode(exclude={"id","devices"})
@ToString(exclude={"devices"})
public class CucmLine implements Serializable, CucmRepo {
	private static final long serialVersionUID = -8429115892777286213L;

	@Getter
	@Setter
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "cucm_line_id_seq", name = "CucmLineIdSequence", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmLineIdSequence")
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Getter
	@Setter
	@Column(name = "pkid")
	private String pkid;
	
	@Getter
	@Setter
	@Column(name = "pattern")
	private String pattern;
	
	@Getter
	@Setter
	@Column(name = "description")
	private String description;
	
	@Getter
	@Setter
	@Column(name = "partition")
	private String partition;
	
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy="line", cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
	private Set<CucmDeviceLine> devices = new HashSet<CucmDeviceLine>(0);

	public boolean isNew(){
		return this.id==null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CucmLine other = (CucmLine) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (partition == null) {
			if (other.partition != null)
				return false;
		} else if (!partition.equals(other.partition))
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		if (pkid == null) {
			if (other.pkid != null)
				return false;
		} else if (!pkid.equals(other.pkid))
			return false;
		return true;
	}
}
