package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_voice_hunt_group", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class CmeVoiceHuntGroup implements Serializable{
	private static final long serialVersionUID = 2134823571597443942L;

	@JsonView(View.CmeVoiceHuntGroup.class)
	@Transient
    private int numberLocalized;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "final_Num")
	private String finalNum;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "hops")
	private String hops;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "cme_group_id")
	private String groupID;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "numbers_count")
	private String listSize;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "pilot_number")
	private String pilotNumber;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "pilot_peer_tag")
	private String pilotPeerTag;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "pilot_preference")
	private String pilotPreference;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "sec_pilot_number")
	private String secPilotNumber;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "sec_pilot_peer_tag")
	private String secPilotPeerTag;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "sec_pilot_preference")
	private String secPilotPreference;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "timeout")
	private String timeout;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "type")
	private String type;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@OneToMany(fetch=FetchType.LAZY, mappedBy="voiceHuntGroup", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<CmeVoiceHuntGroupNumber> numbers = new ArrayList<CmeVoiceHuntGroupNumber>(0);
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="router_id")
    private CmeRouter router;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFinalNum() {
		return finalNum;
	}

	public void setFinalNum(String finalNum) {
		this.finalNum = finalNum;
	}

	public String getHops() {
		return hops;
	}

	public void setHops(String hops) {
		this.hops = hops;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getListSize() {
		return listSize;
	}

	public void setListSize(String listSize) {
		this.listSize = listSize;
	}

	public String getPilotNumber() {
		return pilotNumber;
	}

	public void setPilotNumber(String pilotNumber) {
		this.pilotNumber = pilotNumber;
	}

	public String getPilotPeerTag() {
		return pilotPeerTag;
	}

	public void setPilotPeerTag(String pilotPeerTag) {
		this.pilotPeerTag = pilotPeerTag;
	}

	public String getPilotPreference() {
		return pilotPreference;
	}

	public void setPilotPreference(String pilotPreference) {
		this.pilotPreference = pilotPreference;
	}

	public String getSecPilotNumber() {
		return secPilotNumber;
	}

	public void setSecPilotNumber(String secPilotNumber) {
		this.secPilotNumber = secPilotNumber;
	}

	public String getSecPilotPeerTag() {
		return secPilotPeerTag;
	}

	public void setSecPilotPeerTag(String secPilotPeerTag) {
		this.secPilotPeerTag = secPilotPeerTag;
	}

	public String getSecPilotPreference() {
		return secPilotPreference;
	}

	public void setSecPilotPreference(String secPilotPreference) {
		this.secPilotPreference = secPilotPreference;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CmeVoiceHuntGroupNumber> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<CmeVoiceHuntGroupNumber> numbers) {
		this.numbers.clear();
		this.numbers.addAll(numbers);
	}

	public CmeRouter getRouter() {
		return router;
	}

	public void setRouter(CmeRouter router) {
		this.router = router;
	}
	
	public int getNumberLocalized() {
		return numberLocalized;
	}

	public void setNumberLocalized(int numberLocalized) {
		this.numberLocalized = numberLocalized;
	}

	public boolean isNew(){
		return this.id==null;
	}

	@Override
	public String toString() {
		return "CmeVoiceHuntGroup [id=" + id + ", finalNum=" + finalNum + ", hops=" + hops + ", groupID=" + groupID
				+ ", listSize=" + listSize + ", pilotNumber=" + pilotNumber + ", pilotPeerTag=" + pilotPeerTag
				+ ", pilotPreference=" + pilotPreference + ", secPilotNumber=" + secPilotNumber + ", secPilotPeerTag="
				+ secPilotPeerTag + ", secPilotPreference=" + secPilotPreference + ", timeout=" + timeout + ", type="
				+ type + ", numbers=" + numbers + "]";
	}
}
