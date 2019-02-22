package com.univaq.eaglelibraryapp.repository;

import com.univaq.eaglelibraryapp.domain.Transcript;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Transcript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

    @Query(value = "select distinct transcript from Transcript transcript left join fetch transcript.users",
        countQuery = "select count(distinct transcript) from Transcript transcript")
    Page<Transcript> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct transcript from Transcript transcript left join fetch transcript.users")
    List<Transcript> findAllWithEagerRelationships();

    @Query("select transcript from Transcript transcript left join fetch transcript.users where transcript.id =:id")
    Optional<Transcript> findOneWithEagerRelationships(@Param("id") Long id);

}
