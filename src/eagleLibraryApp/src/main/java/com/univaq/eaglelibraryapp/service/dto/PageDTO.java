package com.univaq.eaglelibraryapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Page entity.
 */
public class PageDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    private Integer chapter;

    private Long literary_workId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Long getLiterary_workId() {
        return literary_workId;
    }

    public void setLiterary_workId(Long literary_workId) {
        this.literary_workId = literary_workId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PageDTO pageDTO = (PageDTO) o;
        if (pageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", chapter=" + getChapter() +
            ", literary_work=" + getLiterary_workId() +
            "}";
    }
}
