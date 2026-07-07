package com.likelion.collab_session.domain.species.repository;

import com.likelion.collab_session.domain.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
}
