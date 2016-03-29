package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.utils.CustomLocalDateTimeDeserializer;
import ru.obelisk.cucmaxl.database.models.entity.utils.CustomLocalDateTimeSerializer;
import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "jobs_result", catalog="adsync")
@ToString(exclude={"job"})
public class JobResult implements Serializable {
 	private static final long serialVersionUID = -8250213484020612335L;

 	@JsonView(value={View.Job.class})
	@Getter
    @Setter
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
    private Integer id;
     
    @Transient
    @Getter
    @Setter
    private int numberLocalized;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "logfilename")
    private String logfilename;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "logfilepath")
    private String logfilepath;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "numberrecords_total")
    private int numberRecordsTotal;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "numberrecords_processed")
    private int numberRecordsProcessed;
    
    @JsonView(value={View.Job.class})
    @Getter
    @Setter
    @Column(name = "numberrecords_failed")
    private int numberRecordsFailed;
    
    @JsonView(value={View.Job.class})
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Getter
    @Setter
    @Column(name = "launch_date")
    private LocalDateTime launchDate;
    
    @Getter
    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name = "job_id")
   	private Job job;
    
    public boolean isNew(){
    	return this.id==null;
    }
}
