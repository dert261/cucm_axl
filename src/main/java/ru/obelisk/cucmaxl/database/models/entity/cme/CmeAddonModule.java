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
@Table(name = "cme_addon_module", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeAddonModule implements Serializable{
	private static final long serialVersionUID = 3709126789148061176L;

	@JsonView(View.CmeDevice.class)
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "cme_addon_module_id_seq", name = "CmeAddonModuleIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeAddonModuleIdSequence")
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(View.CmeDevice.class)
	@Column(name = "name")
	private String name;
	
	@JsonView(View.CmeDevice.class)
	@Column(name = "type")
	private String type;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="device_id")
    private CmeDevice device;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public CmeDevice getDevice() {
		return device;
	}

	public void setDevice(CmeDevice device) {
		this.device = device;
	}
	
	@Override
	public String toString() {
		return "CmeAddonModule [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	
	
}
