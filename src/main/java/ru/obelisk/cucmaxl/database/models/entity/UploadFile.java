package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "upload_file", catalog="adsync", schema="public")
@EqualsAndHashCode(exclude={"id"})
@ToString
public class UploadFile implements Serializable {
	private static final long serialVersionUID = -1931496567325899131L;

	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Getter
	@Setter
	@Transient
	private int numberLocalized;
	
	@Getter
	@Setter
	@Column(name = "name")
	private String name;
	
	@Getter
	@Setter
	@Column(name = "description")
	private String description;
	
	@Getter
	@Setter
	@Column(name = "filename")
	private String filename;
	
	@Getter
	@Setter
	@Column(name = "filepath")
	private String filepath;
	
	@Getter
	@Setter
	@Transient
	private MultipartFile uploadedfile;
	
}
