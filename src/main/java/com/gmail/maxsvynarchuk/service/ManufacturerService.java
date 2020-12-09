package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.entity.Manufacturer;
import com.gmail.maxsvynarchuk.persistence.dao.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public Manufacturer getOrCreate(String name, String address) {
        Optional<Manufacturer> manufacturerOpt =
                manufacturerRepository.findByManufacturerNameAndAddress(name, address);
        if (manufacturerOpt.isPresent()) {
            return manufacturerOpt.get();
        }
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setManufacturerName(name);
        newManufacturer.setAddress(address);
        return manufacturerRepository.save(newManufacturer);
    }
}
