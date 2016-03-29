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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_custom_extension", catalog="adsync")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(exclude={"cmeExtension"})
public class CmeCustomExtension implements Serializable {
	private static final long serialVersionUID = 1436518227169422675L;

	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="number")
    private String number;
	
	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="label")
    private String label;
	
	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="enable")
    private boolean enable = true;
	
	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="external_phone_number_mask")
    private String externalPhoneNumberMask;
	
	@JsonView({View.CmeCustomExtension.class, View.CmeExtension.class, View.CmeDevice.class})
	@Getter
	@Setter
	@Column(name="pickup_group")
    private String pickupGroup;
	
	@JsonView(View.CmeCustomExtension.class)
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_extension_id")
	private CmeExtension cmeExtension;
	
	public boolean isNew() {
		return this.id==null;
	}
	
	
}
