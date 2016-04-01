package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "cme_url_service", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(exclude={"cmeGlobal"}, includeFieldNames=true)
@EqualsAndHashCode(exclude={"id"})
public class CmeUrlService implements Serializable{
	private static final long serialVersionUID = 8611104703152929147L;
	
	@JsonView({View.CmeUrlService.class, View.CmeRouter.class, View.CmeGlobal.class})
	@Getter
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeUrlService.class, View.CmeRouter.class, View.CmeGlobal.class})
	@Getter
	@Setter
	@Column(name = "url_link")
	private String urlLink;
	
	@JsonView({View.CmeUrlService.class, View.CmeRouter.class, View.CmeGlobal.class})
	@Getter
	@Setter
	@Column(name = "url_type")
	private String urlType;
	
	@JsonView({View.CmeUrlService.class, View.CmeRouter.class})
	@Getter
	@Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="global2url_service", catalog="adsync",
    	joinColumns=@JoinColumn(name="url_service_id"),
    	inverseJoinColumns=@JoinColumn(name="global_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmeGlobal> cmeGlobal;

}
