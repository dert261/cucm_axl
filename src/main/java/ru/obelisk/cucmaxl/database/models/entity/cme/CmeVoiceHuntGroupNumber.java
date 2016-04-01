package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_voice_hunt_group_number", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class CmeVoiceHuntGroupNumber implements Serializable{
	private static final long serialVersionUID = -1689231400333661117L;

	@JsonView(View.CmeVoiceHuntGroup.class)
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "num_index")
	private int index = 0;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@Column(name = "number")
	private String number;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name="sip_number_id")
	private CmeSipExtension sipNumber;
	
	@JsonView(View.CmeVoiceHuntGroup.class)
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name="sccp_number_id")
	private CmeExtension sccpNumber;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="voice_hunt_group_id")
    private CmeVoiceHuntGroup voiceHuntGroup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public CmeVoiceHuntGroup getVoiceHuntGroup() {
		return voiceHuntGroup;
	}

	public void setVoiceHuntGroup(CmeVoiceHuntGroup voiceHuntGroup) {
		this.voiceHuntGroup = voiceHuntGroup;
	}
	
	public CmeSipExtension getSipNumber() {
		return sipNumber;
	}

	public void setSipNumber(CmeSipExtension sipNumber) {
		this.sipNumber = sipNumber;
	}

	public CmeExtension getSccpNumber() {
		return sccpNumber;
	}

	public void setSccpNumber(CmeExtension sccpNumber) {
		this.sccpNumber = sccpNumber;
	}

	@Override
	public String toString() {
		return "CmeVoiceHuntGroupNumber [id=" + id + ", index=" + index + ", number=" + number + "]";
	}
}
