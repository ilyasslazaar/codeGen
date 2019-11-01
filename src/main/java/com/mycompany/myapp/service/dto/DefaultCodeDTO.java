package com.mycompany.myapp.service.dto;

import java.io.Serializable;

public class DefaultCodeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String boilerplate;
	
	private Long codeid;
	
    private Long refLangId;

	


	public Long getCodeid() {
		return codeid;
	}

	public void setCodeid(Long codeid) {
		this.codeid = codeid;
	}

	public Long getRefLangId() {
		return refLangId;
	}

	public void setRefLangId(Long refLangId) {
		this.refLangId = refLangId;
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

}
