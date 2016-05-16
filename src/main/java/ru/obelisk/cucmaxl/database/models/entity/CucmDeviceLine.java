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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cucm_device_line", catalog="adsync", schema="public",
		uniqueConstraints=@UniqueConstraint(columnNames={"device_id", "line_id"}))
@ToString
public class CucmDeviceLine implements Serializable, Comparable<CucmDeviceLine> {
	private static final long serialVersionUID = 7054028254892414948L;

	
	@Getter
	@Setter
	@Id
	@SequenceGenerator(sequenceName = "cucm_device_line_id_seq", name = "CucmDeviceLineIdSequence", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CucmDeviceLineIdSequence")
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Getter
	@Setter
	@Column(name = "line_index")
	private String lineIndex;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@Getter
	@Setter
	@NotNull
	@JoinColumn(name = "device_id")
	private CucmDevice device;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@Getter
	@Setter
	@NotNull
	@JoinColumn(name = "line_id")
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
