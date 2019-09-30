package com.mycompany.myapp.domain;



import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RefCode.
 */
@Entity
@Table(name = "ref_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefCode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "langage")
    private String langage;

    @OneToMany(mappedBy = "refCode")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BuilderPlate> builderPlates = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangage() {
        return langage;
    }

    public RefCode langage(String langage) {
        this.langage = langage;
        return this;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    public Set<BuilderPlate> getBuilderPlates() {
        return builderPlates;
    }

    public RefCode builderPlates(Set<BuilderPlate> builderPlates) {
        this.builderPlates = builderPlates;
        return this;
    }

    public RefCode addBuilderPlate(BuilderPlate builderPlate) {
        this.builderPlates.add(builderPlate);
        builderPlate.setRefCode(this);
        return this;
    }

    public RefCode removeBuilderPlate(BuilderPlate builderPlate) {
        this.builderPlates.remove(builderPlate);
        builderPlate.setRefCode(null);
        return this;
    }

    public void setBuilderPlates(Set<BuilderPlate> builderPlates) {
        this.builderPlates = builderPlates;
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
        RefCode refCode = (RefCode) o;
        if (refCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefCode{" +
            "id=" + getId() +
            ", langage='" + getLangage() + "'" +
            "}";
    }
}
