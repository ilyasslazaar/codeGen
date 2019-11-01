package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ref_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefLanguage implements Serializable {

	private static final long serialVersionUID = 1L;
	

	  @Id
	//  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	  @Column(name = "language")
	    private String language;
	  

	    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getLanguage() {
	        return language;
	    }

	    public RefLanguage language(String language) {
	        this.language = language;
	        return this;
	    }

	    public void setLanguage(String language) {
	        this.language = language;
	    }
	    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        RefLanguage refLanguage = (RefLanguage) o;
	        if (refLanguage.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), refLanguage.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

	    @Override
	    public String toString() {
	        return "RefLanguage{" +
	            "id=" + getId() +
	            ", language='" + getLanguage() + "'" +
	            "}";
	    }

}
