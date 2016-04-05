package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.User;
import ru.obelisk.cucmaxl.database.models.entity.enums.CmeUserType;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_custom_device", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(exclude={"cmeDevice"})
public class CmeCustomDevice implements Serializable {
	private static final long serialVersionUID = 1436518227169422675L;

	@JsonView({View.CmeCustomDevice.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Id
	@SequenceGenerator(sequenceName = "cme_custom_device_id_seq", name = "CmeCustomDeviceIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeCustomDeviceIdSequence")
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@JsonView({View.CmeCustomDevice.class, View.CmeDevice.class})
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;
	
	@JsonView({View.CmeCustomDevice.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="user_type")
	@Enumerated(EnumType.STRING)
    private CmeUserType userType = CmeUserType.ANONYMOUS;
	
	@JsonView({View.CmeCustomDevice.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="enable")
	private boolean enable = true;
		
	@JsonView(View.CmeCustomDevice.class)
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_device_id")
	private CmeDevice cmeDevice;
	
	public boolean isNew() {
		return this.id==null;
	}

	
}
