package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Argumts.
 */
@Entity
@Table(name = "argumts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Argumts implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Argumts() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("argumts")
    private Fonctions fonctions;

    

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Argumts nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public Argumts type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Fonctions getFonctions() {
        return fonctions;
    }

    public Argumts fonctions(Fonctions fonctions) {
        this.fonctions = fonctions;
        return this;
    }

    public void setFonctions(Fonctions fonctions) {
        this.fonctions = fonctions;
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
        Argumts argumts = (Argumts) o;
        if (argumts.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), argumts.getId());
    }

    public Argumts(String nom, String type) {
		super();
		this.nom = nom;
		this.type = type;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Argumts{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
