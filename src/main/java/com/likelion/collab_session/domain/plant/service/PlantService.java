package com.likelion.collab_session.domain.plant.service;

import com.likelion.collab_session.domain.plant.dto.request.CreatePlantReqDto;
import com.likelion.collab_session.domain.plant.dto.response.PlantCardResDto;
import com.likelion.collab_session.domain.plant.entity.Plant;
import com.likelion.collab_session.domain.plant.repository.PlantRepository;
import com.likelion.collab_session.domain.record.entity.GrowthTag;
import com.likelion.collab_session.domain.record.repository.RecordRepository;
import com.likelion.collab_session.domain.species.entity.Species;
import com.likelion.collab_session.domain.species.repository.SpeciesRepository;
import com.likelion.collab_session.domain.user.entity.User;
import com.likelion.collab_session.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.likelion.collab_session.domain.record.entity.Record;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {

    private static final Long DEMO_USER_ID = 1L;

    private final PlantRepository plantRepository;
    private final SpeciesRepository speciesRepository;
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

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

    public List<PlantCardResDto> getPlantCards() {
        List<Plant> plants = plantRepository.findAllWithSpeciesByUserId(DEMO_USER_ID);
        if (plants.isEmpty()) {
            return List.of();
        }

        List<Long> plantIds = plants.stream().map(Plant::getId).toList();
        Map<Long, List<Record>> recordsByPlant = recordRepository
                .findAllByPlantIdInOrderByRecordDateAsc(plantIds).stream()
                .collect(Collectors.groupingBy(r -> r.getPlant().getId()));

        LocalDate today = LocalDate.now();
        return plants.stream()
                .map(p -> toCard(p, recordsByPlant.getOrDefault(p.getId(), List.of()), today))
                .toList();
    }

    private PlantCardResDto toCard(Plant plant, List<Record> records, LocalDate today) {
        Species species = plant.getSpecies();
        LocalDate startDate = plant.getCreatedAt().toLocalDate();
        boolean dead = plant.isDead();

        LocalDate lastWateredDate = null;
        for (int i = records.size() - 1; i >= 0; i--) {
            if (records.get(i).isWatered()) {
                lastWateredDate = records.get(i).getRecordDate();
                break;
            }
        }

        Integer waterDday = null;
        if (!dead) {
            waterDday = (lastWateredDate == null)
                    ? 0
                    : (int) ChronoUnit.DAYS.between(today, lastWateredDate.plusDays(plant.getWateringIntervalDays()));
        }

        Set<LocalDate> recordDates = records.stream()
                .map(Record::getRecordDate)
                .collect(Collectors.toSet());
        List<Boolean> streak = new ArrayList<>(7);
        for (int i = 6; i >= 0; i--) {
            streak.add(recordDates.contains(today.minusDays(i)));
        }

        GrowthTag recentTag = null;
        LocalDate recentTagDate = null;
        for (int i = records.size() - 1; i >= 0; i--) {
            if (records.get(i).getGrowthTag() != null) {
                recentTag = records.get(i).getGrowthTag();
                recentTagDate = records.get(i).getRecordDate();
                break;
            }
        }

        Integer daysTogether = dead
                ? (int) ChronoUnit.DAYS.between(startDate, plant.getDiedAt().toLocalDate()) + 1
                : null;

        return PlantCardResDto.builder()
                .plantId(plant.getId())
                .nickname(plant.getNickname())
                .speciesName(species.getName())
                .category(species.getCategory())
                .photoUrl(plant.getPhotoUrl())
                .speciesImageUrl(species.getImageUrl())
                .wateringIntervalDays(plant.getWateringIntervalDays())
                .daysSinceStart((int) ChronoUnit.DAYS.between(startDate, today) + 1)
                .recordCount(records.size())
                .lastWateredDate(lastWateredDate)
                .waterDday(waterDday)
                .streak(streak)
                .recentTag(recentTag)
                .recentTagDate(recentTagDate)
                .dead(dead)
                .daysTogether(daysTogether)
                .build();
    }
}