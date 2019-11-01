package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class RefLanguageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	private String language;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RefLanguageDTO refLanguageDTO = (RefLanguageDTO) o;
		if (refLanguageDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), refLanguageDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "RefLanguageDTO{" + "id=" + getId() + ", language='" + getLanguage() + "'" + "}";
	}

}
