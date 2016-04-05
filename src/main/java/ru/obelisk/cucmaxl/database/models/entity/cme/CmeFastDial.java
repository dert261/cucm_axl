package ru.obelisk.cucmaxl.database.models.entity.cme;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_fast_dial", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeFastDial implements Serializable{
	private static final long serialVersionUID = 5733927576860518642L;

	@JsonView(View.CmeDevice.class)
	@Id
	@SequenceGenerator(sequenceName = "cme_fast_dial_id_seq", name = "CmeFastDialIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeFastDialIdSequence")
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(View.CmeDevice.class)
	@Column(name = "fd_index")
	private String index;
	
	@JsonView(View.CmeDevice.class)
	@Column(name = "name")
	private String name;
	
	@JsonView(View.CmeDevice.class)
	@Column(name = "number")
	private String number;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="device_id")
    private CmeDevice device;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public CmeDevice getDevice() {
		return device;
	}

	public void setDevice(CmeDevice device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "CmeFastDial [id=" + id + ", index=" + index + ", name=" + name + ", number=" + number + "]";
	}
}
