package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.entity.Country;
import com.gmail.maxsvynarchuk.persistence.dao.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Optional<Country> findByCode(String code) {
        return countryRepository.findByCode(code);
    }
}
