package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * A Fonctions.
 */
@Entity
@Table(name = "fonctions")
public class Fonctions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "fonctions")
    private List<Argumts> arguments;

    public List<Argumts> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argumts> arguments) {
		this.arguments = arguments;
	}


    @ManyToOne
    @JsonIgnoreProperties("fonctions")
    private BaseClass baseClass;

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

    public Fonctions nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public Fonctions type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Fonctions addArgumts(Argumts argumts) {
        this.arguments.add(argumts);
        argumts.setFonctions(this);
        return this;
    }

    public Fonctions removeArgumts(Argumts argumts) {
        this.arguments.remove(argumts);
        argumts.setFonctions(null);
        return this;
    }

    public void setArgumts(List<Argumts> argumts) {
        this.arguments = argumts;
    }

    public BaseClass getBaseClass() {
        return baseClass;
    }

    public Fonctions baseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
        return this;
    }

    public void setBaseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fonctions)) {
            return false;
        }
        return id != null && id.equals(((Fonctions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fonctions{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
