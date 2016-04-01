package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "partition_filter", catalog="adsync", schema="public")
@ToString(exclude={"numberLocalized"})
public class PartitionFilter implements Serializable {
 	private static final long serialVersionUID = 1771992995961268796L;

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
 	 	
    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name=null;
    
    @Getter
    @Setter
    @Column(name = "partitions", nullable = false)
    private String partitions=null;
    
    @Getter
    @Setter
    @Column(name = "description", nullable = false)
    private String description=null;
    
    public boolean isNew(){
    	return this.id==null;
    }
}
