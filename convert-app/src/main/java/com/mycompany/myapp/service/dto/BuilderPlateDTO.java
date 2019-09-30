package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BuilderPlate entity.
 */
public class BuilderPlateDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String defaultCode;


    private Long baseClassId;

    private Long refCodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    public Long getBaseClassId() {
        return baseClassId;
    }

    public void setBaseClassId(Long baseClassId) {
        this.baseClassId = baseClassId;
    }

    public Long getRefCodeId() {
        return refCodeId;
    }

    public void setRefCodeId(Long refCodeId) {
        this.refCodeId = refCodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuilderPlateDTO builderPlateDTO = (BuilderPlateDTO) o;
        if (builderPlateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), builderPlateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuilderPlateDTO{" +
            "id=" + getId() +
            ", defaultCode='" + getDefaultCode() + "'" +
            ", baseClass=" + getBaseClassId() +
            ", refCode=" + getRefCodeId() +
            "}";
    }
}
