package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverHelper {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverHelper(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver findById(String id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }
}
