package com.univaq.eaglelibraryapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Form.
 */
@Entity
@Table(name = "form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Form implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "year_of_the_study")
    private Integer year_of_the_study;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "creation_date")
    private LocalDate creation_date;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Form code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYear_of_the_study() {
        return year_of_the_study;
    }

    public Form year_of_the_study(Integer year_of_the_study) {
        this.year_of_the_study = year_of_the_study;
        return this;
    }

    public void setYear_of_the_study(Integer year_of_the_study) {
        this.year_of_the_study = year_of_the_study;
    }

    public String getComment() {
        return comment;
    }

    public Form comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public Form creation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
        return this;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public User getUser() {
        return user;
    }

    public Form user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Form form = (Form) o;
        if (form.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), form.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Form{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", year_of_the_study=" + getYear_of_the_study() +
            ", comment='" + getComment() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            "}";
    }
}
