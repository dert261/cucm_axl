package ru.obelisk.cucmaxl.database.models.entity.cme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "cme_location", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmeLocation implements Serializable {
	private static final long serialVersionUID = 368289999948162835L;
	
	@JsonView({View.CmeLocation.class, View.CmeRouter.class})
	@Transient
    private int numberLocalized;
	
	@JsonView({View.CmeLocation.class, View.CmeRouter.class})
	@Id
	@SequenceGenerator(sequenceName = "cme_location_id_seq", name = "CmeLocationIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmeLocationIdSequence")
    @Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView({View.CmeLocation.class, View.CmeRouter.class})
	@Column(name = "name", length = 50, nullable = false)
	@NotNull 
	@NotEmpty
	private String name;
	
	@JsonView({View.CmeLocation.class, View.CmeRouter.class})
	@Column(name = "description")
	private String description;
	
	@JsonView(View.CmeLocation.class)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JoinColumn(name="cme_location_id")
	private List<CmeRouter> routers = new ArrayList<CmeRouter>(0);
		
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CmeRouter> getRouters() {
		return routers;
	}
	public void setRouters(List<CmeRouter> routers) {
		this.routers = routers;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isNew(){
		return this.id==null;
	}
	
	@Override
	public String toString() {
		return "CmeLocation [numberLocalized=" + numberLocalized + ", id=" + id + ", name=" + name + ", description="
				+ description + ", routers=" + routers + "]";
	}
}
