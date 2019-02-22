package com.univaq.eaglelibraryapp.repository;

import com.univaq.eaglelibraryapp.domain.Form;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Form entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

}
