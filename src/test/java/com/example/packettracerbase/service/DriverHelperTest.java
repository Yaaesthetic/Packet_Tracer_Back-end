package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverHelperTest {

    private DriverHelper driverHelper;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverHelper = new DriverHelper(driverRepository);
    }

    @Test
    void testFindById_DriverExists() {
        // Arrange
        Driver mockDriver = new Driver();
        mockDriver.setCinDriver("D1");
        when(driverRepository.findById("D1")).thenReturn(Optional.of(mockDriver));

        // Act
        Driver result = driverHelper.findById("D1");

        // Assert
        assertNotNull(result);
        assertEquals("D1", result.getCinDriver());
        verify(driverRepository, times(1)).findById("D1");
    }

    @Test
    void testFindById_DriverNotFound() {
        // Arrange
        when(driverRepository.findById("D1")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            driverHelper.findById("D1");
        });
        assertEquals("Driver not found with id: D1", exception.getMessage());
    }
}
