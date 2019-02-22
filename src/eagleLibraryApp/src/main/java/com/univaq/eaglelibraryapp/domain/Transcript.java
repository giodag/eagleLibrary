package com.univaq.eaglelibraryapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Transcript.
 */
@Entity
@Table(name = "transcript")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transcript implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    @Lob
    @Column(name = "tei_format")
    private String tei_format;

    @Column(name = "status")
    private String status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Page page;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_trans_link",
               joinColumns = @JoinColumn(name = "transcript_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTei_format() {
        return tei_format;
    }

    public Transcript tei_format(String tei_format) {
        this.tei_format = tei_format;
        return this;
    }

    public void setTei_format(String tei_format) {
        this.tei_format = tei_format;
    }

    public String getStatus() {
        return status;
    }

    public Transcript status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Page getPage() {
        return page;
    }

    public Transcript page(Page page) {
        this.page = page;
        return this;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Transcript users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Transcript addUser(User user) {
        this.users.add(user);
        //user.getTranscripts().add(this);
        return this;
    }

    public Transcript removeUser(User user) {
        this.users.remove(user);
        //user.getTranscripts().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
        Transcript transcript = (Transcript) o;
        if (transcript.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transcript.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transcript{" +
            "id=" + getId() +
            ", tei_format='" + getTei_format() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
