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
@Table(name = "cme_sip_device", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeSipDevice implements Serializable {
	private static final long serialVersionUID = 1616971777135923950L;

	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class})
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Id
	@SequenceGenerator(sequenceName = "cme_sip_device_id_seq", name = "CmeSipDeviceIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeSipDeviceIdSequence")
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Column(name = "device_cme_id")
	private String deviceCmeId;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Column(name = "name")
	private String name;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Column(name = "codec")
    private String codec;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Column(name = "pool_dtmf_relay")
    private String poolDtmfRelay;
	
	@JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Column(name = "pool_max_registration")
    private String poolMaxRegistration;
	    
    @JsonView({View.CmeSipDevice.class, View.CmeRouter.class})
    @OneToMany(fetch=FetchType.LAZY, mappedBy="device", cascade=CascadeType.ALL)
	private Set<CmeSipExtMapStatus> lines = new HashSet<CmeSipExtMapStatus>(0);
    
    @JsonView({View.CmeSipDevice.class, View.CmeSipExtension.class})
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="router_id")
    private CmeRouter router;
    
    @JsonView(View.CmeSipDevice.class)
    @Getter
    @Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_custom_sip_device_id")
	private CmeCustomSipDevice customSipDevice;
    
    @JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Getter
	@Setter
	@Column(name = "export_state")
    private boolean exported;
	
    @JsonView({View.CmeSipDevice.class, View.CmeRouter.class, View.CmeSipExtension.class})
	@Getter
	@Setter
	@Column(name = "export_message")
    private String export_message;
	
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

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getPoolDtmfRelay() {
		return poolDtmfRelay;
	}

	public void setPoolDtmfRelay(String poolDtmfRelay) {
		this.poolDtmfRelay = poolDtmfRelay;
	}

	public String getPoolMaxRegistration() {
		return poolMaxRegistration;
	}

	public void setPoolMaxRegistration(String poolMaxRegistration) {
		this.poolMaxRegistration = poolMaxRegistration;
	}

	public Set<CmeSipExtMapStatus> getLines() {
		return lines;
	}

	public void setLines(Set<CmeSipExtMapStatus> lines) {
		this.lines = lines;
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
		return "CmeSipDevice [numberLocalized=" + numberLocalized + ", id=" + id + ", deviceCmeId=" + deviceCmeId
				+ ", name=" + name + ", codec=" + codec + ", poolDtmfRelay=" + poolDtmfRelay + ", poolMaxRegistration="
				+ poolMaxRegistration + "]";
	}
}
