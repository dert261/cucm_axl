package ru.obelisk.cucmaxl.database.models.entity.jobs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.Job;
import ru.obelisk.cucmaxl.database.models.entity.UploadFile;

@Entity
@Table(name = "job_changenumber_detail", catalog="adsync")
@ToString(exclude={"job"})
public class ChangeNumberDetail implements Serializable {
 	private static final long serialVersionUID = 1771992995961268796L;

	@Getter
    @Setter
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
    private Integer id;
	
	@Getter
	@Setter
	@Column(name = "partition", length = 50, nullable = false)
	private String partition;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY)
	private UploadFile datasource;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY)
	private CucmAxlPort destination;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY)
	private Job job;
	
	public boolean isNew(){
    	return this.id==null;
    }
}
