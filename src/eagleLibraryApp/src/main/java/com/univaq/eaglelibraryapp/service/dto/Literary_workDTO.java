package com.univaq.eaglelibraryapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Literary_work entity.
 */
public class Literary_workDTO implements Serializable {

    private Long id;

    private String title;

    private String author;

    private Integer year;

    private String category;

    private Integer number_of_pages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(Integer number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Literary_workDTO literary_workDTO = (Literary_workDTO) o;
        if (literary_workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), literary_workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Literary_workDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", year=" + getYear() +
            ", category='" + getCategory() + "'" +
            ", number_of_pages=" + getNumber_of_pages() +
            "}";
    }
}
