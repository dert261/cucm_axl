package ru.obelisk.cucmaxl.database.models.entity;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;

@Entity
@Table(name = "ftp_config", catalog="adsync", schema="public")
@ToString (exclude={"collector"})
public class CollectorFtpConfig implements Serializable {
 	private static final long serialVersionUID = 1771992995961268796L;

 	@JsonView(value={View.Collector.class})
 	@Getter
    @Setter
 	@Id
 	@SequenceGenerator(sequenceName = "ftp_config_id_seq", name = "FtpConfigIdSequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FtpConfigIdSequence")
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
    @Column(name = "host", nullable = false)
    private String host;
    
 	@JsonView(value={View.Collector.class})
 	@Getter
    @Setter
    @Column(name = "port", nullable = false)
    private int port = 21;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    private String username;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "password", nullable = false)
    private String password;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "directory", nullable = false)
    private String directory = "/";
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "recursive")
    private boolean recursive = false;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "consumer_delay")
    private long consumerDelay = 60000;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "include_pattern")
    private String includePattern;
    
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "exclude_pattern")
    private String excludePattern;
 	
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "threadpool_size")
    private int threadpoolSize = 1;
 	
 	@JsonView(value={View.Collector.class})
    @Getter
    @Setter
    @Column(name = "threadpool_max_size")
    private int threadpoolMaxSize = 2;
    
    @Getter
	@Setter
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional = true, orphanRemoval=true)
   	@JoinColumn(name = "collector_id")
    private Collector collector;
    
    public boolean isNew(){
    	return this.id==null;
    }
}
