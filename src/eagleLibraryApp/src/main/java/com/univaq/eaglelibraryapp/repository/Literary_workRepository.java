package com.univaq.eaglelibraryapp.repository;

import com.univaq.eaglelibraryapp.domain.Literary_work;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Literary_work entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Literary_workRepository extends JpaRepository<Literary_work, Long> {

}
