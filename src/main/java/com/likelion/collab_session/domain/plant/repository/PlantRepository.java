package com.likelion.collab_session.domain.plant.repository;

import com.likelion.collab_session.domain.plant.entity.Plant;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlantRepository extends JpaRepository<Plant, Long> {

  @Query("select p from Plant p join fetch p.species where p.user.id = :userId order by p.createdAt asc")
  List<Plant> findAllWithSpeciesByUserId(@Param("userId") Long userId);
}
