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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_callforward", catalog="adsync")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeCallForward implements Serializable{
	private static final long serialVersionUID = 548352710978394386L;

	@JsonView(View.CmeExtension.class)
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(View.CmeExtension.class)
	@Column(name = "cfwdall_number")
	private String cfwdAllNumber;
	
	@JsonView(View.CmeExtension.class)
	@Column(name = "cfwdbusy_number")
	private String cfwdBusyNumber;
	
	@JsonView(View.CmeExtension.class)
	@Column(name = "max_length")
	private String maxLength;
	
	@JsonView(View.CmeExtension.class)
	@Column(name = "cfwdnoans_number")
	private String cfwdNoanNumber;
	
	@JsonView(View.CmeExtension.class)
	@Column(name = "cfwdnoans_timeout")
	private String cfwdNoanTimeout;
	
	@JsonView({View.CmeExtension.class, View.CmeDevice.class, View.CmeRouter.class})
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="extension_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private CmeExtension extension;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCfwdAllNumber() {
		return cfwdAllNumber;
	}

	public void setCfwdAllNumber(String cfwdAllNumber) {
		this.cfwdAllNumber = cfwdAllNumber;
	}

	public String getCfwdBusyNumber() {
		return cfwdBusyNumber;
	}

	public void setCfwdBusyNumber(String cfwdBusyNumber) {
		this.cfwdBusyNumber = cfwdBusyNumber;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getCfwdNoanNumber() {
		return cfwdNoanNumber;
	}

	public void setCfwdNoanNumber(String cfwdNoanNumber) {
		this.cfwdNoanNumber = cfwdNoanNumber;
	}

	public String getCfwdNoanTimeout() {
		return cfwdNoanTimeout;
	}

	public void setCfwdNoanTimeout(String cfwdNoanTimeout) {
		this.cfwdNoanTimeout = cfwdNoanTimeout;
	}

	public CmeExtension getExtension() {
		return extension;
	}

	public void setExtension(CmeExtension extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "CmeCallForward [id=" + id + ", cfwdAllNumber=" + cfwdAllNumber + ", cfwdBusyNumber=" + cfwdBusyNumber
				+ ", maxLength=" + maxLength + ", cfwdNoanNumber=" + cfwdNoanNumber + ", cfwdNoanTimeout="
				+ cfwdNoanTimeout + "]";
	}
}
