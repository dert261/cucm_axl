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
@Table(name = "cme_ext_map_status", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeExtMapStatus implements Serializable{
	private static final long serialVersionUID = -3744600591640271299L;

	@JsonView({View.CmeDevice.class, View.CmeExtension.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
		
	@JsonView({View.CmeDevice.class, View.CmeExtension.class})
	@Column(name = "line_id")
	private String lineId;
	
	@JsonView({View.CmeDevice.class, View.CmeExtension.class})
	@Column(name = "line_state")
	private String lineState;
	
	@JsonView(View.CmeExtension.class)
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="device_id")
    private CmeDevice device;
	
	@JsonView(View.CmeDevice.class)
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="extension_id")
	private CmeExtension extension;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineState() {
		return lineState;
	}

	public void setLineState(String lineState) {
		this.lineState = lineState;
	}

	public CmeDevice getDevice() {
		return device;
	}

	public void setDevice(CmeDevice device) {
		this.device = device;
	}

	public CmeExtension getExtension() {
		return extension;
	}

	public void setExtension(CmeExtension extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "CmeExtMapStatus [id=" + id + ", lineId=" + lineId + ", lineState=" + lineState + ", extension="
				+ extension + "]";
	}
}
