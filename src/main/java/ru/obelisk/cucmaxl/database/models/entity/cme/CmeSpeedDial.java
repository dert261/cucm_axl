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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_speed_dial", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeSpeedDial implements Serializable{
	private static final long serialVersionUID = 3075655849781111324L;

	@JsonView({View.CmeRouter.class, View.CmeDevice.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeRouter.class, View.CmeDevice.class})
	@Column(name = "sd_index")
	private String index;
	
	@JsonView({View.CmeRouter.class, View.CmeDevice.class})
	@Column(name = "label")
	private String label;
	
	@JsonView({View.CmeRouter.class, View.CmeDevice.class})
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
		return "CmeSpeedDial [id=" + id + ", index=" + index + ", label=" + label + ", number=" + number + "]";
	}
}
