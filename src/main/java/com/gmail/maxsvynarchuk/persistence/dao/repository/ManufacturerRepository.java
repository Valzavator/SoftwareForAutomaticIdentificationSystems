package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.dao.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByManufacturerNameAndAddress(String manufacturerName, String address);

}
