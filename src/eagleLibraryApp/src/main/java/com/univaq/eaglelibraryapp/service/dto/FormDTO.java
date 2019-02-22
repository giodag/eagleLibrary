package com.univaq.eaglelibraryapp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Form entity.
 */
public class FormDTO implements Serializable {

    private Long id;

    private String code;

    private Integer year_of_the_study;

    private String comment;

    private LocalDate creation_date;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYear_of_the_study() {
        return year_of_the_study;
    }

    public void setYear_of_the_study(Integer year_of_the_study) {
        this.year_of_the_study = year_of_the_study;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormDTO formDTO = (FormDTO) o;
        if (formDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", year_of_the_study=" + getYear_of_the_study() +
            ", comment='" + getComment() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
