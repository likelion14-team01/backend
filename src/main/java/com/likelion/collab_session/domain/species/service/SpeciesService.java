package com.likelion.collab_session.domain.species.service;

import com.likelion.collab_session.domain.species.dto.SpeciesResDto;
import com.likelion.collab_session.domain.species.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public List<SpeciesResDto> getSpeciesList() {
        return speciesRepository.findAll().stream()
                .map(SpeciesResDto::from)
                .toList();
    }
}
