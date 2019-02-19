package com.univaq.eaglelibraryapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "date_of_birth")
    private LocalDate date_of_birth;

    @Column(name = "address")
    private String address;

    @Column(name = "matriculation_number")
    private String matriculation_number;

    @Column(name = "degree_course")
    private String degree_course;

    @Column(name = "email")
    private String email;
    
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

    public String getName() {
        return name;
    }

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Profile last_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public Profile date_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
        return this;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public Profile address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMatriculation_number() {
        return matriculation_number;
    }

    public Profile matriculation_number(String matriculation_number) {
        this.matriculation_number = matriculation_number;
        return this;
    }

    public void setMatriculation_number(String matriculation_number) {
        this.matriculation_number = matriculation_number;
    }

    public String getDegree_course() {
        return degree_course;
    }

    public Profile degree_course(String degree_course) {
        this.degree_course = degree_course;
        return this;
    }

    public void setDegree_course(String degree_course) {
        this.degree_course = degree_course;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
    
    public User getUser() {
		return user;
	}
    
    public Profile user(User user) {
    	this.user = user;
    	return this;
    }

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", address='" + getAddress() + "'" +
            ", matriculation_number='" + getMatriculation_number() + "'" +
            ", degree_course='" + getDegree_course() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
