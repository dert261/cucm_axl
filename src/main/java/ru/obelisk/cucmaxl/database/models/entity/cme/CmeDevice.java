package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_device", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeDevice implements Serializable {
	private static final long serialVersionUID = 1616971777135923950L;

	@JsonView({View.CmeDevice.class, View.CmeRouter.class})
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Id
	@SequenceGenerator(sequenceName = "cme_device_id_seq", name = "CmeDeviceIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeDeviceIdSequence")
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "device_cme_id")
	private String deviceCmeId;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "name")
	private String name;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "description")
    private String description;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "config_type")
    private String configType;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "type")
    private String type;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "dnd")
    private String dnd;
    
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "status")
    private String status;
    
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "last_status")
    private String lastStatus;
    
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "username")
    private String username;
    
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "password")
    private String password;
    
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Column(name = "address")
    private String address;
	
	
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Getter
	@Setter
	@Column(name = "export_state")
    private boolean exported;
	
	@JsonView({View.CmeDevice.class, View.CmeRouter.class, View.CmeExtension.class})
	@Getter
	@Setter
	@Column(name = "export_message")
    private String export_message;
	
    
	@JsonView(View.CmeDevice.class)
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL)
	private List<CmeExtMapStatus> lines = new ArrayList<CmeExtMapStatus>(0);
    
    @JsonView(View.CmeDevice.class)
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<CmeBlfSpeedDial> blfSpeedDials = new HashSet<CmeBlfSpeedDial>(0);
    
    @JsonView(View.CmeDevice.class)
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<CmeFastDial> fastDials = new HashSet<CmeFastDial>(0);
    
    @JsonView(View.CmeDevice.class)
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<CmeSpeedDial> speedDials = new HashSet<CmeSpeedDial>(0);
    
    @JsonView(View.CmeDevice.class)
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<CmeAddonModule> addonModules = new HashSet<CmeAddonModule>(0);
    
    @JsonView({View.CmeDevice.class, View.CmeExtension.class})
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="router_id")
    private CmeRouter router;
    
    @JsonView(View.CmeDevice.class)
    @Getter
    @Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_custom_device_id")
	private CmeCustomDevice customDevice;

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

	public String getDeviceCmeId() {
		return deviceCmeId;
	}

	public void setDeviceCmeId(String deviceCmeId) {
		this.deviceCmeId = deviceCmeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDnd() {
		return dnd;
	}

	public void setDnd(String dnd) {
		this.dnd = dnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CmeExtMapStatus> getLines() {
		return lines;
	}

	public void setLines(List<CmeExtMapStatus> lines) {
		this.lines = lines;
	}

	public Set<CmeBlfSpeedDial> getBlfSpeedDials() {
		return blfSpeedDials;
	}

	public void setBlfSpeedDials(Set<CmeBlfSpeedDial> blfSpeedDials) {
		this.blfSpeedDials.clear();
		this.blfSpeedDials.addAll(blfSpeedDials);
	}

	public Set<CmeFastDial> getFastDials() {
		return fastDials;
	}

	public void setFastDials(Set<CmeFastDial> fastDials) {
		this.fastDials.clear();
		this.fastDials.addAll(fastDials);
	}

	public Set<CmeSpeedDial> getSpeedDials() {
		return speedDials;
	}

	public void setSpeedDials(Set<CmeSpeedDial> speedDials) {
		this.speedDials.clear();
		this.speedDials.addAll(speedDials);
	}

	public Set<CmeAddonModule> getAddonModules() {
		return addonModules;
	}

	public void setAddonModules(Set<CmeAddonModule> addonModules) {
		this.addonModules.clear();
		this.addonModules.addAll(addonModules);
	}

	public CmeRouter getRouter() {
		return router;
	}

	public void setRouter(CmeRouter router) {
		this.router = router;
	}
	
	public boolean isNew(){
		return this.id==null;
	}
		
	@Override
	public String toString() {
		return "CmeDevice [id=" + id + ", deviceCmeId=" + deviceCmeId + ", name=" + name + ", description="
				+ description + ", configType=" + configType + ", type=" + type + ", dnd=" + dnd + ", status=" + status
				+ ", lastStatus=" + lastStatus + ", username=" + username + ", password=" + password + ", address="
				+ address + ", lines=" + lines + ", customDevice=" + customDevice + "]";
	}
    
    
}
