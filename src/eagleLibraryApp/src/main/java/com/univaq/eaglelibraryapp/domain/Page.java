package com.univaq.eaglelibraryapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Page.
 */
@Entity
@Table(name = "page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private Integer number;

    @Column(name = "chapter")
    private Integer chapter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Literary_work literary_work;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Page number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getChapter() {
        return chapter;
    }

    public Page chapter(Integer chapter) {
        this.chapter = chapter;
        return this;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Literary_work getLiterary_work() {
        return literary_work;
    }

    public Page literary_work(Literary_work literary_work) {
        this.literary_work = literary_work;
        return this;
    }

    public void setLiterary_work(Literary_work literary_work) {
        this.literary_work = literary_work;
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
        Page page = (Page) o;
        if (page.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), page.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Page{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", chapter=" + getChapter() +
            "}";
    }
}
