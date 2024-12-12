package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.DriverDTO;
import com.example.packettracerbase.dto.DriverDTOMobile;
import com.example.packettracerbase.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> getAllDrivers();
    Optional<Driver> getDriverById(String id);
    Driver createDriver(Driver driver);
    Driver updateDriver(String id, Driver driverDetails);
    void deleteDriver(String id);
    public DriverDTOMobile convertToDriverDTOMoblie(String idMobileDriver);

    DriverDTO addDriver(DriverDTO driverDTO);
}
