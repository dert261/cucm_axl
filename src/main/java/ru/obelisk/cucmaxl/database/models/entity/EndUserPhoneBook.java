package ru.obelisk.cucmaxl.database.models.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonView;

import ru.obelisk.cucmaxl.database.models.views.View;

@Entity
@Table(name = "end_user_phone_book", catalog="adsync", schema="public")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class EndUserPhoneBook implements Serializable {
	private static final long serialVersionUID = 4627361682900573460L;

	@Id
	@JsonView(value={View.User.class, View.EndUserPhoneBook.class})
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@JsonView(value={View.User.class, View.EndUserPhoneBook.class})
	@Column(name = "upload_phone")
	private boolean uploadPhone = true;
	
	@JsonView(value={View.User.class, View.EndUserPhoneBook.class})
	@Column(name = "use_custom_phone")
	private boolean useCustomPhone = false;
	
	@JsonView(value={View.User.class, View.EndUserPhoneBook.class})
	@Column(name = "phone")
	private String phone = "";
	
	/*@JsonView(value={View.EndUserPhoneBook.class})
	@OneToOne(cascade=CascadeType.ALL)
	private EndUser endUser;*/
	
	@JsonView(value={View.EndUserPhoneBook.class})
	@OneToOne(cascade=CascadeType.ALL)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isUseCustomPhone() {
		return useCustomPhone;
	}

	public void setUseCustomPhone(boolean useCustomPhone) {
		this.useCustomPhone = useCustomPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*public EndUser getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}*/
	
	public boolean isUploadPhone() {
		return uploadPhone;
	}

	public void setUploadPhone(boolean uploadPhone) {
		this.uploadPhone = uploadPhone;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isNew() {
		return this.id==null;
	}
	

	@Override
	public String toString() {
		return "EndUserPhoneBook [id=" + id + ", uploadPhone=" + uploadPhone + ", useCustomPhone=" + useCustomPhone
				+ ", phone=" + phone + "]";
	}

	
}
