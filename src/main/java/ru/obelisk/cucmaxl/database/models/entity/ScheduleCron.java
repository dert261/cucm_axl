package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;
import ru.obelisk.cucmaxl.web.validator.NotEmpty;
import ru.obelisk.cucmaxl.web.validator.NotNullField;

@Entity
@Table(name = "schedule_cron", catalog="adsync", schema="public")
public class ScheduleCron implements Serializable {
	private static final long serialVersionUID = -8143008404515034945L;

	@Id
	@JsonView(value={View.ScheduleJob.class})
	@SequenceGenerator(sequenceName = "schedule_cron_id_seq", name = "ScheduleCronIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ScheduleCronIdSequence")
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "seconds")
	@NotNullField
    @NotEmpty
	private String seconds = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "minutes")
	@NotNullField
    @NotEmpty
	private String minutes = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "hours")
	@NotNullField
    @NotEmpty
	private String hours = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "day_of_month")
	@NotNullField
    @NotEmpty
	private String dayOfMonth = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "month")
	@NotNullField
    @NotEmpty
	private String month = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "day_of_week")
	@NotNullField
    @NotEmpty
	private String dayOfWeek = "*";
	
	@JsonView(value={View.ScheduleJob.class})
	@Column(name = "year")
	private String year = "*";
	
	@OneToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="schedule_job_id")
	private ScheduleJob scheduleJob;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public ScheduleJob getScheduleJob() {
		return scheduleJob;
	}
	public void setScheduleJob(ScheduleJob scheduleJob) {
		this.scheduleJob = scheduleJob;
	}
	@Override
	public String toString() {
		return "ScheduleCron [id=" + id + ", seconds=" + seconds + ", minutes=" + minutes + ", hours=" + hours
				+ ", dayOfMonth=" + dayOfMonth + ", month=" + month + ", dayOfWeek=" + dayOfWeek + ", year=" + year
				+ ", scheduleJob=" + scheduleJob + "]";
	}
}
