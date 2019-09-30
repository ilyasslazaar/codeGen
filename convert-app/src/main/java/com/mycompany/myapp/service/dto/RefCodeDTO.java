package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefCode entity.
 */
public class RefCodeDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String langage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangage() {
        return langage;
    }

    public void setLangage(String langage) {
        this.langage = langage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefCodeDTO refCodeDTO = (RefCodeDTO) o;
        if (refCodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refCodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefCodeDTO{" +
            "id=" + getId() +
            ", langage='" + getLangage() + "'" +
            "}";
    }
}
