package ru.obelisk.cucmaxl.database.models.entity.cme;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_sip_extension", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeSipExtension implements Serializable{
	private static final long serialVersionUID = -4061350618527819540L;

	@JsonView(View.CmeSipExtension.class)
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class, View.CmeRouter.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
		
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class, View.CmeRouter.class})
	@Column(name = "first_name")
	private String firstName;
	
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class, View.CmeRouter.class})
	@Column(name = "ext_cme_id")
	private String extCmeID;
	
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class, View.CmeRouter.class})
	@Column(name = "number")
	private String number;
	
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class, View.CmeRouter.class})
	@Column(name = "last_name")
	private String lastName;
	
	@JsonView({View.CmeSipExtension.class, View.CmeRouter.class})
    @OneToMany(fetch=FetchType.LAZY, mappedBy="extension", cascade=CascadeType.ALL)
	private Set<CmeSipExtMapStatus> devices = new HashSet<CmeSipExtMapStatus>(0);
	
	@JsonView({View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_custom_sip_extension_id")
	private CmeCustomSipExtension customSipExtension;

	public int getNumberLocalized() {
		return numberLocalized;
	}

	public void setNumberLocalized(int numberLocalized) {
		this.numberLocalized = numberLocalized;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getExtCmeID() {
		return extCmeID;
	}

	public void setExtCmeID(String extCmeID) {
		this.extCmeID = extCmeID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<CmeSipExtMapStatus> getDevices() {
		return devices;
	}

	public void setDevices(Set<CmeSipExtMapStatus> devices) {
		this.devices = devices;
	}
	
	public boolean isNew(){
		return this.id==null;
	}

	@Override
	public String toString() {
		return "CmeSipExtension [numberLocalized=" + numberLocalized + ", id=" + id + ", firstName=" + firstName
				+ ", extCmeID=" + extCmeID + ", number=" + number + ", lastName=" + lastName + "]";
	}
}
