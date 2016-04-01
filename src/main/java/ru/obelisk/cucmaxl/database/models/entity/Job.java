package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.enums.JobFunction;
import ru.obelisk.cucmaxl.database.models.entity.enums.JobStatus;
import ru.obelisk.cucmaxl.database.models.entity.jobs.ChangeNumberDetail;
import ru.obelisk.cucmaxl.database.models.entity.utils.CustomLocalDateTimeDeserializer;
import ru.obelisk.cucmaxl.database.models.entity.utils.CustomLocalDateTimeSerializer;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "jobs", catalog="adsync", schema="public")
@ToString
public class Job implements Serializable {
 	private static final long serialVersionUID = 1771992995961268796L;

 	@JsonView(value={View.Job.class})
	@Getter
    @Setter
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
 	@JsonView(value={View.Job.class})
    @Transient
    @Getter
    @Setter
    private int numberLocalized;
 	 	
    @Transient
    @Getter
    @Setter
    private boolean chancel;
    
 	@JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "name", length = 50, nullable = false)
    private String name=null;
    
 	@JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "description")
    private String description;
    
 	@JsonView(value={View.Job.class})
    @Getter
    @Setter
    @OneToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name = "user_id")
   	private User user = null;
    
 	@Getter
    @Setter
    @OneToOne(fetch=FetchType.LAZY, optional = true)
   	@JoinColumn(name = "uploadfile_id")
   	private UploadFile uploadFile = null;
    
    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional = true, orphanRemoval=true)
   	@JoinColumn(name = "changeNumberDetail_id")
   	private ChangeNumberDetail changeNumberDetail = null;

    @JsonView(value={View.Job.class})
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Getter
    @Setter
    @Column(name = "create_date")
	private LocalDateTime createDate;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.HOLD;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "job_function", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobFunction jobFunction=null;
    
    @JsonView(value={View.Job.class})
    @Getter
	@Setter
	@OneToMany(fetch=FetchType.LAZY, mappedBy="job", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<JobResult> results = new ArrayList<JobResult>(0);
    
    public boolean isNew(){
    	return this.id==null;
    }
}
