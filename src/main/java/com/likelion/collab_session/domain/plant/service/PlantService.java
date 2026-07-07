package com.likelion.collab_session.domain.plant.service;

import com.likelion.collab_session.domain.plant.dto.request.CreatePlantReqDto;
import com.likelion.collab_session.domain.plant.dto.response.PlantCardResDto;
import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.domain.plant.repository.PlantRepository;
import com.likelion.collab_session.domain.species.entity.Species;
import com.likelion.collab_session.domain.species.repository.SpeciesRepository;
import com.likelion.collab_session.domain.user.entity.User;
import com.likelion.collab_session.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {

    private static final Long DEMO_USER_ID = 1L;

    private final PlantRepository plantRepository;
    private final SpeciesRepository speciesRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlantCardResDto createPlant(CreatePlantReqDto reqDto) {
        User user = userRepository.findById(DEMO_USER_ID)
                .orElseThrow(() -> new IllegalStateException("데모 사용자가 없습니다."));

        Species species = speciesRepository.findById(reqDto.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종입니다."));

        Plant plant = Plant.builder()
                .user(user)
                .species(species)
                .nickname(reqDto.getNickname())
                .wateringIntervalDays(reqDto.getWateringIntervalDays())
                .photoUrl(reqDto.getPhotoUrl())
                .build();

        return PlantCardResDto.ofNew(plantRepository.save(plant));
    }
}