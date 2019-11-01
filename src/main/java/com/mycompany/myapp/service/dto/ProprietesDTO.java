package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Proprietes entity.
 */
public class ProprietesDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String nom;

    private String type;


    private Long baseClassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBaseClassId() {
        return baseClassId;
    }

    public void setBaseClassId(Long baseClassId) {
        this.baseClassId = baseClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProprietesDTO proprietesDTO = (ProprietesDTO) o;
        if (proprietesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proprietesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProprietesDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            ", baseClass=" + getBaseClassId() +
            "}";
    }
}
