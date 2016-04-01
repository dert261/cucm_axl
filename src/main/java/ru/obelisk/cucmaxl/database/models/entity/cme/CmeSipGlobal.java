package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;


@Entity
@Table(name = "cme_sip_global_state", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeSipGlobal implements Serializable {
	private static final long serialVersionUID = -2996907910162345512L;

	@JsonView(View.CmeSipGlobal.class)
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "address")
	private String address;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "mode")
	private String mode;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "max_dn")
	private String maxDN;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "max_pool")
	private String maxPool;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "max_redirect")
	private String maxRedirect;
	
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "port_number")
	private String portNumber;
		
	@JsonView({View.CmeRouter.class, View.CmeSipGlobal.class})
	@Column(name = "version")
	private String version;
	
	@JsonView({View.CmeSipGlobal.class})
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cme_router_id")
	private CmeRouter cmeRouter;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMaxDN() {
		return maxDN;
	}

	public void setMaxDN(String maxDN) {
		this.maxDN = maxDN;
	}

	public String getMaxPool() {
		return maxPool;
	}

	public void setMaxPool(String maxPool) {
		this.maxPool = maxPool;
	}

	public String getMaxRedirect() {
		return maxRedirect;
	}

	public void setMaxRedirect(String maxRedirect) {
		this.maxRedirect = maxRedirect;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
		return "CmeSipGlobal [numberLocalized=" + numberLocalized + ", id=" + id + ", address=" + address + ", mode="
				+ mode + ", maxDN=" + maxDN + ", maxPool=" + maxPool + ", maxRedirect=" + maxRedirect + ", portNumber="
				+ portNumber + ", version=" + version + "]";
	}
}
