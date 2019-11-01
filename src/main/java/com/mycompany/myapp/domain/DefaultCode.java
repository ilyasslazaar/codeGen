package com.mycompany.myapp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "default_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DefaultCode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @Column(name = "boilerplate")
	    private String boilerplate;

	  @ManyToOne
	    private RefLanguage refLanguage;
	  
	  @Column(name = "code_id")
	    private Long codeid;
	  
	  
	  @NotNull
	    @Column(name = "is_delete" , nullable = false)
	    private Boolean isDelete = false;
	  
	  
	public Long getCodeid() {
		return codeid;
	}

	public void setCodeid(Long codeid) {
		this.codeid = codeid;
	}

	public RefLanguage getRefLanguage() {
		return refLanguage;
	}

	public void setRefLanguage(RefLanguage refLanguage) {
		this.refLanguage = refLanguage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBoilerplate() {
		return boilerplate;
	}

	public void setBoilerplate(String boilerplate) {
		this.boilerplate = boilerplate;
	}
    /**
	 * @return the isDelete
	 */
	public Boolean isIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	
	  
	  
}
