package com.mycompany.myapp.domain;



import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BaseClass.
 */
@Entity
@Table(name = "base_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseClass implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "imports")
    private String imports;

    @OneToMany(mappedBy = "baseClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fonctions> fonctions = new HashSet<>();
    @OneToMany(mappedBy = "baseClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Langages> langages = new HashSet<>();
    @OneToMany(mappedBy = "baseClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proprietes> proprietes = new HashSet<>();
    @OneToMany(mappedBy = "baseClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public BaseClass nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImports() {
        return imports;
    }

    public BaseClass imports(String imports) {
        this.imports = imports;
        return this;
    }

    public void setImports(String imports) {
        this.imports = imports;
    }

    public Set<Fonctions> getFonctions() {
        return fonctions;
    }

    public BaseClass fonctions(Set<Fonctions> fonctions) {
        this.fonctions = fonctions;
        return this;
    }

    public BaseClass addFonctions(Fonctions fonctions) {
        this.fonctions.add(fonctions);
        fonctions.setBaseClass(this);
        return this;
    }

    public BaseClass removeFonctions(Fonctions fonctions) {
        this.fonctions.remove(fonctions);
        fonctions.setBaseClass(null);
        return this;
    }

    public void setFonctions(Set<Fonctions> fonctions) {
        this.fonctions = fonctions;
    }

    public Set<Langages> getLangages() {
        return langages;
    }

    public BaseClass langages(Set<Langages> langages) {
        this.langages = langages;
        return this;
    }

    public BaseClass addLangages(Langages langages) {
        this.langages.add(langages);
        langages.setBaseClass(this);
        return this;
    }

    public BaseClass removeLangages(Langages langages) {
        this.langages.remove(langages);
        langages.setBaseClass(null);
        return this;
    }

    public void setLangages(Set<Langages> langages) {
        this.langages = langages;
    }

    public Set<Proprietes> getProprietes() {
        return proprietes;
    }

    public BaseClass proprietes(Set<Proprietes> proprietes) {
        this.proprietes = proprietes;
        return this;
    }

    public BaseClass addProprietes(Proprietes proprietes) {
        this.proprietes.add(proprietes);
        proprietes.setBaseClass(this);
        return this;
    }

    public BaseClass removeProprietes(Proprietes proprietes) {
        this.proprietes.remove(proprietes);
        proprietes.setBaseClass(null);
        return this;
    }

    public void setProprietes(Set<Proprietes> proprietes) {
        this.proprietes = proprietes;
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
        BaseClass baseClass = (BaseClass) o;
        if (baseClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseClass.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseClass{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", imports='" + getImports() + "'" +
            "}";
    }
}
