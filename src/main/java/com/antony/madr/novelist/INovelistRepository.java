package com.antony.madr.novelist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface INovelistRepository extends JpaRepository<NovelistEntity, Integer> {
    Optional<NovelistEntity> findByName(String name);

    Page<NovelistEntity> findByNameStartingWith(String name, Pageable pageable);


}

