package com.univaq.eaglelibraryapp.repository;

import com.univaq.eaglelibraryapp.domain.Transcript;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transcript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

}
