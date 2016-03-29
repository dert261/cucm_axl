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
@Table(name = "cme_extension", catalog="adsync")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeExtension implements Serializable{
	private static final long serialVersionUID = -4061350618527819540L;

	@JsonView(View.CmeExtension.class)
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "description")
	private String description;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "first_name")
	private String firstName;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "call_status")
	private String callStatus;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "ext_cme_id")
	private String extCmeID;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "line_mode")
	private String lineMode;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "multilines")
	private String multiLines;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "number")
	private String number;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "port_name")
	private String portName;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "sec_number")
	private String secNumber;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "status")
	private String status;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "type")
	private String type;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "ext_usage")
	private String usage;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "label")
	private String label;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "last_name")
	private String lastName;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "mobility")
	private String mobility;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "name")
	private String name;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@Column(name = "pickup_group")
	private String pickupGroup;
	
	@JsonView(View.CmeExtension.class)
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cfwd_id")
	private CmeCallForward callForward = new CmeCallForward();
	
	@JsonView({View.CmeExtension.class, View.CmeRouter.class})
    @OneToMany(fetch=FetchType.LAZY, mappedBy="extension", cascade=CascadeType.MERGE)
	private Set<CmeExtMapStatus> devices = new HashSet<CmeExtMapStatus>(0);
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_custom_extension_id")
	private CmeCustomExtension customExtension;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public String getExtCmeID() {
		return extCmeID;
	}

	public void setExtCmeID(String extCmeID) {
		this.extCmeID = extCmeID;
	}

	public String getLineMode() {
		return lineMode;
	}

	public void setLineMode(String lineMode) {
		this.lineMode = lineMode;
	}

	public String getMultiLines() {
		return multiLines;
	}

	public void setMultiLines(String multiLines) {
		this.multiLines = multiLines;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getSecNumber() {
		return secNumber;
	}

	public void setSecNumber(String secNumber) {
		this.secNumber = secNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobility() {
		return mobility;
	}

	public void setMobility(String mobility) {
		this.mobility = mobility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPickupGroup() {
		return pickupGroup;
	}

	public void setPickupGroup(String pickupGroup) {
		this.pickupGroup = pickupGroup;
	}

	public CmeCallForward getCallForward() {
		return callForward;
	}

	public void setCallForward(CmeCallForward callForward) {
		this.callForward = callForward;
	}

	public Set<CmeExtMapStatus> getDevices() {
		return devices;
	}

	public void setDevices(Set<CmeExtMapStatus> devices) {
		this.devices = devices;
	}
	
	public boolean isNew(){
		return this.id==null;
	}

	@Override
	public String toString() {
		return "CmeExtension [id=" + id + ", description=" + description + ", firstName=" + firstName + ", callStatus="
				+ callStatus + ", extCmeID=" + extCmeID + ", lineMode=" + lineMode + ", multiLines=" + multiLines
				+ ", number=" + number + ", portName=" + portName + ", secNumber=" + secNumber + ", status=" + status
				+ ", type=" + type + ", usage=" + usage + ", label=" + label + ", lastName=" + lastName + ", mobility="
				+ mobility + ", name=" + name + ", pickupGroup=" + pickupGroup + ", callForward=" + callForward
				+ ", customExtension=" + customExtension + "]";
	}
}
