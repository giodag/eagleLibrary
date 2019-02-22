package com.univaq.eaglelibraryapp.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Transcript entity.
 */
public class TranscriptDTO implements Serializable {

    private Long id;

    @Lob
    private String tei_format;

    private String status;


    private Long pageId;

    private String pageNumber;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTei_format() {
        return tei_format;
    }

    public void setTei_format(String tei_format) {
        this.tei_format = tei_format;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TranscriptDTO transcriptDTO = (TranscriptDTO) o;
        if (transcriptDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transcriptDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TranscriptDTO{" +
            "id=" + getId() +
            ", tei_format='" + getTei_format() + "'" +
            ", status='" + getStatus() + "'" +
            ", page=" + getPageId() +
            ", page='" + getPageNumber() + "'" +
            "}";
    }
}
