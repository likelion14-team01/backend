package com.likelion.collab_session.domain.plant.repository;

import com.likelion.collab_session.domain.plant.entity.Plant;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {

  @EntityGraph(attributePaths = "species")
  List<Plant> findAllByUserIdOrderByIdAsc(Long userId);
}
