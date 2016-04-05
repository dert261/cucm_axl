package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_custom_sip_extension", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(exclude={"cmeSipExtension"})
public class CmeCustomSipExtension implements Serializable {
	private static final long serialVersionUID = 1436518227169422675L;

	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Id
	@SequenceGenerator(sequenceName = "cme_custom_sip_extension_id_seq", name = "CmeCustomSipExtensionIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeCustomSipExtensionIdSequence")
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Column(name="number")
    private String number;
	
	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Column(name="label")
    private String label;
	
	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Column(name="enable")
    private boolean enable = true;
	
	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Column(name="external_phone_number_mask")
    private String externalPhoneNumberMask;
	
	@JsonView({View.CmeCustomSipExtension.class, View.CmeSipExtension.class, View.CmeSipDevice.class})
	@Getter
	@Setter
	@Column(name="pickup_group")
    private String pickupGroup;
	
	@JsonView(View.CmeCustomSipExtension.class)
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_sip_extension_id")
	private CmeSipExtension cmeSipExtension;
	
	public boolean isNew() {
		return this.id==null;
	}
	
	
}
