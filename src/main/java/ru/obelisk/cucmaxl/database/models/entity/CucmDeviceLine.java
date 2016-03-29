package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cucm_device_line", catalog="adsync",
		uniqueConstraints=@UniqueConstraint(columnNames={"deviceId", "lineId"}))
@EqualsAndHashCode(exclude={"id"})
@ToString
public class CucmDeviceLine implements Serializable, Comparable<CucmDeviceLine> {
	private static final long serialVersionUID = 7054028254892414948L;

	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Column(name = "lineIndex")
	@Getter
	@Setter
	private String lineIndex;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@Getter
	@Setter
	@JoinColumn(name = "deviceId")
	private CucmDevice device;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@Getter
	@Setter
	@JoinColumn(name = "lineId")
	private CucmLine line;

	@Override
	public int compareTo(CucmDeviceLine o) {
		int result = Integer.parseInt(this.lineIndex) - Integer.parseInt(o.getLineIndex());
        if(result != 0) {
               return (int) result / Math.abs( result );
        }
		return 0;
	}
}
