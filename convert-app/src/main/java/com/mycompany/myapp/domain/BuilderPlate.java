package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BuilderPlate.
 */
@Entity
@Table(name = "builder_plate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BuilderPlate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "default_code")
    private String defaultCode;

    @ManyToOne
    @JsonIgnoreProperties("builderPlates")
    private BaseClass baseClass;

    @ManyToOne
    @JsonIgnoreProperties("builderPlates")
    private RefCode refCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public BuilderPlate defaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
        return this;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    public BaseClass getBaseClass() {
        return baseClass;
    }

    public BuilderPlate baseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
        return this;
    }

    public void setBaseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
    }

    public RefCode getRefCode() {
        return refCode;
    }

    public BuilderPlate refCode(RefCode refCode) {
        this.refCode = refCode;
        return this;
    }

    public void setRefCode(RefCode refCode) {
        this.refCode = refCode;
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
        BuilderPlate builderPlate = (BuilderPlate) o;
        if (builderPlate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), builderPlate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuilderPlate{" +
            "id=" + getId() +
            ", defaultCode='" + getDefaultCode() + "'" +
            "}";
    }
}
