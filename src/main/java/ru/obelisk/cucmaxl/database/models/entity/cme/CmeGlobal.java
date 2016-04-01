package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonView;


import ru.obelisk.cucmaxl.database.models.views.View;


@Entity
@Table(name = "cme_global_state", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeGlobal implements Serializable {
	private static final long serialVersionUID = -2996907910162345512L;

	@JsonView(View.CmeGlobal.class)
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "address")
	private String address;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "configured_device")
	private String configuredDevice;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "configured_extension")
	private String configuredExtension;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "device_registered")
	private String deviceRegistered;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "keep_alive_interval")
	private String keepAliveInterval;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "max_conference")
	private String maxConference;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "max_dn")
	private String maxDN;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "max_ephone")
	private String maxEphone;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "max_redirect")
	private String maxRedirect;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "mode")
	private String mode;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "name")
	private String name;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "peak_device_registered")
	private String peakDeviceRegistered;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "peak_device_registered_time")
	private String peakDeviceRegisteredTime;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "port_number")
	private String portNumber;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "service_engine")
	private String serviceEngine;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "version")
	private String version;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
	@Column(name = "voice_mail")
	private String voiceMail;
	
	@JsonView({View.CmeRouter.class, View.CmeGlobal.class})
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="global2url_service", catalog="adsync",
    	joinColumns=@JoinColumn(name="global_id"),
    	inverseJoinColumns=@JoinColumn(name="url_service_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmeUrlService> urlServices;
		
	@JsonView({View.CmeGlobal.class})
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cme_router_id")
	private CmeRouter cmeRouter;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConfiguredDevice() {
		return configuredDevice;
	}
	public void setConfiguredDevice(String configuredDevice) {
		this.configuredDevice = configuredDevice;
	}
	public String getConfiguredExtension() {
		return configuredExtension;
	}
	public void setConfiguredExtension(String configuredExtension) {
		this.configuredExtension = configuredExtension;
	}
	public String getDeviceRegistered() {
		return deviceRegistered;
	}
	public void setDeviceRegistered(String deviceRegistered) {
		this.deviceRegistered = deviceRegistered;
	}
	public String getKeepAliveInterval() {
		return keepAliveInterval;
	}
	public void setKeepAliveInterval(String keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}
	public String getMaxConference() {
		return maxConference;
	}
	public void setMaxConference(String maxConference) {
		this.maxConference = maxConference;
	}
	public String getMaxDN() {
		return maxDN;
	}
	public void setMaxDN(String maxDN) {
		this.maxDN = maxDN;
	}
	public String getMaxEphone() {
		return maxEphone;
	}
	public void setMaxEphone(String maxEphone) {
		this.maxEphone = maxEphone;
	}
	public String getMaxRedirect() {
		return maxRedirect;
	}
	public void setMaxRedirect(String maxRedirect) {
		this.maxRedirect = maxRedirect;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPeakDeviceRegistered() {
		return peakDeviceRegistered;
	}
	public void setPeakDeviceRegistered(String peakDeviceRegistered) {
		this.peakDeviceRegistered = peakDeviceRegistered;
	}
	public String getPeakDeviceRegisteredTime() {
		return peakDeviceRegisteredTime;
	}
	public void setPeakDeviceRegisteredTime(String peakDeviceRegisteredTime) {
		this.peakDeviceRegisteredTime = peakDeviceRegisteredTime;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getServiceEngine() {
		return serviceEngine;
	}
	public void setServiceEngine(String serviceEngine) {
		this.serviceEngine = serviceEngine;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVoiceMail() {
		return voiceMail;
	}
	public void setVoiceMail(String voiceMail) {
		this.voiceMail = voiceMail;
	}
	public Set<CmeUrlService> getUrlServices() {
		return urlServices;
	}
	public void setUrlServices(Set<CmeUrlService> urlServices) {
		this.urlServices = urlServices;
	}
	public int getNumberLocalized() {
		return numberLocalized;
	}
	public void setNumberLocalized(int numberLocalized) {
		this.numberLocalized = numberLocalized;
	}
	public CmeRouter getCmeRouter() {
		return cmeRouter;
	}
	public void setCmeRouter(CmeRouter cmeRouter) {
		this.cmeRouter = cmeRouter;
	}
	public boolean isNew(){
		return this.id==null; 
	}
	
	@Override
	public String toString() {
		return "CmeGlobal [address=" + address + ", configuredDevice=" + configuredDevice + ", configuredExtension="
				+ configuredExtension + ", deviceRegistered=" + deviceRegistered + ", keepAliveInterval="
				+ keepAliveInterval + ", maxConference=" + maxConference + ", maxDN=" + maxDN + ", maxEphone="
				+ maxEphone + ", maxRedirect=" + maxRedirect + ", mode=" + mode + ", name=" + name
				+ ", peakDeviceRegistered=" + peakDeviceRegistered + ", peakDeviceRegisteredTime="
				+ peakDeviceRegisteredTime + ", portNumber=" + portNumber + ", serviceEngine=" + serviceEngine
				+ ", version=" + version + ", voiceMail=" + voiceMail + ", urlServices=" + urlServices + "]";
	}
}
