package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.enums.CmeRouterSyncStatus;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "cme_router", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(exclude={"location"})
@EqualsAndHashCode(exclude={"id","numberLocalized"})
public class CmeRouter implements Serializable {
	private static final long serialVersionUID = 1634935505364487243L;

	@JsonView({View.CmeRouter.class, View.CmeLocation.class})
	@Getter
	@Setter
	@Transient
    private int numberLocalized;
	
	@JsonView({View.RouterExportDetail.class, View.CmeRouterWithOutRelations.class, View.CmeRouter.class, View.CmeLocation.class})
	@Getter
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.RouterExportDetail.class, View.CmeRouterWithOutRelations.class, View.CmeRouter.class, View.CmeLocation.class})
	@Getter
	@Setter
	@Column(name = "name", length = 50, nullable = false)
	@NotNull 
	@NotEmpty
	private String name;
	
	@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@Column(name = "description")
	private String description;
	
	@JsonView({View.CmeRouter.class, View.CmeLocation.class})
	@Getter
	@Setter
	@Column(name = "ip_address", length = 50, nullable = false)
	@NotNull 
	@NotEmpty
	private String ipAddress;
	
	@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@Column(name = "username", length = 50, nullable = false)
	@NotNull 
	@NotEmpty
	private String username;
	
	@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@Column(name = "password", length = 50, nullable = false)
	@NotNull 
	@NotEmpty
	private String password;
	
	@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinColumn(name="cme_location_id")
    private CmeLocation location;
	
	@JsonView({View.CmeRouter.class})
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_global_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private CmeGlobal cmeGlobal = new CmeGlobal();
	
	@JsonView({View.CmeRouter.class})
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cme_sip_global_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private CmeSipGlobal cmeSipGlobal = new CmeSipGlobal();
	
	//@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="router", cascade=CascadeType.ALL)
	private Set<CmeDevice> sccpDevices = new HashSet<CmeDevice>(0);
	
	//@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="router", cascade=CascadeType.ALL)
    private Set<CmeSipDevice> sipDevices = new HashSet<CmeSipDevice>(0);
	
	//@JsonView(View.CmeRouter.class)
	@Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="router", cascade=CascadeType.ALL)
    private Set<CmeVoiceHuntGroup> voiceHuntGroups = new HashSet<CmeVoiceHuntGroup>(0);

	@Getter
	@Setter
	@JsonView({View.CmeRouter.class})
	private boolean sync = false;
	
	@JsonView({View.CmeRouter.class})
	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	private CmeRouterSyncStatus syncStatus = CmeRouterSyncStatus.NONSYNC;
	
	@JsonView({View.CmeRouter.class})
	@Getter
	@Setter
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastUpdateTime;
		
	public boolean isNew() {
		return this.id==null;
	}
}
