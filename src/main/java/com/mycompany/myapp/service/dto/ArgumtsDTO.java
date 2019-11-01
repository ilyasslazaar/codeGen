package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Argumts entity.
 */
public class ArgumtsDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String nom;

    private String type;


    private Long fonctionsId;

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

    public Long getFonctionsId() {
        return fonctionsId;
    }

    public void setFonctionsId(Long fonctionsId) {
        this.fonctionsId = fonctionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArgumtsDTO argumtsDTO = (ArgumtsDTO) o;
        if (argumtsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), argumtsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArgumtsDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            ", fonctions=" + getFonctionsId() +
            "}";
    }
}
