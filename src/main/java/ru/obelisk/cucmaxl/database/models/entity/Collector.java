package ru.obelisk.cucmaxl.database.models.entity;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.enums.CollectorResourceSourceType;
import ru.obelisk.cucmaxl.database.models.entity.enums.CollectorType;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;
import ru.obelisk.cucmaxl.web.validator.NotNullRelationField;

@Entity
@Table(name = "collector", catalog="adsync", schema="public")
@ToString
public class Collector implements Serializable {
 	private static final long serialVersionUID = 1771992995961268796L;

 	@JsonView(value={View.Collector.class})
 	@Getter
    @Setter
 	@Id
 	@SequenceGenerator(sequenceName = "collector_id_seq", name = "CollectorIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CollectorIdSequence")
	@Column(name = "id", nullable = false)
    private Integer id;
    
 	@JsonView(value={View.Collector.class})
 	@Transient
    @Getter
    @Setter
    private int numberLocalized;
 	 	
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "description")
    private String description;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @NotNullRelationField
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name = "source_axl_port_id")
    private CucmAxlPort sourceAxlPort;

 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CollectorType type = CollectorType.FTP;
 	
 	@JsonView(value={View.Collector.class})
    @Getter	
    @Setter
    @Column(name = "resource_source_type")
    @Enumerated(EnumType.STRING)
    private CollectorResourceSourceType resourceSourceType = CollectorResourceSourceType.CDR;
 	
    
 	@JsonView(value={View.Collector.class})
    @Getter
	@Setter
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional = true, orphanRemoval=true)
   	@JoinColumn(name = "collector_ftp_config_id")
    private CollectorFtpConfig collectorFtpConfig;
    
    public boolean isNew(){
    	return this.id==null;
    }
}
